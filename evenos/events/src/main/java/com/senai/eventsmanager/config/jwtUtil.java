package com.senai.eventsmanager.config;


import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class jwtUtil {

    private final String SEGREDO = "umaChaveSuperSecretaDeNoMinimo32Caracteres!";

    private final Key key = Keys.hmacShaKeyFor(SEGREDO.getBytes());
    
}
