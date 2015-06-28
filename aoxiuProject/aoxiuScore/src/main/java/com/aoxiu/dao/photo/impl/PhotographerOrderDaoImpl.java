package com.aoxiu.dao.photo.impl;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.PhotographerOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/29.
 */
public class PhotographerOrderDaoImpl  extends BaseDao implements PhotographerOrderDao {
    @Override
    public int insertPhotographerOrder(PhotographerOrder photographerOrder) {
        return getSqlSession().insert("photographerOrder.insertPhotographerOrder",photographerOrder);
    }

    @Override
    public int deletePhotographerOrder(String orderId) {
        return getSqlSession().delete("photographerOrder.deletePhotographerOrder",orderId);
    }

    @Override
    public int updatePhotographerOrder(PhotographerOrder photographerOrder) {
        return getSqlSession().update("photographerOrder.updatePhotographerOrder",photographerOrder);
    }



    @Override
    public List<PhotographerOrder> getPhotographerOrdersByPhotographersIdByCond(
            String photographersId,int pageNum,int pageRecord,PaginationInfo paginationInfo) {
        Map<String,Object> params = new HashMap<>();
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage(pageNum);
        paginationInfo.setRecordPerPage(pageRecord);
        params.put("photographersId",photographersId);
        List<PhotographerOrder> photographerOrderList =  selectPaginationList
                ("photographerOrder.getPhotographerOrdersByPhotographersIdByCond",params,paginationInfo);

        return photographerOrderList;
    }

    @Override
    public PhotographerOrder getPhotographerOrderById(String orderId) {
        return getSqlSession().selectOne("photographerOrder.getPhotographerOrderById",orderId);
    }

    @Override
    public PhotographerOrder getPhotographerOrderByGetCode(String getCode) {
        return getSqlSession().selectOne("photographerOrder.getPhotographerOrderByGetCode",getCode);
    }

    @Override
    public PhotographerOrder getPhotographerOrderByGetCodeSelected(String getCodeSelect) {
        return getSqlSession().selectOne("photographerOrder.getPhotographerOrderByGetCodeSelected",getCodeSelect);
    }

    @Override
    public PhotographerOrder getPhotographerOrderByUserId(String userId) {
        return getSqlSession().selectOne("photographerOrder.getPhotographerOrderByUserId",userId);
    }

    @Override
    public PhotographerOrder getPhotographerOrderByUserIdAndPhotographersId(int userId, int photographersId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("photographersId",photographersId);
        return getSqlSession().selectOne("photographerOrder.getPhotographerOrderByUserIdAndPhotographersId",params);
    }

    @Override
    public List<PhotographerOrder> getPhotographerOrderWithoutAppraisaled(Integer orderId) {
        return getSqlSession().selectList("photographerOrder.getPhotographerOrderWithoutAppraisaled");
    }

    @Override
    public List<PhotographerOrder> getPhotographerOrdersCommentedByPhotographersIdByCond(String photographersId, int pageNum, int pageRecord, PaginationInfo paginationInfo) {
        Map<String,Object> params = new HashMap<>();
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage(pageNum);
        paginationInfo.setRecordPerPage(pageRecord);
        params.put("photographersId",photographersId);
        List<PhotographerOrder> photographerOrderList =  selectPaginationList("photographerOrder.getPhotographerOrdersCommentedByPhotographersIdByCond",params,paginationInfo);

        return photographerOrderList;
    }

    @Override
    public List<PhotographerOrder> getPhotographerOrdersUnCommentedByPhotographersIdByCond(String photographersId, int pageNum, int pageRecord, PaginationInfo paginationInfo) {
        Map<String,Object> params = new HashMap<>();
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage(pageNum);
        paginationInfo.setRecordPerPage(pageRecord);
        params.put("photographersId",photographersId);
        List<PhotographerOrder> photographerOrderList =  selectPaginationList("photographerOrder.getPhotographerOrdersUnCommentedByPhotographersIdByCond",params,paginationInfo);

        return photographerOrderList;
    }

    @Override
    public List<PhotographerOrder> getPhotographerOrderByStatusAndPhotographerIdByCond(Map<String, Object> params, PaginationInfo paginationInfo) {
        if(paginationInfo == null){
            paginationInfo = new PaginationInfo();
        }
        paginationInfo.setCurrentPage((Integer)params.get("pageNum"));
        paginationInfo.setRecordPerPage((Integer)params.get("recordPerPage"));
        return selectPaginationList("photographerOrder.getPhotographerOrderByStatusAndPhotographerIdByCond",params,paginationInfo);
    }
}
