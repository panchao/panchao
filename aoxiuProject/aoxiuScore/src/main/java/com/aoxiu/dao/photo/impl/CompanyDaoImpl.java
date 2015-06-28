package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.CompanyDao;
import com.aoxiu.meta.photo.Company;
import org.springframework.stereotype.Repository;

/**
 * Created by panchao on 15/4/26.
 */
@Repository
public class CompanyDaoImpl extends BaseDao implements CompanyDao {
    @Override
    public Company getCompanyUserByName(String userName) {
        return getSqlSession().selectOne("company.getCompanyUserByName",userName);
    }

    @Override
    public int insertCompany(Company company) {
        return getSqlSession().insert("company.insertCompany",company);
    }
}
