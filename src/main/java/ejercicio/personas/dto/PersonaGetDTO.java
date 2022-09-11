package ejercicio.personas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ejercicio.personas.entities.Persona;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaGetDTO implements Serializable{

    private long personaId;

    private String nombre;

    private String apellido;

    private long numeroDeDocumento;

    private String tipoDeDocumento;

    private char sexo;

    private String pais;

    private LocalDate fechaDeNacimiento;

    private String email;

    private String telefono;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hijos", "padre"})
    private PersonaDTO padre;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hijos", "padre"})
    private Set<PersonaDTO> hijos;

}
