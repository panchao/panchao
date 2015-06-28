package com.aoxiu.service.photo.impl;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.service.photo.PhotographerOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by panchao on 15/4/29.
 */
public class PhotographerOrderServiceImpl implements PhotographerOrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PhotographerOrderDao photographerOrderDao;
    @Autowired
    private CustomerDao customerDao;
    @Override
    public List<PhotographerOrder> selectCustomers(String id, String type,int pageNum,
                                                   int recordPerPage,PaginationInfo paginationInfo) {
        return photographerOrderDao.getPhotographerOrdersByPhotographersIdByCond(id,pageNum,recordPerPage,paginationInfo);
    }

    @Override
    public boolean deleteOrder(String orderId, String photographersId,String type) {
        List<Customer> customers = customerDao.getCustomers(photographersId);
        boolean flag = false;
        if(customers == null || customers.size() < 1){
            flag = photographerOrderDao.deletePhotographerOrder(orderId) == 1?true:false;
        }
        return flag;
    }

    @Override
    public boolean updateOrder(PhotographerOrder photographerOrder,String type) {
        if(type.equals(AoxiuConstant.AOXIU_USER_PHOTOGRAPHESR)){
            if(photographerOrderDao.updatePhotographerOrder(photographerOrder) == 1){
                return true;
            }
        }else{
            //TODO  婚庆公司
        }

        return false;
    }

    @Override
    public boolean addOrder(PhotographerOrder photographerOrder,String type) {
        if(photographerOrderDao.insertPhotographerOrder(photographerOrder) == 1){
            return true;
        }
        return false;
    }
}
