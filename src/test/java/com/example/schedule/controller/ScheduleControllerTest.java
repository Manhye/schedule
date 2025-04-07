package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@WebMvcTest(ScheduleController.class)
@Import(ScheduleControllerTest.MockConfig.class)
class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Nested
    @DisplayName("🔍 findById() test")
    class FindByIdTest {

        @Test
        @DisplayName("If exists, return the info of the id")
        void shouldReturnScheduleWhenIdExists() {

            Long id = 1L;
            ScheduleResponseDto expectedDto = mock(ScheduleResponseDto.class);
            when(scheduleService.findById(id)).thenReturn(expectedDto);


            ResponseEntity<ScheduleResponseDto> response = scheduleController.findById(id);


            assertEquals(200, response.getStatusCodeValue());
            assertEquals(expectedDto, response.getBody());
            verify(scheduleService, times(1)).findById(id);
        }

        @Test
        @DisplayName("If id doesn't exist, NoContentException")
        void shouldThrowExceptionWhenIdNotFound() {

            Long id = 999L;
            when(scheduleService.findById(id)).thenThrow(new RuntimeException("NULL"));


            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                scheduleController.findById(id);
            });

            assertEquals("NULL", exception.getMessage());
            verify(scheduleService).findById(id);
        }

    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ScheduleService scheduleService() {
            return Mockito.mock(ScheduleService.class);
        }
    }
}
