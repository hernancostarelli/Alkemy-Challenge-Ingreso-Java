package com.alkemy.HFC.disney.mapper;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.MovieException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
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

    // MAPPER TO PASS FROM DTO MOVIE TO ENTITY
    public MovieEntity movieDTO2Entity(MovieDTO movieDTO, boolean loadCharacter) {

        MovieEntity movieEntity = new MovieEntity();
        movierEntityRefreshValues(movieEntity, movieDTO);
        return movieEntity;
    }

    // MAPPER TO PASS FROM ENTITY TO DTO MOVIE
    public MovieDTO movieEntity2DTO(MovieEntity movieEntity, boolean loadCharacter) {

        if (movieEntity != null) {

            MovieDTO movieDTO = new MovieDTO();
            movieDTORefreshValues(movieDTO, movieEntity, loadCharacter);
            return movieDTO;

        } else {
            throw new MovieException(ExceptionMessage.ENTITY_WRONG_DATA);
        }
    }

    // MAPPER TO PASS FROM ENTITY TO BASIC DTO MOVIE
    public MovieDTOBasic movieEntity2BasicDTO(MovieEntity movieEntity) {

        if (movieEntity != null) {
            MovieDTOBasic movieDTOBasic = new MovieDTOBasic();
            movieDTOBasicRefreshValues(movieDTOBasic, movieEntity);
            return movieDTOBasic;

        } else {
            throw new MovieException(ExceptionMessage.ENTITY_WRONG_DATA);
        }
    }

    // MAPPER FOR RETURN A LIST OF DTO MOVIES
    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean load) {

        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (MovieEntity entity : entities) {
            movieDTOList.add(movieEntity2DTO(entity, load));
        }
        return movieDTOList;
    }

    // MAPPER FOR RETURN A LIST OF ENTITIES MOVIES
    public List<MovieEntity> movieDTOList2EntityList(List<MovieDTO> movieDTOList, boolean loadCharacter) {

        List<MovieEntity> movieEntitiesList = new ArrayList<>();

        for (MovieDTO dto : movieDTOList) {
            movieEntitiesList.add(movieDTO2Entity(dto, loadCharacter));
        }
        return movieEntitiesList;
    }

    // MAPPER FOR RETURN A LIST OF BASIC DTO MOVIES
    public List<MovieDTOBasic> movieEntityList2BasicDTO(List<MovieEntity> entities) {

        List<MovieDTOBasic> movieDTOBasicList = new ArrayList<>();

        if (entities != null) {
            for (MovieEntity entity : entities) {
                movieDTOBasicList.add(movieEntity2BasicDTO(entity));
            }
        }
        return movieDTOBasicList;
    }

    // FORMATTING OF STRING TO LOCALDATE
    private LocalDate string2LocalDate(String stringDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate formattedDate = LocalDate.parse(stringDate, formatter);

        return formattedDate;
    }

    // FORMATTING OF LOCALDATE TO STRING
    private String localDate2String(LocalDate localDate) {

        String formatDate = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        return formatDate;
    }

    // UPDATE MOVIE ENTITY
    public void movierEntityRefreshValues(MovieEntity movieEntity, MovieDTO movieDTO) {

        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());

        // CONVERT STRING TO DATE
        String date = movieDTO.getCreationDate();
        movieEntity.setCreationDate(string2LocalDate(date));

        movieEntity.setRating(movieDTO.getRating());

//        // SET CHARACTERS TO MOVIE
//        List<CharacterEntity> characterEntityList = characterMapper.characterDTOList2EntityList(movieDTO.getCharacters());
//        movieEntity.setCharacters(characterEntityList);
//
//        // SET GENRES TO MOVIE
//        List<GenreEntity> genreEntityList = genreMapper.genreDTOList2EntityList(movieDTO.getGenres());
//        movieEntity.setGenres(genreEntityList);
    }

    // UPDATE DTO
    public void movieDTORefreshValues(MovieDTO movieDTO, MovieEntity movieEntity, boolean loadCharacter) {

        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());

        // CONVERT STRING TO DATE
        LocalDate date = movieEntity.getCreationDate();
        movieDTO.setCreationDate(localDate2String(date));

        movieDTO.setRating(movieEntity.getRating());

        if (loadCharacter) {
            // CAST
            movieDTO.setCharacters((List<CharacterDTO>) characterMapper.characterEntityList2DTOList((List<CharacterEntity>) movieEntity.getCharacters(), false));
            // CAST
            movieDTO.setGenres((List<GenreDTO>) genreMapper.genreEntityList2DTOList((List<GenreEntity>) movieEntity.getGenres()));
        }
    }

    // UPDATE DTO BASIC
    public void movieDTOBasicRefreshValues(MovieDTOBasic movieDTOBasic, MovieEntity movieEntity) {

        movieDTOBasic.setId(movieEntity.getId());
        movieDTOBasic.setImage(movieEntity.getImage());
        movieDTOBasic.setTitle(movieEntity.getTitle());

        // CONVERT IT TO STRING
        LocalDate localDate = movieEntity.getCreationDate();
        movieDTOBasic.setCreationDate(localDate2String(localDate));
    }
}
