package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.CharacterDTOFilter;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.exception.CharacterException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import com.alkemy.HFC.disney.mapper.CharacterMapper;
import com.alkemy.HFC.disney.repository.CharacterRepository;
import com.alkemy.HFC.disney.repository.specification.CharacterSpecification;
import com.alkemy.HFC.disney.service.CharacterService;
import com.alkemy.HFC.disney.validations.DTOValidations;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class CharacterServiceImpl implements CharacterService {

    @Lazy
    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private CharacterRepository characterRepository;

    @Lazy
    @Autowired
    private CharacterSpecification characterSpecification;

    @Autowired
    private DTOValidations dtoValidations;

    // SAVE A CHARACTER
    @Override
    public CharacterDTO saveCharacter(CharacterDTO characterDTO) {

        if (dtoValidations.characterDTOIsValid(characterDTO)) {

            // I RECEIVE AS PARAMETER A DTO CHARACTER AND CONVERT IT TO AN ENTITY
            CharacterEntity characterEntity = characterMapper.characterDTO2Entity(characterDTO);

            // ONCE I GET THE ENTITY, I SAVE IT IN THE DATABASE AND I GET BACK THE SAVED ENTITY
            CharacterEntity savedCharacter = characterRepository.save(characterEntity);

            // PASS THE SAVED ENTITY TO DTO CHARACTER AND END UP RETURNING THE DTO CHARACTER
            CharacterDTO savedCharacterDTO = characterMapper.characterEntity2DTO(savedCharacter, false);

            return savedCharacterDTO;

        } else {
            throw new CharacterException(ExceptionMessage.DTO_WRONG_DATA);
        }

    }

    // DELETE A CHARACTER - SOFT DELETE
    @Override
    public void deleteCharacter(String idCharacter) {

        if (characterRepository.existsById(idCharacter)) {
            characterRepository.deleteById(idCharacter);
        } else {
            throw new EntityNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
    }

    // MODIFIES A CHARACTER
    @Override
    public CharacterDTO modifyCharacter(String idCharacter, CharacterDTO characterDTO) {

        if (characterRepository.existsById(characterDTO.getId())) {
            if (dtoValidations.characterDTOIsValid(characterDTO)) {

                CharacterEntity savedCharacter = characterMapper.characterDTO2Entity(characterDTO);
                CharacterEntity editedChar = characterRepository.save(savedCharacter);
                CharacterDTO savedDTO = characterMapper.characterEntity2DTO(editedChar, false);

                return savedDTO;

            } else {
                throw new CharacterException(ExceptionMessage.DTO_WRONG_DATA);
            }
        } else {
            throw new CharacterException(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
    }

    // LIST OF BASIC CHARACTERS
    @Override
    public List<CharacterDTOBasic> getAllCharacterBasic() {

        List<CharacterEntity> characterEntityList = characterRepository.findAll();

        List<CharacterDTOBasic> characterDTOBasicList = characterMapper.entityList2DTOBasicList(characterEntityList);

        return characterDTOBasicList;
    }

    // SHOWS ALL CHARACTERS
    @Override
    public List<CharacterDTO> getAllCharacter() {

        List<CharacterEntity> chararacterEntities = characterRepository.findAll();
        List<CharacterDTO> characterDTOList = characterMapper.characterEntityList2DTOList(chararacterEntities, true);

        return characterDTOList;
    }

    // LIST OF CHARACTERS BY FILTER
    @Override
    public List<CharacterDTO> getCharacterByFilters(String id, String name, Integer age, Double weight, List<MovieDTO> movies, String order) {

        if (id.isEmpty() || String.valueOf(id) == null
                && name.isEmpty() || String.valueOf(name) == null
                && String.valueOf(age).isEmpty() || age == null
                && String.valueOf(weight).isEmpty() || String.valueOf(weight) == null
                && movies.isEmpty() || movies == null && order.isEmpty() || order == null) {

            return getAllCharacter();

        } else {

            // WE INSTANTIATE THE DTO WITH FILTERS
            CharacterDTOFilter characterFilter = new CharacterDTOFilter(id, name, age, weight, movies, order);

            // WE SEARCH ACCORDING TO FILTERS
            List<CharacterEntity> characterEntities = characterRepository.findAll(characterSpecification.getByFilters(characterFilter));

            // WE CONVERT THE LIST TO DTO
            List<CharacterDTO> characterDTOList = characterMapper.characterEntityList2DTOList(characterEntities, true);

            return characterDTOList;
        }
    }

    // DISPLAY A CHARACTER ACCORDING TO THE ID
    @Override
    public CharacterDTO getCharacterById(String idCharacter) {

        if (characterRepository.existsById(idCharacter)) {

            CharacterEntity characterEntity = characterRepository.getById(idCharacter);
            CharacterDTO characterDTO = new CharacterDTO();
            characterDTO = characterMapper.characterEntity2DTO(characterEntity, true);

            return characterDTO;

        } else {
            throw new EntityNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
    }
}
