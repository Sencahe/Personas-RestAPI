
package ejercicio.personas.validators;

import ejercicio.personas.entities.Persona;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ejercicio.personas.annotations.ContactoRequerido;


public class ValidadorContacto implements ConstraintValidator<ContactoRequerido, Persona> {

    @Override
    public boolean isValid(Persona persona, ConstraintValidatorContext cvc) {
        return (persona.getEmail() != null || persona.getTelefono() != null);
    }
    
}
