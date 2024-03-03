package com.mrbonk97.authstarter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_USER", "일반 계정"),
    USER("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
