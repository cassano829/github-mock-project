/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ACER
 */
public class Utility {

    public static String getSiteURL(HttpServletRequest req) {
        String siteURL = req.getRequestURL().toString();
        return siteURL.replace(req.getServletPath(), "");
    }
}
