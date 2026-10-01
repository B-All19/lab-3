/**
 * Clase de utilidad con las validaciones que comparten las demás clases.
 * Todas las reglas incumplidas se reportan con IllegalArgumentException.
 */
public final class Validador {

    private Validador() {
        // No se deben crear instancias de esta clase.
    }

    public static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    public static int validarRango(int valor, int minimo, int maximo, String campo) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException("El campo " + campo + " debe estar entre "
                    + minimo + " y " + maximo + ".");
        }
        return valor;
    }
}
