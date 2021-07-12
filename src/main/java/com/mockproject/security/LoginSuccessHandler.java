/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author ACER
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();
        if (userDetail.hasRole("ADMIN")) {
            redirectUrl += "/index";
        } else if (userDetail.hasRole("STUDENT")) {
            redirectUrl += "/index";
        } else if (userDetail.hasRole("TEACHER")) {
            redirectUrl += "/index";
        }
        response.sendRedirect(redirectUrl);
    }
}
