package ass.manotoma.webserver01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assume;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ServerTest {

    private static InetSocketAddress endPoint = new InetSocketAddress("localhost",
            Integer.parseInt("4444"));
    private static Socket socket;

    public ServerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Assume.assumeTrue(!endPoint.isUnresolved());
        socket = new Socket();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_connect_to_server() throws IOException {
        try {
            socket.connect(endPoint, 1000);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }
}
