package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.CharacterException;
import com.alkemy.HFC.disney.exception.GenreException;
import com.alkemy.HFC.disney.exception.MovieException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import com.alkemy.HFC.disney.mapper.MovieMapper;
import com.alkemy.HFC.disney.repository.CharacterRepository;
import com.alkemy.HFC.disney.repository.GenreRepository;
import com.alkemy.HFC.disney.repository.MovieRepository;
import com.alkemy.HFC.disney.repository.specification.MovieSpecification;
import com.alkemy.HFC.disney.service.MovieService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private CharacterRepository characterRepository;

    @Lazy
    @Autowired
    private CharacterEntity characterEntity;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Lazy
    @Autowired
    private MovieSpecification movieSpecification;

    @Lazy
    @Autowired
    private MovieEntity movieEntity;

    @Autowired
    private GenreRepository genreRepository;

    @Lazy
    @Autowired
    private GenreEntity genreEntity;

    // SAVE A MOVIE
    @Override
    public MovieDTO saveMovie(MovieDTO movieDTO) {

        try {

            MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO, false);
            MovieEntity savedMovie = movieRepository.save(movieEntity);
            MovieDTO savedMovieDTO = movieMapper.movieEntity2DTO(savedMovie, false);

            return savedMovieDTO;

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.DTO_WRONG_DATA);
        }
    }

    // MODIFIES A MOVIE
    @Override
    public MovieDTO modifyMovie(String idMovie, MovieDTO movieDTO) {

        try {
            MovieEntity savedMovie = movieRepository.getById(idMovie);

            movieMapper.movierEntityRefreshValues(savedMovie, movieDTO);

            MovieEntity modifiedMovieEntity = movieRepository.save(savedMovie);

            MovieDTO modifiedMovieDTO = movieMapper.movieEntity2DTO(modifiedMovieEntity, false);

            return modifiedMovieDTO;

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);

        } catch (Exception exception) {
            throw new MovieException(ExceptionMessage.DTO_WRONG_DATA);
        }
    }

    // DELETE A MOVIE - SOFT DELETE
    @Override
    public void deleteMovie(String idMovie) {

        if (movieRepository.existsById(idMovie)) {
            movieRepository.deleteById(idMovie);
        } else {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // LIST OF BASIC MOVIES
    @Override
    public List<MovieDTOBasic> getAllMovieBasic() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();
        List<MovieDTOBasic> movieDTOBasicList = movieMapper.movieEntityList2BasicDTO(movieEntityList);

        return movieDTOBasicList;
    }

    // SHOWS ALL MOVIES
    @Override
    public List<MovieDTO> getAllMovies() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();
        List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

        return movieDTOList;
    }

    // LIST OF MOVIES BY FILTER
    @Override
    public List<MovieDTO> getMovieByFilters(String title, List<String> characters, List<String> genres, String order) {

        MovieDTOFilter movieDTOFilters = new MovieDTOFilter(title, characters, genres, order);
        List<MovieEntity> movieEntityList = movieRepository.findAll(movieSpecification.getFiltered(movieDTOFilters));
        List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

        return movieDTOList;
    }

    // GET MOVIE DTO
    @Override
    public MovieDTO getMovieById(String idMovie) {

        if (movieRepository.existsById(idMovie)) {

            MovieEntity movieEntity = movieRepository.getById(idMovie);
            MovieDTO movieDTO = movieMapper.movieEntity2DTO(movieEntity, true);
            return movieDTO;

        } else {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // ADD CHARACTER TO A MOVIE
    @Override
    public void addCharacterToMovie(String idCharacter, String idMovie) {

        try {

            movieEntity = movieRepository.getById(idMovie);
            characterEntity = characterRepository.getById(idCharacter);

            List<CharacterEntity> characters = movieEntity.getCharacters();

            characters.add(characterEntity);
            movieEntity.setCharacters(characters);
            movieRepository.save(movieEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);

        } catch (CharacterException exception) {
            throw new CharacterException(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
    }

    // REMOVE CHARACTER FROM A MOVIE
    @Override
    public void removeCharacterFromMovie(String idCharacter, String idMovie) {

        try {
            movieEntity = movieRepository.getById(idMovie);
            characterEntity = characterRepository.getById(idCharacter);

            List<CharacterEntity> characters = movieEntity.getCharacters();

            characters.remove(characterEntity);
            movieEntity.setCharacters(characters);
            movieRepository.save(movieEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);

        } catch (CharacterException exception) {
            throw new CharacterException(ExceptionMessage.CHARACTER_NOT_FOUND);
        }
    }

    // ADD GENRE TO A MOVIE
    @Override
    public void addGenreToMovie(String idGenre, String idMovie) {

        try {
            movieEntity = movieRepository.getById(idMovie);
            genreEntity = genreRepository.getById(idGenre);

            List<GenreEntity> genres = movieEntity.getGenres();

            genres.add(genreEntity);
            movieEntity.setGenres(genres);
            movieRepository.save(movieEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);

        } catch (GenreException exception) {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }
    }

    // REMOVE GENRE FROM A MOVIE
    @Override
    public void removeGenreFromMovie(String idGenre, String idMovie) {

        try {
            movieEntity = movieRepository.getById(idMovie);
            genreEntity = genreRepository.getById(idGenre);

            List<GenreEntity> genres = movieEntity.getGenres();

            genres.remove(genreEntity);
            movieEntity.setGenres(genres);
            movieRepository.save(movieEntity);

        } catch (MovieException exception) {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);

        } catch (GenreException exception) {
            throw new GenreException(ExceptionMessage.GENRE_NOT_FOUND);
        }
    }

    // GET MOVIE ENTITY
    @Override
    public MovieEntity getMovieEntityById(String idMovie) {

        Optional<MovieEntity> movieEntity = movieRepository.findById(idMovie);
        if (!movieEntity.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
        return movieEntity.get();
    }
}
