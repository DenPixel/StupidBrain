package com.pixel.stupidbrain.config;

import com.pixel.stupidbrain.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JPAUserDetailsService userDetailsService;

    private final CsrfTokenRepository csrfTokenRepository;

    @Autowired
    public SecurityConfig(JPAUserDetailsService userDetailsService,
                          CsrfTokenRepository csrfTokenRepository) {
        this.userDetailsService = userDetailsService;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers("/", "/home", "/registration").permitAll()
                    .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).hasRole("ADMIN")
                    .antMatchers("/questions/**", "/api/**").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()

                .httpBasic().and()

                .userDetailsService(userDetailsService)
                .formLogin()
                    .loginPage("/login").permitAll()
                    .and()

                .logout().permitAll().and()

                .csrf().csrfTokenRepository(csrfTokenRepository).and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .authorizeRequests().antMatchers("/**").permitAll()
//                .anyRequest().authenticated();
    }


}
