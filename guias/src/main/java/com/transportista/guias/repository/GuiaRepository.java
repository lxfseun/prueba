/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.transportista.guias.repository;

import com.transportista.guias.model.Guia;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author faxin
 */

@Repository
public class GuiaRepository {
    private final ConcurrentHashMap<String, Guia> guias = new ConcurrentHashMap<>();    
    
    public Guia save(Guia guia) {
        guias.put(guia.getId(), guia);
        return guia;
    }

    public Guia findById(String id) {
        return guias.get(id);
    }

    public Guia delete(String id) {
        return guias.remove(id);
    }

    public List<Guia> findAll() {
        return new ArrayList<>(guias.values());
    }

    public List<Guia> findByTransportistaAndFecha(String transportista, LocalDate fecha) {
        List<Guia> resultado = new ArrayList<>();
        for (Guia guia : guias.values()) {
            if (guia.getTransportista().equals(transportista) && 
                guia.getFecha().equals(fecha)) {
                resultado.add(guia);
            }
        }
        return resultado;
    }    
}
