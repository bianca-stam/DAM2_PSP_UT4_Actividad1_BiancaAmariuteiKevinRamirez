package comm.psp;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Clase que implementa la interfaz remota {@link RMIInterface}.
 * Actúa como el servidor en la arquitectura RMI, proporcionando servicios
 * de procesamiento de texto a los clientes conectados.
 * * @author Bianca Stefania Amariutei, Kevin Ramirez
 * @version 1.0
 */

public class RMIServidor implements RMIInterface{
    /** Puerto en el que escuchará el registro RMI. */
    private static final int PUERTO = 5555;

    /**
     * Cuenta las ocurrencias exactas de una palabra dentro de un texto dado.
     * Utiliza expresiones regulares con límites de palabra (\\b) para asegurar
     * que solo se cuenten coincidencias exactas.
     * * @param palabra La palabra que se desea buscar.
     * @param texto El cuerpo de texto donde se realizará la búsqueda.
     * @return El número de veces que aparece la palabra en el texto.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
    @Override
    public synchronized int contar(String palabra, String texto) {
        String[] partes = texto.split("\\b" + palabra + "\\b");
        return partes.length - 1;
    }

    /**
     * Reemplaza todas las apariciones de una palabra específica por una nueva
     * dentro de un texto, respetando los límites de palabra.
     * * @param palabra La palabra original que se desea sustituir.
     * @param palabraNueva La nueva palabra que reemplazará a la original.
     * @param texto El texto sobre el cual realizar la operación.
     * @return El texto resultante tras realizar los reemplazos.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
    @Override
    public synchronized String reemplazar(String palabra, String palabraNueva, String texto) {
        if (texto.contains(palabra)){
            String textoNuevo = texto.replaceAll("\\b"+palabra+"\\b", palabraNueva);
            return textoNuevo;
        } else {
            return "No se puede reemplazar una palabra que no esta en el texto";
        }
    }

    /**
     * Método de inicialización del servidor.
     * Crea el registro RMI en el puerto especificado, instancia el objeto servidor
     * y lo vincula al nombre "ManipularTexto" para que los clientes puedan localizarlo.
     */
    public static void main(String[] args) {
        System.out.println("Servidor iniciado.");
        Registry reg = null;

        try {
            reg = LocateRegistry.createRegistry(PUERTO);
            RMIServidor serverObject = new RMIServidor();

            reg.rebind("ManipularTexto", (RMIInterface) UnicastRemoteObject.exportObject(serverObject, 0));

        } catch (RemoteException e) {
            System.out.println("Error. No se pudo crear el registro.");
        }
    }

}
