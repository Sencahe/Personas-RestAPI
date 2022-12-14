package ejercicio.personas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@Component
@EnableCaching
@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class PersonasApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonasApplication.class, args);
    }

}
