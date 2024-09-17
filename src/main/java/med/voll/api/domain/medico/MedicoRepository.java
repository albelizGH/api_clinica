package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select m from Medico m
            where m.activo=true
            and
            m.especialidad=:especialidad
            and
            m.id not in( 
                select c.medico.id from Consulta c
                where
                c.data=:fecha
            )
            order by rand()
            limit 1
            """)//La subconsulta que esta entre () le dice que el id de ese medico que busco no este en la tabla consultas con una consulta en la misma fecha que yo le estoy pasando
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query("""
            select m.activo
            from Medico m
            where
            m.id = :id
            """)
    Boolean findActivoById(Long id);
}

