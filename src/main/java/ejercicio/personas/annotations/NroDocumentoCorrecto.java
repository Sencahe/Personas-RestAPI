package ejercicio.personas.annotations;

import ejercicio.personas.validators.ValidadorEmail;
import ejercicio.personas.validators.ValidadorNroDocumento;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorNroDocumento.class})
public @interface NroDocumentoCorrecto {

    String message() default "El numero de documento no puede ser 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
