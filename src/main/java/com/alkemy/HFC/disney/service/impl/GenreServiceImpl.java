package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.GenreDTOBasic;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.exception.GenreException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import com.alkemy.HFC.disney.mapper.GenreMapper;
import com.alkemy.HFC.disney.repository.GenreRepository;
import com.alkemy.HFC.disney.service.GenreService;
import com.alkemy.HFC.disney.validations.DTOValidations;
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

    @Autowired
    private DTOValidations dTOValidations;

    @Override
    public GenreDTO saveGenre(GenreDTO genreDTO) {

        if (dTOValidations.genreDTOIsValid(genreDTO)) {

            GenreEntity genreEntity = genreMapper.genreDTO2Entity(genreDTO);
            GenreEntity genreSaved = genreRepository.save(genreEntity);
            GenreDTO genreSavedDTO = genreMapper.genreEntity2DTO(genreSaved);

            return genreSavedDTO;

        } else {
            throw new GenreException(ExceptionMessage.DTO_WRONG_DATA);
        }
    }

    @Override
    public GenreDTO modifyGenre(String idGenre, GenreDTO genreDTO) {

        if (genreRepository.existsById(idGenre)) {
            if (dTOValidations.genreDTOIsValid(genreDTO)) {

                GenreEntity savedGenre = genreRepository.getById(idGenre);
                savedGenre.setName(genreDTO.getName());
                GenreEntity modifiedGenreEntity = genreRepository.save(savedGenre);
                GenreDTO modifiedGenreDTO = genreMapper.genreEntity2DTO(modifiedGenreEntity);

                return modifiedGenreDTO;

            } else {
                throw new GenreException(ExceptionMessage.DTO_WRONG_DATA);
            }
        } else {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }
    }

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

    @Override
    public GenreEntity getGenreById(String idGenre) {

        Optional<GenreEntity> genreEntity = genreRepository.findById(idGenre);
        if (!genreEntity.isPresent()) {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }

        return genreEntity.get();
    }

    @Override
    public List<GenreDTOBasic> getAllGenresBasic() {

        List<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreDTOBasic> genreDTOBasicList = genreMapper.genreEntityList2DTOBasicList(genreEntities);

        return genreDTOBasicList;
    }

}
