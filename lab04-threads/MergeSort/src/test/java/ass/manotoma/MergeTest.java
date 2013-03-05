package ass.manotoma;

import java.util.Arrays;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class MergeTest {

    private double[] numbs;
    private final static int SIZE = 100;
    private final static int MAX = 500;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        numbs = new double[SIZE];
        Random generator = new Random();
        for (int i = 0; i < numbs.length; i++) {
            numbs[i] = generator.nextInt(MAX);
        }
    }

    @Test
    public void test_sorting_plain() throws Exception {
        Sorter sorter = new MergeSorter();
        double[] compared = Arrays.copyOf(numbs, numbs.length);
        sorter.sort(numbs);
        Arrays.sort(compared);
        assertArrayEquals("Your sorted array is not equal with the same array, that was sorted by standard Java sort", numbs, compared, DELTA);
    }
    
    @Test
    public void compare_sorting_plain() throws Exception {
        Sorter sorter = new MergeSorter();
        double[] compared = Arrays.copyOf(numbs, numbs.length);
        long start = System.currentTimeMillis();
        sorter.sort(numbs);
        long end = System.currentTimeMillis();
        long diff = end - start;
        
        MergeSorter s2 = new MergeSorter();
        double[] numbs2 =  Arrays.copyOf(numbs, numbs.length);
        long start2 = System.currentTimeMillis();
        s2.sortSynchronous(numbs2);
        long end2 = System.currentTimeMillis();
        long diff2 = end2 - start2;
        System.out.println("diff1: "+diff);
        System.out.println("diff2: "+diff2);
        Arrays.sort(compared);
        assertArrayEquals("Your sorted array is not equal with the same array, that was sorted by standard Java sort", numbs, compared, DELTA);
    }

    @Test
    public void testMergeSort() throws Exception {
        Sorter sorter = new MergeSorter();
        sorter.sort(numbs);
        for (int i = 0; i < numbs.length - 1; i++) {
            if (numbs[i] > numbs[i + 1]) {
                fail("Should not happen");
            }
        }
        assertTrue(true);

    }

    @Test
    public void itWorksRepeatably() throws Exception {
        for (int i = 0; i < 200; i++) {
            numbs = new double[SIZE];
            Random generator = new Random();
            for (int a = 0; a < numbs.length; a++) {
                numbs[a] = generator.nextInt(MAX);
            }
            Sorter sorter = new MergeSorter();
            sorter.sort(numbs);
            for (int j = 0; j < numbs.length - 1; j++) {
                if (numbs[j] > numbs[j + 1]) {
                    fail("Should not happen");
                }
            }
            assertTrue(true);
        }
    }

    @Test
    public void testStandardSort() {
        long start = System.currentTimeMillis();
        Arrays.sort(numbs);
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("Standard Java sort " + diff);

        for (int i = 0; i < numbs.length - 1; i++) {
            if (numbs[i] > numbs[i + 1]) {
                fail("Should not happen");
            }
        }
        assertTrue(true);
    }
}
