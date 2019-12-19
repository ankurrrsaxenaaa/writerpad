package com.xebia.fs101.writerpad.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int expiresIn;

    @Value("${jwt.cookie}")
    private String tokenCookie;

    @Autowired
    private JwtTokenService tokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        String jws = this.tokenHelper.generateToken(user.getUsername());

        Cookie authCookie = new Cookie(this.tokenCookie, (jws));

        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(this.expiresIn);
        authCookie.setPath("/");
        response.addCookie(authCookie);
        response.sendRedirect("/");
    }
}
