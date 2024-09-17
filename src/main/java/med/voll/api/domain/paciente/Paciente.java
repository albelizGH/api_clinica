package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    @Embedded private Direccion direccion;
    private Boolean activo;

    public Paciente(DatosPaciente paciente) {
        this.nombre=paciente.nombre();
        this.email=paciente.email();
        this.documento= paciente.documento();
        this.telefono=paciente.telefono();
        this.direccion=new Direccion(paciente.direccion());
        this.activo=true;
    }

    public void actualizarDatos(DatosActualizacionPaciente datos){
        if(datos.nombre()!=null){
            this.nombre=datos.nombre();
        }
        if(datos.telefono()!=null){
            this.telefono=datos.telefono();
        }
        if(datos.direccion()!=null){
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo=false;
    }


}
