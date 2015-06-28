package com.aoxiu.aoxiuApp.utils;

/**
 * Created by panchao on 15/4/29.
 */
public class StringMatchUtils {

    public static boolean  match(String str1,String str2){
        if(str1.indexOf(str2) != -1){
            return true;
        }else{
            return false;
        }
    }
}
