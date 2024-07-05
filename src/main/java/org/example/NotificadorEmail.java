package org.example;

public class NotificadorEmail {
    private EmailCliente emailCliente;

    public NotificadorEmail(EmailCliente emailCliente) {
        if (emailCliente == null) {
            throw new IllegalArgumentException("El cliente de email no puede ser nulo");
        }
        this.emailCliente = emailCliente;
    }

    public void notificar(String direccion, String mensaje) {
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección de correo no puede ser vacía");
        }
        if (mensaje == null) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo");
        }

        emailCliente.enviarCorreo(direccion, mensaje);
    }
}
