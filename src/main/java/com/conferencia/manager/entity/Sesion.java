package com.conferencia.manager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "sesiones")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaCreacion;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    @ManyToMany
    @JoinTable(
            name = "sesiones_personas",
            joinColumns = @JoinColumn(name = "sesion_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private List<Persona> personas = new ArrayList<>();

    public Sesion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Sesion(LocalDateTime horaInicio, LocalDateTime horaFin) {
        this();
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public boolean agregarPersona(Persona persona) {
        for (Persona p : personas) {
            if (p.getId().equals(persona.getId())) {
                return false; // La persona ya está en esta sesión.
            }
        }
        return personas.add(persona);
    }

    public boolean conflictoDeHorarios(LocalDateTime inicio, LocalDateTime fin) {
        return !horaFin.isBefore(inicio) && !horaInicio.isAfter(fin);
    }
    @Override
    public String toString() {
        return "Sesion{" +
                "id=" + id +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
