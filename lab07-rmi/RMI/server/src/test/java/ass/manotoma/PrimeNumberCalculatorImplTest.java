package ass.manotoma;

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
public class PrimeNumberCalculatorImplTest {

    public PrimeNumberCalculatorImplTest() {
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

    /**
     * Test of computePrimes method, of class PrimeNumberCalculatorImpl.
     */
    @Test
    public void testComputePrimes() throws Exception {
        int max = 10;
        PrimeNumberCalculatorImpl instance = new PrimeNumberCalculatorImpl();
        int[] expResult = {1, 3, 5, 7};
        int[] result = instance.computePrimes(max);
        assertArrayEquals(expResult, result);
    }
}
