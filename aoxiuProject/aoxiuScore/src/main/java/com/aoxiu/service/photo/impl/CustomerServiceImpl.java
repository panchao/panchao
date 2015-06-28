package com.aoxiu.service.photo.impl;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.service.photo.CustomerService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/27.
 */
public class CustomerServiceImpl implements CustomerService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CustomerDao customerDao;

    @Override
    public Customer updateCustomer(String customerId,String phoneNumber, String QQNumber, String weChat, String email) {
        Customer customer = new Customer(null,phoneNumber,QQNumber,email,0,weChat);
        int count = 0;
        customer.setCustomerId(Integer.valueOf(customerId));
        try{
            count =  customerDao.updateCustomer(customer);
            if(count == 1){
                return customer;
            }else{
                return customer;
            }
        }catch (Exception e){
            logger.error("[updateCustomer] update customer error:phoneNumber =  " + phoneNumber);
            return null;
        }
    }

    @Override
    public Customer addCustomer(Customer customer) {
        int count = 0;
        try{
            count = customerDao.insertCustomer(customer);
            if(count == 1){
                return customer;
            }else{
                return null;
            }
        }catch (Exception e){
            logger.error("[addCustomer] add customer error" );
        }
        return null;
    }

    @Override
    public List<Customer> getPhotographersUsers(String photograherId,PaginationInfo paginationInfo,int pageNum,int recordPerPage) {
        Map<String,Object> params = new HashMap<>();
        params.put("photographerId",photograherId);
        params.put("pageNum",pageNum);
        params.put("recordPerPage",recordPerPage);
        return customerDao.getCustomerByPhotographerIdByCond(params, paginationInfo);
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        return customerDao.deleteCustomer(customerId) == 1?true:false;
    }

    @Override
    public List<Customer> getCustomersByNameAndPhotographerId(String customerName,Integer photographerId
            ,PaginationInfo paginationInfo,int pageNum,int recordPerPage) {
        Map<String,Object> params = new HashMap<>();
        params.put("customerName",customerName);
        params.put("photographerId",photographerId);
        params.put("pageNum",pageNum);
        params.put("recordPerPage",recordPerPage);
        return customerDao.getCustomerByNameAndPhotoIdByCond(params,paginationInfo);
    }

    @Override
    public List<Customer> getCustomersByPhotographerId(String photographerId,int pageNum,int recordPerPage,PaginationInfo paginationInfo) {
        Map<String,Object> params = new HashMap<>();
        params.put("photographerId",photographerId);
        params.put("pageNum",pageNum);
        params.put("recordPerPage",recordPerPage);
        return customerDao.getCustomerByPhotographerIdByCond(params,paginationInfo);
    }
}
