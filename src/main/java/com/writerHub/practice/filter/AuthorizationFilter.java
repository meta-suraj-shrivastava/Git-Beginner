package com.writerHub.practice.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.models.AuthenticationError;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/auth/login") ||
                request.getServletPath().equals("/auth/token/refresh") ||
                request.getServletPath().equals("/auth/signup")){
            filterChain.doFilter(request,response);
        }
        else{
            try {
                String access_token = JWTUtil.extractToken(request);
                DecodedJWT decoded = JWTUtil.decodeToken(access_token);
                if(decoded != null){
                    String username = decoded.getSubject();
                    String role = decoded.getClaim("role").asString();
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    request.setAttribute("username",username);
                    filterChain.doFilter(request,response);
                }
            }catch (Exception exception){
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    AuthenticationError error = new AuthenticationError();
                    error.setStatusCode(HttpStatus.UNAUTHORIZED);
                    error.setMessage(exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                }
            }
        }
    }
