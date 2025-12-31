package com.Learnings.practical.Security;

import com.Learnings.practical.Entity.User;
import com.Learnings.practical.Repositry.UserRepositry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepositry userRepositry;

    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
log.info(" incoming request: {}", request.getRequestURI());
final String authorizationHeader = request.getHeader("Authorization");
if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    filterChain.doFilter(request,response);
    return;
}
String token = authorizationHeader.split("Bearer")[1];
// it means thT we will split the token and get the token from it which have array 1
String username = AuthUtil.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepositry.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
filterChain.doFilter(request,response);

    }
}
