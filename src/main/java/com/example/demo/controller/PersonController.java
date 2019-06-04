package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Person;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Actors and directors management")
@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ApiOperation(value = "Get a list of all peoples", response = Iterable.class)
    @ApiResponse(code = 200, message = "Successfully retrieved list")
    @GetMapping
    public ResponseEntity<Iterable<Person>> getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @ApiOperation(value = "Get movie by id", response = Movie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Not found person by id")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getOne(@PathVariable long id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(person);
    }

    @ApiOperation(value = "Add new person", response = Movie.class)
    @ApiResponse(code = 201, message = "Person successfully added")
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(person));
    }

    @ApiOperation(value = "Get persona directed movies", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Not found person by id")
    })
    @GetMapping(value = "/{id}/directed")
    public ResponseEntity<Iterable<Movie>> getDirectedMovies(@PathVariable long id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(person.getDirectedMovies());
    }

    @ApiOperation(value = "Get persons movies as actor", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Not found person by id")
    })
    @GetMapping(value = "/{id}/acted")
    public ResponseEntity<Iterable<Movie>> getActedMovies(@PathVariable long id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(person.getMovies());
    }
}
