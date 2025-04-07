package com.conferencia.manager.services;

import com.conferencia.manager.entity.Persona;
import com.conferencia.manager.entity.Sesion;
import com.conferencia.manager.repository.PersonaRepository;
import com.conferencia.manager.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SesionService {
    private final SesionRepository sesionRepository;
    private final PersonaRepository personaRepository;

    public SesionService(SesionRepository sesionRepository, PersonaRepository personaRepository) {
        this.sesionRepository = sesionRepository;
        this.personaRepository = personaRepository;
    }

    public Sesion crearSesion(LocalDateTime horaInicio, LocalDateTime horaFin) {
        Sesion sesion = new Sesion(horaInicio, horaFin);
        return sesionRepository.save(sesion);
    }

    public boolean agregarPersonaASesion(Long sesionId, Long personaId) {
        Sesion sesionNueva = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada."));
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada."));

        // Obtenemos todas las sesiones en las que la persona ya está asignada
        List<Sesion> sesionesDePersona = sesionRepository.findByPersonas_Id(personaId);

        // Verificamos si alguna de estas sesiones se solapa con la sesión nueva
        for (Sesion sesionExistente : sesionesDePersona) {
            if (sesionExistente.conflictoDeHorarios(sesionNueva.getHoraInicio(), sesionNueva.getHoraFin())) {
                return false;  // Existe conflicto de horarios
            }
        }

        // Si no hay conflicto, agregamos la persona a la sesión nueva
        sesionNueva.agregarPersona(persona);
        sesionRepository.save(sesionNueva);
        return true;
    }

    public List<Sesion> obtenerTodasLasSesiones() {
        return sesionRepository.findAll();
    }
}
