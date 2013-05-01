package ass.manotoma.webserver01.security;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.server.processor.PreProcessor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class SecurityFilter implements PreProcessor<HttpRequest> {

    private static SecurityFilter INSTANCE = new SecurityFilter();
    private SecurityInterceptor interceptor = PropertiesSecurityInterceptor.getInstance();
    private AuthenticationManager am = new BasicAuthenticationManager();

    private SecurityFilter() {
        // prevent instantiation
    }

    public static SecurityFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public HttpRequest preProcess(HttpRequest req) {
        if (interceptor.isApplied(req.getTarget().getName())) {
            req.setSecuredTarget(true);

            String hValue = req.getHeaders().get(HttpRequest.Header.AUTHORIZATION.getFormated());
            if (hValue == null) {
                req.setIsAuthenticated(false);
                return req;
            }
            String[] credentials = convertToCredentials(hValue);

            Authentication request = new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
            Authentication auth = am.authenticate(request);
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
