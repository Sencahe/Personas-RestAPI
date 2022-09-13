package ejercicio.personas.controller;

import ejercicio.personas.dto.ResponseMsgDTO;
import ejercicio.personas.entities.Persona;
import ejercicio.personas.services.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CacheConfig(cacheNames = "controllersCache")
@RequestMapping("api")
public class RelacionController {

    @Autowired
    private PersonaService personaService;

    @Operation(summary = "Encontrar relacion entre dos personas (Herman@s, Prim@s o Ti@s)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relacion encontrada",
                content = @Content),
        @ApiResponse(responseCode = "202", description = "No hay relacion",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "No se encontraron las personas indicadas",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("relaciones/{idPersona1}/{idPersona2}")
    public ResponseEntity getRelacion(@PathVariable("idPersona1") long idPersona1, @PathVariable("idPersona2") long idPersona2) {
        try {
            // Validaciones de input
            if (idPersona1 == idPersona2) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Id de Persona1 y Persona2 no puede ser iguales"), HttpStatus.BAD_REQUEST);
            }
            Persona persona1 = personaService.getPersonaById(idPersona1);
            if (persona1 == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona1 no encontrada"), HttpStatus.NOT_FOUND);
            }

            Persona persona2 = personaService.getPersonaById(idPersona2);
            if (persona2 == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona2 no encontrada"), HttpStatus.NOT_FOUND);
            }
            
            Persona padre1 = persona1.getPadre();
            Persona padre2 = persona2.getPadre();
            if (padre1 != null && padre2 != null) {
                // son hermanos ?
                if (padre1.getPersonaId() == padre2.getPersonaId()) {
                    return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("La relacion es de HERMAN@S"), HttpStatus.OK);
                }
                Persona abuelo1 = padre1.getPadre();
                Persona abuelo2 = padre2.getPadre();
                // primos ?
                if (abuelo1 != null && abuelo2 != null) { 
                    if (abuelo1.getPersonaId() == abuelo2.getPersonaId()) {
                        return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("La relacion es de PRIM@S"), HttpStatus.OK);
                    }
                }
                // sobrino - tio ? 
                // Si el abuelo de persona1 es igual a al padre de persona2, persona2 es tio de persona1 y viceversa
                if (abuelo1 != null) {
                    if (abuelo1.getPersonaId() == padre2.getPersonaId()) {
                        return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona2 es TI@ de Persona1"), HttpStatus.OK);
                    }
                } else if (abuelo2 != null) {
                    if (abuelo2.getPersonaId() == padre1.getPersonaId()) {
                        return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona1 es TI@ de Persona2"), HttpStatus.OK);
                    }
                }
            }
            // sin relacion
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("No se encontro relacion entre las dos personas"), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
