package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    /**
     * FROM DTO CHARACTER TO ENTITY
     *
     * @param characterDTO
     * @return
     */
    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO) {

        CharacterEntity characterEntity = new CharacterEntity();

        characterEntity.setName(characterDTO.getName());
        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setBiography(characterDTO.getBiography());

        return characterEntity;
    }

    /**
     * FROM ENTITY TO DTO CHARACTER
     *
     * @param characterEntity
     * @param loadMovie
     * @return
     */
    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity, boolean loadMovie) {

        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setId(characterEntity.getId());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setBiography(characterEntity.getBiography());

        // VALID IF MOVIES ARE UPLOADED
        if (loadMovie) {
            List<MovieDTO> movieDTOListList = new ArrayList<>();
            for (MovieEntity entity : characterEntity.getMovies()) {
                movieDTOListList.add(movieMapper.movieEntity2DTO(entity, false));
            }
            characterDTO.setMovies(movieDTOListList);
        }
        return characterDTO;
    }

    /**
     * RETURN A LIST OF DTO CHARACTERS
     *
     * @param entities
     * @param loadMovie
     * @return
     */
    public List<CharacterDTO> characterEntityList2DTOList(List<CharacterEntity> entities, boolean loadMovie) {

        List<CharacterDTO> characterDTOList = new ArrayList<>();

        for (CharacterEntity entity : entities) {
            characterDTOList.add(characterEntity2DTO(entity, loadMovie));
        }
        return characterDTOList;
    }

    /**
     *
     * @param entities
     * @return
     */
    public List<CharacterDTOBasic> entityList2DTOBasicList(Collection<CharacterEntity> entities) {

        List<CharacterDTOBasic> characterDTOBasicList = new ArrayList<>();

        CharacterDTOBasic characterDTOBasic;

        for (CharacterEntity entity : entities) {
            characterDTOBasic = new CharacterDTOBasic();
            characterDTOBasic.setId(entity.getId());
            characterDTOBasic.setName(entity.getName());
            characterDTOBasic.setImage(entity.getImage());

            characterDTOBasicList.add(characterDTOBasic);
        }
        return characterDTOBasicList;
    }

    /**
     * FROM ENTITY TO BASIC DTO CHARACTER
     *
     * @param characterEntity
     * @return
     */
    public CharacterDTOBasic characterEntity2BasicDTO(CharacterEntity characterEntity) {

        CharacterDTOBasic characterDTOBasic = new CharacterDTOBasic();

        characterDTOBasic.setImage(characterEntity.getImage());
        characterDTOBasic.setName(characterEntity.getName());

        return characterDTOBasic;
    }

}
