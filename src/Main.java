import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver program de la agencia de detectives. Muestra el menú y conecta
 * al usuario con las operaciones del caso.
 */
public class Main {

    private static final int OPCION_SALIR = 13;

    private static Scanner scanner = new Scanner(System.in);
    private static Caso caso;

    public static void main(String[] args) {
        System.out.println("AGENCIA DE DETECTIVES - CASO MISTERIOSO");
        System.out.println();
        caso = crearCaso();

        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = leerEntero("Seleccione una opción: ");
                ejecutarOpcion(opcion);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("----------------------------------------");
            }
        } while (opcion != OPCION_SALIR);

        scanner.close();
    }

    // ---------------------------------------------------------------
    // Menú
    // ---------------------------------------------------------------

    private static void mostrarMenu() {
        System.out.println("CASO: " + caso.getNombre() + " (" + caso.getCodigo() + ")"
                + " - Detective: " + caso.getDetectiveResponsable());
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicación");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicación");
        System.out.println("5. Modificar ubicación");
        System.out.println("6. Descartar ubicación");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigación");
        System.out.println("13. Salir");
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                nuevoCaso();
                break;
            case 2:
                registrarUbicacion();
                break;
            case 3:
                consultarUbicaciones();
                break;
            case 4:
                consultarUbicacion();
                break;
            case 5:
                modificarUbicacion();
                break;
            case 6:
                descartarUbicacion();
                break;
            case 7:
                registrarPista();
                break;
            case 8:
                consultarPistas();
                break;
            case 9:
                buscarPista();
                break;
            case 10:
                modificarPista();
                break;
            case 11:
                eliminarPista();
                break;
            case 12:
                mostrarReporte();
                break;
            case 13:
                System.out.println("Cerrando el sistema. Hasta pronto.");
                break;
            default:
                System.out.println("Opción inválida. Elija un número entre 1 y 13.");
        }
    }

    // ---------------------------------------------------------------
    // Lectura de datos
    // ---------------------------------------------------------------

    /**
     * Pide un entero y vuelve a preguntar mientras el usuario escriba algo que no lo sea.
     * El finally descarta el resto de la línea tanto si la lectura funcionó como si falló,
     * así el buffer del Scanner siempre queda limpio.
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            } finally {
                scanner.nextLine();
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerPosicion() {
        return leerEntero("Posición (0 a " + (Caso.CAPACIDAD_UBICACIONES - 1) + "): ");
    }

    // ---------------------------------------------------------------
    // Caso
    // ---------------------------------------------------------------

    private static Caso crearCaso() {
        System.out.println("Ingrese la información del caso.");
        while (true) {
            try {
                String nombre = leerTexto("Nombre del caso: ");
                String codigo = leerTexto("Código de identificación: ");
                String detective = leerTexto("Nombre del detective responsable: ");
                return new Caso(nombre, codigo, detective);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Intente de nuevo.");
            }
        }
    }

    private static void nuevoCaso() {
        caso = crearCaso();
        System.out.println("Se creó el nuevo caso. Comienza sin ubicaciones ni pistas.");
    }

    // ---------------------------------------------------------------
    // Ubicaciones
    // ---------------------------------------------------------------

    private static void registrarUbicacion() {
        int posicion = leerPosicion();
        if (!caso.posicionValida(posicion)) {
            System.out.println("La posición " + posicion + " no existe en el arreglo.");
            return;
        }
        if (caso.estaOcupada(posicion)) {
            System.out.println("La posición " + posicion + " ya está ocupada.");
            return;
        }

        String codigo = leerTexto("Código de la ubicación: ");
        String nombre = leerTexto("Nombre: ");
        String direccion = leerTexto("Dirección o descripción: ");
        int riesgo = leerEntero("Nivel de riesgo (1 a 10): ");
        String estado = leerTexto("Estado: ");

        Ubicacion ubicacion = new Ubicacion(codigo, nombre, direccion, riesgo, estado);
        caso.registrarUbicacion(posicion, ubicacion);
        System.out.println("Ubicación registrada en la posición " + posicion + ".");
    }

    private static void consultarUbicaciones() {
        int encontradas = 0;
        for (int i = 0; i < Caso.CAPACIDAD_UBICACIONES; i++) {
            Ubicacion ubicacion = caso.obtenerUbicacion(i);
            if (ubicacion != null) {
                System.out.println("Posición " + i + " -> " + ubicacion);
                encontradas++;
            }
        }
        if (encontradas == 0) {
            System.out.println("Todavía no hay ubicaciones registradas.");
        }
    }

    private static void consultarUbicacion() {
        int posicion = leerPosicion();
        if (!caso.posicionValida(posicion)) {
            System.out.println("La posición " + posicion + " no existe en el arreglo.");
            return;
        }
        Ubicacion ubicacion = caso.obtenerUbicacion(posicion);
        if (ubicacion == null) {
            System.out.println("La posición " + posicion + " está vacía.");
        } else {
            System.out.println("Posición " + posicion + " -> " + ubicacion);
        }
    }

    private static void modificarUbicacion() {
        int posicion = leerPosicion();
        if (!caso.posicionValida(posicion)) {
            System.out.println("La posición " + posicion + " no existe en el arreglo.");
            return;
        }
        if (!caso.estaOcupada(posicion)) {
            System.out.println("La posición " + posicion + " está vacía, no se puede modificar.");
            return;
        }

        int riesgo = leerEntero("Nuevo nivel de riesgo (1 a 10): ");
        String estado = leerTexto("Nuevo estado: ");
        caso.modificarUbicacion(posicion, riesgo, estado);
        System.out.println("Ubicación modificada correctamente.");
    }

    private static void descartarUbicacion() {
        int posicion = leerPosicion();
        caso.descartarUbicacion(posicion);
        System.out.println("Ubicación descartada. La posición " + posicion + " quedó disponible.");
    }

    // ---------------------------------------------------------------
    // Pistas
    // ---------------------------------------------------------------

    private static void registrarPista() {
        String codigo = leerTexto("Código de la pista: ");
        if (caso.buscarPista(codigo) != null) {
            System.out.println("Ya existe una pista con el código " + codigo + ".");
            return;
        }
        String descripcion = leerTexto("Descripción: ");
        String tipo = leerTexto("Tipo de evidencia: ");
        int importancia = leerEntero("Nivel de importancia (1 a 10): ");
        int confiabilidad = leerEntero("Nivel de confiabilidad (0 a 100): ");

        Pista pista = new Pista(codigo, descripcion, tipo, importancia, confiabilidad);
        caso.registrarPista(pista);
        System.out.println("Pista registrada correctamente.");
    }

    private static void consultarPistas() {
        ArrayList<Pista> pistas = caso.getPistas();
        if (pistas.isEmpty()) {
            System.out.println("Todavía no hay pistas registradas.");
            return;
        }
        for (Pista pista : pistas) {
            System.out.println(pista);
        }
    }

    private static void buscarPista() {
        String codigo = leerTexto("Código de la pista: ");
        Pista pista = caso.buscarPista(codigo);
        if (pista == null) {
            System.out.println("No se encontró ninguna pista con el código " + codigo + ".");
        } else {
            System.out.println(pista);
        }
    }

    private static void modificarPista() {
        String codigo = leerTexto("Código de la pista a modificar: ");
        if (caso.buscarPista(codigo) == null) {
            System.out.println("No se encontró ninguna pista con el código " + codigo + ".");
            return;
        }
        String descripcion = leerTexto("Nueva descripción: ");
        String tipo = leerTexto("Nuevo tipo de evidencia: ");
        int importancia = leerEntero("Nuevo nivel de importancia (1 a 10): ");
        int confiabilidad = leerEntero("Nuevo nivel de confiabilidad (0 a 100): ");

        caso.modificarPista(codigo, descripcion, tipo, importancia, confiabilidad);
        System.out.println("Pista modificada correctamente.");
    }

    private static void eliminarPista() {
        String codigo = leerTexto("Código de la pista a eliminar: ");
        caso.eliminarPista(codigo);
        System.out.println("Pista eliminada correctamente.");
    }

    // ---------------------------------------------------------------
    // Reporte
    // ---------------------------------------------------------------

    private static void mostrarReporte() {
        System.out.println("REPORTE DE INVESTIGACIÓN");
        System.out.println("Ubicaciones registradas: " + caso.contarUbicaciones());
        System.out.println("Espacios disponibles para ubicaciones: " + caso.contarEspaciosDisponibles());

        Ubicacion mayorRiesgo = caso.obtenerUbicacionMayorRiesgo();
        if (mayorRiesgo == null) {
            System.out.println("Ubicación con mayor riesgo: no hay ubicaciones registradas.");
        } else {
            System.out.println("Ubicación con mayor riesgo: " + mayorRiesgo);
        }

        System.out.println("Pistas registradas: " + caso.contarPistas());
        if (caso.contarPistas() == 0) {
            System.out.println("No se calculan la pista más importante, la más confiable ni el promedio "
                    + "porque no hay pistas.");
            return;
        }
        System.out.println("Pista con mayor importancia: " + caso.obtenerPistaMayorImportancia());
        System.out.println("Pista con mayor confiabilidad: " + caso.obtenerPistaMayorConfiabilidad());
        System.out.println("Promedio de importancia: " + String.format("%.2f", caso.calcularPromedioImportancia()));
    }
}
