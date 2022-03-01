package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
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

    /*
    7. Listado de Películas
    Deberá mostrar solamente los campos imagen, título y fecha de creación.
    El endpoint deberá ser:
        ● GET /movies
     */
    //
    // ENDPOINT TO GET ALL MOVIES WITH BASIC ATTRIBUTES
    @GetMapping
    public ResponseEntity<List<MovieDTOBasic>> getAllMovieBasic() {
        List<MovieDTOBasic> movieBasicList = movieService.getAllMovieBasic();
        return ResponseEntity.ok().body(movieBasicList);
    }

    /*
    8. Detalle de Película / Serie con sus personajes
        Devolverá todos los campos de la película o serie junto a los personajes asociados a la misma
     */
    //
    // ENDPOINT TO GET A MOVIE WITH ALL ATTRIBUTES
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String idMovie) {
        MovieDTO movieDTO = movieService.getMovieById(idMovie);
        return ResponseEntity.status(HttpStatus.OK).body(movieDTO);
    }

    /*
    9. Creación, Edición y Eliminación de Película / Serie
        Deberán existir las operaciones básicas de creación, edición y eliminación de películas o series.
     */
    //
    // ENDPOINT TO CREATE MOVIE
    @PostMapping
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO savedMovie = movieService.saveMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    // ENDPOINT TO MODIFY MOVIES 
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> modifyMovie(@PathVariable String idMovie, @RequestBody MovieDTO movieDTO) {
        MovieDTO editedMovie = movieService.modifyMovie(idMovie, movieDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedMovie);
    }

    // ENDPOINT TO DELETE MOVIES 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String idMovie) {
        movieService.deleteMovie(idMovie);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ENDPOINT TO ADD CHARACTER TO A MOVIE
    @PostMapping("/{movieId}/character/{idCharacter}")
    public ResponseEntity<Void> addCharacterToMovie(@PathVariable String idCharacter, @PathVariable String movieId) {
        movieService.addCharacterToMovie(idCharacter, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO REMOVE A CHARACTER FROM A MOVIE
    @DeleteMapping("/{movieId}/character/{idCharacter}")
    public ResponseEntity<Void> removeCharacterFromMovie(@PathVariable String idCharacter, @PathVariable String movieId) {
        movieService.removeCharacterFromMovie(idCharacter, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO ADD GENRE TO A MOVIE
    @PostMapping("/{movieId}/genre/{idGenre}")
    public ResponseEntity<Void> addGenreToMovie(@PathVariable String idGenre, @PathVariable String movieId) {
        movieService.addGenreToMovie(idGenre, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ENDPOINT TO REMOVE A GENRE FROM A MOVIE
    @DeleteMapping("/{movieId}/genre/{idGenre}")
    public ResponseEntity<Void> removeGenreFromMovie(@PathVariable String idGenre, @PathVariable String idMovie) {
        movieService.removeGenreFromMovie(idGenre, idMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    10.Búsqueda de Películas o Series
        Deberá permitir buscar por título, y filtrar por género. Además, permitir ordenar los resultados por
        fecha de creación de forma ascendiente o descendiente.
        El término de búsqueda, filtro u ordenación se deberán especificar como parámetros de query:
            ● /movies?name=nombre
            ● /movies?genre=idGenero
            ● /movies?order=ASC | DESC
     */
    //
    // ENDPOINT TO OBTAIN MOVIES BY FILTERS
    @GetMapping("/filters")
    public ResponseEntity<List<MovieDTO>> getByFilters(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<CharacterDTO> characters,
            @RequestParam(required = false) List<GenreDTO> genres,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<MovieDTO> movieDTOList = movieService.getMovieByFilters(id, title, characters, genres, order);
        return ResponseEntity.status(HttpStatus.OK).body(movieDTOList);
    }
}
