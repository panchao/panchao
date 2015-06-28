package com.aoxiu.service.photo.impl;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.dao.photo.CompanyDao;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.dao.photo.PhotographersDao;
import com.aoxiu.meta.photo.Company;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.service.photo.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by panchao on 15/4/25.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PhotographersDao photographersDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private CustomerDao customerDao;
    @Override
    public Object examinUser(String userName, String passWord,String type) {
        if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)){
            Photographers photographers = photographersDao.getPhotographersByUserName(userName);
            if(photographers != null && photographers.getPassWord().equals(passWord)){
                return photographers;
            }
        }else  if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)){
            Company company = companyDao.getCompanyUserByName(userName);
            if(company != null && company.getPassWord().equals(passWord)){
                return company;
            }
        }

        return null;
    }

    @Override
    public boolean examinUserExisit(String userName,String type) {
        if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)){
            Company company = companyDao.getCompanyUserByName(userName);
            if(company == null){
                return true;
            }
        }else if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)){
            Photographers photographers = photographersDao.getPhotographersByUserName(userName);
            if(photographers == null){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean examinUserPhoneNumber(String phoneNumber) {
        Customer customer = customerDao.getCustomerByPhoneNumber(phoneNumber);
        if(customer != null){
            return true;
        }else{
            return false;
        }
    }
}
