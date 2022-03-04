package com.alkemy.HFC.disney.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @Email(message = "USERNAME MUST BE AN EMAIL")
    @NotNull
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
}
