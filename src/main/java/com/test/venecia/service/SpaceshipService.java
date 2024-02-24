package com.test.venecia.service;

import com.test.venecia.exception.SpaceshipNotFoundException;
import com.test.venecia.persistence.SpaceshipRepository;
import com.test.venecia.persistence.entity.Spaceship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {


    @Autowired
    SpaceshipRepository spaceshipRepository;

    public Optional<Spaceship> getById(Long id){
        Optional<Spaceship> spaceships = spaceshipRepository.findById(id);

        if (spaceships.isEmpty()) {
            throw new SpaceshipNotFoundException("No se encontraron naves espaciales que coincidan con el criterio de búsqueda: " + id);
        }

        return spaceships;

    }

    public List<Spaceship> getAll(){
        return   (List<Spaceship>) spaceshipRepository.getAll();
    }

    public Spaceship create(Spaceship spaceship){
        return spaceshipRepository.save(spaceship);
    }

    public Page<Spaceship> spaceShipPageable( Pageable pageable) {
        return spaceshipRepository.spaceShipPageable( pageable);
    }
    public Optional<Spaceship> update(Spaceship spaceship, Long id) {
        return getById(id)
                .map(espacial -> {
                    if(spaceship.getName()!= null) espacial.setName(spaceship.getName());
                    if(spaceship.getModel()!= null) espacial.setModel(spaceship.getModel());
                    if(spaceship.getSeries()!= null) espacial.setSeries(spaceship.getSeries());
                    if(spaceship.getMovie()!= null) espacial.setMovie(spaceship.getMovie());
                    if(spaceship.getCrewCapacity()!= 0) espacial.setCrewCapacity(spaceship.getCrewCapacity());
                    return  Optional.of(spaceshipRepository.save(espacial));
                })
                .orElseThrow(() -> new SpaceshipNotFoundException("No se encontró ninguna nave espacial con el ID: " + id));
    }

    public boolean delete(Long id){
        return getById(id).map(espacial->{
            spaceshipRepository.delete(id);
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
