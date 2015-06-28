package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.Company;

/**
 * Created by panchao on 15/4/26.
 */
public interface CompanyDao {
    /**
     * 通过用户名获得公司用户
     * @param userName
     * @return
     */
    public Company getCompanyUserByName(String userName);

    /**
     * 插入新的记录
     * @param company
     * @return
     */
    public int insertCompany(Company company);
}
