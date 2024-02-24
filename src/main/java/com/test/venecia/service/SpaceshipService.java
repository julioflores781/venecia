package com.test.venecia.service;

import com.test.venecia.exception.SpaceshipNotFoundException;
import com.test.venecia.persistence.repository.SpaceshipRepository;
import com.test.venecia.persistence.entity.Spaceship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {


    @Autowired
    private SpaceshipRepository spaceshipRepository;

    public Optional<Spaceship> getById(Long id) {
        Optional<Spaceship> spaceships = spaceshipRepository.findById(id);

        if (spaceships.isEmpty()) {
            throw new SpaceshipNotFoundException("No se encontraron naves espaciales que coincidan con el criterio de búsqueda: " + id);
        }
        return spaceships;
    }

    public List<Spaceship> getAll() {
        return (List<Spaceship>) spaceshipRepository.findAll();
    }

    public Spaceship create(Spaceship spaceship) {
        return spaceshipRepository.save(spaceship);
    }

    public Page<Spaceship> spaceShipPageable(Pageable pageable) {
        return spaceshipRepository.spaceShipPageable(pageable);
    }

    public Optional<Spaceship> update(Spaceship spaceship, Long id) {
        return spaceshipRepository.findById(id)
                .map(existingSpaceship -> {
                    updateSpaceship(existingSpaceship, spaceship);
                    return spaceshipRepository.save(existingSpaceship);
                });
    }

    private void updateSpaceship(Spaceship existingSpaceship, Spaceship updatedSpaceship) {
        if (updatedSpaceship.getName() != null)
            existingSpaceship.setName(updatedSpaceship.getName());
        if (updatedSpaceship.getModel() != null)
            existingSpaceship.setModel(updatedSpaceship.getModel());
        if (updatedSpaceship.getSeries() != null)
            existingSpaceship.setSeries(updatedSpaceship.getSeries());
        if (updatedSpaceship.getMovie() != null)
            existingSpaceship.setMovie(updatedSpaceship.getMovie());
        if (updatedSpaceship.getCrewCapacity() != 0)
            existingSpaceship.setCrewCapacity(updatedSpaceship.getCrewCapacity());
    }

    public boolean delete(Long id) {
        return getById(id).map(espacial -> {
            spaceshipRepository.deleteById(id);
            return true;
        }).orElseThrow(() -> new SpaceshipNotFoundException("No se encontró ninguna nave espacial con el ID: " + id));
    }

    public Optional<List<Spaceship>> findByNameContaining(String keyword) {
        Optional<List<Spaceship>> spaceships = spaceshipRepository.findByNameContaining(keyword);

        if (spaceships.get().isEmpty()) {
            throw new SpaceshipNotFoundException("No se encontraron naves espaciales que coincidan con el criterio de búsqueda: " + keyword);
        }
        return spaceships;
    }
}
