package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        @NotBlank
        String nombre,
        @NotBlank @Email
        String email,
        @NotBlank
        String telefono,
        @NotBlank @Pattern(regexp ="\\d{4,6}")
        String documento,
        @NotNull//Porque es enum
        Especialidad especialidad,
        @NotNull//Usamos notnull porque es un objeto
        @Valid
        DatosDireccion direccion
) {
}
