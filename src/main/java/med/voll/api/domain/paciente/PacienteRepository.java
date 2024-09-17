package med.voll.api.domain.paciente;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    @Query("""
            select p.activo
            from Paciente p
            where
            p.id = :id
            """)
    Boolean findActivoById(Long id);
}


