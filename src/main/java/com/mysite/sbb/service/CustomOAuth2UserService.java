package com.mysite.sbb.service;

import com.mysite.sbb.dto.CustomOAuth2User;
import com.mysite.sbb.dto.GoogleReponse;
import com.mysite.sbb.dto.NaverResponse;
import com.mysite.sbb.dto.OAuth2Response;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserRole;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAuth2UserService OAuth2UserService의 구현체

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Optional<SiteUser> _siteUser = userRepository.findByUsername(username);

        String role = null;
        if (_siteUser.isEmpty()) {
            //중복된 이메일 검사필요
            //다른 SNS로 가인했거나 등등
            String email = oAuth2Response.getEmail();
            Optional<SiteUser> _userByEmail = userRepository.findByEmail(email);
            if(_userByEmail.isPresent()) {
                throw new DuplicateKeyException("email already exist in user table!!");
            }
            SiteUser userEntity = new SiteUser();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole(UserRole.USER.getValue());

            userRepository.save(userEntity);
        }
        else {
            SiteUser userEntity = _siteUser.get();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());

            role = _siteUser.get().getRole();

            userRepository.save(_siteUser.get());
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}