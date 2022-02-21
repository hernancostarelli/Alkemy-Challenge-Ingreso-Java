package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    MovieDTO saveMovie(MovieDTO movie);

    List<MovieDTO> getAllMovies();

    List<MovieDTOBasic> getAllMoviesBasic();

    void deleteMovie(String id);

    MovieDTO modifyMovie(String id, MovieDTO movieDTO);

    void addCharacter(String movieId, String characterId);

    MovieEntity getMovieById(String id);

    MovieDTO getMovieByDetails(String id);

    void addGenre(String movieId, String genreId);

    List<MovieDTO> getMovieByFilters(String title, Set<String> genre, String order);
}
