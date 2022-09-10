package ejercicio.personas.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage implements Serializable{

    public static final String NOT_FOUND_BY_ID = "No se encontro la entidad con el Id provisto en la peticion.";
    public static final String DELETED_OK = "El registro ha sido eliminado correctamente.";

    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

}
