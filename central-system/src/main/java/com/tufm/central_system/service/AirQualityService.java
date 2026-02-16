package com.tufm.central_system.service;

// Importamos la Entidad y el Repositorio
import com.tufm.central_system.model.Medicion;
import com.tufm.central_system.repository.MedicionRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AirQualityService {

    private final MedicionRepository repository;

    public AirQualityService(MedicionRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "mediciones-aire", groupId = "grupo-gestion-aire")
    public void procesarMensaje(String mensaje) {
        System.out.println("📩 Mensaje recibido: " + mensaje);

        try {
            // Lógica de parsing (extraer datos del texto)
            String[] partes = mensaje.split("\\|");

            String sensorId = partes[0].split(":")[1].trim();
            Double temperatura = Double.parseDouble(partes[1].split(":")[1].trim().replace(",", "."));
            Double co2 = Double.parseDouble(partes[2].split(":")[1].trim().replace(",", "."));

            // Crear y guardar el objeto
            Medicion medicion = new Medicion();
            medicion.setSensorId(sensorId);
            medicion.setTemperatura(temperatura);
            medicion.setCo2(co2);
            medicion.setFechaRegistro(LocalDateTime.now());

            repository.save(medicion);
            System.out.println("✅ Dato guardado en BBDD: ID " + medicion.getId());

        } catch (Exception e) {
            System.err.println("❌ Error al procesar mensaje: " + e.getMessage());
            // Imprimimos el error para saber qué falla
            e.printStackTrace();
        }
    }
}