package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.GenreDTOBasic;
import com.alkemy.HFC.disney.service.GenreService;
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

@Controller
@RequestMapping("genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // ENDPOINT TO OBTAIN ALL MOVIE GENRES
    @GetMapping
    public ResponseEntity<List<GenreDTOBasic>> getAllGenres() {
        List<GenreDTOBasic> genreList = genreService.getAllGenresBasic();
        return ResponseEntity.ok().body(genreList);
    }

    // ENDPOINT TO SAVE MOVIE GENRES
    @PostMapping
    public ResponseEntity<GenreDTO> saveGenre(@Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO savedGenre = genreService.saveGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    // ENDPOINT TO MODIFY MOVIE GENRES
    @PutMapping("/{idGenre}")
    public ResponseEntity<GenreDTO> modifyGenre(@Valid @PathVariable String idGenre, @RequestBody GenreDTO genreDTO) {
        GenreDTO editedGenre = genreService.modifyGenre(idGenre, genreDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedGenre);
    }

    // ENDPOINT TO REMOVE MOVIE GENRES
    @DeleteMapping("/{idGenre}")
    public ResponseEntity<Void> deleteGenre(@PathVariable String idGenre) {
        genreService.deleteGenreById(idGenre);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
