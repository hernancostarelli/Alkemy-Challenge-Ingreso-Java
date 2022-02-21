package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {

        List<MovieDTO> movies = movieService.getAllMovies();

        return ResponseEntity.ok().body(movies);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movieDTO) {

        MovieDTO savedMovie = movieService.saveMovie(movieDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieByDetails(@PathVariable String idMovie) {

        MovieDTO movie = movieService.getMovieByDetails(idMovie);

        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> modifyMovie(@PathVariable String idMovie, @RequestBody MovieDTO movieDTO) {

        MovieDTO editedMovie = movieService.modifyMovie(idMovie, movieDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedMovie);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<MovieDTO>> getByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<String> genre,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<MovieDTO> filteredMovies = movieService.getMovieByFilters(title, genre, order);

        return ResponseEntity.status(HttpStatus.OK).body(filteredMovies);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String idMovie) {

        movieService.deleteMovie(idMovie);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
