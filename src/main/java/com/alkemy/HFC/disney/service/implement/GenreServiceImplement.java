package com.alkemy.HFC.disney.service.implement;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.exception.ParamNotFound;
import com.alkemy.HFC.disney.mapper.GenreMapper;
import com.alkemy.HFC.disney.repository.GenreRepository;
import com.alkemy.HFC.disney.service.GenreService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class GenreServiceImplement implements GenreService {

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreDTO saveGenre(GenreDTO genreDTO) {

        GenreEntity genreEntity = genreMapper.genreDTO2Entity(genreDTO);

        GenreEntity genreSaved = genreRepository.save(genreEntity);

        GenreDTO genreSavedDTO = genreMapper.genreEntity2DTO(genreSaved);

        return genreSavedDTO;
    }

    @Override
    public GenreDTO modifyGenre(String idGenre, GenreDTO genreDTO) {

        GenreEntity savedGenre = genreRepository.getById(idGenre);

        savedGenre.setName(genreDTO.getName());

        GenreEntity modifiedGenreEntity = genreRepository.save(savedGenre);

        GenreDTO modifiedGenreDTO = genreMapper.genreEntity2DTO(modifiedGenreEntity);

        return modifiedGenreDTO;
    }

    @Override
    public void deleteGenreById(String idGenre) {
    }

    @Override
    public List<GenreDTO> getAllGenres() {

        List<GenreEntity> genreEntities = genreRepository.findAll();

        List<GenreDTO> genreDTOList = genreMapper.genreEntityList2DTOList(genreEntities);

        return genreDTOList;
    }

    @Override
    public GenreEntity getGenreById(String idGenre) {

        Optional<GenreEntity> genreEntity = genreRepository.findById(idGenre);

        if (!genreEntity.isPresent()) {
            throw new ParamNotFound("THIS GENRE DOES NOT EXIST");
        }
        return genreEntity.get();
    }

}
