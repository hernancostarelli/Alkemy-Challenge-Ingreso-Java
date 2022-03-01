package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.GenreDTOBasic;
import com.alkemy.HFC.disney.entity.GenreEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    // MAPPER FOR FROM DTO GENRE TO ENTITY
    public GenreEntity genreDTO2Entity(GenreDTO genreDTO) {

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(genreDTO.getName());
        genreEntity.setImage(genreDTO.getImage());

        return genreEntity;
    }

    // MAPPER FOR FROM ENTITY TO DTO GENRE
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

    public GenreDTOBasic genreEntity2BasicDTO(GenreEntity genreEntity) {

        GenreDTOBasic genreDTOBasic = new GenreDTOBasic();
        genreDTOBasic.setId(genreEntity.getId());
        genreDTOBasic.setName(genreEntity.getName());
        genreDTOBasic.setImage(genreEntity.getImage());
        return genreDTOBasic;
    }

    public List<GenreDTOBasic> genreEntityList2DTOBasicList(List<GenreEntity> entities) {

        List<GenreDTOBasic> genreDTOBasicList = new ArrayList<>();

        for (GenreEntity entity : entities) {
            genreDTOBasicList.add(genreEntity2BasicDTO(entity));
        }
        return genreDTOBasicList;
    }

    List<GenreEntity> genreDTOList2EntityList(List<GenreDTO> entities) {

        List<GenreEntity> genreEnriryList = new ArrayList<>();

        for (GenreDTO genreDTO : entities) {
            genreEnriryList.add(genreDTO2Entity(genreDTO));
        }
        return genreEnriryList;
    }

//    // MAPPER FOR GENRE DTO LIST TO GENRE ENTITY SET CONVERSION
//    public Set<GenreEntity> genreDTOList2EntitySet(List<GenreDTO> genreDTOList) {
//        return genreDTOList.stream().map(genreDTO -> genreDTO2Entity(genreDTO)).collect(Collectors.toSet());
//    }
//
//    // MAPPER FOR GENRE ENTITY SET TO GENRE DTO LIST CONVERSION
//    public List<GenreDTO> genreEntitySet2DTOList(Collection<GenreEntity> genreEntitySet, boolean loadMovies) {
//        return genreEntitySet.stream().map(genre -> genreEntity2DTO(genre)).collect(Collectors.toList());
//    }
}
