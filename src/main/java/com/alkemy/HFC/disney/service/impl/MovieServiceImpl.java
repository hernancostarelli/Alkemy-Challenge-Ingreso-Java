package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.dto.CharacterDTO;
import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.MovieException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import com.alkemy.HFC.disney.mapper.MovieMapper;
import com.alkemy.HFC.disney.repository.CharacterRepository;
import com.alkemy.HFC.disney.repository.GenreRepository;
import com.alkemy.HFC.disney.repository.MovieRepository;
import com.alkemy.HFC.disney.repository.specification.MovieSpecification;
import com.alkemy.HFC.disney.service.MovieService;
import com.alkemy.HFC.disney.validations.DTOValidations;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Lazy
    @Autowired
    private MovieSpecification movieSpecification;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private DTOValidations dTOValidations;

    @Override
    public MovieDTO saveMovie(MovieDTO movieDTO) {

//        if (dTOValidations.movieDTOIsValid(movieDTO)) {
        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO, false);
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        MovieDTO savedMovieDTO = movieMapper.movieEntity2DTO(savedMovie, false);

        return savedMovieDTO;
//        } else {
//            throw new MovieException(ExceptionMessage.DTO_WRONG_DATA);
//        }
    }

    @Override
    public MovieDTO modifyMovie(String idMovie, MovieDTO movieDTO) {

        if (movieRepository.existsById(idMovie)) {
            if (dTOValidations.movieDTOIsValid(movieDTO)) {

                MovieEntity savedMovie = movieRepository.getById(idMovie);

                movieMapper.movierEntityRefreshValues(savedMovie, movieDTO);

                MovieEntity modifiedMovieEntity = movieRepository.save(savedMovie);

                MovieDTO modifiedMovieDTO = movieMapper.movieEntity2DTO(modifiedMovieEntity, false);

                return modifiedMovieDTO;

            } else {
                throw new MovieException(ExceptionMessage.DTO_WRONG_DATA);
            }
        } else {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    @Override
    public void deleteMovie(String idMovie) {

        if (movieRepository.existsById(idMovie)) {
            movieRepository.deleteById(idMovie);
        } else {
            throw new MovieException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    @Override
    public List<MovieDTOBasic> getAllMovieBasic() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();
        List<MovieDTOBasic> movieDTOBasicList = movieMapper.movieEntityList2BasicDTO(movieEntityList);

        return movieDTOBasicList;
    }

    @Override
    public List<MovieDTO> getAllMovies() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();
        List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

        return movieDTOList;
    }

    @Override
    public List<MovieDTO> getMovieByFilters(String id, String title, List<CharacterDTO> characters, List<GenreDTO> genres, String order) {

        if (id.isEmpty() || String.valueOf(id) == null
                && title.isEmpty() || title == null
                && characters.isEmpty() || characters == null
                && genres.isEmpty() || genres == null
                && order.isEmpty() || order == null) {

            return getAllMovies();

        } else {
            
            MovieDTOFilter movieDTOFilters = new MovieDTOFilter(id, title, characters, genres, order);
            List<MovieEntity> movieEntityList = movieRepository.findAll(movieSpecification.getFiltered(movieDTOFilters));
            List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

            return movieDTOList;
        }
    }

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

        if (characterRepository.existsById(idCharacter) && movieRepository.existsById(idMovie)) {

            MovieEntity movie = movieRepository.getById(idMovie);
            CharacterEntity character = characterRepository.getById(idCharacter);

            List<CharacterEntity> characters = movie.getCharacters();

            characters.add(character);
            movie.setCharacters(characters);
            movieRepository.save(movie);

        } else if (!characterRepository.existsById(idCharacter)) {
            throw new EntityNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND);

        } else if (!movieRepository.existsById(idMovie)) {
            throw new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // REMOVE CHARACTER FROM A MOVIE
    @Override
    public void removeCharacterFromMovie(String idCharacter, String idMovie) {

        if (characterRepository.existsById(idCharacter) && movieRepository.existsById(idMovie)) {

            MovieEntity movie = movieRepository.getById(idMovie);
            CharacterEntity character = characterRepository.getById(idCharacter);

            List<CharacterEntity> characters = movie.getCharacters();

            characters.remove(character);
            movie.setCharacters(characters);
            movieRepository.save(movie);

        } else if (!characterRepository.existsById(idCharacter)) {
            throw new EntityNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND);

        } else if (!movieRepository.existsById(idMovie)) {
            throw new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // ADD GENRE TO A MOVIE
    @Override
    public void addGenreToMovie(String idGenre, String idMovie) {

        if (genreRepository.existsById(idGenre) && movieRepository.existsById(idMovie)) {

            MovieEntity movie = movieRepository.getById(idMovie);
            GenreEntity genre = genreRepository.getById(idGenre);

            List<GenreEntity> genres = movie.getGenres();

            genres.add(genre);
            movie.setGenres(genres);
            movieRepository.save(movie);

        } else if (!genreRepository.existsById(idGenre)) {
            throw new EntityNotFoundException(ExceptionMessage.GENRE_NOT_FOUND);

        } else if (!movieRepository.existsById(idMovie)) {
            throw new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }

    // REMOVE GENRE FROM A MOVIE
    @Override
    public void removeGenreFromMovie(String idGenre, String idMovie) {

        if (genreRepository.existsById(idGenre) && movieRepository.existsById(idMovie)) {

            MovieEntity movie = movieRepository.getById(idMovie);
            GenreEntity genre = genreRepository.getById(idGenre);

            List<GenreEntity> genres = movie.getGenres();

            genres.remove(genre);
            movie.setGenres(genres);
            movieRepository.save(movie);

        } else if (!genreRepository.existsById(idGenre)) {
            throw new EntityNotFoundException(ExceptionMessage.GENRE_NOT_FOUND);

        } else if (!movieRepository.existsById(idMovie)) {
            throw new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND);
        }
    }
}
