package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
import javax.validation.Valid;
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

    // ENDPOINT TO GET A MOVIE WITH ALL ATTRIBUTES
    @GetMapping("/{idMovie}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String idMovie) {
        MovieDTO movieDTO = movieService.getMovieById(idMovie);
        return ResponseEntity.status(HttpStatus.OK).body(movieDTO);
    }

    // ENDPOINT TO CREATE MOVIE
    @PostMapping
    public ResponseEntity<MovieDTO> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO savedMovie = movieService.saveMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    // ENDPOINT TO MODIFY MOVIES 
    @PutMapping("/{idMovie}")
    public ResponseEntity<MovieDTO> modifyMovie(@Valid @PathVariable String idMovie, @RequestBody MovieDTO movieDTO) {
        MovieDTO editedMovie = movieService.modifyMovie(idMovie, movieDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedMovie);
    }

    // ENDPOINT TO DELETE MOVIES 
    @DeleteMapping("/{idMovie}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String idMovie) {
        movieService.deleteMovie(idMovie);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ENDPOINT TO ADD CHARACTER TO A MOVIE
    @PostMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<Void> addCharacterToMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {
        movieService.addCharacterToMovie(idCharacter, idMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO REMOVE A CHARACTER FROM A MOVIE
    @DeleteMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<Void> removeCharacterFromMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {
        movieService.removeCharacterFromMovie(idCharacter, idMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO ADD GENRE TO A MOVIE
    @PostMapping("/{idMovie}/genre/{idGenre}")
    public ResponseEntity<Void> addGenreToMovie(@PathVariable String idGenre, @PathVariable String idMovie) {
        movieService.addGenreToMovie(idGenre, idMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO REMOVE A GENRE FROM A MOVIE
    @DeleteMapping("/{idMovie}/genre/{idGenre}")
    public ResponseEntity<Void> removeGenreFromMovie(@PathVariable String idGenre, @PathVariable String idMovie) {
        movieService.removeGenreFromMovie(idGenre, idMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO OBTAIN MOVIES BY FILTERS
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> characters,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<MovieDTO> movieDTOList = movieService.getMovieByFilters(title, characters, genres, order);
        return ResponseEntity.status(HttpStatus.OK).body(movieDTOList);
    }
}
