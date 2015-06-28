package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/4/26.
 */
public class UserCompany {
    int id;
    int userId;
    int companyId;

    public UserCompany(){}
    public UserCompany(int userId,int companyId){
        this.userId = userId;
        this.companyId = companyId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
