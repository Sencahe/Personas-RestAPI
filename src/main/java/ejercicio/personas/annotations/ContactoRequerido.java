package ejercicio.personas.annotations;

import ejercicio.personas.validators.ValidadorContacto;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorContacto.class})
public @interface ContactoRequerido {
    
    String message() default "Una persona debe tener por lo menos un medio de contacto (telefono o email)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
