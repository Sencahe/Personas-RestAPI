package ejercicio.personas.controller;

import ejercicio.personas.dto.PersonaDTO;
import ejercicio.personas.dto.PersonaGetDTO;
import ejercicio.personas.dto.ResponseMsgDTO;
import ejercicio.personas.entities.Persona;
import ejercicio.personas.services.PersonaService;
import ejercicio.personas.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CacheConfig(cacheNames = "controllersCache")
@Slf4j
@RequestMapping("api")
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
    @Cacheable(key = "#root.methodName")
    @GetMapping("personas")
    public ResponseEntity getAllPersonas() {
        try {

            List<Persona> personas = personaService.getAllPersonas();
            List<PersonaDTO> personasDTO = Mapper.mapAll(personas, PersonaDTO.class);

            return new ResponseEntity<List<PersonaDTO>>(personasDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Obtener Persona por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona encontrada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaGetDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("persona/{id}")
    public ResponseEntity getPersona(@PathVariable("id") long id) {
        try {
            Persona persona = personaService.getPersonaById(id);
            if (persona == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }

            PersonaGetDTO personaGetDTO = Mapper.map(persona, PersonaGetDTO.class);
            return new ResponseEntity<PersonaGetDTO>(personaGetDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener Persona por Identidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona encontrada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaGetDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @GetMapping("persona/identidad")
    public ResponseEntity getPersonaByParams(@RequestParam("numeroDeDocumento") long numeroDeDocumento,
            @RequestParam("tipoDeDocumento") String tipoDeDocumento,
            @RequestParam("pais") String pais,
            @RequestParam("sexo") char sexo) {

        try {
            Persona persona = personaService.getPersonaByIdentity(numeroDeDocumento, tipoDeDocumento, pais, sexo);
            if (persona == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }
            PersonaGetDTO personaGetDTO = Mapper.map(persona, PersonaGetDTO.class);
            return new ResponseEntity<PersonaGetDTO>(personaGetDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @DeleteMapping("persona/{id}")
    public ResponseEntity deletePersona(@PathVariable("id") long id) {
        try {

            Persona persona = personaService.getPersonaById(id);
            if (persona == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona no encontrada"), HttpStatus.NOT_FOUND);
            }
            // eliminar la referencia en los hijos
            Set<Persona> hijos = persona.getHijos();
            if (hijos != null) {
                for (Persona hijo : hijos) {
                    hijo.setPadre(null);
                    personaService.savePersona(hijo);
                }
            }
            // eliminar la entidad
            personaService.deletePersona(persona);

            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona Eliminada"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear Persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona Creada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PostMapping("persona")
    public ResponseEntity savePersona(@Valid @RequestBody PersonaDTO personaDTO) {

        try {
            Persona persona = Mapper.map(personaDTO, Persona.class);
            persona.setPersonaId(0);
            persona.setHijos(new HashSet());
            persona.setCreated(new Date());
            persona.setUpdated(new Date());
            Persona newPersona = personaService.savePersona(persona);
            personaDTO = Mapper.map(newPersona, PersonaDTO.class);

            return new ResponseEntity<PersonaDTO>(personaDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar Persona")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Persona Actualizada",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PutMapping("persona")
    public ResponseEntity updatePersona(@Valid @RequestBody PersonaDTO personaDTO) {
        try {

            Persona persona = personaService.getPersonaById(personaDTO.getPersonaId());

            if (persona == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona no encontrada para actualizar"), HttpStatus.NOT_FOUND);
            }

            //  Actualizar solo los valores que no esten definidios (o sean nulos) en el request
            persona.setNombre(personaDTO.getNombre() == null ? persona.getNombre() : personaDTO.getNombre());
            persona.setApellido(personaDTO.getApellido() == null ? persona.getApellido() : personaDTO.getApellido());
            persona.setNumeroDeDocumento(personaDTO.getNumeroDeDocumento() == 0 ? persona.getNumeroDeDocumento() : personaDTO.getNumeroDeDocumento());
            persona.setTipoDeDocumento(personaDTO.getTipoDeDocumento() == null ? persona.getTipoDeDocumento() : personaDTO.getTipoDeDocumento());
            persona.setPais(personaDTO.getPais() == null ? persona.getPais() : personaDTO.getPais());
            persona.setSexo(personaDTO.getSexo() == 0 ? persona.getSexo() : personaDTO.getSexo());
            persona.setFechaDeNacimiento(personaDTO.getFechaDeNacimiento() == null ? persona.getFechaDeNacimiento() : personaDTO.getFechaDeNacimiento());
            persona.setEmail(personaDTO.getEmail() == null ? persona.getEmail() : personaDTO.getEmail());
            persona.setTelefono(personaDTO.getTelefono() == null ? persona.getTelefono() : personaDTO.getTelefono());

            persona.setUpdated(new Date());

            Persona updatedPersona = personaService.savePersona(persona);

            personaDTO = Mapper.map(updatedPersona, PersonaDTO.class);
            return new ResponseEntity<PersonaDTO>(personaDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar Hij@ a Padre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hij@ Asignado a Padre",
                content = @Content),
        @ApiResponse(responseCode = "202", description = "Hij@ y Padre ya estan relacionados",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "Hij@ es padre, abuelo, etc de Padre",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Padre y/o Hij@ no encontrados",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody
    @PostMapping("personas/{idPadre}/padre/{idHijo}")
    public ResponseEntity addHijoToPadre(@PathVariable("idPadre") long idPadre, @PathVariable("idHijo") long idHijo) {
        try {
            // Validaciones basicas
            if (idHijo == idPadre) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Id de Padre e Hij@ no pueden ser iguales"), HttpStatus.FORBIDDEN);
            }

            Persona padre = personaService.getPersonaById(idPadre);
            if (padre == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona Padre no encontrado"), HttpStatus.NOT_FOUND);
            }

            Persona hijo = personaService.getPersonaById(idHijo);
            if (hijo == null) {
                return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Persona Hij@ no encontrad@"), HttpStatus.NOT_FOUND);
            }

            if (hijo.getPadre() != null) {
                if (hijo.getPadre().getPersonaId() == padre.getPersonaId()) {
                    return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("La relacion Padre e Hij@ ya esta establecida"), HttpStatus.ACCEPTED);
                }
            }
            // Validacion que hijo no sea abuelo, bisabuelo, etc...
            Persona abuelo = padre.getPadre();
            while (true) {
                if (abuelo != null) {
                    if (abuelo.getPersonaId() == hijo.getPersonaId()) {
                        return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO("Hij@ es Padre o (por lo menos) abuelo del padre indicado en la peticion"),
                                HttpStatus.FORBIDDEN);
                    } else {
                        abuelo = abuelo.getPadre();
                    }
                } else {
                    break;
                }
            }
            // agregar padre a hijo
            hijo.setPadre(padre);
            // agregar hijo a padre
            Set<Persona> hijos = padre.getHijos();
            if (hijos == null) {
                hijos = new HashSet();
            }
            hijos.add(hijo);
            padre.setHijos(hijos);
            // commit
            personaService.savePersona(padre);
            personaService.savePersona(hijo);

            String message = "El id " + idPadre + " fue asignado como Padre del id " + idHijo;
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(message), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
