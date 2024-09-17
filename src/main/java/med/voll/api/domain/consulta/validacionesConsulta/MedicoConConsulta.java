package med.voll.api.domain.consulta.validacionesConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{
    ConsultaRepository repository;

    @Autowired
    public MedicoConConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosAgendarConsulta datos){

        if(datos.idMedico()==null){
            return;
        }

        LocalDateTime fechaConsulta = datos.fecha();
        boolean medicoConConsulta=repository.existsByMedicoIdAndData(datos.idMedico(),fechaConsulta);

        if(medicoConConsulta){
            throw new ValidationException("El médico ya tiene registrada una consulta para ese horario");
        }
    }
}
