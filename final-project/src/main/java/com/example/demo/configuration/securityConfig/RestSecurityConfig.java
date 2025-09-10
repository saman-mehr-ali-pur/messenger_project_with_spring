package com.example.demo.configuration.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebSecurity
public class RestSecurityConfig {



//    @Autowired
//    private UserRepo userRepo;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll().
                        requestMatchers(HttpMethod.OPTIONS,"/auth/**").permitAll().
                        requestMatchers(HttpMethod.GET,"/user/all/**").hasRole("ADMIN").
                        requestMatchers(HttpMethod.POST,"/user/save").permitAll().
                        requestMatchers(HttpMethod.OPTIONS,"user/save").permitAll().
                        requestMatchers(HttpMethod.POST,"/user/get").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/user/get").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/room/all_rooms").permitAll()
                        .requestMatchers(HttpMethod.POST,"/room/all_rooms").permitAll().
                        requestMatchers(HttpMethod.GET,"/user/check_username/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/room/find/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/room/find/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/user/search").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/user/search").permitAll()


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getEncoder());
        return daoAuthenticationProvider;

    }



    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }



}
