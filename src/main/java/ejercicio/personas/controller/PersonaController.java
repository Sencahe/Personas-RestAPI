package ejercicio.personas.controller;

import ejercicio.personas.dto.PersonaDTO;
import ejercicio.personas.dto.ResponseMessage;
import ejercicio.personas.models.Persona;
import ejercicio.personas.services.PersonaService;
import ejercicio.personas.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Operation(summary = "Obtener todas las personas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de personas",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaDTO.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("api/personas")
    public ResponseEntity getAllPersonas() {
        try {

            List<Persona> personas = personaService.getAllPersonas();
            List<PersonaDTO> personasDTO = Mapper.mapAll(personas, PersonaDTO.class);

            return new ResponseEntity<List<PersonaDTO>>(personasDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Obtener Persona por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona encontrada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class))}),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("api/persona/{id}")
    public ResponseEntity getPersona(@PathVariable("id") long id) {
        try {
            Persona persona = personaService.getPersonaById(id);
            if (persona == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Persona>(persona, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener Persona por Parametros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona encontrada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class))}),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("api/persona/{numeroDeDocumento}/{tipoDeDocumento}/{pais}/{sexo}")
    public ResponseEntity getPersonaByParams(@PathVariable("numeroDeDocumento") long numeroDeDocumento,
            @PathVariable("tipoDeDocumento") String tipoDeDocumento,
            @PathVariable("pais") String pais,
            @PathVariable("sexo") char sexo) {
        try {
            Persona persona = personaService.getPersonaByParams(numeroDeDocumento, tipoDeDocumento, pais, sexo);
            if (persona == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Persona>(persona, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar Persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona Eliminada",
                content = {
                    @Content}),
        @ApiResponse(responseCode = "404", description = "Persona no encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @DeleteMapping("api/persona/{id}")
    public ResponseEntity deletePersona(@PathVariable("id") long id) {
        try {

            Persona persona = personaService.getPersonaById(id);
            if (persona == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }

            personaService.deletePersona(persona);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona Eliminada"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear Persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona Creada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Pais no encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PostMapping("api/persona")
    public ResponseEntity savePersona(@Valid @RequestBody PersonaDTO personaDTO) {

        try {

            Persona persona = Mapper.map(personaDTO, Persona.class);
            personaService.savePersona(persona);
            personaDTO = Mapper.map(persona, PersonaDTO.class);

            return new ResponseEntity<PersonaDTO>(personaDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar Persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona Actualizada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Pais y/o Persona no encontrada",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PutMapping("api/persona")
    public ResponseEntity updatePersona(@Valid @RequestBody PersonaDTO personaDTO) {
        try {

            Persona persona = Mapper.map(personaDTO, Persona.class);

            if (personaService.getPersona(persona) == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona no encontrada para actualizar"), HttpStatus.NOT_FOUND);
            }

            personaService.savePersona(persona);
            personaDTO = Mapper.map(persona, PersonaDTO.class);

            return new ResponseEntity<PersonaDTO>(personaDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar Hijo a Padre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hijo Asignado a Padre",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))}),
        @ApiResponse(responseCode = "404", description = "Pais no encontrado",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PostMapping("api/personas/{idPadre}/padre/{idHijo}")
    public ResponseEntity setHijoToPadre(@PathVariable("idPadre") long idPadre, @PathVariable("idHijo") long idHijo) {
        try {
            // Validaciones basicas
            if (idHijo == idPadre) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Id de Padre e Hij@ no pueden ser iguales"), HttpStatus.BAD_REQUEST);
            }
            Persona padre = personaService.getPersonaById(idPadre);
            if (padre == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona Padre no encontrado"), HttpStatus.NOT_FOUND);
            }

            Persona hijo = personaService.getPersonaById(idHijo);
            if (hijo == null) {
                return new ResponseEntity<ResponseMessage>(new ResponseMessage("Persona Hij@ no encontrad@"), HttpStatus.NOT_FOUND);
            }
            // Validacion que hijo no sea abuelo, bisabuelo, etc...
            Persona abuelo = padre.getPadre();
            while (true) {
                if (abuelo != null) {
                    if (abuelo.equals(hijo)) {
                        return new ResponseEntity<ResponseMessage>(new ResponseMessage("El Hijo es (por lo menos) Abuelo del padre indicado en la peticion"),
                                HttpStatus.BAD_REQUEST);
                    } else {
                        abuelo = abuelo.getPadre();
                    }
                } else {
                    break;
                }
            }

            // agrego padre a hijo
            hijo.setPadre(padre);

            // agrego hijo a padre
            Set<Persona> hijos = padre.getHijos();
            hijos.add(hijo);
            padre.setHijos(hijos);

            // commit
            personaService.savePersona(hijo);
            personaService.savePersona(padre);

            String message = "El id " + idPadre + " es Padre del id " + idHijo;
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(message), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
