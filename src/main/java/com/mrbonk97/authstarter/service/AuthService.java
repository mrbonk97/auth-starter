package com.mrbonk97.authstarter.service;

import com.mrbonk97.authstarter.model.Account;
import com.mrbonk97.authstarter.model.Provider;
import com.mrbonk97.authstarter.model.RefreshToken;
import com.mrbonk97.authstarter.repository.AccountRepository;
import com.mrbonk97.authstarter.repository.RefreshTokenRepository;
import com.mrbonk97.authstarter.util.InMemoryTokenBlackListService;
import com.mrbonk97.authstarter.util.JwtTokenUtils;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account createAccount(String email, String password) {
        accountRepository.findByEmail(email).ifPresent(e -> {throw new RuntimeException("유저 중복");});
        //TODO : 에러 처리
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setProvider(Provider.local);
        return account;
    }

    @Transactional
    public Account loginAccount (String email, String password) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저 중복"));
        if(!passwordEncoder.matches(password, account.getPassword())) throw new RuntimeException("패스워드 잘못됨");

        String jwtRefresh = JwtTokenUtils.generateToken(email, "REFRESH");
        String jwtAccess = JwtTokenUtils.generateToken(email, "ACCESS");

        account.setRefreshToken(jwtRefresh);
        account.setAccessToken(jwtAccess);

        RefreshToken refreshToken = refreshTokenRepository.findByAccount(account).orElse(new RefreshToken());
        if(refreshToken.getAccount() == null) refreshToken.setAccount(account);
        refreshToken.setToken(jwtRefresh);
        refreshTokenRepository.save(refreshToken);

        return account;
    }

    @Transactional
    public void logoutAccount(String accessToken, String email) {
        InMemoryTokenBlackListService.addToBlockList(accessToken);
        refreshTokenRepository.deleteByAccount_Email(email);
    }

    public Account loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("히히"));
    }

    public String[] refreshToken(String token) {
        //TODO : 예외처리
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("ASD"));
        System.out.println(refreshToken.getToken());

        if(JwtTokenUtils.validateToken(refreshToken.getToken())){
            String jwtRefresh = JwtTokenUtils.generateToken(refreshToken.getAccount().getEmail(), "REFRESH");
            String jwtAccess = JwtTokenUtils.generateToken(refreshToken.getAccount().getEmail(), "ACCESS");
            refreshToken.setToken(jwtRefresh);
            refreshTokenRepository.save(refreshToken);
            return new String [] {jwtRefresh, jwtAccess};
        }
        else {
            throw new RuntimeException("토큰 만료됨 ㅋㅋ");
        }

    }

}
