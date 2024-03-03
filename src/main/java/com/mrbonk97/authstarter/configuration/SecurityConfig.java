package com.mrbonk97.authstarter.configuration;

import com.mrbonk97.authstarter.oauth2.OAuth2FailureHandler;
import com.mrbonk97.authstarter.oauth2.OAuth2SuccessHandler;
import com.mrbonk97.authstarter.repository.CookieAuthorizationRequestRepository;
import com.mrbonk97.authstarter.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/*").permitAll()
//                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(p -> p.userService(customOAuth2UserService)));

//        httpSecurity.oauth2Login(oauth2 -> oauth2
//
//                .redirectionEndpoint(point -> point
//                        .baseUri("/oauth2/callback/*"))
//                .userInfoEndpoint(point -> point
//                        .userService(new CustomOAuth2UserService()))
//                .authorizationEndpoint(point -> point
//                        .baseUri("/oauth2/authorization/*")
//                        .authorizationRequestRepository(new CookieAuthorizationRequestRepository()))
//                .successHandler(new OAuth2SuccessHandler())
//                .failureHandler(new OAuth2FailureHandler())
//        );

        return httpSecurity.build();
    }


}
