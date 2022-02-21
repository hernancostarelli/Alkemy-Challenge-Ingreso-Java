package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.repository.GenreRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    @Lazy
    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreRepository genreRepository;

    /**
     * FROM DTO MOVIE TO ENTITY
     *
     * @param movieDTO
     * @param loadCharacter
     * @return
     */
    public MovieEntity movieDTO2Entity(MovieDTO movieDTO, boolean loadCharacter) {

        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());

        // CONVERT STRING TO DATE
        String date = movieDTO.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate transformedDate = LocalDate.parse(date, formatter);

        movieEntity.setCreationDate(transformedDate);

        movieEntity.setRating(movieDTO.getRating());

        return movieEntity;
    }

    /**
     * FROM ENTITY TO DTO MOVIE
     *
     * @param movieEntity
     * @param loadCharacter
     * @return
     */
    public MovieDTO movieEntity2DTO(MovieEntity movieEntity, boolean loadCharacter) {

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());

        // GET THE ORIGINAL FORMAT DATE
        LocalDate date = movieEntity.getCreationDate();
        // CONVERT IT TO STRING
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        movieDTO.setCreationDate(formatDate);

        movieDTO.setRating(movieEntity.getRating());

        if (loadCharacter) {
            movieDTO.setCharacters(characterMapper.characterEntityList2DTOList(movieEntity.getCharacters(), false));
            movieDTO.setGenres(genreMapper.genreEntityList2DTOList(movieEntity.getGenres()));
        }

        return movieDTO;
    }

    /**
     * RETURN A LIST OF DTO MOVIES
     *
     * @param entities
     * @param load
     * @return
     */
    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean load) {

        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (MovieEntity entity : entities) {
            movieDTOList.add(movieEntity2DTO(entity, load));
        }
        return movieDTOList;
    }

    /**
     * RETURN A LIST OF ENTITIES MOVIES
     *
     * @param movieDTOList
     * @param load
     * @return
     */
    public List<MovieEntity> movieDTOList2EntityList(List<MovieDTO> movieDTOList, boolean loadCharacter) {

        List<MovieEntity> movieEntitiesList = new ArrayList<>();

        for (MovieDTO dto : movieDTOList) {
            movieEntitiesList.add(movieDTO2Entity(dto, loadCharacter));
        }
        return movieEntitiesList;
    }

    /**
     * FROM ENTITY TO BASIC DTO MOVIE
     *
     * @param movieEntity
     * @return
     */
    public MovieDTOBasic movieEntity2BasicDTO(MovieEntity movieEntity) {

        MovieDTOBasic movieDTOBasic = new MovieDTOBasic();

        movieDTOBasic.setImage(movieEntity.getImage());
        movieDTOBasic.setTitle(movieEntity.getTitle());

        // GET THE ORIGINAL FORMAT DATE
        LocalDate date = movieEntity.getCreationDate();
        // CONVERT IT TO STRING
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        movieDTOBasic.setCreationDate(formatDate);

        return movieDTOBasic;
    }

    /**
     * RETURN A LIST OF BASIC DTO MOVIES
     *
     * @param entities
     * @return
     */
    public List<MovieDTOBasic> movieEntityList2BasicDTO(List<MovieEntity> entities) {

        List<MovieDTOBasic> movieDTOBasicList = new ArrayList<>();

        for (MovieEntity entity : entities) {
            movieDTOBasicList.add(movieEntity2BasicDTO(entity));
        }
        return movieDTOBasicList;
    }

    /**
     * FORMATTING OF STRING TO LOCALDATE
     *
     * @param stringDate
     * @return
     */
    private LocalDate string2LocalDate(String stringDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate formattedDate = LocalDate.parse(stringDate, formatter);

        return formattedDate;
    }

}
