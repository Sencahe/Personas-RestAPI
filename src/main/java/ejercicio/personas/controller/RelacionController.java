package ejercicio.personas.controller;

import ejercicio.personas.dto.ResponseMsgDTO;
import ejercicio.personas.entities.Persona;
import ejercicio.personas.services.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
            // Validaciones basicas
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

            // son hermanos ?
            if (persona1.getPadre().equals(persona2.getPadre())) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("La relacion es de HERMAN@S"), HttpStatus.OK);
            }

            // primos ?
            Persona padre1 = persona1.getPadre();
            Persona padre2 = persona2.getPadre();
            if (padre1.getPadre().equals(padre2.getPadre())) {
                 return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("La relacion es de PRIM@S"), HttpStatus.OK);
            }

            // sobrino - tio ? 
            if (padre1.getPadre().equals(padre2)) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona2 es TI@ de Persona1"), HttpStatus.OK);
            }
            if (padre2.getPadre().equals(padre1)) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(" Persona1 es TI@ de Persona2"), HttpStatus.OK);
            }

            // sin relacion
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("No se encontro relacion entre las dos personas"), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity(new ResponseMsgDTO("No se encontro relacion entre las dos personas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
