package med.voll.api.domain.consulta.validadorcancelamiento;

import org.springframework.stereotype.Component;

public interface ValidadoresCancelamiento {
    public void validar(DatosCancelamientoConsulta datos);
}
