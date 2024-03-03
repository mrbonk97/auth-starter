package com.mrbonk97.authstarter.configuration;

import com.mrbonk97.authstarter.util.InMemoryTokenBlackListService;
import com.mrbonk97.authstarter.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("깔깔 낄낄 깔");

        if (header == null) {
            log.info("Authorization 헤더가 없음");
            filterChain.doFilter(request, response);
            return;
        }

        if (!header.startsWith("Bearer")) {
            log.info("헤더가 없음 Bearer 타입이 아님");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1].trim();

        if(!JwtTokenUtils.validateToken(token)) {
            log.info("Jwt Token validate 실패");
            filterChain.doFilter(request, response);
            return;
        }

        if(InMemoryTokenBlackListService.isBlacklisted(token)) {
            log.info("Blacklist에 추가되어있는 Token");
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtTokenUtils.getEmail(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
