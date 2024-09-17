package med.voll.api.domain.consulta.validadorcancelamiento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadoresCancelamiento {

    private ConsultaRepository repository;

    @Autowired
    public ValidadorHorarioAntecedencia(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DatosCancelamientoConsulta datos) {
        Consulta consulta=repository.getReferenceById(datos.idConsulta());
        var ahora= LocalDateTime.now();
        var diferenciaEnHoras= Duration.between(ahora,consulta.getData()).toHours();

        if(diferenciaEnHoras<24){
            throw new ValidationException("La consulta solo puede ser cancelada con antecedencia minima de 24 horas");
        }
    }
}
