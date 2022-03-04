package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CharacterService {

    public CharacterDTO saveCharacter(CharacterDTO characterDTO);

    public CharacterDTO modifyCharacter(String idCharacter, CharacterDTO characterDTO);

    public void deleteCharacter(String idCharacter);

    List<CharacterDTOBasic> getAllCharacterBasic();

    List<CharacterDTO> getAllCharacter();

    List<CharacterDTO> getCharacterByFilters(String name, Integer age, List<String> movies, String order);

    CharacterDTO getCharacterById(String idCharacter);

    void addMovieCharacter(String idCharacter, String idMovie);

    void removeMovieCharacter(String idCharacter, String idMovie);

    CharacterEntity getCharacterEntityById(String idCharacter) throws Exception;
}
