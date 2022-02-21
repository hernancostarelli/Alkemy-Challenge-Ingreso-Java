package com.alkemy.HFC.disney.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTOBasic {
    
    private String id;
    private String image;
    private String title;
    private String creationDate;
    private Integer rating;

}
