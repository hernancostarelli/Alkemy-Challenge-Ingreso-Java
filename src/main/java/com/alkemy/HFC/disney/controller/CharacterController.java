package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.service.CharacterService;
import java.util.List;
import javax.validation.Valid;
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

    // ENDPOINT TO CREATE CHARACTER
    @PostMapping
    public ResponseEntity<CharacterDTO> saveCharacter(@Valid @RequestBody CharacterDTO characterDTO) {
        CharacterDTO savedCharacter = characterService.saveCharacter(characterDTO);
        // RETURNS ONE HTTP STATE, CREATED (201) OF TYPE ResponseEntity WITH A BODY, CHARACTER SAVED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);

    }

    // ENDPOINT TO MODIFY CHARACTER
    @PutMapping("/{idCharacter}")
    public ResponseEntity<CharacterDTO> modifyCharacter(@Valid @PathVariable String idCharacter, @RequestBody CharacterDTO characterDTO) {
        CharacterDTO updatedcharacter = characterService.modifyCharacter(idCharacter, characterDTO);
        return ResponseEntity.ok(updatedcharacter);
    }

    // ENDPOINT TO DELETE MOVIES 
    @DeleteMapping("/{idCharacter}")
    public ResponseEntity<CharacterDTO> deleteCharacter(@PathVariable String idCharacter) {
        characterService.deleteCharacter(idCharacter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ENDPOINT TO GET A CHARACTER WITH ALL ATTRIBUTES
    @GetMapping("/{idCharacter}")
    public ResponseEntity<CharacterDTO> getCharacterDetailById(@PathVariable String idCharacter) {
        CharacterDTO characterDTO = characterService.getCharacterById(idCharacter);
        return ResponseEntity.ok(characterDTO);
    }

    // ENDPOINT TO OBTAIN CHARACTERS BY FILTERS
    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getCharacterByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) List<String> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        List<CharacterDTO> characterDTOList = characterService.getCharacterByFilters(name, age, movies, order);
        return ResponseEntity.ok(characterDTOList);
    }

    // ENDPOINT TO ADD A MOVIE TO THE CHARACTER
    @PostMapping("/{idCharacter}/pelicula/{idMovie}")
    public ResponseEntity<Void> addMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {
        characterService.addMovieCharacter(idCharacter, idMovie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // ENDPOINT TO REMOVE A MOVIE TO THE CHARACTER
    @DeleteMapping("/{idCharacter}/movie/{idMovie}")
    public ResponseEntity<Void> removeMovie(@PathVariable String idCharacter, @PathVariable String idMovie) {
        characterService.removeMovieCharacter(idCharacter, idMovie);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
