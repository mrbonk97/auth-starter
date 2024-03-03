package com.mrbonk97.authstarter.dto.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;

    public static RefreshTokenResponse of(String [] tokens) {
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setRefreshToken(tokens[0]);
        refreshTokenResponse.setAccessToken(tokens[1]);
        return refreshTokenResponse;
    }
}
