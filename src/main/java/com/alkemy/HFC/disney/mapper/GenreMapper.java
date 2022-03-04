package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.GenreDTOBasic;
import com.alkemy.HFC.disney.entity.GenreEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    // MAPPER TO PASS FROM DTO GENRE TO ENTITY GENRE
    public GenreEntity genreDTO2Entity(GenreDTO genreDTO) {

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(genreDTO.getName());
        genreEntity.setImage(genreDTO.getImage());

        return genreEntity;
    }

    // MAPPER TO PASS FROM ENTITY GENRE TO DTO GENRE
    public GenreDTO genreEntity2DTO(GenreEntity genreEntity) {

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genreEntity.getId());
        genreDTO.setName(genreEntity.getName());
        genreDTO.setImage(genreEntity.getImage());

        return genreDTO;
    }

    // MAPPER FOR RETURN A LIST OF DTO GERNRES
    public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> entities) {

        List<GenreDTO> genreDTOList = new ArrayList<>();

        for (GenreEntity entity : entities) {
            genreDTOList.add(this.genreEntity2DTO(entity));
        }
        return genreDTOList;
    }

    // MAPPER TO PASS FROM ENTITY GENRE TO BASIC DTO GENRE
    public GenreDTOBasic genreEntity2BasicDTO(GenreEntity genreEntity) {

        GenreDTOBasic genreDTOBasic = new GenreDTOBasic();
        genreDTOBasic.setId(genreEntity.getId());
        genreDTOBasic.setName(genreEntity.getName());
        genreDTOBasic.setImage(genreEntity.getImage());

        return genreDTOBasic;
    }

    // MAPPER FOR RETURN A LIST OF BASIC DTO GERNRES
    public List<GenreDTOBasic> genreEntityList2DTOBasicList(List<GenreEntity> entities) {

        List<GenreDTOBasic> genreDTOBasicList = new ArrayList<>();

        for (GenreEntity entity : entities) {
            genreDTOBasicList.add(genreEntity2BasicDTO(entity));
        }
        return genreDTOBasicList;
    }

    // MAPPER FOR RETURN A LIST OF ENTITY GERNRES
    List<GenreEntity> genreDTOList2EntityList(List<GenreDTO> entities) {

        List<GenreEntity> genreEnriryList = new ArrayList<>();

        for (GenreDTO genreDTO : entities) {
            genreEnriryList.add(genreDTO2Entity(genreDTO));
        }
        return genreEnriryList;
    }
}
