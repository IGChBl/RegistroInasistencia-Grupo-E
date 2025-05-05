package online.jadg13.solicitud.service;

import online.jadg13.solicitud.entity.Carrera;
import online.jadg13.solicitud.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository repository;

    public Carrera save(Carrera carrera) {
        return repository.save(carrera);
    }

    public List<Carrera> findAll() {
        return repository.findAll();
    }

    public Optional<Carrera> findById(Long id) {
        return repository.findById(id);
    }

    public Carrera update(Carrera carrera) {
        return repository.save(carrera);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public long contarCarrera() {
        return repository.count();
    }
}