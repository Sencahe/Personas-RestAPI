package ejercicio.personas.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTO implements Serializable {

    private long personaId;

    private String nombre;

    private String apellido;

    private long numeroDeDocumento;

    private String tipoDeDocumento;

    private String pais;

    private char sexo;

    private LocalDate fechaDeNacimiento;

    private String email;

    private String telefono;

}
