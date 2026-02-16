package com.tufm.central_system.repository; // <--- PAQUETE CON GUION BAJO

// IMPORTANTE: Aquí importamos la clase Medicion desde su paquete correcto
import com.tufm.central_system.model.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
}