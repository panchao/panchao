package com.aoxiu.service.photo.impl;

import com.aoxiu.dao.photo.CompanyDao;
import com.aoxiu.meta.photo.Company;
import com.aoxiu.service.photo.RegisterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by panchao on 15/4/26.
 */
public class RegisterUserServiceImpl implements RegisterUserService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CompanyDao companyDao;
    @Override
    public boolean registerCompanyUser(String userName, String passWord, String province, String city, String area, String phoneNumber, String weChat,String QQNumber,String email) {
//        int id = 0;
        Company company = new Company( 0,userName,passWord,province, city,area,phoneNumber, weChat,QQNumber,email);
        if(companyDao.insertCompany(company) == 1){
            return true;
        }
        return false;
    }
}
