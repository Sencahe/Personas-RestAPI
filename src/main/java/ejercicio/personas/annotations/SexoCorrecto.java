package ejercicio.personas.annotations;

import ejercicio.personas.validators.ValidadorSexo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidadorSexo.class})
public @interface SexoCorrecto {

    String message() default "El sexo debe ser 'M', 'F' o 'X'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
