package com.alkemy.HFC.disney.service.implement;

import com.alkemy.HFC.disney.dto.MovieDTO;
import com.alkemy.HFC.disney.dto.MovieDTOBasic;
import com.alkemy.HFC.disney.dto.MovieDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import com.alkemy.HFC.disney.exception.ParamNotFound;
import com.alkemy.HFC.disney.mapper.MovieMapper;
import com.alkemy.HFC.disney.repository.MovieRepository;
import com.alkemy.HFC.disney.repository.specification.MovieSpecification;
import com.alkemy.HFC.disney.service.CharacterService;
import com.alkemy.HFC.disney.service.GenreService;
import com.alkemy.HFC.disney.service.MovieService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class MovieServiceImplement implements MovieService {

    @Lazy
    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Lazy
    @Autowired
    private MovieSpecification movieSpecification;

    @Autowired
    private GenreService genreService;

    @Override
    public MovieDTO saveMovie(MovieDTO movie) {

        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movie, true);

        MovieEntity savedMovie = movieRepository.save(movieEntity);

        MovieDTO savedMovieDTO = movieMapper.movieEntity2DTO(savedMovie, false);

        return savedMovieDTO;
    }

    @Override
    public void deleteMovie(String idMovie) {
        movieRepository.deleteById(idMovie);
    }

    @Override
    public MovieDTO modifyMovie(String idMovie, MovieDTO movieDTO) {

        MovieEntity savedMovie = movieRepository.getById(idMovie);

        savedMovie.setImage(movieDTO.getImage());
        savedMovie.setTitle(movieDTO.getTitle());

        String date = movieDTO.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate transformedDate = LocalDate.parse(date, formatter);

        savedMovie.setCreationDate(transformedDate);

        savedMovie.setRating(movieDTO.getRating());

        MovieEntity modifiedMovieEntity = movieRepository.save(savedMovie);

        MovieDTO modifiedMovieDTO = movieMapper.movieEntity2DTO(modifiedMovieEntity, false);

        return modifiedMovieDTO;
    }

    @Override
    public List<MovieDTO> getAllMovies() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();

        List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

        return movieDTOList;
    }

    @Override
    public List<MovieDTOBasic> getAllMoviesBasic() {

        List<MovieEntity> movieEntityList = movieRepository.findAll();

        List<MovieDTOBasic> movieDTOBasicList = movieMapper.movieEntityList2BasicDTO(movieEntityList);

        return movieDTOBasicList;
    }

    @Override
    public MovieEntity getMovieById(String idMovie) {

        Optional<MovieEntity> movieEntity = movieRepository.findById(idMovie);

        if (!movieEntity.isPresent()) {
            throw new ParamNotFound("THIS MOVIE DOES NOT EXIST");
        }
        return movieEntity.get();
    }

    @Override
    public MovieDTO getMovieByDetails(String idMovie) {

        MovieEntity movieEntity = movieRepository.getById(idMovie);

        MovieDTO movieDTO = movieMapper.movieEntity2DTO(movieEntity, true);

        return movieDTO;
    }

    @Override
    public void addCharacter(String idMovie, String idCharacter) {

        MovieEntity movieEntity = movieRepository.getById(idMovie);

        movieEntity.getCharacters().size();

        CharacterEntity characterEntity = characterService.getCharacterById(idCharacter);

        movieEntity.addCharacter(characterEntity);

        movieRepository.save(movieEntity);
    }

    @Override
    public void addGenre(String idMovie, String idGenre) {

        MovieEntity movieEntity = movieRepository.getById(idMovie);

        movieEntity.getGenres().size();

        GenreEntity genreEntity = genreService.getGenreById(idGenre);

        movieEntity.addGenre(genreEntity);

        movieRepository.save(movieEntity);
    }

    @Override
    public List<MovieDTO> getMovieByFilters(String title, Set<String> genre, String order) {

        MovieDTOFilter movieDTOFilters = new MovieDTOFilter(title, genre, order);

        List<MovieEntity> movieEntityList = movieRepository.findAll(movieSpecification.getFiltered(movieDTOFilters));

        List<MovieDTO> movieDTOList = movieMapper.movieEntityList2DTOList(movieEntityList, true);

        return movieDTOList;
    }

}
