/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.controller;

import com.transportista.guias.model.Guia;
import com.transportista.guias.service.GuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/guias")
public class GuiaController {
    
    @Autowired
    private GuiaService guiaService;
    
    @PostMapping
    public ResponseEntity<?> crearGuia(@RequestBody Map<String, String> request) {
        try {
            Guia guia = guiaService.crearGuia(request.get("transportista"), request.get("contenido"));
            return ResponseEntity.status(HttpStatus.CREATED).body(guia);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/upload-to-s3")
    public ResponseEntity<?> subirACS3(@PathVariable String id) {
        try {
            Guia guia = guiaService.subirACS3(id);
            return ResponseEntity.ok("Subida a S3: " + guia.getS3Key());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}/download")
    public ResponseEntity<?> descargarGuia(@PathVariable String id) {
        try {
            String contenido = guiaService.descargarGuia(id);
            return ResponseEntity.ok(contenido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarGuia(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            Guia guia = guiaService.modificarGuia(id, request.get("contenido"));
            return ResponseEntity.ok(guia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarGuia(@PathVariable String id) {
        try {
            guiaService.eliminarGuia(id);
            return ResponseEntity.ok("Eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> consultarGuias(
            @RequestParam(required = false) String transportista,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        if (transportista != null && fecha != null) {
            return ResponseEntity.ok(guiaService.consultarPorTransportistaYFecha(transportista, fecha));
        }
        return ResponseEntity.ok(guiaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerGuia(@PathVariable String id) {
        Guia guia = guiaService.obtenerPorId(id);
        if (guia == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrada");
        return ResponseEntity.ok(guia);
    }
}
