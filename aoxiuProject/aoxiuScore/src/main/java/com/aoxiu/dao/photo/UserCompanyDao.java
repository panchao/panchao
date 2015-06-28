package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.UserCompany;

import java.util.List;

/**
 * Created by panchao on 15/4/26.
 */
public interface UserCompanyDao {
    /**
     *  插入
     * @param userCompany
     * @return
     */
    public int insertUserCompany(UserCompany userCompany);

    /**
     * 删除
     * @param userCompany
     * @return
     */
    public int deleteUserCompany(UserCompany userCompany);

    /**
     * 通过婚庆公司编号获得公司摄影师
     * @param companyId
     * @return
     */
    public List<UserCompany> getUserCompanys(String companyId);
}
