package com.alkemy.HFC.disney.controller;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.service.CharacterService;
import java.util.List;
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

    /*
    3. Listado de Personajes
        El listado deberá mostrar:
            ● Imagen.
            ● Nombre.

        El endpoint deberá ser:
            ● /characters
     */
    //
    // ENDPOINT TO GET ALL CHARACTER WITH BASIC ATTRIBUTES
    @GetMapping
    public ResponseEntity<List<CharacterDTOBasic>> getAllCharacterBasic() {
        List<CharacterDTOBasic> characterBasicList = characterService.getAllCharacterBasic();
        return ResponseEntity.ok().body(characterBasicList);
    }

    /*
    4. Creación, Edición y Eliminación de Personajes (CRUD)
        Deberán existir las operaciones básicas de creación, edición y eliminación de personajes.
     */
    //
    // ENDPOINT TO CREATE CHARACTER
    @PostMapping
    public ResponseEntity<CharacterDTO> saveCharacter(@RequestBody CharacterDTO characterDTO) {
        CharacterDTO savedCharacter = characterService.saveCharacter(characterDTO);
        // RETURNS ONE HTTP STATE, CREATED (201) OF TYPE ResponseEntity WITH A BODY, CHARACTER SAVED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);

    }

    // ENDPOINT TO MODIFY CHARACTER
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> modifyCharacter(@PathVariable String idCharacter, @RequestBody CharacterDTO charDTO) {
        CharacterDTO updatedcharacter = characterService.modifyCharacter(idCharacter, charDTO);
        return ResponseEntity.ok(updatedcharacter);
    }

    // ENDPOINT TO DELETE MOVIES 
    @DeleteMapping("/{id}")
    public ResponseEntity<CharacterDTO> deleteCharacter(@PathVariable String idCharacter) {
        characterService.deleteCharacter(idCharacter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
    5. Detalle de Personaje
        En el detalle deberán listarse todos los atributos del personaje, como así también sus películas o
        series relacionadas.
     */
    //
    // ENDPOINT TO GET A CHARACTER WITH ALL ATTRIBUTES
//    @GetMapping("/{id}")
//    public ResponseEntity<CharacterDTO> getCharacterDetailById(@PathVariable String idCharacter) {
//        CharacterDTO characterDTO = characterService.getCharacterById(idCharacter);
//        return ResponseEntity.ok(characterDTO);
//    }

    /*
    6. Búsqueda de Personajes
        Deberá permitir buscar por nombre, y filtrar por edad, peso o películas/series en las que participó.
        Para especificar el término de búsqueda o filtros se deberán enviar como parámetros de query:
            ● GET /characters?name=nombre
            ● GET /characters?age=edad
            ● GET /characters?movies=idMovie
     */
    //
    // ENDPOINT TO OBTAIN CHARACTERS BY FILTERS
    @GetMapping("/filters")
    public ResponseEntity<List<CharacterDTO>> getCharacterByFilters(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) List<MovieDTO> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        List<CharacterDTO> characterDTOList = characterService.getCharacterByFilters(id, name, age, weight, movies, order);
        return ResponseEntity.ok(characterDTOList);
    }
}
