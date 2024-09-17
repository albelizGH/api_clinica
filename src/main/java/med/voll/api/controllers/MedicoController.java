package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
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
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")//Para que pida el token en esta consulta en la documentacion
public class MedicoController {
    private MedicoRepository medicoRepository;

    @Autowired
    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Operation(summary = "Registra un médico")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico= medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico=new DatosRespuestaMedico(medico);
        URI url=uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

    }

    @Operation(summary = "Lista todos los médicos")
    @GetMapping()
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 5,sort = "nombre",direction = Sort.Direction.ASC) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(medico -> new DatosListadoMedico(medico));
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(medico -> new DatosListadoMedico(medico)));
    }

    //.filter(medico -> medico.getActivo()==true)
    @Operation(summary = "Actualiza los datos de un medico")
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizacionMedico datos){
        Medico medico=medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);
        DatosRespuestaMedico datosRespuestaMedico= new DatosRespuestaMedico(medico);
        return ResponseEntity.ok(datosRespuestaMedico);
    }

    //BORRADO LOGICO
    @Operation(summary = "Desactiva un médico")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarMedico(@PathVariable Long id){
        Medico medico=medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene datos de médico")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedios(@PathVariable Long id){
        Medico medico=medicoRepository.getReferenceById(id);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        return ResponseEntity.ok(datosRespuestaMedico);
    }

}

