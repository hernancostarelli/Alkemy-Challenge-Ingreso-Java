package com.alkemy.HFC.disney.auth.controller;

import com.alkemy.HFC.disney.auth.dto.AuthenticationRequest;
import com.alkemy.HFC.disney.auth.dto.AuthenticationResponse;
import com.alkemy.HFC.disney.auth.dto.UserDTO;
import com.alkemy.HFC.disney.auth.service.JwtUtils;
import com.alkemy.HFC.disney.auth.service.UserDetailsCustomService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserDetailsCustomService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;

    @PostMapping("/singup")
    public ResponseEntity<AuthenticationResponse> singup(@Valid @RequestBody UserDTO userDTO) throws Exception {
        
        userDetailsService.register(userDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/singin")
    public ResponseEntity<AuthenticationResponse> singin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        UserDetails userDetails;
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            userDetails = (UserDetails) authentication.getPrincipal();

        } catch (BadCredentialsException exception) {
            throw new Exception("USERNAME OR PASSWORD INCORRECT", exception);
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
