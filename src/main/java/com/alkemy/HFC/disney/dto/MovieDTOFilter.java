package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTOFilter {

    private String title;
    private List<String> characters;
    private List<String> genres;
    private String order;

    public MovieDTOFilter(String title, List<String> characters, List<String> genres, String order) {
        this.title = title;
        this.characters = characters;
        this.genres = genres;
        this.order = order;
    }

    public boolean isASC() {
        return order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return order.compareToIgnoreCase("DESC") == 0;
    }

}
