package comm.psp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Clase cliente que implementa una conexión RMI (Remote Method Invocation).
 * Permite interactuar con un servidor remoto para realizar operaciones de
 * manipulación de texto como conteo y reemplazo de palabras.
 *  * @author Bianca Stefania Amariutei, Kevin Ramirez
 * @version 1.0
 */

public class RMICliente {
    /**
     * Punto de entrada de la aplicación cliente.
     * Gestiona la conexión con el registro RMI, la entrada de usuario por consola
     * y la lógica del menú de operaciones.
     */
    public static void main(String[] args) {
        /**
         * Texto base sobre el cual se realizarán las operaciones remotas.
         * Se define como una constante local para ser enviada como parámetro facilitando
         * el cuerpo del texto al servidor para la ejecución de los servicios de
         * conteo y reemplazo.
         */

        final String texto = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, " +
                            "no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, " +
                            "adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca " +
                            "que carnero, salpicón las más noches, duelos y quebrantos los sábados, " +
                            "lentejas los viernes, algún palomino de añadidura los domingos, consumían las tres partes de su hacienda";

        Scanner scString = new Scanner(System.in);

        RMIInterface manipularTexto = null;

        try {
            System.out.println("Introduzca la IP del servidor a conectarse: (127.0.0.1 para localhost)");
            String ip = scString.nextLine();

            if (validarSubred(ip)) {
                // Obtención del registro en el puerto 5555
                Registry registry = LocateRegistry.getRegistry(ip, 5555);
                manipularTexto = (RMIInterface) registry.lookup("ManipularTexto");

                System.out.println("IP válida...");

                if (manipularTexto != null) {
                    int opcion = 0;

                    do {
                        System.out.println(" ----- Intoduzca la opción a elegir: -----  ");
                        System.out.println("1. Contar las palabras dado un texto");
                        System.out.println("2. Reemplazar una palabra dado un texto");
                        System.out.println("3. Salir");

                        opcion = Integer.parseInt(scString.nextLine());

                        switch (opcion) {
                            case 1:
                                System.out.println("Opcion 1 elegida.");

                                System.out.println("Introduzca la palabra a contar:");
                                String palabra = scString.nextLine();

                                System.out.println("Contando palabras...\n" + manipularTexto.contar(palabra, texto));
                                break;

                            case 2:
                                System.out.println("Opcion 2 elegida.");

                                System.out.println("Introduzca la palabra a reemplazar:");
                                String palabraARemplazar = scString.nextLine();

                                System.out.println("Introduzca la palabra nueva:");
                                String palabraNueva = scString.nextLine();

                                System.out.println("Reemplazando palabra " + palabraARemplazar + " por " + palabraNueva + "...\n"
                                        + manipularTexto.reemplazar(palabraARemplazar, palabraNueva, texto));
                                break;

                            case 3:
                                System.out.println("Adiós.");
                                break;

                            default:
                                System.out.println("Por favor, introduzca una opción válida.");
                        }

                    } while (opcion != 3);

                }
            } else {
                System.out.println("La IP introducida no es correcta.");
            }
        } catch (RemoteException e) {
            System.err.println("Error de comunicación: No se pudo conectar con el servidor en la IP o el puerto 5555.");
        } catch (NotBoundException e) {
            System.err.println("Error: El servicio 'ManipularTexto' no se encuentra registrado en el servidor.");
        } catch (Exception e) {
            System.err.println("Se ha producido un error inesperado: " + e.getMessage());
        }
    }
    /**
     * (Metodo seleccionado del exaen) Valida si una cadena de texto tiene el formato de una dirección IPv4 válida.
     * Comprueba que contenga cuatro octetos y que cada uno esté en el rango [0, 255].
     * * @param ip La cadena de texto que contiene la dirección IP a validar.
     * @return {@code true} si la IP es válida; {@code false} en caso contrario.
     */
    public static boolean validarSubred(String ip) {
        // Step 1: Separate the given string into an array of strings using the dot as delimiter
        String[] parts = ip.split("\\.");

        // Step 2: Check if there are exactly 3 parts
        if (parts.length != 4) {
            return false;
        }

        // Step 3: Check each part for valid number
        for (String part : parts) {
            try {
                // Step 4: Convert each part into a number
                int num = Integer.parseInt(part);

                // Step 5: Check whether the number lies in between 0 to 255
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                // If parsing fails, it's not a valid number
                return false;
            }
        }

        // If all checks passed, return true
        return true;
    }
}
