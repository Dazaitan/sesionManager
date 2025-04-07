package com.conferencia.manager.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SesionTest {

    @Test
    void testAgregarPersonaExitoso() {
        Sesion sesion = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        Persona persona = new Persona();
        persona.setId(1L);

        // Llama al método agregarPersona
        boolean resultado = sesion.agregarPersona(persona);

        // Verifica el resultado
        assertTrue(resultado); // Debe devolver true
        assertEquals(1, sesion.getPersonas().size()); // La lista ahora debe tener 1 elemento
        assertEquals(persona, sesion.getPersonas().get(0)); // La persona añadida debe ser la misma
    }
    @Test
    void testAgregarPersonaDuplicada() {
        // Lista inicial con una persona
        Sesion sesion = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        Persona persona = new Persona();
        persona.setId(1L);
        sesion.agregarPersona(persona); // Agregamos la persona inicialmente

        // Intentar agregar la misma persona nuevamente
        boolean resultado = sesion.agregarPersona(persona);

        // Verifica el resultado
        assertFalse(resultado); // Debe devolver false
        assertEquals(1, sesion.getPersonas().size()); // La lista debe seguir teniendo solo 1 elemento
    }
}