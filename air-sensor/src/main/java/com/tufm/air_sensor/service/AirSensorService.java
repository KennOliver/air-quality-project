package com.tufm.air_sensor.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AirSensorService {
    private final Random random = new Random();
    @Scheduled(fixedRate = 5000)
    public void simularMedicion() {

            double probabilidad = random.nextDouble();

            double temperatura;
            double co2;

            if (probabilidad < 0.9) {

                temperatura = 20 + (5 * random.nextDouble());
                // CO2 entre 400 y 800 ppm (partes por millón)
                co2 = 400 + (400 * random.nextDouble());

            } else {
                // 10% de las veces: VALORES CRÍTICOS (Incendio o fallo)
                // Temp disparada entre 35 y 50 grados
                temperatura = 35 + (15 * random.nextDouble());
                // CO2 peligroso entre 1000 y 2000 ppm
                co2 = 1000 + (1000 * random.nextDouble());

                System.out.println("⚠️ ALERTA: VALORES CRÍTICOS DETECTADOS");
            }

            System.out.printf("Sensor activo -> Temp: %.2f ºC | CO2: %.2f ppm%n", temperatura, co2);
        }

}
