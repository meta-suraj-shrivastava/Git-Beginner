package com.writerHub.practice.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.AuthenticationSuccess;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.WriterHubUserService;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final WriterHubUserService writerHubUserService;

    public AuthenticationFilter(AuthenticationManager authenticationManager,WriterHubUserService writerHubUserService) {
        this.authenticationManager = authenticationManager;
        this.writerHubUserService = writerHubUserService;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        WriterHubUser appUser = writerHubUserService.getUser(user.getUsername());
        String access_token = JWTUtil.getToken(appUser,request.getServletPath().toString(),false);
        String refresh_token = JWTUtil.getToken(appUser,request.getServletPath().toString(),true);
        AuthenticationSuccess responseBody = new AuthenticationSuccess();
        responseBody.setAccess_token(access_token);
        responseBody.setRefresh_token(refresh_token);
        responseBody.setUser(appUser);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        AuthenticationError error = new AuthenticationError();
        error.setStatusCode(HttpStatus.UNAUTHORIZED);
        error.setMessage("Incorrect Credentials..!");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(),error);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }
}
