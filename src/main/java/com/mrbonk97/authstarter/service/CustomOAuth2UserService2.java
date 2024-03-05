//package com.mrbonk97.authstarter.service;
//
//import com.mrbonk97.authstarter.model.Account;
//import com.mrbonk97.authstarter.oauth2.OAuthAttributes;
//import com.mrbonk97.authstarter.oauth2.SessionUser;
//import com.mrbonk97.authstarter.repository.AccountRepository;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@RequiredArgsConstructor
//@Service
//public class CustomOAuth2UserService2 implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final AccountRepository accountRepository;
//    private final HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //google
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        OAuth2Attribute oAuth2Attribute =
//                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//
//        System.out.println(registrationId);
//        System.out.println(userNameAttributeName);
//        System.out.println("==========================");
//
//
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//        Account account = saveOrUpdate(attributes);
//
//        httpSession.setAttribute("user", new SessionUser(account));
//
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(account.getRole().getKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
//    }
//
//    private Account saveOrUpdate(OAuthAttributes attributes) {
//        Account account = accountRepository
//                .findByEmail(attributes.getEmail())
//                .orElse(new Account());
//
//        account.setName(attributes.getName());
//        account.setImage(attributes.getPicture());
//        return accountRepository.save(account);
//    }
//}