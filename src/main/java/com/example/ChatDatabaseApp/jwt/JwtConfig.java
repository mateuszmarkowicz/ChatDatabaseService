package com.example.ChatDatabaseApp.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
@Data
@ConfigurationProperties(prefix = "application.jwt")
@Controller
public class JwtConfig {
    //klasa przechowujaca dane czesto wykozystywane w klasach Jwt
    //przechowuje ona dane w pliku application.properties
    //mozna je rozpostac po przedrostku application.jwt
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    public JwtConfig() {
    }
    //funkcja zwracajaca token dostarczony w naglowku zapytania
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
