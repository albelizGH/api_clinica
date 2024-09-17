package med.voll.api.domain.consulta.validacionesConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteConConsulta implements ValidadorDeConsultas{

    ConsultaRepository repository;

    @Autowired
    public PacienteConConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosAgendarConsulta datos){
        var fechaConsulta=datos.fecha();
        var primerHorario=fechaConsulta.withHour(7);//Me agarra la fecha que yo le paso y le cambia la hora a 7
        var ultimoHorario=fechaConsulta.withHour(18);

        Boolean pacienteConConsulta=repository.existsByPacienteIdAndDataBetween(datos.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidationException("No permita programar más de una consulta en el mismo día para el mismo paciente;");
        }

    }
}
