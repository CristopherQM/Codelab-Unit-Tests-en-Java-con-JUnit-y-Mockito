package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private NotificadorEmail notificador;

    @PostMapping("/enviarCorreo")
    public void enviarCorreo(@RequestParam String direccion, @RequestParam String mensaje) {
        notificador.notificar(direccion, mensaje);
    }
}
