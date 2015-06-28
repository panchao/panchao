package com.aoxiu.service.photo;

/**
 * Created by panchao on 15/4/26.
 */
public interface RegisterUserService {
    /**
     * 公司用户注册
     * @param userName
     * @param passWord
     * @param province
     * @param city
     * @param area
     * @param phoneNumber
     * @param weChat
     * @param QQNumber
     * @param email
     * @return
     */
    public boolean registerCompanyUser(String userName,String passWord,String province,
                                       String city,String area,String phoneNumber,String weChat,String QQNumber,String email);
}
