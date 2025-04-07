package com.conferencia.manager.controller;

import com.conferencia.manager.entity.Sesion;
import com.conferencia.manager.services.SesionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(SesionController.class)
class SesionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SesionService sesionService;

    @Test
    void testCrearSesion() throws Exception{
        //Simulacion de datos
        LocalDateTime horaInicio = LocalDateTime.now();
        LocalDateTime horaFin = horaInicio.plusHours(2);
        Sesion sesionMock = new Sesion(horaInicio, horaFin);
        sesionMock.setId(1L);
        //Cuando se ejecute la funcion crearSesion va retornar un objeto Sesion
        when(sesionService.crearSesion(horaInicio, horaFin)).thenReturn(sesionMock);
        //Probar el endpoint
        mockMvc.perform(post("/sesiones")
                        .param("horaInicio", horaInicio.toString())
                        .param("horaFin", horaFin.toString()))//parametros
                .andExpect(status().isOk())//probando las respuestas
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.horaInicio").value(horaInicio.withNano(0).toString()))
                .andExpect(jsonPath("$.horaFin").value(horaFin.withNano(0).toString()));
    }

    @Test
    void testObtenerSesiones() throws Exception {
        Sesion sesionMock1 = new Sesion(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Sesion sesionMock2 = new Sesion(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3));
        when(sesionService.obtenerTodasLasSesiones()).thenReturn(Arrays.asList(sesionMock1, sesionMock2));

        mockMvc.perform(get("/sesiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void agregarPersonaASesion() throws Exception {
        Long sesionId = 1L;
        Long personaId = 2L;
        when(sesionService.agregarPersonaASesion(sesionId, personaId)).thenReturn(true);

        mockMvc.perform(post("/sesiones/{sesionId}/persona/{personaId}", sesionId, personaId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}