package com.openclassroom.mdd.mddauth.filters;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.mappers.UserMapper;
import com.openclassroom.mdd.mddauth.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return (
            path.equals("/auth/signin") ||
            path.equals("/auth/signup") ||
            path.equals("/auth/refresh")
        );
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("New doFilterInternal request:");
        System.out.println(req.getHeader("referer"));

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Missing bearer!");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        try {
            UserDto userDto = tokenService.decodeAccessToken(token);
            User user = UserMapper.INSTANCE.fromDto(userDto);
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                    /*principal=*/ user,
                    /*credentials=*/ null,
                    /*authorities=*/ user.getAuthorities()
                );
            auth.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(req)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid JWT");
            return;
        }
        filterChain.doFilter(req, res);
    }
}
