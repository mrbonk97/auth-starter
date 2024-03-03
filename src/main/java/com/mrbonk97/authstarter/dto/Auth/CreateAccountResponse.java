package com.mrbonk97.authstarter.dto.Auth;

import com.mrbonk97.authstarter.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountResponse {
    private Long id;
    private String email;
    private String nickname;

    public static CreateAccountResponse of(Account account) {
        CreateAccountResponse loginAccountResponse = new CreateAccountResponse();
        loginAccountResponse.setId(account.getId());
        loginAccountResponse.setEmail(account.getEmail());
        loginAccountResponse.setNickname(account.getName());
        return loginAccountResponse;
    }
}
