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
public class GenreDTO {

    private String id;
    private String name;
    private String image;
    private List<MovieDTO> movies;

}
