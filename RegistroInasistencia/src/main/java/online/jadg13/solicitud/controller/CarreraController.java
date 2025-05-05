package online.jadg13.solicitud.controller;

import jakarta.validation.Valid;
import online.jadg13.solicitud.entity.Carrera;
import online.jadg13.solicitud.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrera")
public class CarreraController {

    @Autowired
    private CarreraService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        Map<String, Object> json = new HashMap<>();
        try {
            List<Carrera> carreras = service.findAll();
            if (carreras.isEmpty()) {
                json.put("status", "success");
                json.put("message", "No hay carreras registradas");
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "success");
                json.put("data", carreras.stream().map(Carrera::toString).collect(Collectors.toList()));
                json.put("cantidad de carreras", carreras.size());
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> store(@RequestBody Map<String, String> request) {
        Map<String, Object> json = new HashMap<>();
        try {
            Carrera carrera = new Carrera();
            carrera.setNombre(request.get("nombre"));
            carrera.setDescripcion(request.get("descripcion"));

            Carrera savedCarrera = service.save(carrera);
            json.put("status", "success");
            json.put("message", "Carrera creada con éxito");
            json.put("data", savedCarrera.toString());
            return new ResponseEntity<>(json, HttpStatus.CREATED);
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable Long id) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Carrera> carreraOptional = service.findById(id);
            if (carreraOptional.isPresent()) {
                json.put("status", "success");
                json.put("data", carreraOptional.get().toString());
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "error");
                json.put("message", "Carrera no encontrada con id: " + id);
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody Carrera carrera) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Carrera> existingCarreraOptional = service.findById(id);
            if (existingCarreraOptional.isPresent()) {
                Carrera existingCarrera = existingCarreraOptional.get();
                existingCarrera.setNombre(carrera.getNombre());
                existingCarrera.setDescripcion(carrera.getDescripcion());
                Carrera updatedCarrera = service.update(existingCarrera);
                json.put("status", "success");
                json.put("message", "Carrera actualizada con éxito");
                json.put("data", updatedCarrera.toString());
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "error");
                json.put("message", "Carrera no encontrada");
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Carrera> carreraOptional = service.findById(id);
            if (carreraOptional.isPresent()) {
                service.delete(id);
                json.put("status", "success");
                json.put("message", "Carrera eliminada con éxito");
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "error");
                json.put("message", "Carrera no encontrada");
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}