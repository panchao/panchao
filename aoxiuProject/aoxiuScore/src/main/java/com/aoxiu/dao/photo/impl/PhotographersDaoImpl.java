package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.PhotographersDao;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.meta.photo.UserCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panchao on 15/4/25.
 */

public class PhotographersDaoImpl extends BaseDao implements PhotographersDao{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Photographers getPhotographersByUserId(String userId) {
        return getSqlSession().selectOne("photographers.getPhotographersByUserId",userId);
    }

    @Override
    public Photographers getPhotographersByUserName(String userName) {
        return getSqlSession().selectOne("photographers.getPhotographersByUserName",userName);
    }

    @Override
    public Photographers getPhotographersByPhoneNumber(String phoneNumber) {
        return getSqlSession().selectOne("photographers.getPhotographersByPhoneNumber",phoneNumber);
    }

    @Override
    public List<Photographers> getPhotographersBatch(List<UserCompany> userCompanies) {
        return getSqlSession().selectList("photographers.getPhotographersBatch",userCompanies);
    }
}
