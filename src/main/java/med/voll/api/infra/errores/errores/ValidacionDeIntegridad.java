package med.voll.api.infra.errores.errores;

public class ValidacionDeIntegridad extends RuntimeException {

    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
