
import ass.student.manotoma.INode;
import ass.student.manotoma.INodeFactory;
import java.io.File;
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
public class TestRetrievingSize {

    private INode root;

    public TestRetrievingSize() {
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
        root = null;
    }

    @Test
    public void test_file_size() {
        root = INodeFactory.newInstance(new File("src/test/resources/test-file"));
        assertEquals(new Long(274L), new Long(root.size()));
    }

    @Test
    public void test_dir_size() {
        root = INodeFactory.newInstance(new File("src/test/resources/test-dirs"));
        assertEquals(new Long(1240L), new Long(root.size()));
    }
}
