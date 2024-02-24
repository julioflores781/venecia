package com.test.venecia.persistence.crud;

import com.test.venecia.persistence.entity.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceshipCrudRepository extends CrudRepository<Spaceship,Long> {


    @Query("SELECT t FROM Spaceship t")
    Page<Spaceship> spaceShipPageable( Pageable pageable);

    @Query("SELECT s FROM Spaceship s WHERE LOWER(s.name) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Optional<List<Spaceship>> findByNameContaining(String keyword);
}
