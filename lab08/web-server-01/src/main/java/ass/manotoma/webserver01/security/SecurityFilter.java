package ass.manotoma.webserver01.security;

import ass.manotoma.webserver01.http.HttpRequest;
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
public class SecurityFilter {

    private static SecurityFilter INSTANCE = new SecurityFilter();
    private SecurityInterceptor interceptor = PropertiesSecurityInterceptor.getInstance();
    private AuthenticationManager am = new BasicAuthenticationManager();

    private SecurityFilter() {
        // prevent instantiation
    }

    public static SecurityFilter getInstance() {
        return INSTANCE;
    }

    public HttpRequest filter(HttpRequest req) {
        if (interceptor.isApplied(req.getTarget().getName())) {
            req.setSecuredTarget(true);

            String authHeader = req.getHeaders().get(HttpRequest.Header.AUTHORIZATION.getFormated());
            if (authHeader == null) {
                req.setIsAuthenticated(false);
                return req;
            }
            String[] credentials = convertToCredentials(authHeader);

            Authentication request = new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
            Authentication auth = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            req.setIsAuthenticated(auth.isAuthenticated());
            return req;
        }
        return req;
    }

    public String[] convertToCredentials(String authHeader) {
        String encoded = authHeader.split(" ")[1];
        byte[] decodedBytes = Base64.decodeBase64(encoded.getBytes());
        String decoded = StringUtils.newStringUtf8(decodedBytes);
        return decoded.split(":");
    }
}
