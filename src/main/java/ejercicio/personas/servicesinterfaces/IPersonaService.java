package ejercicio.personas.servicesinterfaces;

import ejercicio.personas.entities.Persona;
import java.util.List;

public interface IPersonaService {

    public List<Persona> getAllPersonas();

    public Persona getPersona(Persona persona);

    public Persona getPersonaById(long id);

    public Persona getPersonaByParams(long numeroDeDocumento,
            String tipoDeDocumento, String pais, char sexo);

    public void savePersona(Persona persona);

    public void deletePersona(Persona persona);
}
