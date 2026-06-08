/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.service;

import com.transportista.guias.model.Guia;
import com.transportista.guias.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author faxin
 */

@Service
public class GuiaService {
    
    @Autowired
    private GuiaRepository guiaRepository;
    
    @Autowired
    private EfsStorageService efsStorageService;
    
    @Autowired
    private S3Service s3Service;
    
    public Guia crearGuia(String transportista, String contenido) throws IOException {
        String id = UUID.randomUUID().toString();
        Guia guia = new Guia(id, transportista, contenido);
        String rutaLocal = efsStorageService.guardarEnEfs(id, transportista, contenido);
        guia.setRutaLocal(rutaLocal);
        guiaRepository.save(guia);
        return guia;
    }
    
    public Guia subirACS3(String id) throws IOException {
        Guia guia = guiaRepository.findById(id);
        if (guia == null) throw new RuntimeException("Guía no encontrada");
        
        String s3Key = guia.generarS3Key();
        s3Service.subirACS3(s3Key, guia.getRutaLocal());
        guia.setS3Key(s3Key);
        guia.setEstado("EN_S3");
        guiaRepository.save(guia);
        return guia;
    }
    
    public String descargarGuia(String id) throws IOException {
        Guia guia = guiaRepository.findById(id);
        if (guia == null) throw new RuntimeException("Guía no encontrada");
        if (guia.getS3Key() == null) throw new RuntimeException("La guía no está en S3");
        return s3Service.leerContenidoDeS3(guia.getS3Key());
    }
    
    public Guia modificarGuia(String id, String nuevoContenido) throws IOException {
        Guia guia = guiaRepository.findById(id);
        if (guia == null) throw new RuntimeException("Guía no encontrada");
        
        efsStorageService.actualizarEnEfs(guia.getRutaLocal(), nuevoContenido);
        guia.setContenido(nuevoContenido);
        
        if (guia.getS3Key() != null) {
            s3Service.subirACS3(guia.getS3Key(), guia.getRutaLocal());
            guia.setEstado("ACTUALIZADA");
        }
        guiaRepository.save(guia);
        return guia;
    }
    
    public void eliminarGuia(String id) throws IOException {
        Guia guia = guiaRepository.findById(id);
        if (guia == null) throw new RuntimeException("Guía no encontrada");
        
        efsStorageService.eliminarDeEfs(guia.getRutaLocal());
        if (guia.getS3Key() != null) s3Service.eliminarDeS3(guia.getS3Key());
        guiaRepository.delete(id);
    }
    
    public List<Guia> consultarPorTransportistaYFecha(String transportista, LocalDate fecha) {
        return guiaRepository.findByTransportistaAndFecha(transportista, fecha);
    }
    
    public List<Guia> obtenerTodas() {
        return guiaRepository.findAll();
    }
    
    public Guia obtenerPorId(String id) {
        return guiaRepository.findById(id);
    }
}