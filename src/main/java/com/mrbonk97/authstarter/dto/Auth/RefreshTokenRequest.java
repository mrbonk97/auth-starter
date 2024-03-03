package com.mrbonk97.authstarter.dto.Auth;

import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class RefreshTokenRequest {
    private String token;

    @ConstructorProperties({"token"})
    public RefreshTokenRequest(String token) {
        this.token = token;
    }
}
