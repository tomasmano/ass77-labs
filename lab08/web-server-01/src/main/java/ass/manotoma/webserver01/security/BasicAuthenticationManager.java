package ass.manotoma.webserver01.security;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class BasicAuthenticationManager implements AuthenticationManager {
    
    public static final Logger LOG = LoggerFactory.getLogger(BasicAuthenticationManager.class);
    
    private UserDetailsService userService = new InMemoryUserService();

    public BasicAuthenticationManager() {
        LOG.info("Using {} as Authentication Manager..", this.getClass().getSimpleName());
    }
    

    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        UserDetails details;
        try {
            details = userService.loadUserByUsername(auth.getName());
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Bad Credentials");
        }
        if (auth.getCredentials().toString().equals(details.getPassword())) {
            LOG.debug("Authentication succesfull for user [{}]", auth.getName());
            return new UsernamePasswordAuthenticationToken(details,
                    auth.getCredentials(), details.getAuthorities());
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
