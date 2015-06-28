package com.aoxiu.service.photo.impl;

import com.aoxiu.AoxiuConstant;
import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.photo.AppraisalDao;
import com.aoxiu.dao.photo.CustomerDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.Appraisal;
import com.aoxiu.meta.photo.Customer;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.common.PhotographerOrderAppraisal;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;
import com.aoxiu.service.photo.AppraisalService;
import com.aoxiu.service.photo.PhotographerOrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/20.
 */
public class AppraisalServiceImpl implements AppraisalService {
    @Resource
    private AppraisalDao appraisalDao;
    @Resource
    private PhotographerOrderService photographerOrderService;
    @Resource
    private PhotographerOrderDao photographerOrderDao;

    @Resource
    private CustomerDao customerDao;
    @Override
    @Transactional
    public boolean addAppraisal(String orderId, String appraisal,String appraisalStar,String type) {
        Appraisal appraisal1 = new Appraisal();
        PhotographerOrder photographerOrder = new PhotographerOrder();
        appraisal1.setAppraisal(appraisal);
        appraisal1.setAppraisalId(Integer.parseInt(orderId));
        appraisal1.setAppraisalStar(Integer.valueOf(appraisalStar));
        photographerOrder.setOrderId(Integer.valueOf(orderId));
        photographerOrder.setIsAppraisaled('Y');
        int count = appraisalDao.insertAppraisal(appraisal1);     //插入评价
        if(count == 1){
            photographerOrderService.updateOrder(photographerOrder,type);   //更新订单的评价状态
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Appraisal getAppraisalsByOrderId(String orderId) {
        return appraisalDao.selectAppraisalsByOrderId(orderId);
    }

    @Override
    public List<Appraisal> getAllAppraisals(Integer photographerId) {
        return null;
    }

    @Override
    public List<PhotographerOrderAppraisal> getAllPhotographerOrderAppraisal(Integer photographerId,Integer pageNum,
                                                                             Integer recordPerPage,PaginationInfo paginationInfo,String type) {
        List<PhotographerOrder> photographerOrderList = null;

        if(StringUtils.isEmpty(type)){
        photographerOrderList = photographerOrderDao.getPhotographerOrdersByPhotographersIdByCond(photographerId.toString(),
                pageNum, recordPerPage,paginationInfo);
        }else if(type.equals(AoxiuConstant.AOXIU_ORDER_COMMENTED)){
            photographerOrderList = photographerOrderDao.getPhotographerOrdersCommentedByPhotographersIdByCond
                    (photographerId.toString(),pageNum,recordPerPage,paginationInfo);
        }else {
            photographerOrderList = photographerOrderDao.getPhotographerOrdersUnCommentedByPhotographersIdByCond
                    (photographerId.toString(), pageNum, recordPerPage, paginationInfo);
        }
        if(photographerOrderList != null && photographerOrderList.size() > 0){
            List<PhotographerOrderAppraisal> photographerOrderAppraisals = new ArrayList<>();
            Customer customer = null;
            Appraisal appraisal = null;
            for(PhotographerOrder tempOrder: photographerOrderList){
               customer = customerDao.getCustomerByUserId(tempOrder.getUserId());
                if(customer == null){
                    continue;   //TODO 这种情况是错的
                }else{
                    PhotographerOrderAppraisal photographerOrderAppraisal = new PhotographerOrderAppraisal();
                    appraisal = appraisalDao.selectAppraisalsByOrderId(tempOrder.getOrderId() + "");
                    photographerOrderAppraisal.setOrderId(tempOrder.getOrderId());
                    photographerOrderAppraisal.setCustomerName(customer.getRealName());
                    photographerOrderAppraisal.setCustomerPhone(customer.getPhoneNumber());
                    photographerOrderAppraisal.setStatus(tempOrder.getIsAppraisaled());
                    if(appraisal != null){
                        photographerOrderAppraisal.setAppraisal(appraisal.getAppraisal());
                        photographerOrderAppraisal.setStarNum(appraisal.getAppraisalStar());
                    }
                    photographerOrderAppraisals.add(photographerOrderAppraisal);
                }
            }
            return photographerOrderAppraisals;
        }else{
            return null;
        }
    }

    @Override
    public List<PhotographerOrderAppraisal> searchAppraisalByName(String customerName, String photographerId,PaginationInfo paginationInfo) {

        Map<String,Object> params = new HashMap<>();
        params.put("customerName",customerName);
        params.put("photographerId",photographerId);
        List<Customer> customers = customerDao.getCustomerByNameAndPhotoIdByCond(params, paginationInfo);
        if(customers != null && customers.size() > 1){
            List<PhotographerOrderAppraisal> photographerOrderAppraisals = new ArrayList<>(customers.size());
            PhotographerOrder photographerOrder = null;
            Appraisal appraisal = null;
            for(Customer customer:customers){
                photographerOrder = photographerOrderDao.getPhotographerOrderByUserId(customer.getCustomerId() + "");
                if(photographerOrder != null){
                    PhotographerOrderAppraisal photographerOrderAppraisal = new PhotographerOrderAppraisal();
                    photographerOrderAppraisal.setOrderId(photographerOrder.getOrderId());
                    photographerOrderAppraisal.setStatus(photographerOrder.getIsAppraisaled());
                    photographerOrderAppraisal.setCustomerPhone(customer.getPhoneNumber());
                    photographerOrderAppraisal.setCustomerName(customer.getRealName());
                    appraisal = appraisalDao.selectAppraisalsByOrderId(photographerOrder.getOrderId() + "");
                    if(appraisal != null){
                        photographerOrderAppraisal.setStarNum(appraisal.getAppraisalStar());
                        photographerOrderAppraisal.setAppraisal(appraisal.getAppraisal());
                    }
                    photographerOrderAppraisals.add(photographerOrderAppraisal);
                }else{
                    continue;    //TODO 这种情况是错误的，应该打个错误日志
                }
            }
            return photographerOrderAppraisals;
        }else{
            return null;
        }
    }
}
