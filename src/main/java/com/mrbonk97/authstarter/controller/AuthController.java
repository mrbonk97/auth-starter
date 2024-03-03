package com.mrbonk97.authstarter.controller;

import com.mrbonk97.authstarter.dto.Auth.*;
import com.mrbonk97.authstarter.model.Account;
import com.mrbonk97.authstarter.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        Account account = authService.createAccount(createAccountRequest.getEmail(), createAccountRequest.getPassword());
        return CreateAccountResponse.of(account);
    }

    @PostMapping("/login")
    public LoginAccountResponse loginAccount(@RequestBody LoginAccountRequest loginAccountRequest) {
        Account account = authService.loginAccount(loginAccountRequest.getEmail(), loginAccountRequest.getPassword());
        return LoginAccountResponse.of(account);
    }

    @GetMapping("/logout")
    public void logoutAccount(HttpServletRequest httpServletRequest, Authentication authentication) {
        authService.logoutAccount(
                httpServletRequest.getHeader("Authorization").split(" ")[1].trim(),
                authentication.getName());
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return RefreshTokenResponse.of(authService.refreshToken(refreshTokenRequest.getToken()));
    }

    @GetMapping("/test")
    public String test() {
        return "AUTH TEST";
    }

}
