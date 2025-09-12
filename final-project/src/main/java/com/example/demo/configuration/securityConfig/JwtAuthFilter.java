package com.example.demo.configuration.securityConfig;
import com.example.demo.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authCooki = request.getHeader(HttpHeaders.COOKIE);
        String jwtToken;
        String userEmail;
        String authrizaion = request.getHeader("Authorization") ;
        System.out.println("method: "+request.getMethod());
        System.out.println(request.getRequestURI()+" "+authrizaion);
        // Bypass authentication for the login endpoint
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())){
            filterChain.doFilter(request,response);
            return;
        }
        if ((request.getRequestURI().startsWith("/auth") || request.getRequestURI().startsWith("/user/save")) && request.getMethod().equals("POST")) {
            System.out.println("do auth");
            filterChain.doFilter(request, response);
            return;
        }



        if (authrizaion == null && authCooki == null) {
            response.setStatus(401);
            filterChain .doFilter(request, response);
            return;}
        else if (authrizaion!=null)
            jwtToken = authrizaion;
        else
            jwtToken = authCooki.split("=")[1];

        // Extract the username (subject) from the token
        userEmail = jwtService.extractUsername(jwtToken);

        // If the username is valid and the user is not yet authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Validate the token
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}