package com.conferencia.manager.entity;

import com.conferencia.manager.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonaTest {
    @Autowired
    private PersonaRepository personaRepository;

    @Test
    void testPersistenciaPersona() {
        // Crear una nueva instancia de Persona
        Persona persona = new Persona("Juan Pérez");

        // Guardar la persona en la base de datos
        Persona personaGuardada = personaRepository.save(persona);

        // Verificar que se ha asignado un ID
        assertNotNull(personaGuardada.getId());
        assertEquals("Juan Pérez", personaGuardada.getNombre());

        // Recuperar la persona y verificar sus datos
        Persona personaRecuperada = personaRepository.findById(personaGuardada.getId()).orElse(null);
        assertNotNull(personaRecuperada);
        assertEquals(personaGuardada.getId(), personaRecuperada.getId());
        assertEquals("Juan Pérez", personaRecuperada.getNombre());
    }
    @Test
    void testGetterSetter() {
        // Crear una instancia de Persona
        Persona persona = new Persona();

        // Usar los setters para establecer valores
        persona.setId(1L);
        persona.setNombre("Carlos García");

        // Usar los getters para verificar los valores
        assertEquals(1L, persona.getId());
        assertEquals("Carlos García", persona.getNombre());
    }
    @Test
    void testConstructorSinParametros() {
        // Crear una instancia usando el constructor sin parámetros
        Persona persona = new Persona();

        // Verificar que los valores iniciales sean nulos
        assertNull(persona.getId());
        assertNull(persona.getNombre());
    }

    @Test
    void testConstructorConNombre() {
        // Crear una instancia usando el constructor con parámetros
        Persona persona = new Persona("Luis Martínez");

        // Verificar que el nombre se haya asignado correctamente
        assertNull(persona.getId());
        assertEquals("Luis Martínez", persona.getNombre());
    }

}