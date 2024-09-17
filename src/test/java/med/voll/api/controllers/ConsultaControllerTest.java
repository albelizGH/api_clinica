package med.voll.api.controllers;

import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest//Nos permite trabajar test con todo lo que hay en sprint, repositorios,servicios,etc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    /*Tenemos 2 estrategias
     * 1-Usamos un @Resttemplate que simula un servidor que hace una peticion http a nuestro controller y hace el camino real
     * 2-Simulamos en que la peticion fue bien y solo nos centramos en el componente ignorando los repositorios servicios etc
     * y vemos el estado que retorna cuando realizamos la peticion con esos datos
     * */

    /*Vamos a usar la opcion 2
     * para eso creamos un mock y la inyectamos en la clase de prueba y eso con la anotacion @AutoconfigureMockMvc simula una peticion al controlador*/

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;//toma un objeto de tipo java y lo pasa a tipo Json y viceversa
    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJacksonTester;
    @MockBean
    ConsultaService consultaService;


    @Test
    @DisplayName("Debería retornar estado HTTP 400 cuando los datos ingresados son inválidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        // given
        // Datos de entrada vacíos o inválidos

        // when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería retornar estado HTTP 200 cuando los datos ingresados son válidos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        // given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datosAgendarConsulta = new DatosAgendarConsulta(null, 2L, 5L, fecha, especialidad);
        var jsonAgendarConsulta = agendarConsultaJacksonTester.write(datosAgendarConsulta).getJson();
        var datos = new DatosDetalleConsulta(null, 2L, 5L, fecha);

        when(consultaService.agendar(any())).thenReturn(datos);

        // when
        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAgendarConsulta))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var datosDetalleConsultaEsperado = datos;
        var jsonEsperado = datosDetalleConsultaJacksonTester.write(datosDetalleConsultaEsperado).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
    
}