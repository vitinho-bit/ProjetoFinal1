package com.senai.eventsmanager.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
  
    @Autowired
    private jwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
             String authHeader = request.getHeader("Authorization");

             if(authHeader != null && 
             authHeader.startsWith( "Bearer ")){

                /*Extrair token de acesso */
                 String token = authHeader.substring( 7);
                
                /*Extrair email */
               String email = jwtUtil.extrairEmail(token);

                
                if(email != null &&
                 SecurityContextHolder.getContext().getAuthentication() == null){
                  UsernamePasswordAuthenticationToken authToken =
                  new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                 
                 //salvar a sessão criada
                  SecurityContextHolder.getContext().setAuthentication(authToken);

                }
                }
                 //Continuar a requisição
                filterChain.doFilter(request, response);
             }
    }


    




