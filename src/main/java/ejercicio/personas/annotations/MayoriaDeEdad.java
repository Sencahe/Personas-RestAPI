package ejercicio.personas.annotations;

import ejercicio.personas.validators.ValidadorEdad;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorEdad.class})
public @interface MayoriaDeEdad {

    String message() default "La persona debe ser mayor de edad";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
