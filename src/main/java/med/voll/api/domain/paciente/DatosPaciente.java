package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosPaciente(
        @NotBlank
        String nombre,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotBlank
        String telefono,
        @NotNull @Valid
        DatosDireccion direccion
) {
        public DatosPaciente(Paciente paciente){
                this(
                        paciente.getNombre(),
                        paciente.getEmail(),
                        paciente.getDocumento(),
                        paciente.getTelefono(),
                        new DatosDireccion(paciente.getDireccion())
                );
        }
}
