package com.example.school.Security;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!"application/json".equals(request.getContentType())) {
            return super.attemptAuthentication(request, response);
        }

        try (BufferedReader reader = request.getReader()) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(reader);
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);


            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException ex) {
            throw new AuthenticationServiceException("Parsing Request failed", ex);
        }
    }
}