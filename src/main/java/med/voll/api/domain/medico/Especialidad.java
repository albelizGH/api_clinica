package med.voll.api.domain.medico;

public enum Especialidad {
    ORTOPEDIA("Ortopédia"),
    CARDIOLOGIA("Cardiología"),
    GINECOLOGIA("Ginecología"),
    PEDIATRIA("Pediatra");

    private final String especialidad;

    // Constructor
    Especialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Getter para el valor del campo especialidad
    public String getEspecialidad() {
        return especialidad;
    }

    }
