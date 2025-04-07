package com.conferencia.manager.repository;

import com.conferencia.manager.entity.Persona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class PersonaRepositoryTest {
    @Autowired
    private PersonaRepository personaRepository;
    @Test
    void testSaveAndFindById() {
        // Guardar una nueva persona
        Persona persona = new Persona();
        persona.setNombre("Test Persona");
        persona = personaRepository.save(persona);

        // Verificar que fue guardada correctamente
        assertNotNull(persona.getId());

        // Recuperar la persona por su ID
        Optional<Persona> encontrada = personaRepository.findById(persona.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Test Persona", encontrada.get().getNombre());
    }
    @Test
    void testFindAll() {
        // Guardar varias personas
        Persona persona1 = new Persona();
        persona1.setNombre("Persona 1");
        Persona persona2 = new Persona();
        persona2.setNombre("Persona 2");

        personaRepository.save(persona1);
        personaRepository.save(persona2);

        // Recuperar todas las personas
        var personas = personaRepository.findAll();
        assertEquals(2, personas.size());
    }
    @Test
    void testDeleteById() {
        // Guardar una nueva persona
        Persona persona = new Persona();
        persona.setNombre("Persona para eliminar");
        persona = personaRepository.save(persona);

        // Eliminar la persona
        personaRepository.deleteById(persona.getId());

        // Verificar que ya no existe
        Optional<Persona> encontrada = personaRepository.findById(persona.getId());
        assertFalse(encontrada.isPresent());
    }
}