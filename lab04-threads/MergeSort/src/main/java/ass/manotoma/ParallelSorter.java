package ass.manotoma;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ParallelSorter implements Runnable{
    
    private MergeSorter sorter;
    private double[] data;

    public ParallelSorter(MergeSorter sorter, double[] data) {
        this.sorter = sorter;
        this.data = data;
//        new Thread(this).start();
    }

    public void run() {
//        System.out.println(String.format("Firing thread %s", Thread.currentThread()));
        try {
            sorter.sort(data);
        } catch (Exception ex) {
            System.err.println("Somethin' went wrong..");
        }
    }
    
}
