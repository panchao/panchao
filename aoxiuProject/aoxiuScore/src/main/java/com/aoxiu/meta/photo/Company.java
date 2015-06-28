package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/4/26.
 */
public class Company {
    //公司编号
    private Integer companyId;
    //公司用户名
    private String userName;
    //密码
    private String passWord;
    //所在省
    private String province;
    //城市
    private String city;
    //地区
    private String area;
    //电话号码
    private String phoneNumber;
    //微信
    private String weChat;
    //QQ
    private String QQNumber;
    //email
    private String email;

    public Company(){}

    public Company(int comanyId,String userName,String passWord,String province,
                   String city,String area,String phoneNumber,String weChat,String QQNumber,String email){
        this.companyId = comanyId;
        this.userName = userName;
        this.passWord = passWord;
        this.province = province;
        this.city = city;
        this.area = area;
        this.phoneNumber = phoneNumber;
        this.weChat = weChat;
        this.QQNumber = QQNumber;
        this.email = email;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
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

    public void setEmail(String emial) {
        this.email = emial;
    }
}
