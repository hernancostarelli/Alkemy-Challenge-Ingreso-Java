package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharacterDTO {

    private String id;
    private String name;
    private String image;
    private Integer age;
    private Double weight;
    private String biography;

    private List<MovieDTO> movies;

}
