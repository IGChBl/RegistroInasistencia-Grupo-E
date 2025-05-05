package online.jadg13.solicitud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioController {

    @GetMapping("/inicio")
    @ResponseBody // Añade esta anotación
    public String inicio() {
        return "¡Bienvenido! Has iniciado sesión exitosamente.";
    }
}