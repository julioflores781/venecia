package com.test.venecia;


import com.test.venecia.Auth.AuthResponse;
import com.test.venecia.Auth.LoginRequest;
import com.test.venecia.persistence.entity.Spaceship;
import com.test.venecia.persistence.repository.SpaceshipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceshipControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    String keyword = "Falcon";
    Long ultimoId = 0L;
    String token = "";
    Spaceship spaceship = new Spaceship("Name to update Falcon", "Series to modify", "Movie to modify", "Model to modify", 1);
    LoginRequest requestBody = new LoginRequest("julioflores781@gmail.com", "12345");

    @BeforeEach
    public void setUp() {
        spaceshipRepository.save(spaceship);

        List<Spaceship> spaceships = (List<Spaceship>) spaceshipRepository.findAll();
        Spaceship ultimaSpaceship = spaceships.get(spaceships.size() - 1);
        ultimoId = ultimaSpaceship.getId();

        extratToToken();
    }

    @AfterEach
    public void clean() {
        spaceshipRepository.deleteById(ultimoId);
    }

    @Test
    public void testLoginEndpoint() {

        HttpEntity<LoginRequest> requestEntity = getLoginRequestHttpEntity();

        ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
                "/auth/login",
                HttpMethod.POST,
                requestEntity,
                AuthResponse.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getToken());

        System.out.println("Token de autenticación: " + responseEntity.getBody().getToken());
    }


    @Test
    public void testGetByNameContaining() {
        HttpEntity<String> requestEntity = getStringHttpEntity();

        ResponseEntity<List<Spaceship>> responseEntity = restTemplate.exchange(
                "/spaceships?keyword={keyword}",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Spaceship>>() {
                },
                keyword
        );

        List<Spaceship> spaceshipsList = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(spaceshipsList.isEmpty(), "La lista de Spaceships no debe estar vacía");
    }


    @Test
    public void testGetByNameContainingNotFound() {
        HttpEntity<String> requestEntity = getStringHttpEntity();
        String keyword2 = "prueba";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/spaceships?keyword={keyword2}",
                HttpMethod.GET,
                requestEntity,
                String.class,
                keyword2
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAll() {
        HttpEntity<String> requestEntity = getStringHttpEntity();
        ResponseEntity<List<Spaceship>> responseEntity = restTemplate.exchange(
                "/spaceships/",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Spaceship>>() {
                }
        );

        List<Spaceship> spaceshipsList = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(spaceshipsList.isEmpty(), "La lista de Spaceships no debe estar vacía");
    }

    @Test
    public void testUpdateOk() {


        Long id = ultimoId;
        Spaceship updatedSpaceship = new Spaceship("Updated Name Falcon", "Updated Series", "Updated Movie", "Updated Model", 1);
        HttpEntity<Spaceship> requestEntity = getSpaceshipHttpEntity(updatedSpaceship);

        ResponseEntity<Spaceship> responseEntity = restTemplate.exchange(
                "/spaceships/{id}",
                HttpMethod.PUT,
                requestEntity,
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
        HttpEntity<Spaceship> requestEntity = getSpaceshipHttpEntity(updatedSpaceship);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/spaceships/{id}",
                HttpMethod.PUT,
                requestEntity,
                String.class,
                id
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    private HttpEntity<Spaceship> getSpaceshipHttpEntity(Spaceship updatedSpaceship) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Spaceship> requestEntity = new HttpEntity<>(updatedSpaceship, headers);
        return requestEntity;
    }

    private void extratToToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
                "/auth/login",
                HttpMethod.POST,
                requestEntity,
                AuthResponse.class
        );
        if (responseEntity != null) {
            token = responseEntity.getBody().getToken();
        }
    }

    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return requestEntity;
    }

    private HttpEntity<LoginRequest> getLoginRequestHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(requestBody, headers);
        return requestEntity;
    }
}
