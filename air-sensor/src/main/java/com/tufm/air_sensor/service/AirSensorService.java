package com.tufm.air_sensor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AirSensorService {

    // --- VARIABLES DE CONFIGURACIÓN  ---

    @Value("${sensor.probabilidad-critica}")
    private double probabilidadCritica;

    @Value("${sensor.temp.normal.base}")
    private double tempNormalBase;

    @Value("${sensor.temp.normal.rango}")
    private double tempNormalRango;

    @Value("${sensor.temp.critica.base}")
    private double tempCriticaBase;

    @Value("${sensor.temp.critica.rango}")
    private double tempCriticaRango;

    // --- HERRAMIENTAS ---
    private final Random random = new Random();
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Constructor
    public AirSensorService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void simularMedicion() {

        double probabilidad = random.nextDouble();
        double temperatura;
        double co2;

        if (probabilidad < probabilidadCritica) {
            // Caso NORMAL
            temperatura = tempNormalBase + (tempNormalRango * random.nextDouble());
            co2 = 400 + (400 * random.nextDouble());
        } else {
            // Caso CRITICO
            temperatura = tempCriticaBase + (tempCriticaRango * random.nextDouble());

            co2 = 1000 + (1000 * random.nextDouble());

            System.out.println("⚠️ ALERTA: VALORES CRÍTICOS DETECTADOS");
        }

        String mensaje = String.format("SensorID: 1 | Temp: %.2f | CO2: %.2f", temperatura, co2);
        kafkaTemplate.send("mediciones-aire", mensaje);

        System.out.println("Mensaje enviado a Kafka: " + mensaje);
    }
}