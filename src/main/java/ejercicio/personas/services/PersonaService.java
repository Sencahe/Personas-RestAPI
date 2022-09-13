package ejercicio.personas.services;

import ejercicio.personas.entities.Persona;
import ejercicio.personas.repositories.PersonaRepository;
import ejercicio.personas.servicesinterfaces.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@CacheConfig(cacheNames = "personasCache")
public class PersonaService implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Cacheable(cacheNames = "personasListCache")
    public List<Persona> getAllPersonas() {
        return (List<Persona>) (List<Persona>) personaRepository.findAll(Sort.by(Sort.Direction.ASC, "personaId"));
    }

    @Override
    public Persona getPersona(Persona persona) {
        return personaRepository.findById(persona.getPersonaId()).orElse(null);
    }

    @Override
    @Cacheable(key = "#id")
    public Persona getPersonaById(long id) {
        Persona persona = personaRepository.findById(id).orElse(null);
        if (persona != null) {
            persona.getHijos().size();
        }
        return persona;
    }

    @Override
    @Caching(put = @CachePut(cacheNames = {"personasCache"}, key = "#persona.personaId"),
            evict = @CacheEvict(cacheNames = {"personasListCache", "controllersCache"}, allEntries = true))
    public Persona savePersona(Persona persona) {
        String tipoDeDocumento = persona.getTipoDeDocumento();
        String pais = persona.getPais();
        persona.setTipoDeDocumento(tipoDeDocumento != null ? tipoDeDocumento.toUpperCase() : null);
        persona.setPais(pais != null ? pais.toUpperCase() : null);
        personaRepository.save(persona);
        return persona;
    }

    @Override
    @Caching(evict = {
        @CacheEvict(cacheNames = {"personasCache"}, key = "#persona.personaId"),
        @CacheEvict(cacheNames = {"personasListCache", "controllersCache"}, allEntries = true)})
    public void deletePersona(Persona persona) {
        personaRepository.delete(persona);
    }

    @Override
    public Persona getPersonaByIdentity(long numeroDeDocumento, String tipoDeDocumento, String pais, char sexo) {
        return personaRepository.findByIdentity(numeroDeDocumento, tipoDeDocumento, pais, sexo);
    }

}
