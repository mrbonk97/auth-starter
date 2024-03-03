package com.mrbonk97.authstarter.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginAccountRequest {
    private String email;
    private String password;
}
