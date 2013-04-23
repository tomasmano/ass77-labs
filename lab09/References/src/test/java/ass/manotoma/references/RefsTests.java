package ass.manotoma.references;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class RefsTests {

    public RefsTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void soft_reference_not_cleaned_when_enough_memory_test() {
        // given
        String s = "Test String";

        //when
        SoftReference<String> sr = new SoftReference<String>(s);
        s = null;
        Runtime.getRuntime().gc();

        //then
        assertNull(s);
        assertNotNull(sr);
        assertNotNull("Soft refernce doesn't contain strong refernce", sr.get());
        assertEquals("Test String", sr.get());
    }

    @Test
    public void soft_reference_cleaned_when_full_memory_test() throws Exception{
        System.out.format("Total memory start: [%s mb]%n", Runtime.getRuntime().totalMemory() / 1048576);
        System.out.format("Free memory start: [%s mb]%n", Runtime.getRuntime().freeMemory() / 1048576);
        
        // given
        byte[] placeholder1 = new byte[1024 * 1024 * 540];  // 540 mb

        //when
        SoftReference<byte[]> sr = new SoftReference<byte[]>(placeholder1);
        placeholder1 = null;

        byte[] placeholder2 = new byte[1024 * 1024 * 50];  // 50 mb
        
        System.out.format("Total memory before gc: [%s mb]%n", Runtime.getRuntime().totalMemory() / 1048576);
        System.out.format("Free memory before gc: [%s mb]%n", Runtime.getRuntime().freeMemory() / 1048576);
        
        Runtime.getRuntime().gc();
        
        Thread.sleep(3000);
        
        System.out.format("Total memory after gc: [%s mb]%n", Runtime.getRuntime().totalMemory() / 1048576);
        System.out.format("Free memory after gc: [%s mb]%n", Runtime.getRuntime().freeMemory() / 1048576);

        //then
        assertNull(placeholder1);
        assertNotNull(sr);
        assertNull("Soft refernce contain strong refernce when gc called and java doesn't have enough memory.", sr.get());
    }

    @Test
    public void weak_reference_always_cleaned_when_gc_test() throws Exception {
        // given
        List list = new ArrayList(1000);

        //when
        WeakReference<List> wr = new WeakReference<List>(list);
        list = null;
        Runtime.getRuntime().gc();

        //then
        assertNull(list);
        assertNotNull(wr);
        assertNull("Weak refernce contain strong refernce when gc called.", wr.get());
    }
}
