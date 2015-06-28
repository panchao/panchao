package com.aoxiu.aoxiuApp.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by panchao on 15/4/29.
 */
public class CookieUtils {
    private  static final String AOXIU_COOKIE_ID = "JSESSIONID";
    private static final  String AOXIU_PHONE_ID = "phoneExamine";

    public static String getAoxiuCookie(HttpServletRequest httpServletRequest){
        Cookie [] cookies = httpServletRequest.getCookies();
        for(int i = 0; cookies != null && i < cookies.length; i ++){
            if(cookies[i].getName().equals(AOXIU_COOKIE_ID)){
                return cookies[i].getValue();
            }
        }

        return null;
    }
    public static String getAoxiuCookiePhone(HttpServletRequest httpServletRequest){
        Cookie [] cookies = httpServletRequest.getCookies();
        for(int i = 0; cookies != null && i < cookies.length; i ++){
            if(cookies[i].getName().equals(AOXIU_PHONE_ID)){
                return cookies[i].getValue();
            }
        }

        return null;
    }
}
