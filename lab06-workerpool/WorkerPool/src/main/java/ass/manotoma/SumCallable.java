package ass.manotoma;

import java.util.concurrent.Callable;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class SumCallable implements Callable<Integer> {

    private int x;
    private int y;

    public SumCallable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer call() throws Exception {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Args cannot be less than zero");
        }
        int sum = x + y;
        return new Integer(sum);
    }

    @Override
    public String toString() {
        return "SumCallable{" + "x=" + x + ", y=" + y + '}';
    }
}