package com.example.school.Security;

import com.example.school.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Autowired // Autowired member variable
    private final MyUserDetailsService userDetailsService;

    public SecurityConfiguration(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //To configure
        http
                .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(request ->
                                new CorsConfiguration().applyPermitDefaultValues()
                        ))
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/users").permitAll().requestMatchers(HttpMethod.OPTIONS).permitAll().requestMatchers(HttpMethod.POST, "/users").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session
                        .maximumSessions(3)
                        .sessionRegistry(sessionRegistry())
                        .maxSessionsPreventsLogin(true))
                .userDetailsService(userDetailsService)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"You are logged out!\"}");
                        }))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        JSONLoginConfigurer<HttpSecurity> configurer = new JSONLoginConfigurer<>();
        configurer.setBuilder(http);
        http.apply(configurer
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Logged in successfully\"}");
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json");
                    if (exception instanceof BadCredentialsException) {
                        // set status code to 400 (Bad Request) when the username or password is incorrect
                        // by default, the response status code is 200 (OK)

                        response.setStatus(HttpStatus.BAD_REQUEST.value());
                        response.getWriter().write("{\"message\": \"Incorrect username or password\"}");
                    } else {
                        // set status code to 500 (Internal Server Error) for other exceptions
                        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                        response.getWriter().write("{\"message\": \"Authentication failed\"}");
                    }
                })
                .permitAll());

        return http.build();
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        /*authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());*/
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider(passwordEncoder))
                .build();
    }

    /*@Bean
    public CustomPasswordMatcher customPasswordMatcher(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new CustomPasswordMatcher(userDetailsService, passwordEncoder);
    }*/



}
