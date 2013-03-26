package ass.manotoma;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumberCalculatorImpl implements PrimeNumberCalculator {

    public int[] computePrimes(int max) throws RemoteException {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < max; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
        return copyToInt(result.toArray(new Integer[result.size()]));
    }

    /**
     * Checks whether an int is prime or not.
     *
     * @param n the given number
     * @return whether an int is prime or not
     */
    private boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) {
            return false;
        }
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    private int[] copyToInt(Integer[] input){
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i];
        }
        return result;
    }
    
}
