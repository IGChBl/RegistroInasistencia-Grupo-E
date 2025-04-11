package online.jadg13.solicitud.controller;

import online.jadg13.solicitud.entity.Carrera;
import online.jadg13.solicitud.entity.Estudiante;
import online.jadg13.solicitud.service.CarreraService;
import online.jadg13.solicitud.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;
    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public Map<String, Object> getAll() {
        Map<String, Object> json = new HashMap<>();
        try {
            var estudiantes = service.findAll();
            if (estudiantes.isEmpty()) {
                json.put("status", "success");
                json.put("message", "No hay estudiantes registrados");
            } else {
                List<Map<String, Object>> estudianteData = estudiantes.stream()
                        .sorted(Comparator.comparing(Estudiante::getCif))
                        .map(estudiante -> {
                            Map<String, Object> data = new LinkedHashMap<>(); //se reemplazó HashMap con LinkedHashMap para asegurar el orden de los campos
                            data.put("id", estudiante.getId());
                            data.put("cif", estudiante.getCif());
                            data.put("nombre", estudiante.getNombre());
                            data.put("apellido", estudiante.getApellido());
                            data.put("email", estudiante.getEmail());
                            data.put("telefono", estudiante.getTelefono());
                            List<Long> carrerasIds = estudiante.getCarreras().stream()
                                    .map(Carrera::getId)
                                    .collect(Collectors.toList());
                            data.put("carreras", carrerasIds);
                            return data;
                        })
                        .collect(Collectors.toList());
                json.put("status", "success");
                json.put("data", estudianteData); // Convertir la lista de mapas a String
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @PostMapping
    public Map<String, Object> store(@RequestBody Map<String, Object> request) {
        Map<String, Object> json = new HashMap<>();
        try {
            var estudiante = new Estudiante();
            estudiante.setNombre((String) request.get("nombre"));
            estudiante.setApellido((String) request.get("apellido"));
            estudiante.setEmail((String) request.get("email"));
            estudiante.setCif((String) request.get("cif"));
            estudiante.setTelefono((String) request.get("telefono"));

            List<Integer> carrerasIds = (List<Integer>) request.get("carreras");
            Set<Carrera> carreras = new HashSet<>();
            if (carrerasIds != null) {
                for (Integer carreraId : carrerasIds) {
                    Carrera carrera = carreraService.findById(Long.valueOf(carreraId));
                    if (carrera != null) {
                        carreras.add(carrera);
                    }
                }
            }
            estudiante.setCarreras(carreras);

            var savedEstudiante = service.save(estudiante);

            Map<String, Object> estudianteData = new LinkedHashMap<>(); //se reemplazó HashMap con LinkedHashMap para asegurar el orden de los campos
            estudianteData.put("id", savedEstudiante.getId());
            estudianteData.put("nombre", savedEstudiante.getNombre());
            estudianteData.put("apellido", savedEstudiante.getApellido());
            estudianteData.put("cif", savedEstudiante.getCif());
            estudianteData.put("email", savedEstudiante.getEmail());
            estudianteData.put("telefono", savedEstudiante.getTelefono());
            List<Long> savedCarrerasIds = savedEstudiante.getCarreras().stream()
                    .map(Carrera::getId)
                    .collect(Collectors.toList());
            estudianteData.put("carreras", savedCarrerasIds);

            json.put("status", "success");
            json.put("message", "Estudiante creado con éxito");
            json.put("data", estudianteData.toString());
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @GetMapping("/{id}")
    public Map<String, String> show(@PathVariable("id") Long id) {
        Map<String, String> json = new HashMap<>();
        try {
            var estudianteOptional = service.findById(id);
            if (estudianteOptional.isPresent()) {
                var estudiante = estudianteOptional.get();

                Map<String, Object> estudianteData = new LinkedHashMap<>(); //se reemplazó HashMap con LinkedHashMap para asegurar el orden de los campos
                estudianteData.put("id", estudiante.getId());
                estudianteData.put("nombre", estudiante.getNombre());
                estudianteData.put("apellido", estudiante.getApellido());
                estudianteData.put("cif", estudiante.getCif());
                estudianteData.put("email", estudiante.getEmail());
                estudianteData.put("telefono", estudiante.getTelefono());
                List<Long> carrerasIds = estudiante.getCarreras().stream()
                        .map(Carrera::getId)
                        .collect(Collectors.toList());
                estudianteData.put("carreras", carrerasIds);
                json.put("status", "success");
                json.put("data", estudianteData.toString());
            } else {
                json.put("status", "error");
                json.put("message", "Estudiante no encontrado");
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody Map<String, Object> request) {
        Map<String, Object> json = new HashMap<>();
        try {
            var existingEstudianteOptional = service.findById(id);

            if (existingEstudianteOptional.isPresent()) {
                var existingEstudiante = existingEstudianteOptional.get();
                if (request.containsKey("nombre")) {
                    existingEstudiante.setNombre((String) request.get("nombre"));
                }
                if (request.containsKey("apellido")) {
                    existingEstudiante.setApellido((String) request.get("apellido"));
                }
                if (request.containsKey("email")) {
                    existingEstudiante.setEmail((String) request.get("email"));
                }
                if (request.containsKey("cif")) {
                    existingEstudiante.setCif((String) request.get("cif"));
                }
                if (request.containsKey("telefono")) {
                    existingEstudiante.setTelefono((String) request.get("telefono"));
                }

                if (request.containsKey("carreras")) {
                    List<Integer> carrerasIds = (List<Integer>) request.get("carreras");
                    Set<Carrera> carreras = new HashSet<>();
                    if (carrerasIds != null) {
                        for (Integer carreraId : carrerasIds) {
                            Carrera carrera = carreraService.findById(Long.valueOf(carreraId));
                            if (carrera != null) {
                                carreras.add(carrera);
                            }
                        }
                    }
                    existingEstudiante.setCarreras(carreras);
                }

                var updatedEstudiante = service.update(existingEstudiante);

                Map<String, Object> estudianteData = new LinkedHashMap<>(); //se reemplazó HashMap con LinkedHashMap para asegurar el orden de los campos
                estudianteData.put("id", updatedEstudiante.getId());
                estudianteData.put("nombre", updatedEstudiante.getNombre());
                estudianteData.put("apellido", updatedEstudiante.getApellido());
                estudianteData.put("cif", updatedEstudiante.getCif());
                estudianteData.put("email", updatedEstudiante.getEmail());
                estudianteData.put("telefono", updatedEstudiante.getTelefono());
                List<Long> updatedCarrerasIds = updatedEstudiante.getCarreras().stream()
                        .map(Carrera::getId)
                        .collect(Collectors.toList());
                estudianteData.put("carreras", updatedCarrerasIds);

                json.put("status", "success");
                json.put("message", "Estudiante actualizado con éxito");
                json.put("data", estudianteData.toString());
            } else {
                json.put("status", "error");
                json.put("message", "Estudiante no encontrado");
            }

        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id) {
        Map<String, String> json = new HashMap<>();
        try {
            var estudianteOptional = service.findById(id);
            if (estudianteOptional.isPresent()) {
                service.delete(id);
                json.put("status", "success");
                json.put("message", "Estudiante eliminado con éxito");
            } else {
                json.put("status", "error");
                json.put("message", "Estudiante no encontrado");
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.toString());
        }
        return json;
    }
}