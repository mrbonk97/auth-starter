package com.mrbonk97.authstarter.dto.Auth;

import com.mrbonk97.authstarter.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccountResponse {
    private Long id;
    private String email;
    private String nickname;
    private String image;
    private String accessToken;
    private String refreshToken;

    public static LoginAccountResponse of(Account account) {
        LoginAccountResponse loginAccountResponse = new LoginAccountResponse();
        loginAccountResponse.setId(account.getId());
        loginAccountResponse.setEmail(account.getEmail());
        loginAccountResponse.setNickname(account.getName());
        loginAccountResponse.setImage(account.getImage());
        loginAccountResponse.setAccessToken(account.getAccessToken());
        loginAccountResponse.setRefreshToken(account.getRefreshToken());
        return loginAccountResponse;
    }
}
