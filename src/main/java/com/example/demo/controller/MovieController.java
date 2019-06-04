package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Person;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.MovieRepository;
import com.example.demo.repo.PersonRepository;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Movie management")
@RestController
@RequestMapping(value = "/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @ApiOperation(value = "Get a list of all movies", response = Iterable.class)
    @ApiResponse(code = 200, message = "Successfully retrieved list")
    @GetMapping
    public Iterable<Movie> getAll() {
        return movieRepository.findAll();
    }

    @ApiOperation(value = "Get movie by id", response = Movie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Not found movie by id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOne(@ApiParam(value = "Id of requested movie", required = true) @PathVariable long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(movie);
    }

    @ApiOperation(value = "Add new movie", response = Movie.class)
    @ApiResponse(code = 201, message = "Movie successfully added")
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }

    @ApiOperation(value = "Get actors of movie by id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Not found movie by id")
    })
    @GetMapping("/{id}/actors")
    public ResponseEntity<List<Person>> getDirectedMovies(@PathVariable long id) {
        List<Person> actors = movieRepository.findById(id).orElseThrow(NotFoundException::new).getActors();
        return ResponseEntity.ok(actors);
    }

}
