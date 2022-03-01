package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String id;
    private String image;
    private String title;
    private String creationDate;
    private float rating;
    private List<CharacterDTO> characters;
    private List<GenreDTO> genres;
}
