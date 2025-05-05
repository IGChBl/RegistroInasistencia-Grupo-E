package online.jadg13.solicitud.controller; // Asegúrate de que este paquete coincida con donde creaste el archivo

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/inicio"; // Redirigir a la ruta /inicio de tu backend
    }
}