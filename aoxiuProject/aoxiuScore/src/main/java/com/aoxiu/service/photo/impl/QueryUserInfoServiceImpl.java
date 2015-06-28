package com.aoxiu.service.photo.impl;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.dao.photo.CompanyDao;
import com.aoxiu.dao.photo.PhotographersDao;
import com.aoxiu.service.photo.QueryUserInfoService;
import org.apache.ibatis.annotations.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by panchao on 15/4/26.
 */
public class QueryUserInfoServiceImpl implements QueryUserInfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private PhotographersDao photographersDao;
    @Resource
    private CompanyDao companyDao;

    @Override
    public Object queryUserByUserName(String userName, String type) {
        Object resultObject = null;
        try {
            if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) { //查找摄影师
                resultObject = photographersDao.getPhotographersByUserName(userName);
            } else if (type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)) { //查找婚庆公司用户
                resultObject = companyDao.getCompanyUserByName(userName);
            }
        } catch (Exception e) {
            logger.error("[queryUserByUserName] query user info error: userName = " + userName + " type = " +
                    type + "->" + e.getMessage());
            return resultObject;
        }
        return resultObject;
    }

    @Override
    public Object queryUserByUserId(String userId, String type) {
        Object resultObject = null;
        try{
            if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) { //摄影师
                resultObject = photographersDao.getPhotographersByUserId(userId);
            }else if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)){ //婚庆公司
//                resultObject = companyDao.get
            }
        }catch (Exception e){
            logger.error("[queryUserByUserId] query user info error: userName = " + userId + " type = " +
                    type + "->" + e.getMessage());
            return resultObject;
        }
        return resultObject;
    }

    @Override
    public Object queryUserByPhoneNumber(String phoneNumber, String type) {
        Object resultObject = null;
        try{
            if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)) { //摄影师
                resultObject = photographersDao.getPhotographersByPhoneNumber(phoneNumber);
            }else if(type.toUpperCase().equals(AoxiuConstant.AOXIU_USER_COMPNAY)){ //婚庆公司
//                resultObject = companyDao.get
            }
        }catch (Exception e){
            logger.error("[queryUserByPhoneNumber] query user info error: userName = " + phoneNumber + " type = " +
                    type + "->" + e.getMessage());
            return resultObject;
        }
        return resultObject;    }
}
