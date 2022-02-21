package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.entity.GenreEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    /**
     * FROM DTO GENRE TO ENTITY
     *
     * @param genreDTO
     * @return
     */
    public GenreEntity genreDTO2Entity(GenreDTO genreDTO) {

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(genreDTO.getName());

        return genreEntity;
    }

    /**
     * FROM ENTITY TO DTO GENRE
     *
     * @param genreEntity
     * @return
     */
    public GenreDTO genreEntity2DTO(GenreEntity genreEntity) {

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genreEntity.getId());
        genreDTO.setName(genreEntity.getName());

        return genreDTO;
    }

    /**
     * RETURN A LIST OF DTO GERNRES
     *
     * @param entities
     * @return
     */
    public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> entities) {

        List<GenreDTO> genreDTOList = new ArrayList<>();

        for (GenreEntity entity : entities) {
            genreDTOList.add(this.genreEntity2DTO(entity));
        }
        return genreDTOList;
    }
}
