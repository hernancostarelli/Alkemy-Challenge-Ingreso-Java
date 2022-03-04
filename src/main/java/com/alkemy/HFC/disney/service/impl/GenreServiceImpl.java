package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.GenreDTOBasic;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.exception.GenreException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
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
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreRepository genreRepository;

    // SAVE A GENRE
    @Override
    public GenreDTO saveGenre(GenreDTO genreDTO) {

        try {
            GenreEntity genreEntity = genreMapper.genreDTO2Entity(genreDTO);
            GenreEntity genreSaved = genreRepository.save(genreEntity);
            GenreDTO genreSavedDTO = genreMapper.genreEntity2DTO(genreSaved);
            return genreSavedDTO;

        } catch (GenreException exception) {
            throw new GenreException(ExceptionMessage.DTO_WRONG_DATA);
        }
    }

    // MODIFIES A GENRE
    @Override
    public GenreDTO modifyGenre(String idGenre, GenreDTO genreDTO) {

        try {

            GenreEntity savedGenre = genreRepository.getById(idGenre);
            savedGenre.setName(genreDTO.getName());
            savedGenre.setImage(genreDTO.getImage());
            GenreEntity modifiedGenreEntity = genreRepository.save(savedGenre);
            GenreDTO modifiedGenreDTO = genreMapper.genreEntity2DTO(modifiedGenreEntity);

            return modifiedGenreDTO;

        } catch (GenreException exception) {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);

        } catch (Exception exception) {
            throw new GenreException(ExceptionMessage.DTO_WRONG_DATA);
        }
    }

    // DELETE A GENRE - SOFT DELETE
    @Override
    public void deleteGenreById(String idGenre) {

        if (genreRepository.existsById(idGenre)) {
            genreRepository.deleteById(idGenre);
        } else {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }
    }

    @Override
    public List<GenreDTO> getAllGenres() {

        List<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreDTO> genreDTOList = genreMapper.genreEntityList2DTOList(genreEntities);

        return genreDTOList;
    }

    // SHOWS ALL GENRES
    @Override
    public GenreEntity getGenreById(String idGenre) {

        Optional<GenreEntity> genreEntity = genreRepository.findById(idGenre);
        if (!genreEntity.isPresent()) {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }
        return genreEntity.get();
    }

    // LIST OF BASIC GENRES
    @Override
    public List<GenreDTOBasic> getAllGenresBasic() {

        List<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreDTOBasic> genreDTOBasicList = genreMapper.genreEntityList2DTOBasicList(genreEntities);

        return genreDTOBasicList;
    }
}
