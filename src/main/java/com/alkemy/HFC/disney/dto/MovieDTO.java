package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

    private String id;
    private String image;
    private String title;
    private String creationDate;
    private Integer rating;

    private List<CharacterDTO> characters;
    private List<GenreDTO> genres;
}
