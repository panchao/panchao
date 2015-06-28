package com.aoxiu.service.photo;

/**
 * Created by panchao on 15/4/26.
 */
public interface QueryUserInfoService {
    public Object queryUserByUserName(String userName,String type);
    public Object queryUserByUserId(String userId,String type);
    public Object queryUserByPhoneNumber(String phoneNumber,String type);
}
