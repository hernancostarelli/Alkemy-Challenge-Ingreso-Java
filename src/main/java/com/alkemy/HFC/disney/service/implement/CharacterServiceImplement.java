package com.alkemy.HFC.disney.service.implement;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.CharacterDTOBasic;
import com.alkemy.HFC.disney.dto.CharacterDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.ParamNotFound;
import com.alkemy.HFC.disney.mapper.CharacterMapper;
import com.alkemy.HFC.disney.mapper.MovieMapper;
import com.alkemy.HFC.disney.repository.CharacterRepository;
import com.alkemy.HFC.disney.repository.specification.CharacterSpecification;
import com.alkemy.HFC.disney.service.CharacterService;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class CharacterServiceImplement implements CharacterService {

    @Lazy
    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private CharacterRepository characterRepository;

    @Lazy
    @Autowired
    private CharacterSpecification characterSpecification;

    @Lazy
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;


    /**
     * SAVE A CHARACTER
     *
     * @param characterDTO
     * @return
     */
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

    /**
     * SHOWS ALL CHARACTERS
     *
     * @return
     */
    @Override
    public List<CharacterDTO> getAllCharacter() {

        List<CharacterEntity> chararacterEntities = characterRepository.findAll();

        List<CharacterDTO> characterDTOList = characterMapper.characterEntityList2DTOList(chararacterEntities, true);

        return characterDTOList;
    }

    /**
     * DELETE A CHARACTER - SOFT DELETE
     *
     * @param idCharacter
     */
    @Override
    public void deleteCharacter(String idCharacter) {
        characterRepository.deleteById(idCharacter);
    }

    /**
     * MODIFIES A CHARACTER
     *
     * @param idCharacter
     * @param characterDTO
     * @return
     */
    @Override
    public CharacterDTO modifyCharacter(String idCharacter, CharacterDTO characterDTO) {

        CharacterEntity savedCharacter = characterRepository.getById(idCharacter);

        savedCharacter.setName(characterDTO.getName());
        savedCharacter.setImage(characterDTO.getImage());
        savedCharacter.setAge(characterDTO.getAge());
        savedCharacter.setWeight(characterDTO.getWeight());
        savedCharacter.setBiography(characterDTO.getBiography());

        savedCharacter.setMovies(movieMapper.movieDTOList2EntityList(characterDTO.getMovies(), false));

        CharacterEntity editedChar = characterRepository.save(savedCharacter);

        CharacterDTO savedDTO = characterMapper.characterEntity2DTO(editedChar, false);

        return savedDTO;
    }

    /**
     * LIST OF BASIC CHARACTERS
     *
     * @return
     */
    @Override
    public List<CharacterDTOBasic> getBasicCharacterList() {

        List<CharacterEntity> characterEntityList = characterRepository.findAll();

        List<CharacterDTOBasic> characterDTOBasicList = characterMapper.entityList2DTOBasicList(characterEntityList);

        return characterDTOBasicList;
    }

    /**
     * DISPLAYS A CHARACTER ACCORDING TO THE ID
     *
     * @param idCharacter
     * @return
     */
    @Override
    public CharacterEntity getCharacterById(String idCharacter) {

        Optional<CharacterEntity> characterEntity = characterRepository.findById(idCharacter);

        if (!characterEntity.isPresent()) {
            throw new ParamNotFound("THIS CHARACTER DOES NOT EXIST");
        }
        return characterEntity.get();
    }

    /**
     * LIST OF CHARACTERS BY FILTER
     *
     * @param name
     * @param age
     * @param movies
     * @param order
     * @return
     */
    @Override
    public List<CharacterDTO> getCharacterByFilters(String name, Integer age, Set<String> movies, String order) {

        // WE INSTANTIATE THE DTO WITH FILTERS
        CharacterDTOFilter characterFilter = new CharacterDTOFilter(name, age, movies, order);

        // WE SEARCH ACCORDING TO FILTERS
        List<CharacterEntity> characterEntities = characterRepository.findAll(characterSpecification.getByFilters(characterFilter));

        // WE CONVERT THE LIST TO DTO
        List<CharacterDTO> characterDTOList = characterMapper.characterEntityList2DTOList(characterEntities, true);

        return characterDTOList;
    }

    /**
     * SHOWS ALL THE DETAILS OF A CHARACTER
     *
     * @param idCharacter
     * @return
     */
    @Override
    public CharacterDTO getCharacterDetailById(String idCharacter) {

        CharacterEntity characterEntity = characterRepository.getById(idCharacter);

        CharacterDTO characterDTO = characterMapper.characterEntity2DTO(characterEntity, true);

        return characterDTO;
    }

    /**
     * TO ADD A MOVIE TO THE CHARACTER
     *
     * @param idCharacter
     * @param idMovie
     */
    @Override
    public void addMovie(String idCharacter, String idMovie) {

        CharacterEntity characterEntity = characterRepository.getById(idCharacter);
        characterEntity.getMovies().size();

        MovieEntity movieEntity = movieService.getMovieById(idMovie);
        characterEntity.addMovie(movieEntity);

        characterRepository.save(characterEntity);
    }

    /**
     * TO REMOVE A MOVIE FROM THE CHARACTER
     *
     * @param idCharacter
     * @param idMovie
     */
    @Override
    public void removeMovie(String idCharacter, String idMovie) {

        CharacterEntity characterEntity = characterRepository.getById(idCharacter);
        characterEntity.getMovies().size();

        MovieEntity movie = movieService.getMovieById(idMovie);
        characterEntity.removeMovie(movie);

        characterRepository.save(characterEntity);
    }

}
