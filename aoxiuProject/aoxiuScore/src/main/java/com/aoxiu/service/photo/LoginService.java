package com.aoxiu.service.photo;

/**
 * Created by panchao on 15/4/25.
 */
public interface LoginService {
    /**
     * 检验用户名密码是否正确
     * @param userName
     * @param passWord
     * @param type
     * @return
     */
    public Object examinUser(String userName, String passWord,String type);

    /**
     * 判断用户是否存在
     * @param userName
     * @return
     */
    public boolean examinUserExisit(String userName,String type);

    public boolean examinUserPhoneNumber(String phoneNumber);
}
