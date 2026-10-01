/**
 * Representa una pista o evidencia encontrada durante la investigación.
 */
public class Pista {

    private static final int IMPORTANCIA_MINIMA = 1;
    private static final int IMPORTANCIA_MAXIMA = 10;
    private static final int CONFIABILIDAD_MINIMA = 0;
    private static final int CONFIABILIDAD_MAXIMA = 100;

    private String codigo;
    private String descripcion;
    private String tipoEvidencia;
    private int nivelImportancia;
    private int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia,
                 int nivelImportancia, int nivelConfiabilidad) {
        this.codigo = Validador.validarTexto(codigo, "código de la pista");
        this.descripcion = Validador.validarTexto(descripcion, "descripción de la pista");
        this.tipoEvidencia = Validador.validarTexto(tipoEvidencia, "tipo de evidencia");
        this.nivelImportancia = Validador.validarRango(nivelImportancia,
                IMPORTANCIA_MINIMA, IMPORTANCIA_MAXIMA, "nivel de importancia");
        this.nivelConfiabilidad = Validador.validarRango(nivelConfiabilidad,
                CONFIABILIDAD_MINIMA, CONFIABILIDAD_MAXIMA, "nivel de confiabilidad");
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + " | Descripción: " + descripcion + " | Evidencia: " + tipoEvidencia
                + " | Importancia: " + nivelImportancia + " | Confiabilidad: " + nivelConfiabilidad + "%";
    }
}
