package med.voll.api.domain.consulta.validacionesConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas {

    private PacienteRepository repository;

    @Autowired
    public PacienteActivo(PacienteRepository repository) {
        this.repository = repository;
    }


    public void validar(DatosAgendarConsulta datos) {

        if (datos.idPaciente() == null) {
            return;//No hace falta porque puse un @Notnull en el DatosAgendarConuslta pero sigamos el video
        }

        boolean pacienteActivo = repository.findActivoById(datos.idPaciente());

        if (!pacienteActivo) {
            throw new ValidationException("No se permite agendar citas con pacientes inactivos en el sistema");
        }


    }
}
