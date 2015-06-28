package com.aoxiu.dao.photo.impl;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.meta.photo.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/27.
 */
public class CustomerDaoImpl  extends BaseDao implements CustomerDao {
    @Override
    public int updateCustomer(Customer customer) {
        return getSqlSession().update("customer.updateCustomer",customer);
    }

    @Override
    public int insertCustomer(Customer customer) {
        return getSqlSession().insert("customer.insertCustomer",customer);
    }

    @Override
    public List<Customer> getCustomers(String photographersId) {
        return getSqlSession().selectList("customer.getCustomers",photographersId);
    }

    @Override
    public List<Customer> getCustomersByCond(String photographersId,PaginationInfo paginationInfo, int pageNum, int pageRecord) {
        Map<String,Object> params = new HashMap<>();
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage(pageNum);
        paginationInfo.setRecordPerPage(pageRecord);
        params.put("photographersId",photographersId);
        params.put("pageNum",pageNum);
        params.put("recordPerPage",pageRecord);
        return selectPaginationList("customer.getCustomersByCond",params,paginationInfo);
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return getSqlSession().selectOne("customer.getCustomerByPhoneNumber",phoneNumber);
    }

    @Override
    public int deleteCustomer(String customerId) {
        return getSqlSession().delete("customer.deleteCustomer",customerId);
    }

    @Override
    public Customer getCustomerByUserId(int userId) {
        return getSqlSession().selectOne("customer.getCustomerByUserId",userId);
    }

    @Override
    public List<Customer> getCustomerByNameAndPhotoIdByCond(Map<String, Object> params,
                                                            PaginationInfo paginationInfo) {
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage((Integer) params.get("pageNum"));
        paginationInfo.setRecordPerPage((Integer) params.get("recordPerPage"));
        return selectPaginationList("customer.getCustomerByNameAndPhotoIdByCond",params,paginationInfo);
    }

    @Override
    public List<Customer> getCustomerByPhotographerIdByCond(Map<String, Object> params, PaginationInfo paginationInfo) {
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage((Integer) params.get("pageNum"));
        paginationInfo.setRecordPerPage((Integer) params.get("recordPerPage"));
        return selectPaginationList("customer.getCustomerByPhotographerIdByCond",params,paginationInfo);

      }
}
