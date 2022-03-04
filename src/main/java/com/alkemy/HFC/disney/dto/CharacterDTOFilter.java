package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTOFilter {

    private String name;
    private Integer age;
    private List<String> movies;
    private String order;

    public CharacterDTOFilter(String name, Integer age, List<String> movies, String order) {
        this.name = name;
        this.age = age;
        this.movies = movies;
        this.order = order;
    }

    public boolean isASC() {
        return order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return order.compareToIgnoreCase("DESC") == 0;
    }

}
