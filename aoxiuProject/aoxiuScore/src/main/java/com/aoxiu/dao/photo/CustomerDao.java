package com.aoxiu.dao.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/27.
 */
public interface CustomerDao {
    /**
     * 更新客户资料
     * @param customer
     * @return
     */
    public int updateCustomer(Customer customer);

    /**
     * 增加客户
     * @param customer
     * @return
     */
    public int insertCustomer(Customer customer);

    /**
     * 获取客户
     * @param photographersId
     * @return
     */
    public List<Customer> getCustomers(String photographersId);

    /**
     * 分页查询客户
     * @param photographersId
     * @param pageNum
     * @param pageRecord
     * @return
     */
    public List<Customer> getCustomersByCond(String photographersId,PaginationInfo paginationInfo,int pageNum,int pageRecord);

    /**
     *
     * @param phoneNumber
     * @return
     */
    public Customer getCustomerByPhoneNumber(String phoneNumber);

    /**
     *
     * @param customerId
     * @return
     */
    public int deleteCustomer(String customerId);

    /**
     *
     * @param userId
     * @return
     */
    public Customer getCustomerByUserId(int userId);

    /**
     * 获得摄影师客户
     * @param params
     * @return
     */
    public List<Customer> getCustomerByNameAndPhotoIdByCond(Map<String,Object> params,PaginationInfo paginationInfo);

    /**
     *
     * @param params
     * @param paginationInfo
     * @return
     */
    public List<Customer> getCustomerByPhotographerIdByCond(Map<String,Object> params,PaginationInfo paginationInfo);

}
