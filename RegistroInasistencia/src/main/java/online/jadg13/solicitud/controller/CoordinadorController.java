package online.jadg13.solicitud.controller;

import online.jadg13.solicitud.entity.Carrera;
import online.jadg13.solicitud.entity.Coordinador;
import online.jadg13.solicitud.service.CarreraService;
import online.jadg13.solicitud.service.CoordinadorService;
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
@RequestMapping("/api/coordinadores")
public class CoordinadorController {

    @Autowired
    private CoordinadorService service;
    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> json = new HashMap<>();
        try {
            List<Coordinador> coordinadores = service.findAll();
            if (coordinadores.isEmpty()) {
                json.put("status", "success");
                json.put("message", "No hay coordinadores registrados");
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                List<Map<String, Object>> coordinadorData = coordinadores.stream()
                        .map(coordinador -> {
                            Map<String, Object> data = new HashMap<>();
                            data.put("id", coordinador.getId());
                            data.put("nombre", coordinador.getNombre());
                            data.put("apellido", coordinador.getApellido());
                            data.put("email", coordinador.getEmail());
                            data.put("cif", coordinador.getCif());
                            data.put("telefono", coordinador.getTelefono());
                            if (coordinador.getCarrera() != null) {
                                data.put("carrera_id", coordinador.getCarrera().getId());
                                data.put("carrera_nombre", coordinador.getCarrera().getNombre());
                            } else {
                                data.put("carrera_id", null);
                                data.put("carrera_nombre", null);
                            }
                            return data;
                        })
                        .collect(Collectors.toList());
                json.put("status", "success");
                json.put("data", coordinadorData);
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
            Coordinador coordinador = new Coordinador();
            coordinador.setNombre(request.get("nombre"));
            coordinador.setApellido(request.get("apellido"));
            coordinador.setEmail(request.get("email"));
            coordinador.setCif(request.get("cif"));
            coordinador.setTelefono(request.get("telefono"));

            String carreraIdStr = request.get("carrera_id");
            if (carreraIdStr != null && !carreraIdStr.isEmpty()) {
                try {
                    Long carreraId = Long.parseLong(carreraIdStr);
                    Optional<Carrera> carreraOptional = carreraService.findById(carreraId);
                    if (carreraOptional.isPresent()) {
                        coordinador.setCarrera(carreraOptional.get());
                    } else {
                        json.put("status", "error");
                        json.put("message", "No se encontró la carrera con ID: " + carreraId);
                        return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
                    }
                } catch (NumberFormatException e) {
                    json.put("status", "error");
                    json.put("message", "El formato del ID de la carrera no es válido");
                    return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                }
            } else {
                json.put("status", "error");
                json.put("message", "El ID de la carrera es requerido");
                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }

            Coordinador savedCoordinador = service.save(coordinador);
            Map<String, Object> coordinadorData = new HashMap<>();
            coordinadorData.put("id", savedCoordinador.getId());
            coordinadorData.put("nombre", savedCoordinador.getNombre());
            coordinadorData.put("apellido", savedCoordinador.getApellido());
            coordinadorData.put("email", savedCoordinador.getEmail());
            coordinadorData.put("cif", savedCoordinador.getCif());
            coordinadorData.put("telefono", savedCoordinador.getTelefono());
            if (savedCoordinador.getCarrera() != null) {
                coordinadorData.put("carrera_id", savedCoordinador.getCarrera().getId());
                coordinadorData.put("carrera_nombre", savedCoordinador.getCarrera().getNombre());
            } else {
                coordinadorData.put("carrera_id", null);
                coordinadorData.put("carrera_nombre", null);
            }
            json.put("status", "success");
            json.put("message", "Coordinador creado con éxito");
            json.put("data", coordinadorData);
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
            Optional<Coordinador> coordinadorOptional = service.findById(id);
            if (coordinadorOptional.isPresent()) {
                Coordinador coordinador = coordinadorOptional.get();
                Map<String, Object> coordinadorData = new HashMap<>();
                coordinadorData.put("id", coordinador.getId());
                coordinadorData.put("nombre", coordinador.getNombre());
                coordinadorData.put("apellido", coordinador.getApellido());
                coordinadorData.put("email", coordinador.getEmail());
                coordinadorData.put("cif", coordinador.getCif());
                coordinadorData.put("telefono", coordinador.getTelefono());
                if (coordinador.getCarrera() != null) {
                    coordinadorData.put("carrera_id", coordinador.getCarrera().getId());
                    coordinadorData.put("carrera_nombre", coordinador.getCarrera().getNombre());
                } else {
                    coordinadorData.put("carrera_id", null);
                    coordinadorData.put("carrera_nombre", null);
                }
                json.put("status", "success");
                json.put("data", coordinadorData);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "error");
                json.put("message", "Coordinador no encontrado con id: " + id);
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Map<String, Object> json = new HashMap<>();
        try {
            Optional<Coordinador> coordinadorOptional = service.findById(id);
            if (coordinadorOptional.isPresent()) {
                Coordinador existingCoordinador = coordinadorOptional.get();
                if (request.containsKey("nombre")) existingCoordinador.setNombre(request.get("nombre"));
                if (request.containsKey("apellido")) existingCoordinador.setApellido(request.get("apellido"));
                if (request.containsKey("email")) existingCoordinador.setEmail(request.get("email"));
                if (request.containsKey("cif")) existingCoordinador.setCif(request.get("cif"));
                if (request.containsKey("telefono")) existingCoordinador.setTelefono(request.get("telefono"));

                if (request.containsKey("carrera_id")) {
                    String carreraIdStr = request.get("carrera_id");
                    if (carreraIdStr != null && !carreraIdStr.isEmpty()) {
                        try {
                            Long carreraId = Long.parseLong(carreraIdStr);
                            Optional<Carrera> carreraOptional = carreraService.findById(carreraId);
                            if (carreraOptional.isPresent()) {
                                existingCoordinador.setCarrera(carreraOptional.get());
                            } else {
                                json.put("status", "error");
                                json.put("message", "No se encontró la carrera con ID: " + carreraId);
                                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
                            }
                        } catch (NumberFormatException e) {
                            json.put("status", "error");
                            json.put("message", "El formato del ID de la carrera no es válido");
                            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        existingCoordinador.setCarrera(null); // Permite desasociar la carrera
                    }
                }

                Coordinador updatedCoordinador = service.update(existingCoordinador);
                Map<String, Object> coordinadorData = new HashMap<>();
                coordinadorData.put("id", updatedCoordinador.getId());
                coordinadorData.put("nombre", updatedCoordinador.getNombre());
                coordinadorData.put("apellido", updatedCoordinador.getApellido());
                coordinadorData.put("email", updatedCoordinador.getEmail());
                coordinadorData.put("cif", updatedCoordinador.getCif());
                coordinadorData.put("telefono", updatedCoordinador.getTelefono());
                if (updatedCoordinador.getCarrera() != null) {
                    coordinadorData.put("carrera_id", updatedCoordinador.getCarrera().getId());
                    coordinadorData.put("carrera_nombre", updatedCoordinador.getCarrera().getNombre());
                } else {
                    coordinadorData.put("carrera_id", null);
                    coordinadorData.put("carrera_nombre", null);
                }
                json.put("status", "success");
                json.put("message", "Coordinador actualizado con éxito");
                json.put("data", coordinadorData);
                return new ResponseEntity<>(json, HttpStatus.OK);

            } else {
                json.put("status", "error");
                json.put("message", "Coordinador no encontrado con id: " + id);
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        Map<String, String> json = new HashMap<>();
        try {
            Optional<Coordinador> coordinadorOptional = service.findById(id);
            if (coordinadorOptional.isPresent()) {
                service.delete(id);
                json.put("status", "success");
                json.put("message", "Coordinador eliminado con éxito");
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                json.put("status", "error");
                json.put("message", "Coordinador no encontrado con id: " + id);
                return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}