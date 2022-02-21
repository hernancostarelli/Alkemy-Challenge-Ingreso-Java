package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.service.GenreService;
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

@Controller
@RequestMapping("genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {

        List<GenreDTO> genres = genreService.getAllGenres();

        return ResponseEntity.ok().body(genres);
    }

    @PostMapping
    public ResponseEntity<GenreDTO> saveGenre(@RequestBody GenreDTO genreDTO) {

        GenreDTO savedGenre = genreService.saveGenre(genreDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> modifyGenre(@PathVariable String idGenre, @RequestBody GenreDTO genreDTO) {

        GenreDTO editedGenre = genreService.modifyGenre(idGenre, genreDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String idGenre) {

        genreService.deleteGenreById(idGenre);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
