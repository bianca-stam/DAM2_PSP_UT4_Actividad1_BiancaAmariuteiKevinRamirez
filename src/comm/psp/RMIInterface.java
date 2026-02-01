package comm.psp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public int contar(String palabra, String texto) throws RemoteException;
    public String reemplazar(String palabra, String palabraNueva, String texto) throws RemoteException;

}
