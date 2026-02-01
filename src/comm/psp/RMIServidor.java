package comm.psp;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServidor implements RMIInterface{
    private static final int PUERTO = 5555;

    @Override
    public synchronized int contar(String palabra, String texto) {
        String[] partes = texto.split("\\b" + palabra + "\\b");
        return partes.length - 1;
    }

    @Override
    public synchronized String reemplazar(String palabra, String palabraNueva, String texto) {
        String textoNuevo = texto.replaceAll("\\b"+palabra+"\\b", palabraNueva);
        return textoNuevo;
    }

    static void main() {
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
