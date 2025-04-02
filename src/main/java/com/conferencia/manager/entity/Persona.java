package com.conferencia.manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Persona() {}

    public Persona(String nombre) {
        this.nombre = nombre;
    }
}
