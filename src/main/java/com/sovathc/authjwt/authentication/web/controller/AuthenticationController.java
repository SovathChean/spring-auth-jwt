package com.sovathc.authjwt.authentication.web.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sovathc.authjwt.authentication.biz.dto.OAuthTokenFormatDTO;
import com.sovathc.authjwt.authentication.biz.service.AuthenticationService;
import com.sovathc.authjwt.common.controller.ResponseBuilderMessage;
import com.sovathc.authjwt.common.controller.ResponseMessage;
import com.sovathc.authjwt.common.helper.JwtGenerateUniqueKey;
import com.sovathc.authjwt.common.helper.JwtVerified;
import com.sovathc.authjwt.user.biz.entity.UserEntity;
import com.sovathc.authjwt.user.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "api/refreshToken", method = RequestMethod.GET)
    public ResponseMessage<OAuthTokenFormatDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserEntity auth = userService.findAuth();
        String tokenKey = JwtGenerateUniqueKey.getUniqueKey(auth.getUsername());
        DecodedJWT bearerToken = new JwtVerified().getBearerToken(request, response);
        OAuthTokenFormatDTO oAuthTokenFormatDTO = authenticationService.refreshToken(bearerToken.getToken(), tokenKey);

        return new ResponseBuilderMessage<OAuthTokenFormatDTO>()
                    .success()
                    .addMessage("Refresh token successfully")
                    .addData(oAuthTokenFormatDTO)
                    .build();
    }

    @RequestMapping(value = "api/logout", method = RequestMethod.GET)
    public ResponseMessage<?> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DecodedJWT bearerToken = new JwtVerified().getBearerToken(request, response);
        authenticationService.logout(bearerToken.getToken());

        return new ResponseBuilderMessage<>().success().addMessage("Logout successfully").build();
    }

}
