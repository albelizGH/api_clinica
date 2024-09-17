package med.voll.api.domain.consulta.validadorcancelamiento;

import med.voll.api.domain.consulta.MotivoCancelamiento;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta(
        Long idConsulta,
        LocalDateTime fecha,
        MotivoCancelamiento motivo
) {
}
