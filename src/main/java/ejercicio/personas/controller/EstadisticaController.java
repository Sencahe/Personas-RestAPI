package ejercicio.personas.controller;

import ejercicio.personas.dto.EstadisticaDTO;
import ejercicio.personas.dto.ResponseMsgDTO;
import ejercicio.personas.entities.Persona;
import ejercicio.personas.services.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CacheConfig(cacheNames = "controllersCache")
@RestController
@RequestMapping("api")
public class EstadisticaController {

    @Autowired
    private PersonaService personaService;

    @Operation(summary = "Obtener Estadisticas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadisticas",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadisticaDTO.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content)})
    @ResponseBody  
    @Cacheable
    @GetMapping("estadisticas")
    public ResponseEntity getEstadisticas() {
        try {
            List<Persona> personas = personaService.getAllPersonas();
            
            int totalMujeres = 0;
            int totalHombres = 0;
            float totalPersonas = personas.size();
            float totalArgentinos = 0;
            float porcentajeArgentinos = 0;

            for (Persona persona : personas) {
                char sexo = persona.getSexo();
                String pais = persona.getPais().toUpperCase();
                if (sexo == 'M') {
                    totalHombres += 1;
                }
                if (sexo == 'F') {
                    totalMujeres += 1;
                }
                if (pais.equals("ARGENTINA")) {
                    totalArgentinos += 1;
                }
            }

            porcentajeArgentinos = totalPersonas == 0 ? 0 : (totalArgentinos * 100) / totalPersonas;

            EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
            estadisticaDTO.setTotalHombres(totalHombres);
            estadisticaDTO.setTotalMujeres(totalMujeres);
            estadisticaDTO.setPorcentajeArgentinos(String.format("%.2f", porcentajeArgentinos));

            return new ResponseEntity<EstadisticaDTO>(estadisticaDTO, HttpStatus.OK);
            
        } catch (Exception e) {       
            return new ResponseEntity<ResponseMsgDTO>(new ResponseMsgDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
