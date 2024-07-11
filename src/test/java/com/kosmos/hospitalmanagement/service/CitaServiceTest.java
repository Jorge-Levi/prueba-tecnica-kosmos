package com.kosmos.hospitalmanagement.service;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.repository.CitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CitaServiceTest {

    @InjectMocks
    private CitaService citaService;

    @Mock
    private CitaRepository citaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCita_validCita() throws Exception {
        Cita cita = new Cita();
        cita.setHorarioConsulta(LocalDateTime.now().plusDays(1));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita savedCita = citaService.saveCita(cita);

        assertNotNull(savedCita);
    }

    @Test
    public void testSaveCita_invalidCita() {
        Cita cita = new Cita();
        cita.setHorarioConsulta(LocalDateTime.now().plusDays(1));
        when(citaRepository.findByConsultorioAndHorarioConsulta(any(Consultorio.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(cita));

        Exception exception = assertThrows(Exception.class, () -> {
            citaService.saveCita(cita);
        });

        String expectedMessage = "Las reglas de negocio no se cumplen.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
