package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.service.CharacterService;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequestMapping("characters")
public class CharacterController {

    @Lazy
    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<CharacterDTO> saveCharacter(@RequestBody CharacterDTO characterDTO) {

        // SAVE THE CHARACTER
        CharacterDTO savedCharacter = characterService.saveCharacter(characterDTO);

        // RETURNS ONE HTTP STATE, CREATED (201) OF TYPE ResponseEntity WITH A BODY, CHARACTER SAVED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterDTO>> getAllCharacter() {

        List<CharacterDTO> characters = characterService.getAllCharacter();

        return ResponseEntity.ok().body(characters);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CharacterDTO> deleteCharacter(@PathVariable String idCharacter) {

        characterService.deleteCharacter(idCharacter);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> modifyCharacter(@PathVariable String idCharacter, @RequestBody CharacterDTO charDTO) {

        CharacterDTO updatedcharacter = characterService.modifyCharacter(idCharacter, charDTO);

        return ResponseEntity.ok().body(updatedcharacter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterDetailById(@PathVariable String idCharacter) {

        CharacterDTO charDTO = characterService.getCharacterDetailById(idCharacter);

        return ResponseEntity.ok(charDTO);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<CharacterDTO>> getCharacterByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Set<String> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        List<CharacterDTO> characterDTOList = characterService.getCharacterByFilters(name, age, movies, order);

        return ResponseEntity.ok(characterDTOList);
    }

    @PostMapping("/{id}/movie/{idMovie}")
    public ResponseEntity<Void> addMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {

        characterService.addMovie(idCharacter, idMovie);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/movie/{idMovie}")
    public ResponseEntity<Void> removeMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {

        characterService.removeMovie(idCharacter, idMovie);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{movieId}/character/{charId}")
    public ResponseEntity<Void> addCharacter(@PathVariable String idMovie, @PathVariable String idCharacter) {

        movieService.addCharacter(idMovie, idCharacter);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{movieId}/genre/{genreId}")
    public ResponseEntity<Void> addGenre(@PathVariable String idMovie, @PathVariable String idGenre) {

        movieService.addGenre(idMovie, idGenre);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
