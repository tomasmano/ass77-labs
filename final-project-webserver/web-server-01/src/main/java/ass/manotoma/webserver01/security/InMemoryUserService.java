package ass.manotoma.webserver01.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class InMemoryUserService implements UserDetailsService{
    
    public static final Logger LOG = LoggerFactory.getLogger(InMemoryUserService.class);
    
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    private static Map<String, UserDetails> map = new HashMap<String, UserDetails>();
    
    static{
        map.put("tomy", new User("tomy", "pass", AUTHORITIES));
    }

    public InMemoryUserService() {
        LOG.info("Using {} as UserDetailsService..", this.getClass().getSimpleName());
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details = map.get(username);
        if (details == null) {
            throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
        }
        return details;
    }
    
}
