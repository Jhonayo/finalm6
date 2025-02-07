package org.edutecno.backend.config;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.edutecno.backend.auth.model.Token;
import org.edutecno.backend.auth.repository.TokenRepository;
import org.edutecno.backend.auth.service.JwtService;
import org.edutecno.backend.usuario.model.User;
import org.edutecno.backend.usuario.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
//para debug:
        String requestPath = request.getServletPath();
        logger.info("Interceptando solicitud: {}", requestPath);
        //
        if (request.getServletPath().contains("/auth")) {
            logger.info("Solicitud a /auth, permitiendo sin autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("Solicitud a /auth, permitiendo sin autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        logger.info("Token recibido: {}", jwtToken);
        final String userEmail = jwtService.extractUsername(jwtToken);
        logger.info("Usuario extraído del token: {}", userEmail);

        System.out.println("Usuario autenticado: " + userEmail);
        if (userEmail == null) {
            logger.warn("No se pudo extraer el usuario del token");
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            logger.info("El usuario ya está autenticado en SecurityContextHolder");
            filterChain.doFilter(request, response);
            return;
        }

        final Token token = tokenRepository.findByToken(jwtToken).orElse(null);
        if (token == null) {
            logger.warn("Token no encontrado en la base de datos");
            filterChain.doFilter(request, response);
            return;
        }

        if (token.getExpired() || token.getRevoked()) {
            logger.warn("El token está expirado o revocado");
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        final Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            logger.warn("El usuario no fue encontrado en la base de datos");
            filterChain.doFilter(request, response);
            return;
        }

        final boolean isTokenValid = jwtService.isTokenValid(jwtToken, user.get());

        if (!jwtService.isTokenValid(jwtToken, user.get())) {
            logger.warn("El token no es válido");
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("Token válido, autenticando al usuario en SecurityContextHolder");

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //esto no se
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
