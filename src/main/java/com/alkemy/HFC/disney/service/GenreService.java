package com.alkemy.HFC.disney.service;

import com.alkemy.HFC.disney.dto.GenreDTO;
import com.alkemy.HFC.disney.entity.GenreEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface GenreService {

    GenreDTO saveGenre(GenreDTO genreDTO);

    List<GenreDTO> getAllGenres();

    GenreDTO modifyGenre(String id, GenreDTO genreDTO);

    void deleteGenreById(String id);

    GenreEntity getGenreById(String id);
}
