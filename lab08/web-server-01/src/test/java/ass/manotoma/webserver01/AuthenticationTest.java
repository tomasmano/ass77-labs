package ass.manotoma.webserver01;

import ass.manotoma.webserver01.security.BasicAuthenticationManager;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import static org.junit.Assert.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class AuthenticationTest {

    @Test
    public void test_succesful_authentication() {
        //given
        AuthenticationManager am = new BasicAuthenticationManager();
        String name = "tomy";
        String password = "pass";
        
        //when
        Authentication request = new UsernamePasswordAuthenticationToken(name, password);
        Authentication auth = am.authenticate(request);
        
        //then
        User user = (User) auth.getPrincipal();
        System.out.println(">>>>> "+auth);
        assertEquals("tomy", auth.getName());
        assertEquals("pass", auth.getCredentials());
        assertEquals("tomy", user.getUsername());
    }

    @Test(expected=BadCredentialsException.class)
    public void test_unsuccesful_authentication_when_bad_password() {
        //given
        AuthenticationManager am = new BasicAuthenticationManager();
        String name = "tomy";
        String password = "baaaaaaaaaad";
        
        //when
        Authentication request = new UsernamePasswordAuthenticationToken(name, password);
        Authentication auth = am.authenticate(request);
        
        //then
        // expected BadCredentialsException
    }
 
    @Test(expected=BadCredentialsException.class)
    public void test_unsuccesful_authentication_when_user_not_exist() {
        //given
        AuthenticationManager am = new BasicAuthenticationManager();
        String name = "whaaaaaaaat";
        String password = "baaaaaaaaaad";
        
        //when
        Authentication request = new UsernamePasswordAuthenticationToken(name, password);
        Authentication auth = am.authenticate(request);
        
        //then
        // expected BadCredentialsException
    }
}
