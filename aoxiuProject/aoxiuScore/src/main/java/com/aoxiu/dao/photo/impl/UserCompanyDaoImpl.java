package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.UserCompanyDao;
import com.aoxiu.meta.photo.UserCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panchao on 15/4/26.
 */
@Repository
public class UserCompanyDaoImpl extends BaseDao implements UserCompanyDao {
    @Override
    public int insertUserCompany(UserCompany userCompany) {
        return getSqlSession().insert("userCompany.insertUserCompany",userCompany);
    }

    @Override
    public int deleteUserCompany(UserCompany userCompany) {
        return getSqlSession().delete("userCompany.deleteUserCompany",userCompany);
    }

    @Override
    public List<UserCompany> getUserCompanys(String companyId) {
        return getSqlSession().selectList("userCompany.getUserCompanys",companyId);
    }
}
