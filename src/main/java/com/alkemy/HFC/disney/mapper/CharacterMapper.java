package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    // MAPPER TO PASS FROM DTO CHARACTER TO ENTITY CHARACTER
    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO) {

        CharacterEntity characterEntity = new CharacterEntity();
        characterEntityRefreshValues(characterEntity, characterDTO);

        return characterEntity;
    }

    // MAPPER TO PASS FROM ENTITY CHARACTER TO DTO CHARACTER
    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity, boolean loadMovie) {

        CharacterDTO characterDTO = new CharacterDTO();
        characterDTORefreshValues(characterDTO, characterEntity);

        // VALID IF MOVIES ARE UPLOADED
        if (loadMovie) {
            List<MovieDTO> movieDTOList = new ArrayList<>();
            for (MovieEntity entity : characterEntity.getMovies()) {
                movieDTOList.add(movieMapper.movieEntity2DTO(entity, false));
            }
            characterDTO.setMovies(movieDTOList);
        }
        return characterDTO;
    }

    // MAPPER FOR RETURN A LIST OF DTO CHARACTERS
    public List<CharacterDTO> characterEntityList2DTOList(List<CharacterEntity> entities, boolean loadMovie) {

        List<CharacterDTO> characterDTOList = new ArrayList<>();

        for (CharacterEntity entity : entities) {
            characterDTOList.add(characterEntity2DTO(entity, loadMovie));
        }
        return characterDTOList;
    }

    // MAPPER FOR RETURN A LIST OF BASIC DTO CHARACTERS
    public List<CharacterDTOBasic> entityList2DTOBasicList(List<CharacterEntity> entities) {

        List<CharacterDTOBasic> characterDTOBasicList = new ArrayList<>();

        for (CharacterEntity entity : entities) {
            CharacterDTOBasic characterDTOBasic = new CharacterDTOBasic();
            characterDTOBasicRefreshValues(characterDTOBasic, entity);

            characterDTOBasicList.add(characterDTOBasic);
        }
        return characterDTOBasicList;
    }

    // MAPPER TO PASS FROM ENTITY CHARACTER TO BASIC DTO CHARACTER
    public CharacterDTOBasic characterEntity2BasicDTO(CharacterEntity characterEntity) {

        CharacterDTOBasic characterDTOBasic = new CharacterDTOBasic();
        characterDTOBasicRefreshValues(characterDTOBasic, characterEntity);

        return characterDTOBasic;
    }

    // UPDATE CHARACTER ENTITY
    public void characterEntityRefreshValues(CharacterEntity characterEntity, CharacterDTO characterDTO) {

        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setBiography(characterDTO.getBiography());
    }

    // UPDATE DTO
    public void characterDTORefreshValues(CharacterDTO characterDTO, CharacterEntity characterEntity) {

        characterDTO.setId(characterEntity.getId());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setBiography(characterEntity.getBiography());
    }

    // UPDATE DTO BASIC
    public void characterDTOBasicRefreshValues(CharacterDTOBasic characterDTOBasic, CharacterEntity characterEntity) {

        characterDTOBasic.setId(characterEntity.getId());
        characterDTOBasic.setName(characterEntity.getName());
        characterDTOBasic.setImage(characterEntity.getImage());
    }

    // MAPPER TO PASS FROM DTO CHARACTER LIST TO ENTITY CHARACTER LIST
    List<CharacterEntity> characterDTOList2EntityList(List<CharacterDTO> characters) {

        List<CharacterEntity> characterEntityList = new ArrayList<>();

        for (CharacterDTO characterDTO : characters) {
            characterEntityList.add(characterDTO2Entity(characterDTO));
        }
        return characterEntityList;
    }
}
