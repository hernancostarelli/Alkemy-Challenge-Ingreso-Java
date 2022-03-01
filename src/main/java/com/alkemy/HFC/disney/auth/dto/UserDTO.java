package com.alkemy.HFC.disney.auth.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @Email(message = "USERNAME MUST BE AN EMAIL")
    private String username;
    @Size(min = 8)
    private String password;
}
