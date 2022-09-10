
package ejercicio.personas.validators;

import ejercicio.personas.annotations.MayoriaDeEdad;
import ejercicio.personas.annotations.SexoCorrecto;
import ejercicio.personas.models.Persona;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidadorSexo implements ConstraintValidator<SexoCorrecto, Persona> {

    @Override
    public boolean isValid(Persona persona, ConstraintValidatorContext cvc) {
        char sexo = persona.getSexo();
        return (sexo == 'M' || sexo == 'F' || sexo == 'X');
    }
    
}
