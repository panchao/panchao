package com.aoxiu.service.photo;

import com.aoxiu.meta.photo.Photographers;

import java.util.List;

/**
 * Created by panchao on 15/4/26.
 */
public interface CompanyOperatorService {

    /**
     * 婚庆公司新增摄影师
     * @param userId
     * @param company
     * @return
     */
    public boolean addUserToCompany(String userId,String company);

    /**
     * 从婚庆公司删除摄影师
     * @param userId
     * @param companyId
     * @return
     */
    public boolean deleteUserFromCompany(String userId,String companyId);

    /**
     * 婚庆公司获得公司摄影师用户
     * @param companyId
     * @return
     */
    public List<Photographers> getPhotographers(String companyId);
}
