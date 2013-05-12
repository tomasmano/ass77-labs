package ass.manotoma.webserver01;

import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.HttpResponsePool;
import ass.manotoma.webserver01.http.HttpResponseSuccess;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponsePoolTest {
    
    @Test
    public void test_supplying_instances_from_pool() {
        //given
        File target = new File("test.html");
        HttpResponse res1 = HttpResponsePool.getRespose(target);
        
        //when
        HttpResponse res2 = HttpResponsePool.getRespose(target);
        HttpResponse res3 = HttpResponsePool.getRespose(target);
        HttpResponse res4 = new HttpResponseSuccess(target);
        
        //then
        assertTrue(res1 == res2);
        assertTrue(res1 == res3);
        assertTrue(res1 != res4);
    }

    
}
