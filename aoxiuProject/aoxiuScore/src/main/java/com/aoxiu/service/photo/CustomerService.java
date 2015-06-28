package com.aoxiu.service.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Customer;

import java.util.List;

/**
 * Created by panchao on 15/4/27.
 */
public interface CustomerService {
    /**
     * 更新客户信息接口，所有参数可选
     * @param phoneNumber
     * @param QQNumber
     * @param weChat
     * @param email
     * @return
     */
    public Customer updateCustomer(String customerId,String phoneNumber,String QQNumber,String weChat,String email);

    /**
     * 添加客户，参数为一个JSON串
     * @param customer
     * @return
     */
    public Customer addCustomer(Customer customer);

    /**
     *
     * @param photograherId
     * @return
     */
    public List<Customer> getPhotographersUsers(String photograherId,PaginationInfo paginationInfo,int pageNum,int recordPerPage);

    /**
     *
     * @param customerId
     * @return
     */
    public boolean deleteCustomer(String customerId);

    /**
     * 通过客户名搜索客户
     * @param customerName
     * @param photographerId
     * @return
     */
    public List<Customer> getCustomersByNameAndPhotographerId(String customerName,Integer photographerId,
                                                        PaginationInfo paginationInfo,int pageNum,int recordPerPage);

    /**
     *
     * @param photographerId
     * @return
     */
    public List<Customer> getCustomersByPhotographerId(String photographerId,int pageNum,int recordPerPage,PaginationInfo paginationInfo);
}
