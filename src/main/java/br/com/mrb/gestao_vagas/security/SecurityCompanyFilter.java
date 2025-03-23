package br.com.mrb.gestao_vagas.security;

import br.com.mrb.gestao_vagas.providers.JWTComapnyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTComapnyProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if(request.getRequestURI().startsWith("/company")
        || request.getRequestURI().startsWith("/job")) {
            if(authHeader != null) {
                var token = this.jwtProvider.validarToken(authHeader);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).toList();

                request.setAttribute("company_id",token.getSubject());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
