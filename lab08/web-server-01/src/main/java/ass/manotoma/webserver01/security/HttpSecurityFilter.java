package ass.manotoma.webserver01.security;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.exception.UnauthorizedException;
import ass.manotoma.webserver01.server.processor.PreProcessor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Handle authentication if required.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpSecurityFilter implements PreProcessor<HttpRequest> {
    
    public static final Logger LOG = LoggerFactory.getLogger(HttpSecurityFilter.class);

    private static HttpSecurityFilter INSTANCE = new HttpSecurityFilter();
    private SecurityInterceptor interceptor = PropertiesSecurityInterceptor.getInstance();
    private AuthenticationManager am = new BasicAuthenticationManager();

    private HttpSecurityFilter() {
        // prevent instantiation
    }

    public static HttpSecurityFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public HttpRequest preProcess(HttpRequest req) {
        if (interceptor.isApplied(req.getTarget().getName())) {
            req.setSecuredTarget(true);

            String hValue = req.getHeaders().get(HttpRequest.Header.AUTHORIZATION.getFormated());
            if (hValue == null) {
                LOG.debug("Request not authorized [missing authorization header].");
                req.setIsAuthenticated(false);
                throw new UnauthorizedException(req);
            }
            String[] credentials = convertToCredentials(hValue);

            Authentication request = new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
            Authentication auth;
            try {
                auth = am.authenticate(request);
            } catch (AuthenticationException ex) {
                LOG.debug("Client not authorized: {}", ex);
                throw new UnauthorizedException(req);
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            req.setIsAuthenticated(auth.isAuthenticated());
            return req;
        }
        return req;
    }

    /**
     * Retrieve credentials from value of the given Authentication Header.
     * 
     * @param hValue authentication header value
     * @return credentials (username & password)
     */
    private String[] convertToCredentials(String hValue) {
        String encoded = hValue.split(" ")[1];
        byte[] decodedBytes = Base64.decodeBase64(encoded.getBytes());
        String decoded = StringUtils.newStringUtf8(decodedBytes);
        return decoded.split(":");
    }
}
