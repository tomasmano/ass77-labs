package ass.manotoma;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Client {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Primes";
            Registry registry = LocateRegistry.getRegistry(1099);
            PrimeNumberCalculator comp = (PrimeNumberCalculator) registry.lookup(name);
            long start = System.currentTimeMillis();
            System.out.println("Computing primes..");
            int[] result = comp.computePrimes(1000000);
            long end = System.currentTimeMillis();
            long diff = end - start;
            System.out.format("Task executed in [%s ms (%s s)]%n",diff, diff/1000);
            System.out.println("Computed primes: ");
            System.out.println(Arrays.toString(result));
        } catch (Exception e) {
            System.err.println("Computing primes exception:");
            e.printStackTrace();
        }
    }
    
}
