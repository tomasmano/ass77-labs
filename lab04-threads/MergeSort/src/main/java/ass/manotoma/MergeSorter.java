package ass.manotoma;

public class MergeSorter implements Sorter {

    public MergeSorter() {
    }

    public void sort(double[] data) throws Exception{
        
//        System.out.println("Sorting data [length:"+data.length+"] with thread: "+Thread.currentThread().getId());
        
        if (data.length <= 1) {
            return;
        }

        int middle = data.length / 2;

        double[] left = new double[middle];
        double[] right = new double[data.length - middle];
        System.arraycopy(data, 0, left, 0, middle);
        System.arraycopy(data, middle, right, 0, data.length - middle);

        // sort(left);
        Runnable r1 = new ParallelSorter(new MergeSorter(), left);
        Thread t1 = new Thread(r1);
        t1.start();
        
        // sort(right);
        Runnable r2 = new ParallelSorter(new MergeSorter(), right);
        Thread t2 = new Thread(r2);
        t2.start();
        
        t1.join();
        t2.join();

        double[] output = new double[data.length];
        output = merge(left, right);
        System.arraycopy(output, 0, data, 0, output.length);
    }
    
    public void sortSynchronous(double[] data) throws Exception{
        
//        System.out.println("Sorting data [length:"+data.length+"] with thread: "+Thread.currentThread().getId());
        
        if (data.length <= 1) {
            return;
        }

        int middle = data.length / 2;

        double[] left = new double[middle];
        double[] right = new double[data.length - middle];
        System.arraycopy(data, 0, left, 0, middle);
        System.arraycopy(data, middle, right, 0, data.length - middle);

        // sort(left);
        Runnable r1 = new ParallelSorter(new MergeSorter(), left);
        Thread t1 = new Thread(r1);
        t1.start();
        t1.join();
        
        // sort(right);
        Runnable r2 = new ParallelSorter(new MergeSorter(), right);
        Thread t2 = new Thread(r2);
        t2.start();
        t2.join();

        double[] output = new double[data.length];
        output = merge(left, right);
        System.arraycopy(output, 0, data, 0, output.length);
    }

    private double[] merge(double[] left, double[] right) {

        // init the result array
        double[] result = new double[left.length + right.length];

        // init the array indexes
        int i = 0; // left-index
        int j = 0; // right-index
        int k = 0; // result-index

        // Merge the results by comparing each element.
        while (i < left.length && j < right.length) {

            if (left[ i] > right[ j]) {

                result[k] = right[ j];
                j++; // Next right-index

            } else {

                result[k] = left[ i];
                i++; // Next left-index
            }

            k++; // Next result-index
        }

        // Append whatever is left from the left array into the result array.
        while (i < left.length) {
            result[ k] = left[ i];
            i++; // left-index
            k++; // result-index
        }

        // Append whatever is left from the right array into the result array.
        while (j < right.length) {
            result[ k] = right[ j];
            j++; // right-index
            k++; // result-index
        }

        // Done!
        return result;
    }

}
