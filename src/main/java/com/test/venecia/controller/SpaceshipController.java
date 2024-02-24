package com.test.venecia.controller;

import com.test.venecia.exception.KafkaSendFailedException;
import com.test.venecia.persistence.entity.Spaceship;
import com.test.venecia.service.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {


    private static final Logger log = LoggerFactory.getLogger(SpaceshipController.class);


    @Autowired
    SpaceshipService spaceshipService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "spaceship-search-requests";

    @Autowired
    public SpaceshipController(SpaceshipService spaceshipService, KafkaTemplate<String, String> kafkaTemplate) {
        this.spaceshipService = spaceshipService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Cacheable(value = "spaceshipCache", key = "#id")
    @GetMapping("/{id:^[+-]?\\d+$}")
    @Operation(summary = "Get nave By Id", description = "Gets a spaceship by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Nave not found: No se encontraron naves espaciales que coincidan con el criterio de búsqueda")
    })
    public ResponseEntity<Spaceship> getById(@PathVariable("id") Long id) {
        log.info("GET /api/spaceships/" + id);
        return spaceshipService.getById(id)
                .map(nave -> new ResponseEntity<>(nave, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pageable")
    @Cacheable(value = "spaceshipCache", key = "#pageable")
    @Operation(summary = "Get pageable list of spaceships", description = "Obtains a pageable list of spaceships")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Page<Spaceship>> getSpaceShipPageable(@ParameterObject Pageable pageable) {
        log.info(" GET /api/spaceships/" + pageable);
        return ResponseEntity.ok(spaceshipService.spaceShipPageable(pageable));
    }

    @GetMapping
    @Operation(summary = "Get spaceships by name containing keyword", description = "Gets a list of spaceships whose name contains the specified keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Nave not found: No se encontraron naves espaciales que coincidan con el criterio de búsqueda")
    })
    public ResponseEntity<List<Spaceship>> getByNameContaining(@RequestParam("keyword") String keyword) {
        log.info(" GET /api/spaceships/?keyword=" + keyword);

        return spaceshipService.findByNameContaining(keyword)
                .map(spaceship -> new ResponseEntity<>(spaceship, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(summary = "Get all spaceships", description = "Gets a list of all spaceships")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<Spaceship>> getAll() {
        log.info(" GET /api/spaceships");
        return new ResponseEntity<>(spaceshipService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Create a new spaceship", description = "Creates a new spaceship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Nave not found")
    })
    public ResponseEntity<Spaceship> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Spaceship object that needs to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @Schema(implementation = Spaceship.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Example Request",
                                            value = "{\"name\": \"string\", \"series\": \"string\", \"movie\": \"string\", \"model\": \"string\", \"crewCapacity\": 0}"
                                    )
                            }
                    )
            ) @RequestBody Spaceship spaceship) {
        log.info("POST /api/spaceships");
        return new ResponseEntity<>(spaceshipService.create(spaceship), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a spaceship by ID", description = "Updates a spaceship with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Nave not found: No se encontró la nave espacial con el ID especificado")
    })
    public ResponseEntity<Spaceship> update(@PathVariable("id") Long id, @RequestBody() Spaceship spaceship) {
        log.info(" PUT /api/spaceships/" + id);
        return spaceshipService.update(spaceship, id)
                .map(nave -> new ResponseEntity<>(nave, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a spaceship by ID", description = "Deletes a spaceship with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Nave not found: No se encontró la nave espacial con el ID especificado")
    })
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            log.info(" DELETE /api/spaceships/" + id);
            if (spaceshipService.delete(id)) {
                kafkaTemplate.send(TOPIC, "Se ha eliminado el id: " + id);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new KafkaSendFailedException("Error al enviar mensaje a Kafka", ex);
        }

    }

}
