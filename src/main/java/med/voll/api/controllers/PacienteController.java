package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")//Para que pida el token en esta consulta en la documentacion
public class PacienteController {
    private PacienteRepository pacienteRepository;

    @Autowired
    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Operation(summary = "Lista los pacientes")
    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(size = 5,sort = "nombre", direction = Sort.Direction.ASC) Pageable paginacion){
        Page page =pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Registra un paciente")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosPaciente datosPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = new Paciente(datosPaciente);
        pacienteRepository.save(paciente);
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaPaciente);
    }

    @Operation(summary = "Actualiza los datos de un paciente")
    @PutMapping()
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos){
        Paciente paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
    }

    @Operation(summary = "Desactiva un paciente")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarPacienteLogico(@PathVariable Long id){
        Paciente paciente=pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene los datos de un paciente")
    @GetMapping("/{id}")
    public ResponseEntity obtenerPorId(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatosPaciente datosPaciente=new DatosPaciente(paciente);
        return ResponseEntity.ok(datosPaciente);
    }


}
