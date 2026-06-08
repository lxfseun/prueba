/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.model;

import java.time.LocalDate;

/**
 *
 * @author faxin
 */
public class Guia {
    private String id;
    private String transportista;
    private LocalDate fecha;
    private String contenido;
    private String rutaLocal;
    private String s3Key;
    private String estado;

    public Guia() {
    }

    public Guia(String id, String transportista, String contenido) {
        this.id = id;
        this.transportista = transportista;
        this.fecha = LocalDate.now();
        this.contenido = contenido;
        this.estado = "EN_EFS";
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getRutaLocal() {
        return rutaLocal;
    }

    public void setRutaLocal(String rutaLocal) {
        this.rutaLocal = rutaLocal;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String generarS3Key() {
        return String.format("%d%d/%s/guia_%s.pdf", 
            fecha.getYear(), 
            fecha.getMonthValue(), 
            transportista, 
            id);
    }

}
