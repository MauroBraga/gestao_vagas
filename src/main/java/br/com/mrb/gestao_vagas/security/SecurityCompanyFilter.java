package br.com.mrb.gestao_vagas.security;

import br.com.mrb.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if(request.getRequestURI().startsWith("/company")) {
         //   SecurityContextHolder.getContext().setAuthentication(null);
            if(authHeader != null) {
                var token = this.jwtProvider.validarToken(authHeader);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("company_id",token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
