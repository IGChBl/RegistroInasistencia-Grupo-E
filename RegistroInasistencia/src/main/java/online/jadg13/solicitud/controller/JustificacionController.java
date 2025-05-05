package online.jadg13.solicitud.controller;

import online.jadg13.solicitud.entity.Justificacion;
import online.jadg13.solicitud.service.JustificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/justificacion")
public class JustificacionController {

    @Autowired
    private JustificacionService service;

    @GetMapping
    public Map<String, Object> getAll() {
        Map<String, Object> json = new HashMap<>();
        try {
            List<Justificacion> justificaciones = service.findAll();
            json.put("status", "success");
            json.put("data", justificaciones);
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @GetMapping("/{id}")
    public Map<String, Object> show(@PathVariable Long id) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Justificacion> justificacionOptional = service.findById(id);
            if (justificacionOptional.isPresent()) {
                json.put("status", "success");
                json.put("data", justificacionOptional.get());
            } else {
                json.put("status", "error");
                json.put("message", "Justificacion no encontrada con id: " + id);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @PostMapping
    public Map<String, Object> store(@RequestBody Justificacion justificacion) {
        Map<String, Object> json = new HashMap<>();
        try {
            Justificacion savedJustificacion = service.save(justificacion);
            json.put("status", "success");
            json.put("message", "Justificacion creada con éxito");
            json.put("data", savedJustificacion);
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Justificacion justificacion) {
        Map<String, Object> json = new HashMap<>();
        try {
            Justificacion updatedJustificacion = service.update(id, justificacion);
            if (updatedJustificacion != null) {
                json.put("status", "success");
                json.put("message", "Justificacion actualizada con éxito");
                json.put("data", updatedJustificacion);
            } else {
                json.put("status", "error");
                json.put("message", "Justificacion no encontrada con id: " + id);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Justificacion> justificacionOptional = service.findById(id);
            if (justificacionOptional.isPresent()) {
                service.delete(id);
                json.put("status", "success");
                json.put("message", "Justificacion eliminada con éxito");
            } else {
                json.put("status", "error");
                json.put("message", "Justificacion no encontrada con id: " + id);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }
}