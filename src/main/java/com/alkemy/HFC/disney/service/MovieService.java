package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    MovieDTO saveMovie(MovieDTO movie);

    MovieDTO modifyMovie(String id, MovieDTO movieDTO);

    void deleteMovie(String id);

    List<MovieDTOBasic> getAllMovieBasic();

    List<MovieDTO> getAllMovies();

    List<MovieDTO> getMovieByFilters(String title, List<String> characters, List<String> genres, String order);

    MovieDTO getMovieById(String id);

    void addCharacterToMovie(String characterId, String movieId);

    void removeCharacterFromMovie(String idCharacter, String idMovie);

    void addGenreToMovie(String idGenre, String idMovie);

    void removeGenreFromMovie(String idGenre, String idMovie);

    MovieEntity getMovieEntityById(String idMovie);
}
