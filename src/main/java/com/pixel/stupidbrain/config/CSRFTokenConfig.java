package com.pixel.stupidbrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class CSRFTokenConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        return new HttpSessionCsrfTokenRepository();
    }

}
