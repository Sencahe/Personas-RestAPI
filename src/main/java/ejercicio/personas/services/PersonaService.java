package ejercicio.personas.services;

import ejercicio.personas.models.Persona;
import ejercicio.personas.repositories.PersonaRepository;
import ejercicio.personas.servicesinterfaces.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "personaCache")
@Service
public class PersonaService implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Async
    @Override
    @Transactional()
    public List<Persona> getAllPersonas() {
        return (List<Persona>) (List<Persona>) personaRepository.findAll();
    }

    @Async
    @Override
    @Transactional()
    public Persona getPersona(Persona persona) {
        return personaRepository.findById(persona.getPersonaId()).orElse(null);
    }

   
    @Override
    @Transactional()
    public Persona getPersonaById(long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Async
    @Override
    @Transactional()
    public void savePersona(Persona persona) {
        persona.setTipoDeDocumento(persona.getTipoDeDocumento().toUpperCase());
        persona.setPais(persona.getPais().toUpperCase());
        personaRepository.save(persona);
    }

    @Async
    @Override
    @Transactional()
    public void deletePersona(Persona persona) {
        personaRepository.delete(persona);
    }

    @Async
    @Transactional()
    @Override
    public Persona getPersonaByParams(long numeroDeDocumento, String tipoDeDocumento, String pais, char sexo) {
        return personaRepository.findByParams(numeroDeDocumento, tipoDeDocumento, pais, sexo);
    }

}
