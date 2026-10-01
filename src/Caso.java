import java.util.ArrayList;

/**
 * Representa un caso de la agencia. Administra las ubicaciones de investigación
 * en un arreglo básico de tamaño fijo y las pistas en un ArrayList.
 */
public class Caso {

    public static final int CAPACIDAD_UBICACIONES = 5;

    private String nombre;
    private String codigo;
    private String detectiveResponsable;
    private Ubicacion[] ubicaciones;
    private ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detectiveResponsable) {
        this.nombre = Validador.validarTexto(nombre, "nombre del caso");
        this.codigo = Validador.validarTexto(codigo, "código del caso");
        this.detectiveResponsable = Validador.validarTexto(detectiveResponsable, "nombre del detective");
        this.ubicaciones = new Ubicacion[CAPACIDAD_UBICACIONES];
        this.pistas = new ArrayList<Pista>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDetectiveResponsable() {
        return detectiveResponsable;
    }

    // ---------------------------------------------------------------
    // Ubicaciones (arreglo básico)
    // ---------------------------------------------------------------

    public boolean posicionValida(int posicion) {
        return posicion >= 0 && posicion < ubicaciones.length;
    }

    public boolean estaOcupada(int posicion) {
        validarPosicion(posicion);
        return ubicaciones[posicion] != null;
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicación a registrar no tiene información válida.");
        }
        if (ubicaciones[posicion] != null) {
            throw new IllegalArgumentException("La posición " + posicion + " ya está ocupada.");
        }
        ubicaciones[posicion] = ubicacion;
    }

    /**
     * Devuelve la ubicación de la posición indicada o null si la posición está vacía.
     */
    public Ubicacion obtenerUbicacion(int posicion) {
        validarPosicion(posicion);
        return ubicaciones[posicion];
    }

    public void modificarUbicacion(int posicion, int nuevoNivelRiesgo, String nuevoEstado) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalArgumentException("La posición " + posicion + " está vacía, no hay nada que modificar.");
        }
        ubicaciones[posicion].modificar(nuevoNivelRiesgo, nuevoEstado);
    }

    public void descartarUbicacion(int posicion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalArgumentException("La posición " + posicion + " está vacía, no hay nada que descartar.");
        }
        ubicaciones[posicion] = null;
    }

    public int contarUbicaciones() {
        int cantidad = 0;
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int contarEspaciosDisponibles() {
        return ubicaciones.length - contarUbicaciones();
    }

    /**
     * Devuelve la ubicación con mayor nivel de riesgo o null si no hay ubicaciones registradas.
     */
    public Ubicacion obtenerUbicacionMayorRiesgo() {
        Ubicacion mayor = null;
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null
                    && (mayor == null || ubicaciones[i].getNivelRiesgo() > mayor.getNivelRiesgo())) {
                mayor = ubicaciones[i];
            }
        }
        return mayor;
    }

    private void validarPosicion(int posicion) {
        if (!posicionValida(posicion)) {
            throw new IllegalArgumentException("La posición " + posicion + " no existe. Debe estar entre 0 y "
                    + (ubicaciones.length - 1) + ".");
        }
    }

    // ---------------------------------------------------------------
    // Pistas (ArrayList)
    // ---------------------------------------------------------------

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista a registrar no tiene información válida.");
        }
        if (buscarIndicePista(pista.getCodigo()) != -1) {
            throw new IllegalArgumentException("Ya existe una pista con el código " + pista.getCodigo() + ".");
        }
        pistas.add(pista);
    }

    /**
     * Devuelve una copia de la lista para que nadie pueda modificar el ArrayList interno desde afuera.
     */
    public ArrayList<Pista> getPistas() {
        return new ArrayList<Pista>(pistas);
    }

    /**
     * Devuelve la pista con el código indicado o null si no existe.
     */
    public Pista buscarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            return null;
        }
        return pistas.get(indice);
    }

    /**
     * Reemplaza la pista por una nueva con el mismo código. Al crear el objeto nuevo
     * se aplican las mismas validaciones del registro y, si algo falla, la pista original no se toca.
     */
    public void modificarPista(String codigo, String descripcion, String tipoEvidencia,
                               int nivelImportancia, int nivelConfiabilidad) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con el código " + codigo + ".");
        }
        Pista actualizada = new Pista(pistas.get(indice).getCodigo(), descripcion, tipoEvidencia,
                nivelImportancia, nivelConfiabilidad);
        pistas.set(indice, actualizada);
    }

    public void eliminarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con el código " + codigo + ".");
        }
        pistas.remove(indice);
    }

    public int contarPistas() {
        return pistas.size();
    }

    /**
     * Devuelve la pista más importante o null si no hay pistas.
     */
    public Pista obtenerPistaMayorImportancia() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelImportancia() > mayor.getNivelImportancia()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    /**
     * Devuelve la pista más confiable o null si no hay pistas.
     */
    public Pista obtenerPistaMayorConfiabilidad() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelConfiabilidad() > mayor.getNivelConfiabilidad()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    /**
     * Devuelve el promedio de importancia o 0 si no hay pistas.
     */
    public double calcularPromedioImportancia() {
        if (pistas.isEmpty()) {
            return 0;
        }
        int suma = 0;
        for (Pista pista : pistas) {
            suma += pista.getNivelImportancia();
        }
        return (double) suma / pistas.size();
    }

    private int buscarIndicePista(String codigo) {
        if (codigo == null) {
            return -1;
        }
        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getCodigo().equalsIgnoreCase(codigo.trim())) {
                return i;
            }
        }
        return -1;
    }
}
