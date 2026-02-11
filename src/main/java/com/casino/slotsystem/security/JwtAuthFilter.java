package com.casino.slotsystem.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();
        String method = request.getMethod();

        return method.equals("OPTIONS")
                || path.startsWith("/ws")
                || path.startsWith("/topic")
                || path.startsWith("/api/test")
                || path.equals("/api/admin/login")
                || (method.equals("GET") && path.startsWith("/api/slots"));
    }




   @Override
protected void doFilterInternal(HttpServletRequest req,
                                HttpServletResponse res,
                                FilterChain chain)
        throws IOException, ServletException {

    String header = req.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {

        try {
            String token = header.substring(7);
            String username = jwtUtil.extractUsername(token);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (JwtException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }

    // Always continue filter chain
    chain.doFilter(req, res);
}

}


