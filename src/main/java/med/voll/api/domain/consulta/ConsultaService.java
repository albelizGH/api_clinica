package med.voll.api.domain.consulta;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.validacionesConsulta.ValidadorDeConsultas;
import med.voll.api.domain.consulta.validadorcancelamiento.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.validadorcancelamiento.ValidadoresCancelamiento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.DatosListadoPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;

    //Le pongo autowired a todas las clases que quiero que spring cree automaticamente.
    //Si quiero que cree una lista con todos los que implementan ValidadorDeConsultas tengo que ponerles un @Component o @SErvice para que sepa que existe
    //Spring automaticamente me agrega a esta lista o me saca si creo o elimino clases que implementen ValidadorDeConsultas con la anotacion @ correspondiente
    List<ValidadorDeConsultas> validadores;
    List<ValidadoresCancelamiento> validadoresCancelamientos;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository, List<ValidadorDeConsultas> validadores, List<ValidadoresCancelamiento> validadoresCancelamientos) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.validadores = validadores;
        this.validadoresCancelamientos = validadoresCancelamientos;
    }

    @Transactional
    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidacionDeIntegridad("El id para el paciente no fue encontrado");
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El id para el Medico no fue encontrado");
        }

        validadores.forEach(validadorDeConsultas -> validadorDeConsultas.validar(datos));

        //Obtengo paciente y medico y realizamos validaciones
        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();

        //Selecciono u obtengo un medico aleatoriamente que cumpla con la especialidad
        Medico medico = seleccionarMedico(datos);

        if (medico == null) {
            throw new ValidacionDeIntegridad("No existen médicos disponibles para este horario y especialidad");
        }

        //Creo y guardo la consulta
        Consulta consulta = new Consulta(medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionDeIntegridad("Id de la consulta informado no existente");
        }

        validadoresCancelamientos.forEach(v -> v.validar(datos));

        Consulta consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }


    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }


    public Page<DatosDetalleConsulta> listarConsultas(Pageable paginacion) {
        Page page = consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new);
        return page;
    }
}

