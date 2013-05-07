package ass.manotoma.webserver01;

import ass.manotoma.webserver01.server.ContentFinder;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ContentFinderTest {
    
    @Test()
    public void test_not_found() {
        //given
        String notExistFile = "/asdf";
        
        //when
        File exp = ContentFinder.find(notExistFile);
        
        //then
        assertFalse(exp.exists());
        assertEquals("asdf", exp.getName());
    }

    @Test
    public void test_file_found() {
        //given
        String existing = "/pom.xml";
        //when
        File exp = ContentFinder.find(existing);
        
        //then
        assertTrue(exp.exists());
        assertEquals("pom.xml", exp.getName());
    }

    
}
