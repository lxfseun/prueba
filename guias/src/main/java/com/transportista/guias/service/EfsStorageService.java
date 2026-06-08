/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.service;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
public class EfsStorageService {
    
    private static final String EFS_BASE_PATH = "/mnt/efs/guias/";
    
    public EfsStorageService() {
        try {
            Files.createDirectories(Paths.get(EFS_BASE_PATH));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public String guardarEnEfs(String id, String transportista, String contenido) throws IOException {
        String directorio = EFS_BASE_PATH + transportista + "/";
        Files.createDirectories(Paths.get(directorio));
        String rutaCompleta = directorio + id + ".txt";
        Files.writeString(Paths.get(rutaCompleta), contenido);
        return rutaCompleta;
    }

    public String leerDesdeEfs(String ruta) throws IOException {
        return Files.readString(Paths.get(ruta));
    }

    public void actualizarEnEfs(String ruta, String nuevoContenido) throws IOException {
        Files.writeString(Paths.get(ruta), nuevoContenido);
    }

    public void eliminarDeEfs(String ruta) throws IOException {
        Files.deleteIfExists(Paths.get(ruta));
    }
}
