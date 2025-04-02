package com.conferencia.manager.controller;

import com.conferencia.manager.entity.Persona;
import com.conferencia.manager.entity.Sesion;
import com.conferencia.manager.services.SesionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sesiones")
public class SesionController {
    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @PostMapping
    public Sesion crearSesion(@RequestParam LocalDateTime horaInicio, @RequestParam LocalDateTime horaFin) {
        return sesionService.crearSesion(horaInicio, horaFin);
    }

    @GetMapping
    public List<Sesion> obtenerSesiones() {
        return sesionService.obtenerTodasLasSesiones();
    }

    @PostMapping("/{sesionId}/persona")
    public boolean agregarPersonaASesion(@PathVariable Long sesionId, @PathVariable Long personaId) {
        return sesionService.agregarPersonaASesion(sesionId, personaId);
    }
}
