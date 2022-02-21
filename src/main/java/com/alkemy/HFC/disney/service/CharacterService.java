package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public interface CharacterService {

    public CharacterDTO saveCharacter(CharacterDTO characterDTO);

//    public CharacterDTOBasic saveCharacterBasic(CharacterDTOBasic characterDTO);
//    
    public List<CharacterDTO> getAllCharacter();

    public void deleteCharacter(String idCharacter);

    public CharacterDTO modifyCharacter(String idCharacter, CharacterDTO characterDTO);

    List<CharacterDTOBasic> getBasicCharacterList();

    public CharacterEntity getCharacterById(String idCharacter);

    public List<CharacterDTO> getCharacterByFilters(String name, Integer age, Set<String> movies, String order);

    public CharacterDTO getCharacterDetailById(String idCharacter);

    public void addMovie(String idCharacter, String idMovie);

    public void removeMovie(String idCharacter, String idMovie);
}
