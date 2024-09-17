package med.voll.api.domain.consulta.validacionesConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component//Tambien podriamos anotarla como service ya que hace un servicio de validacion
public class MedicoActivo implements ValidadorDeConsultas{

    private MedicoRepository repository;

    @Autowired
    public MedicoActivo(MedicoRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosAgendarConsulta datos) {

        if(datos.idMedico()==null){
            return;
        }

        boolean medicoActivo = repository.findActivoById(datos.idMedico());

        if(!medicoActivo){
            throw new ValidationException("No se permite programar citas con médicos inactivos en el sistema;");
        }

    }


}
