package ejercicio.personas.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTO implements Serializable {

    private long personaId;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    private long numeroDeDocumento;

    @NotNull
    private String tipoDeDocumento;

    @NotNull
    private String pais;

    @NotNull
    private char sexo;

    @NotNull
    private LocalDate fechaDeNacimiento;

    private String email;

    private String telefono;

}
