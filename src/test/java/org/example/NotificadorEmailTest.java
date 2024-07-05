package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificadorEmailTest {

    @Mock
    private EmailCliente emailClienteMock;

    private NotificadorEmail notificador;

    // Configuración inicial antes de cada prueba
    @BeforeEach
    public void setUp() {
        notificador = new NotificadorEmail(emailClienteMock);
    }

    // Test para verificar que se notifica correctamente
    @Test
    public void testNotificar() {
        System.out.println("Ejecutando testNotificar...");

        // Act
        notificador.notificar("ejemplo@test.com", "Hola Mundo");

        // Assert
        verify(emailClienteMock).enviarCorreo("ejemplo@test.com", "Hola Mundo");

        System.out.println("testNotificar completado correctamente.");
    }

    // Test para verificar que no se envía correo con dirección vacía
    @Test
    public void testNotificarConDireccionVacia() {
        System.out.println("Ejecutando testNotificarConDireccionVacia...");

        // Act & Assert
        try {
            notificador.notificar("", "Mensaje");
        } catch (IllegalArgumentException e) {
            assertEquals("La dirección de correo no puede ser vacía", e.getMessage());
        }

        // Verify
        verify(emailClienteMock, times(0)).enviarCorreo(anyString(), anyString());

        System.out.println("testNotificarConDireccionVacia completado correctamente.");
    }

    // Test para verificar el comportamiento con mensaje nulo
    @Test
    public void testNotificarConMensajeNulo() {
        System.out.println("Ejecutando testNotificarConMensajeNulo...");

        // Act & Assert
        try {
            notificador.notificar("ejemplo@test.com", null);
        } catch (IllegalArgumentException e) {
            assertEquals("El mensaje no puede ser nulo", e.getMessage());
        }

        // Verify
        verify(emailClienteMock, times(0)).enviarCorreo(anyString(), anyString());

        System.out.println("testNotificarConMensajeNulo completado correctamente.");
    }

    // Test para verificar el comportamiento con email largo
    @Test
    public void testNotificarConEmailLargo() {
        System.out.println("Ejecutando testNotificarConEmailLargo...");

        // Act
        String longEmail = "a".repeat(256) + "@example.com"; // create a long email address
        notificador.notificar(longEmail, "Mensaje");

        // Assert
        verify(emailClienteMock).enviarCorreo(longEmail, "Mensaje");

        System.out.println("testNotificarConEmailLargo completado correctamente.");
    }

    // Test para verificar el comportamiento con mensaje largo
    @Test
    public void testNotificarConMensajeLargo() {
        System.out.println("Ejecutando testNotificarConMensajeLargo...");

        // Act
        String longMessage = "a".repeat(1000); // create a long message
        notificador.notificar("ejemplo@test.com", longMessage);

        // Assert
        verify(emailClienteMock).enviarCorreo("ejemplo@test.com", longMessage);

        System.out.println("testNotificarConMensajeLargo completado correctamente.");
    }

    // Test para verificar el manejo de excepción durante el envío
    @Test
    public void testNotificarConExcepcionDeEnvio() {
        System.out.println("Ejecutando testNotificarConExcepcionDeEnvio...");

        // Arrange
        doThrow(new RuntimeException("Error al enviar correo")).when(emailClienteMock).enviarCorreo(anyString(), anyString());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> notificador.notificar("ejemplo@test.com", "Mensaje"));

        System.out.println("testNotificarConExcepcionDeEnvio completado correctamente.");
    }
}
