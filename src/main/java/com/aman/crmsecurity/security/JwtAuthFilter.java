package com.aman.crmsecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter {

    @Autowired
    private  JwtServices jwtServices;


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String authHeader = request.getHeader("Authorization");

      if(authHeader == null || !authHeader.startsWith("Bearer ")){
          filterChain.doFilter(request,response);
          return;
      }

      String token = authHeader.substring(7);

      String extractedEmail = jwtServices.extractEmail(token);

      if(extractedEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

        UserDetails userDetails = userDetailsService.loadUserByUsername(extractedEmail);

        if(jwtServices.isTokenValid(extractedEmail , token)){

            UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());

            token1.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

             SecurityContextHolder.getContext().setAuthentication(token1);

        }

      }

      filterChain.doFilter(request, response);

    }
}
