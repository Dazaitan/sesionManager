package com.conferencia.manager.repository;

import com.conferencia.manager.entity.Persona;
import com.conferencia.manager.entity.Sesion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SesionRepositoryTest {

    @Autowired
    private SesionRepository sesionRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Test
    void testFindByPersonas_Id() {
        // Crear una nueva persona y guardarla
        Persona persona = new Persona();
        persona.setNombre("Jhon Doe");
        persona = personaRepository.save(persona);

        // Crear una nueva sesión y asociarla a la persona
        Sesion sesion = new Sesion(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        sesion.getPersonas().add(persona); // Asociación con la persona
        sesion = sesionRepository.save(sesion);

        // Llamar al método y verificar el resultado
        List<Sesion> sesiones = sesionRepository.findByPersonas_Id(persona.getId());
        sesiones.forEach(s -> System.out.println(s)); //Listar sesiones por persona
        assertNotNull(sesiones); // Asegurarse de que la lista no sea nula
        assertEquals(1, sesiones.size()); // Comprobar que solo haya una sesión
        assertEquals(sesion.getId(), sesiones.get(0).getId()); // Validar que es la sesión correcta
    }
}