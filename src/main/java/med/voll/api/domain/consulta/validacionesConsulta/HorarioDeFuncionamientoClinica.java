package med.voll.api.domain.consulta.validacionesConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {

    public void validar(DatosAgendarConsulta datos){

        boolean domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        boolean antesDeApertura=datos.fecha().getHour()<7;
        boolean despuesDeCierre=datos.fecha().getHour()>19;

        if(domingo || antesDeApertura || despuesDeCierre){
            throw new ValidationException("El horario de atención de la clinica es de lunes a sábado, de 7:00 a 19:00 horas");
        }

    }

}
