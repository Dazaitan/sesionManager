package com.conferencia.manager.repository;

import com.conferencia.manager.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByPersonas_Id(Long personaId);
}
