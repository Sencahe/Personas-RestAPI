package ejercicio.personas.repositories;

import ejercicio.personas.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    
    @Query( value = "SELECT * FROM personas p WHERE p.numero_de_documento = :numeroDeDocumento "
            + "and UPPER(p.tipo_de_documento) = UPPER(:tipoDeDocumento) "
            + "and UPPER(p.pais) = UPPER(:pais) "
            + "and p.sexo = UPPER(:sexo)",
            nativeQuery = true)
    public Persona findByIdentity(@Param("numeroDeDocumento") long numeroDeDocumento,
            @Param("tipoDeDocumento") String tipoDeDocumento,
            @Param("pais") String pais,
            @Param("sexo") char sexo);

}
