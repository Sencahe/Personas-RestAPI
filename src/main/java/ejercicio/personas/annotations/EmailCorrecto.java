package ejercicio.personas.annotations;

import ejercicio.personas.validators.ValidadorEmail;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorEmail.class})
public @interface EmailCorrecto {

    String message() default "El formato del email ingresado es invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
