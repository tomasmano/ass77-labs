package ass.manotoma;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrimeNumberCalculator extends Remote {

    public int[] computePrimes(int max) throws RemoteException;
}
