/**
 * Representa un lugar que los detectives deben investigar dentro de un caso.
 */
public class Ubicacion {

    private static final int RIESGO_MINIMO = 1;
    private static final int RIESGO_MAXIMO = 10;

    private String codigo;
    private String nombre;
    private String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion, int nivelRiesgo, String estado) {
        this.codigo = Validador.validarTexto(codigo, "código de la ubicación");
        this.nombre = Validador.validarTexto(nombre, "nombre de la ubicación");
        this.direccion = Validador.validarTexto(direccion, "dirección de la ubicación");
        this.nivelRiesgo = Validador.validarRango(nivelRiesgo, RIESGO_MINIMO, RIESGO_MAXIMO, "nivel de riesgo");
        this.estado = Validador.validarTexto(estado, "estado de la ubicación");
    }

    /**
     * Cambia el nivel de riesgo y el estado. Primero se validan ambos valores
     * para que la ubicación no quede a medias si alguno es inválido.
     */
    public void modificar(int nuevoNivelRiesgo, String nuevoEstado) {
        int riesgoValidado = Validador.validarRango(nuevoNivelRiesgo, RIESGO_MINIMO, RIESGO_MAXIMO, "nivel de riesgo");
        String estadoValidado = Validador.validarTexto(nuevoEstado, "estado de la ubicación");
        this.nivelRiesgo = riesgoValidado;
        this.estado = estadoValidado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Dirección: " + direccion
                + " | Riesgo: " + nivelRiesgo + " | Estado: " + estado;
    }
}
