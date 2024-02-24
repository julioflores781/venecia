package com.test.venecia;


import com.test.venecia.persistence.repository.SpaceshipRepository;
import com.test.venecia.persistence.entity.Spaceship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceshipControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    String keyword = "";
    Long ultimoId = 0L;
    @BeforeEach
    public void setUp() {
        Spaceship spaceship = new Spaceship("Name to update Falcon", "Series to modify", "Movie to modify", "Model to modify", 1);
        spaceshipRepository.save(spaceship);

        List<Spaceship> spaceships = (List<Spaceship>) spaceshipRepository.findAll();
        Spaceship ultimaSpaceship = spaceships.get(spaceships.size() - 1);
        ultimoId = ultimaSpaceship.getId();

        keyword= "Falcon";
    }

    @Test
    public void testGetByNameContaining() {

        ResponseEntity<List<Spaceship>> responseEntity = restTemplate.exchange(
                "/spaceships?keyword={keyword}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Spaceship>>() {},
                keyword
        );

        List<Spaceship> spaceshipsList = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(spaceshipsList.isEmpty(), "La lista de Spaceships no debe estar vacía");
    }

    @Test
    public void testGetByNameContainingNotFound() {
        String keyword2 = "prueba";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/spaceships?keyword={keyword2}",
                HttpMethod.GET,
                null,
                String.class,
                keyword2
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAll() {
        ResponseEntity<List<Spaceship>> responseEntity = restTemplate.exchange(
                "/spaceships/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Spaceship>>() {}
        );

        List<Spaceship> spaceshipsList = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(spaceshipsList.isEmpty(), "La lista de Spaceships no debe estar vacía");
    }

    @Test
    public void testUpdateOk() {
        Long id = ultimoId;
        Spaceship updatedSpaceship = new Spaceship("Updated Name Falcon", "Updated Series", "Updated Movie", "Updated Model", 1);

        ResponseEntity<Spaceship> responseEntity = restTemplate.exchange(
                "/spaceships/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updatedSpaceship),
                Spaceship.class,
                id
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Spaceship responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedSpaceship.getName(), responseBody.getName());
        assertEquals(updatedSpaceship.getSeries(), responseBody.getSeries());
        assertEquals(updatedSpaceship.getMovie(), responseBody.getMovie());
        assertEquals(updatedSpaceship.getModel(), responseBody.getModel());
        assertEquals(updatedSpaceship.getCrewCapacity(), responseBody.getCrewCapacity());

        spaceshipRepository.deleteById(id);

    }

    @Test
    public void testUpdateNotFound() {
        Long id = 999L;
        Spaceship updatedSpaceship = new Spaceship("Updated Name", "Updated Series", "Updated Movie", "Updated Model", 1);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/spaceships/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updatedSpaceship),
                String.class,
                id
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}