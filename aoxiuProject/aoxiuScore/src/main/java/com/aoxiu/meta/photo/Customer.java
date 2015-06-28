package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/4/27.
 */
public class Customer {
    private int customerId;
    private String realName;
    private String phoneNumber;
    private String QQNumber;
    private String email;
    private int photographersId;
    private String weChat;

    public Customer(){}

    public Customer( String realName, String phoneNumber, String QQNumber, String email, int photographersId, String weChat) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.QQNumber = QQNumber;
        this.email = email;
        this.photographersId = photographersId;
        this.weChat = weChat;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQQNumber() {
        return QQNumber;
    }

    public void setQQNumber(String QQNumber) {
        this.QQNumber = QQNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhotographersId() {
        return photographersId;
    }

    public void setPhotographersId(int photographersId) {
        this.photographersId = photographersId;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }
}
