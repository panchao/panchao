package com.aoxiu.service.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Appraisal;
import com.aoxiu.meta.photo.common.PhotographerOrderAppraisal;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;

import java.util.List;

/**
 * Created by panchao on 15/5/20.
 */
public interface AppraisalService {

    /**
     *
     * @param orderId
     * @param appraisal
     * @return
     */
    public boolean addAppraisal(String orderId,String appraisal,String appraisalStar,String type);

    /**
     *
     * @param orderId
     * @return
     */
    public Appraisal getAppraisalsByOrderId(String orderId);

    /**
     * 获得某个摄影师
     * @param photographerId
     * @return
     */
    public List<Appraisal> getAllAppraisals(Integer photographerId);

    /**
     *  获得所有订单，包括客户信息和评价信息
     * @param photographerId
     * @return
     */
    public List<PhotographerOrderAppraisal> getAllPhotographerOrderAppraisal(Integer photographerId,Integer pageNum,
                                                                             Integer recordPerPage,PaginationInfo paginationInfo,String type);

    /**
     * 通过用户名搜索
     * @param customerName
     * @param photographerId
     * @return
     */
    public List<PhotographerOrderAppraisal> searchAppraisalByName(String customerName,String photographerId,PaginationInfo paginationInfo);
}
