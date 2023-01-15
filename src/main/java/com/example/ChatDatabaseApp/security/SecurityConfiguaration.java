package com.example.ChatDatabaseApp.security;

import com.example.ChatDatabaseApp.jwt.JwtConfig;
import com.example.ChatDatabaseApp.jwt.JwtTokenVerifier;
import com.example.ChatDatabaseApp.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguaration  extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Autowired
    DataSource dataSource;
    @Autowired
    public SecurityConfiguaration(PasswordEncoder passwordEncoder,
                                     SecretKey secretKey,
                                     JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }
    //funkcja ustawiajaca jaka baze danych oraz funkcje haszujaca
    //uzyc do autoryzacji uzytkownika
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);

    }
    //glowna funcja odpowiedzialna za zabezpieczenie endpointow przed niezalogowanymi uzytkownikami
    //ustawia  ona rowniez klasy(filtry) przez ktore zapytanie musi przejsc aby zostac zautoryzowane
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/users/**", "messages/**").hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated();
    }
}