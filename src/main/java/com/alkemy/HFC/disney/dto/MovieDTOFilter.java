package com.alkemy.HFC.disney.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTOFilter {

    private String title;
    private Set<String> genre;
    private String order;

    public boolean isASC() {
        return order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return order.compareToIgnoreCase("DESC") == 0;
    }

}
