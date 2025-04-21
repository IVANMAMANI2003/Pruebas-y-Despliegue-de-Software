package pe.edu.upeu.sysalmacen.excepciones;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ModelNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ModelNotFoundException(String message) {
        super(message);
        this.status = null; // Asignar un valor predeterminado si no se proporciona
    }

    public ModelNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}