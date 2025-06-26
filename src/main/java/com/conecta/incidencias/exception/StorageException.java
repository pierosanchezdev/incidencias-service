package com.conecta.incidencias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class StorageException extends RuntimeException {
    public StorageException(String mensaje) {
        super(mensaje);
    }

    public StorageException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
