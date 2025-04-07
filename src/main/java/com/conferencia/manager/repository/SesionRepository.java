package com.conferencia.manager.repository;

import com.conferencia.manager.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByPersonas_Id(Long personaId);
}
