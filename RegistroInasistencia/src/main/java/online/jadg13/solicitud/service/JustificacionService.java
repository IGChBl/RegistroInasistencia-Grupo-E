package online.jadg13.solicitud.service;

import online.jadg13.solicitud.entity.Estudiante;
import online.jadg13.solicitud.entity.Carrera;
import online.jadg13.solicitud.entity.Justificacion;
import online.jadg13.solicitud.repository.JustificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JustificacionService {
    private static final Logger logger = LoggerFactory.getLogger(JustificacionService.class);

    @Autowired
    private JustificacionRepository repository;
    @Autowired
    private EstudianteService estudianteService;
    @Autowired
    private CarreraService carreraService;

    public Justificacion save(Justificacion justificacion) {
        return repository.save(justificacion);
    }

    public List<Justificacion> findAll() {
        return repository.findAll();
    }

    public Optional<Justificacion> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Justificacion update(Long id, Justificacion justificacion) {
        Optional<Justificacion> existingJustificacionOptional = repository.findById(id);
        if (existingJustificacionOptional.isPresent()) {
            Justificacion existingJustificacion = existingJustificacionOptional.get();
            existingJustificacion.setFecha_ini(justificacion.getFecha_ini());
            existingJustificacion.setFecha_fin(justificacion.getFecha_fin());
            existingJustificacion.setMotivo(justificacion.getMotivo());
            existingJustificacion.setEvidencia(justificacion.getEvidencia());
            existingJustificacion.setEstado(justificacion.getEstado());

            logger.info("Justificacion encontrada para actualizar (ID: {}): {}", id, existingJustificacion);

            if (justificacion.getEstudiante() != null && justificacion.getEstudiante().getId() != null) {
                Optional<Estudiante> estudianteOptional = estudianteService.findById(justificacion.getEstudiante().getId());
                estudianteOptional.ifPresentOrElse(
                        existingJustificacion::setEstudiante,
                        () -> logger.warn("No se encontró el estudiante con ID: {}", justificacion.getEstudiante().getId())
                );
                logger.info("Estudiante asociado: {}", existingJustificacion.getEstudiante());
            } else {
                existingJustificacion.setEstudiante(null);
                logger.info("Estudiante desasociado");
            }

            logger.info("Justificacion antes de guardar: {}", existingJustificacion);
            return repository.save(existingJustificacion);
        } else {
            logger.warn("No se encontró la justificacion con ID: {}", id);
            return null;
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}