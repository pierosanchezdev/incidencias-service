package com.conecta.incidencias.application;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String subirArchivo(MultipartFile archivo);
}
