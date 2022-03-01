package com.alkemy.HFC.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTOBasic {

    private String id;
    private String image;
    private String title;
    private String creationDate;
}
