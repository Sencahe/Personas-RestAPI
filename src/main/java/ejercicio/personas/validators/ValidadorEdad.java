
package ejercicio.personas.validators;

import ejercicio.personas.annotations.MayoriaDeEdad;
import ejercicio.personas.models.Persona;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidadorEdad implements ConstraintValidator<MayoriaDeEdad, Persona> {

    @Override
    public boolean isValid(Persona persona, ConstraintValidatorContext cvc) {
        LocalDate fechaDeNacimiento = persona.getFechaDeNacimiento();
        LocalDate now = LocalDate.now();
        int age = (int) ChronoUnit.YEARS.between( fechaDeNacimiento , now );
        return age >= 18;
    }
    
}
