package com.senai.eventsmanager.config;

import java.security.Key;
import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component

/*
 * CHAVE CRIPTOGRAFICA UTILIZADA PARA ASSINAR E VERFIFICAR TOKEN
 * USANDO O ALGORITIMO CHAMADO DE HMAC - SHA
 */
public class JwtUtil {

    private final String SEGREDO = "umaChaveSuperSecretaDeNoMinimo32Caracteres!";

    private final Key key = Keys.hmacShaKeyFor(SEGREDO.getBytes());

    public String gerarToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4))
                .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean verificarSeTokenEValido(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extrairEmail(String token) {
        return getClaims(token).getBody().getSubject();
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // pegar a chave secreta para descriptografar o conteúdo
                .build()
                .parseClaimsJws(token);// Transformar o token em algo legivel
    }

}
