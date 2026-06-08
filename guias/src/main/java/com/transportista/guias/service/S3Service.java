/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author faxin
 */
@Service
public class S3Service {
    @Autowired
    private AmazonS3 s3Client;
    
    @Value("${aws.s3.bucket}")
    private String bucketName;
    
    public void subirACS3(String key, String rutaLocal) throws IOException {
        File archivo = new File(rutaLocal);
        s3Client.putObject(bucketName, key, archivo);
        System.out.println("Subido a S3: " + key);
    }
    
    public String leerContenidoDeS3(String key) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        String contenido = new String(inputStream.readAllBytes());
        inputStream.close();
        return contenido;
    }
    
    public void eliminarDeS3(String key) {
        s3Client.deleteObject(bucketName, key);
    }
}
