package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*Las fases de una prueba son:
* 1.Te dan un conjunto de valores
* 2.Cuando ... condicion
* 3.Entonces... que me tiene que dar?
*
* given-when-then*/




@DataJpaTest//xq vamos a testear algo que tiene que ver con la persistencia
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Si usasemos la database en memoria tendriamos que borrar esta linea
//Con esto le indicamos que estamos por usar una base de datos externa y indicar que no vamos a remplazar la que estamos utilizando
@ActiveProfiles("test")//Cual va a ser el perfil que vamos a utilizar
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {//Le agregamos al nombre Escenario1

        LocalDate now = LocalDate.now();
        LocalDate nextMonday = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)); // Ajusta el día al próximo lunes
        LocalDateTime proximoLunes10AM = nextMonday.atTime(10, 0); // Establece la hora a las 10:00 AM

        //Vamos a registrar una consulta para que me diga que el medico esta ocupado y me devuelva null
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10AM);

        // Trecho de código suprimido

        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("antonio", "a@mail.com", "654321");
        registrarConsulta(medico, paciente, proximoLunes10AM);
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos  en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {

        //given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.CARDIOLOGIA);

        //when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }





    /*Metodos privados comunes*/


    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosPaciente(nombre, email, "12334", "123456", datosDireccion());

    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion("Calle", "Distrito", "Ciudad", "1", "C");
    }


}

/*Cuando nosotros realizamos pruebas o test tenemos que separar la base de datos de produccion de la base de datos de prueba
 * ya que en la de prueba podemos tener valores equivocados y cosas raras que no deberian estar en la de produccion
 * o podemos mandarnos un moco y borrar sin querer la de produccion*/