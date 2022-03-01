package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CharacterService {

    public CharacterDTO saveCharacter(CharacterDTO characterDTO);

    public CharacterDTO modifyCharacter(String idCharacter, CharacterDTO characterDTO);

    public void deleteCharacter(String idCharacter);

    List<CharacterDTOBasic> getAllCharacterBasic();

    List<CharacterDTO> getAllCharacter();

    List<CharacterDTO> getCharacterByFilters(String id, String name, Integer age, Double weight, List<MovieDTO> movies, String order);

    CharacterDTO getCharacterById(String idCharacter);

}
