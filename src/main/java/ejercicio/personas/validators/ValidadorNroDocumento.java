
package ejercicio.personas.validators;

import ejercicio.personas.entities.Persona;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ejercicio.personas.annotations.ContactoRequerido;
import ejercicio.personas.annotations.NroDocumentoCorrecto;


public class ValidadorNroDocumento implements ConstraintValidator<NroDocumentoCorrecto, Persona> {

    @Override
    public boolean isValid(Persona persona, ConstraintValidatorContext cvc) {
        return persona.getNumeroDeDocumento() != 0;
    }
    
}
