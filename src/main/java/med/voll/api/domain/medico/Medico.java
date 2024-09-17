package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Medico")
@Table(name = "medicos")
//Con lombok pongo estas anotaciones y cuando se crea la entidad genera todo esto
@Getter//Todos los getters
@NoArgsConstructor//Constructor sin argumentos
@AllArgsConstructor//Constructor con todos los parametros
@EqualsAndHashCode(of = "id")//El equals lo compara mediante id
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;
    private Boolean activo;

    public Medico(DatosRegistroMedico medico) {
        this.nombre = medico.nombre();
        this.email = medico.email();
        this.telefono = medico.telefono();
        this.documento = medico.documento();
        this.especialidad = medico.especialidad();
        this.direccion = new Direccion(medico.direccion());
        this.activo=true;
    }

    public void actualizarDatos(DatosActualizacionMedico datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.documento() != null) {
            this.documento = datos.documento();
        }
        if(datos.direccion()!=null){
        this.direccion = direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo=false;
    }

}
