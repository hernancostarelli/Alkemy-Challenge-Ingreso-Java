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

    // MAPPER FOR FROM DTO CHARACTER TO ENTITY
    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO) {

        CharacterEntity characterEntity = new CharacterEntity();
        characterEntityRefreshValues(characterEntity, characterDTO);

        return characterEntity;
    }

    // MAPPER FOR FROM ENTITY TO DTO CHARACTER
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

    public List<CharacterDTOBasic> entityList2DTOBasicList(List<CharacterEntity> entities) {

        List<CharacterDTOBasic> characterDTOBasicList = new ArrayList<>();

        for (CharacterEntity entity : entities) {
            CharacterDTOBasic characterDTOBasic = new CharacterDTOBasic();
            characterDTOBasicRefreshValues(characterDTOBasic, entity);

            characterDTOBasicList.add(characterDTOBasic);
        }
        return characterDTOBasicList;
    }

    // MAPPER FOR FROM ENTITY TO BASIC DTO CHARACTER
    public CharacterDTOBasic characterEntity2BasicDTO(CharacterEntity characterEntity) {

        CharacterDTOBasic characterDTOBasic = new CharacterDTOBasic();
        characterDTOBasicRefreshValues(characterDTOBasic, characterEntity);

        return characterDTOBasic;
    }

//    // MAPPER FOR CHARACTERDTOLIST TO CHARACTERENTITYSET CONVERSION
//    public Set<CharacterEntity> characterDTOList2EntitySet(List<CharacterDTO> characterDTOList) {
//        return characterDTOList.stream().map(characterDTO -> characterDTO2Entity(characterDTO)).collect(Collectors.toSet());
//    }
//
//    // MAPPER FOR CHARACTERENTITYSET TO CHARACTERDTOLIST CONVERSION
//    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> characterEntitySet, boolean loadMovies) {
//        return characterEntitySet.stream().map(character -> characterEntity2DTO(character, loadMovies)).collect(Collectors.toList());
//    }

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

    List<CharacterEntity> characterDTOList2EntityList(List<CharacterDTO> characters) {
        
         List<CharacterEntity> characterEntityList = new ArrayList<>();

        for (CharacterDTO characterDTO : characters) {
            characterEntityList.add(characterDTO2Entity(characterDTO));
        }
        return characterEntityList;
    }
}
