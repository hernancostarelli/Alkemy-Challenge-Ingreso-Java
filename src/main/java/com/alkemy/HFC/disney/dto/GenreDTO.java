package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {

    private String id;
    private String name;

    private List<MovieDTO> movies;

}
