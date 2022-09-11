package ejercicio.personas.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ejercicio.personas.annotations.ContactoRequerido;
import ejercicio.personas.annotations.EmailCorrecto;
import ejercicio.personas.annotations.MayoriaDeEdad;
import ejercicio.personas.annotations.NroDocumentoCorrecto;
import ejercicio.personas.annotations.SexoCorrecto;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import javax.persistence.OneToMany;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@ContactoRequerido
@MayoriaDeEdad
@SexoCorrecto
@EmailCorrecto
@NroDocumentoCorrecto
@Table(
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"pais", "numeroDeDocumento", "tipoDeDocumento", "sexo"})
        },
        name = "PERSONAS"
)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private char sexo;

    @NotNull
    private String pais;

    @NotNull
    private LocalDate fechaDeNacimiento;

    private String email;

    private String telefono;
    
    private Date created;
    
    private Date updated;

    // Self relation
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hijos", "padre"})
    private Persona padre;

    @OneToMany(mappedBy = "padre")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hijos", "padre"})
    private Set<Persona> hijos;
    
   

}
