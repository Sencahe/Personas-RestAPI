package ejercicio.personas.validators;

import ejercicio.personas.annotations.EmailCorrecto;
import ejercicio.personas.entities.Persona;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorEmail implements ConstraintValidator<EmailCorrecto, Persona> {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(Persona persona, ConstraintValidatorContext cvc) {
        String email = persona.getEmail();
        if(email == null){
            return true;
        } else {
            return Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches();
        }
        
    }

}
