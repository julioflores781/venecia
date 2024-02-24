package com.test.venecia.persistence;

import com.test.venecia.persistence.crud.SpaceshipCrudRepository;
import com.test.venecia.persistence.entity.Spaceship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpaceshipRepository {

    @Autowired
    private SpaceshipCrudRepository spaceshipCrudRepository;

    public Optional<Spaceship> findById(Long id){
        return spaceshipCrudRepository.findById(id);
    }

    public List<Spaceship> getAll(){
        return (List<Spaceship>) spaceshipCrudRepository.findAll();
    }

    public Spaceship save(Spaceship spaceship){
        return spaceshipCrudRepository.save(spaceship);
    }

    public void delete(Long id){
        spaceshipCrudRepository.deleteById(id);
    }

    public Optional<List<Spaceship>> findByNameContaining(String keyword) {
        return spaceshipCrudRepository.findByNameContaining(keyword);
    }

    public Page<Spaceship> spaceShipPageable( Pageable pageable) {
        return spaceshipCrudRepository.spaceShipPageable( pageable);
    }
}
