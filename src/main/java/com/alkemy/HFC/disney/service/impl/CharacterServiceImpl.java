package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.CharacterDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.CharacterException;
import com.alkemy.HFC.disney.exception.MovieException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import com.alkemy.HFC.disney.mapper.CharacterMapper;
import com.alkemy.HFC.disney.repository.CharacterRepository;
import com.alkemy.HFC.disney.repository.specification.CharacterSpecification;
import com.alkemy.HFC.disney.service.CharacterService;
import com.alkemy.HFC.disney.service.MovieService;
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

    @Lazy
    @Autowired
    private CharacterEntity characterEntity;

    @Autowired
    private CharacterRepository characterRepository;

    @Lazy
    @Autowired
    private CharacterSpecification characterSpecification;

    @Lazy
    @Autowired
    private MovieEntity movieEntity;

    @Autowired
    private MovieService movieService;

    // SAVE A CHARACTER
    @Override
    public CharacterDTO saveCharacter(CharacterDTO characterDTO) {

        // I RECEIVE AS PARAMETER A DTO CHARACTER AND CONVERT IT TO AN ENTITY
        CharacterEntity characterEntity = characterMapper.characterDTO2Entity(characterDTO);

        // ONCE I GET THE ENTITY, I SAVE IT IN THE DATABASE AND I GET BACK THE SAVED ENTITY
        CharacterEntity savedCharacter = characterRepository.save(characterEntity);

        // PASS THE SAVED ENTITY TO DTO CHARACTER AND END UP RETURNING THE DTO CHARACTER
        CharacterDTO savedCharacterDTO = characterMapper.characterEntity2DTO(savedCharacter, false);

        return savedCharacterDTO;
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

        try {

            characterEntity = characterRepository.getById(idCharacter);

            CharacterEntity newCharacterEntity = characterMapper.characterDTO2Entity(characterDTO);

            characterEntity = characterRepository.save(newCharacterEntity);

            CharacterDTO newCharacterDTO = characterMapper.characterEntity2DTO(characterEntity, false);

            return newCharacterDTO;

        } catch (CharacterException exception) {
            throw new CharacterException(ExceptionMessage.CHARACTER_NOT_FOUND);

        } catch (Exception exception) {
            throw new CharacterException(ExceptionMessage.DTO_WRONG_DATA);
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
    public List<CharacterDTO> getCharacterByFilters(String name, Integer age, List<String> movies, String order) {

        // WE INSTANTIATE THE DTO WITH FILTERS
        CharacterDTOFilter characterFilter = new CharacterDTOFilter(name, age, movies, order);

        // WE SEARCH ACCORDING TO FILTERS
        List<CharacterEntity> characterEntities = characterRepository.findAll(characterSpecification.getByFilters(characterFilter));

        // WE CONVERT THE LIST TO DTO
        List<CharacterDTO> characterDTOList = characterMapper.characterEntityList2DTOList(characterEntities, true);

        return characterDTOList;
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

    // ADD A MOVIE TO THE CHARACTER
    @Override
    public void addMovieCharacter(String idCharacter, String idMovie) {

        try {

            CharacterEntity characterEntity = characterRepository.getById(idCharacter);
            characterEntity.getMovies().size();
            movieEntity = movieService.getMovieEntityById(idMovie);
            characterEntity.addMovie(movieEntity);
            characterRepository.save(characterEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // REMOVE A MOVIE TO THE CHARACTER
    @Override
    public void removeMovieCharacter(String idCharacter, String idMovie) {

        try {

            CharacterEntity characterEntity = characterRepository.getById(idCharacter);
            characterEntity.getMovies().size();
            movieEntity = movieService.getMovieEntityById(idMovie);
            characterEntity.removeMovie(movieEntity);
            characterRepository.save(characterEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // GET CHARACTER ENTITY
    @Override
    public CharacterEntity getCharacterEntityById(String idCharacter) throws Exception {

        Optional<CharacterEntity> characterEntity = characterRepository.findById(idCharacter);
        if (!characterEntity.isPresent()) {
            throw new Exception(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
        return characterEntity.get();
    }
}
