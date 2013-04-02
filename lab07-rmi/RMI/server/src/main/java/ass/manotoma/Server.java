package ass.manotoma;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Server {

    public static void main(String[] args) {
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            LocateRegistry.createRegistry(1099); 
            String name = "Primes";
            PrimeNumberCalculator engine = new PrimeNumberCalculatorImpl();
            PrimeNumberCalculator stub = (PrimeNumberCalculator) UnicastRemoteObject.exportObject(engine, 1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Compute engine bound");
        } catch (Exception e) {
            System.err.println("An exception occured.");
            System.err.println(e);
        }
    }
}
