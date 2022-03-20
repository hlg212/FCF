package com.hlg.fcf.web.util;

import javax.servlet.http.Cookie;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2018-11-30 10:04
 **/
public class SsoTokenHelper {

    private final static String COOKIENAME = "Authorization";
    public static void writeToken (String value){
        Cookie cookie = new Cookie(COOKIENAME,value);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        HttpServletHelper.getResponse().addCookie(cookie);
    }

    public static void clearToken (){
        Cookie cookie = new Cookie(COOKIENAME,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        HttpServletHelper.getResponse().addCookie(cookie);
    }
}
