## Sobre el proyecto

Vol.med es una clínica médica ficticia que necesita una aplicación para la gestión de consultas. La aplicación debe tener funcionalidades que permitan el registro de médicos y pacientes, así como la programación y cancelación de consultas.

Mientras que un equipo de desarrollo será responsable de la aplicación móvil, el nuestro será responsable del desarrollo de la API Rest de este proyecto.

## ⚙️ Funcionalidades
- CRUD de médicos;
- CRUD de pacientes;
- Programación de consultas con sus respectivas condiciones;
- Cancelación de consultas con sus respectivas condiciones;

 ### Requerimientos para programación de consultas
  - Las siguientes reglas de negocio deben ser validadas por el sistema:
  - El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas;
  - Las consultas tienen una duración fija de 1 hora;
  - Las consultas deben programarse con al menos 30 minutos de anticipación;
  - No permitir agendar citas con pacientes inactivos en el sistema;
  - No permitir programar citas con médicos inactivos en el sistema;
  - No permita programar más de una consulta en el mismo día para el mismo paciente;
  - No permitir programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora;
  - La elección de un médico es opcional, en cuyo caso de que no exista el id el sistema debe elegir aleatoriamente un médico que esté disponible en la fecha/hora ingresada.
 
 ### Requerimientos para la cancelación de consultas
- Las siguientes reglas de negocio deben ser validadas por el sistema:
- Es obligatorio informar el motivo de la cancelación de la consulta, entre las opciones: paciente se retiró, médico canceló u otras;
- Una cita solo se puede cancelar con al menos 24 horas de anticipación.

## 🛠 Tecnologías
- Java 17
- Spring Boot 3
- Maven
- MySQL
- Hibernate
- Flyway
- Lombok

## 📝 Licencia
Proyecto desarrollado por Alejo Beliz durante el curso de Spring Boot 3: aplica buenas prácticas y protege una API Rest.
