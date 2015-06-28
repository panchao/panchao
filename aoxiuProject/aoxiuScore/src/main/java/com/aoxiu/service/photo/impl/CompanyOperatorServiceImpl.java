package com.aoxiu.service.photo.impl;

import com.aoxiu.dao.photo.PhotographersDao;
import com.aoxiu.dao.photo.UserCompanyDao;
import com.aoxiu.meta.photo.Photographers;
import com.aoxiu.meta.photo.UserCompany;
import com.aoxiu.service.photo.CompanyOperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by panchao on 15/4/26.
 */
public class CompanyOperatorServiceImpl implements CompanyOperatorService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserCompanyDao userCompanyDao;
    @Resource
    private PhotographersDao photographersDao;
    @Override
    public boolean addUserToCompany(String userId, String companyId) {
        try{
            UserCompany userCompany = new UserCompany(Integer.parseInt(userId),Integer.parseInt(companyId));
            int count = userCompanyDao.insertUserCompany(userCompany);
            return count == 1?true:false;
        }catch (Exception e){
            logger.error("[addUserToCompany] insert user to company error:userId = " + userId
            + "companyId = " + companyId + " - > " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUserFromCompany(String userId, String companyId) {
        try{
            UserCompany userCompany = new UserCompany(Integer.parseInt(userId),Integer.parseInt(companyId));
            //TODO 得判断摄影师手下没有订单了
            int count = userCompanyDao.deleteUserCompany(userCompany);
            return count == 1?true:false;
        }catch (Exception e){
            logger.error("[deleteUserFromCompany] delete from company error: userId = " + userId + " companyId = " + companyId
            + " -> " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Photographers> getPhotographers(String companyId) {
        List<Photographers> photographerses = null;
        try{
        List<UserCompany> userCompanies = userCompanyDao.getUserCompanys(companyId);
        photographerses = photographersDao.getPhotographersBatch(userCompanies);
        }catch (Exception e){
            logger.error("[getPhotographers] get company photographers error companyId = " + companyId + " -> " + e.getMessage());
            return photographerses;
        }
        return photographerses;
    }
}
