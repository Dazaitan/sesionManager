package com.conferencia.manager.services;

import com.conferencia.manager.entity.Persona;
import com.conferencia.manager.entity.Sesion;
import com.conferencia.manager.repository.PersonaRepository;
import com.conferencia.manager.repository.SesionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SesionServiceTest {
    @Mock
    private SesionRepository sesionRepository;
    @Mock
    private PersonaRepository personaRepository;
    @InjectMocks
    private SesionService sesionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks antes de ejecutar las pruebas
    }
    @Test
    void testCrearSesion() {
        LocalDateTime horaInicio = LocalDateTime.now();
        LocalDateTime horaFin = horaInicio.plusHours(2);
        Sesion sesionMock = new Sesion(horaInicio, horaFin);

        when(sesionRepository.save(any(Sesion.class))).thenReturn(sesionMock);

        Sesion resultado = sesionService.crearSesion(horaInicio, horaFin);

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(horaInicio, resultado.getHoraInicio());
        assertEquals(horaFin, resultado.getHoraFin());

        // Verifica que se haya llamado a sesionRepository.save()
        verify(sesionRepository, times(1)).save(any(Sesion.class));
    }

    @Test
    void testAgregarPersonaASesion() {
        Long sesionId = 1L;
        Long personaId = 2L;

        Sesion sesionNueva = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        Persona persona = new Persona();
        persona.setId(personaId);

        // Simula el repositorio
        when(sesionRepository.findById(sesionId)).thenReturn(Optional.of(sesionNueva));
        when(personaRepository.findById(personaId)).thenReturn(Optional.of(persona));
        when(sesionRepository.findByPersonas_Id(personaId)).thenReturn(List.of()); // Sin conflictos

        // Llama al método que se está probando
        boolean resultado = sesionService.agregarPersonaASesion(sesionId, personaId);

        // Verifica el resultado
        assertTrue(resultado);

        // Verifica que se hayan llamado los métodos correspondientes
        verify(sesionRepository, times(1)).findById(sesionId);
        verify(personaRepository, times(1)).findById(personaId);
        verify(sesionRepository, times(1)).save(sesionNueva);
    }
    @Test
    void testAgregarPersonaASesionConConflicto() {
        // Datos simulados
        Long sesionId = 1L;
        Long personaId = 2L;

        Sesion sesionNueva = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        Persona persona = new Persona();
        persona.setId(personaId);

        // Sesión existente con conflicto
        Sesion sesionExistente = new Sesion(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));

        // Simula el repositorio
        when(sesionRepository.findById(sesionId)).thenReturn(Optional.of(sesionNueva));
        when(personaRepository.findById(personaId)).thenReturn(Optional.of(persona));
        when(sesionRepository.findByPersonas_Id(personaId)).thenReturn(List.of(sesionExistente));

        // Llama al método que se está probando
        boolean resultado = sesionService.agregarPersonaASesion(sesionId, personaId);

        // Verifica el resultado
        assertFalse(resultado); // No se agrega debido a conflicto

        // Verifica que no se haya llamado a save()
        verify(sesionRepository, times(0)).save(any(Sesion.class));
    }
    @Test
    void testObtenerTodasLasSesiones() {
        Sesion sesion1 = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Sesion sesion2 = new Sesion(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3));

        // Simula el comportamiento del repositorio
        when(sesionRepository.findAll()).thenReturn(Arrays.asList(sesion1, sesion2));

        // Llama al método que se está probando
        List<Sesion> resultado = sesionService.obtenerTodasLasSesiones();

        // Verifica el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Verifica que se haya llamado a findAll()
        verify(sesionRepository, times(1)).findAll();
    }
}