package comm.psp;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota que define las operaciones disponibles para la manipulación de texto.
 * <p>
 * Esta interfaz extiende {@link Remote}, lo que permite que sus métodos sean
 * invocados desde una máquina virtual Java diferente a la que reside el objeto.
 * </p>
 * * @author Bianca Stefania Amariutei, Kevin Ramirez
 * @version 1.0
 */
public interface RMIInterface extends Remote {
    /**
     * Define la firma para contar las ocurrencias de una palabra en un texto.
     * * @param palabra La palabra específica que se desea buscar y contabilizar.
     * @param texto El cuerpo de texto donde se realizará la búsqueda.
     * @return El número total de coincidencias encontradas.
     * @throws RemoteException Si ocurre un error de comunicación o de red durante
     * la ejecución del método remoto.
     */
    public int contar(String palabra, String texto) throws RemoteException;

    /**
     * Define la firma para reemplazar todas las apariciones de una palabra por otra nueva.
     * * @param palabra La palabra original que se desea sustituir.
     * @param palabraNueva El nuevo término que ocupará el lugar de la palabra original.
     * @param texto El texto fuente sobre el cual realizar la modificación.
     * @return Una cadena de texto con todas las sustituciones realizadas.
     * @throws RemoteException Si ocurre un error de comunicación o de red durante
     * la ejecución del método remoto.
     */
    public String reemplazar(String palabra, String palabraNueva, String texto) throws RemoteException;

}
