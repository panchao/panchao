package com.aoxiu;

/**
 * Created by panchao on 15/4/26.
 */
public class ComRet {
    public static String retCode = "retCode";
    public static String retDesc = "retDesc";
    public static String retData = "data";
    public static String retPage = "totalPages";
    public static String retCount = "totalCount";


    //成功
    public static String SUCESS = "200";
    public static String SUCESS_DESC = "SUCESS";

    //未知异常
    public static String FAIL = "500";
    public static String FAIL_DESC = "网络错误，请稍后重试";

    //请求参数错误
    public static String WRONG_PARAMETER = "401";
    public static String WRONG_PARAMETER_DESC = "请求参数错误，请重试";

    //用户名已经存在
    public static String USER_EXISIT = "402";
    public  static String USER_EXISIT_DESC = "用户名已经存在，请换一个用户名";

    //密码长度错误
    public static String WRONG_PASSWORD = "403";
    public static String WRONG_PASSWORD_DESC = "密长度错误";

    //电话号码不对
    public static String WRONG_PHONE = "404";
    public static String WRONG_PHONE_DESC = "电话号码格式不对";

    //用户名或密码错误
    public static String PASSWORD_ERROR = "405";
    public static String PASSWORD_ERROR_DESC ="用户名或密码错误";

    //没有登陆
    public static String NO_LOGIN = "406";
    public static String NO_LOGIN_DESC = "您没有登陆，请您先登陆";

    //电话号码验证失败信息
    public static String PHONE_NUMBER = "407";
    public static String PHONE_NUMBER_DESC = "电话号码验证失败";

    //订单已经生成页面入口，不能删除
    public static String HAD_GETCODE = "408";
    public static String HAD_GETCODE_DESC = "订单已经生成页面入口，不能删除";



}
