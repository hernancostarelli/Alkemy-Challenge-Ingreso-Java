package com.alkemy.HFC.disney.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * DTO TO HANDLE ALL ERRORS
 */
@Data
@AllArgsConstructor
public class APIErrorDTO {

    private HttpStatus status;
    private String message;
    private List<String> errors;
}
