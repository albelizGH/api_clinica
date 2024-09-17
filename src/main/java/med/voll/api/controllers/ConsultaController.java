package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.consulta.validadorcancelamiento.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")//Para que pida el token en esta consulta en la documentacion
public class ConsultaController {

    private ConsultaService service;

    @Autowired
    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @Operation(summary = "Agendamos una consulta", description = "Agendamos una consulta pasandole los datos correspondientes")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        DatosDetalleConsulta datosDetalleConsulta = service.agendar(datos);
        return ResponseEntity.ok(datosDetalleConsulta);
    }

    @Operation(summary = "Cancelamos una consulta")
    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos){
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtenemos todas las consultas")
    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listarConsultas(@PageableDefault(size = 5,sort = "data", direction = Sort.Direction.ASC) Pageable paginacion){
        Page page =service.listarConsultas(paginacion);
        return ResponseEntity.ok(page);
    }


}
