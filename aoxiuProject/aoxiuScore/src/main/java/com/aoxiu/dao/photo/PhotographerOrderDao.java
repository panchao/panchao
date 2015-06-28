package com.aoxiu.dao.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.PhotoContent;
import com.aoxiu.meta.photo.PhotographerOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/4/29.
 */
public interface PhotographerOrderDao {

    public int insertPhotographerOrder(PhotographerOrder photographerOrder);

    public int deletePhotographerOrder(String orderId);

    public int updatePhotographerOrder(PhotographerOrder photographerOrder);

    public List<PhotographerOrder> getPhotographerOrdersByPhotographersIdByCond(String photographersId,
                                                                                int pageNum,int pageRecord,PaginationInfo paginationInfo);

    public PhotographerOrder getPhotographerOrderById(String orderId);

    public PhotographerOrder getPhotographerOrderByGetCode(String getCode);
    public PhotographerOrder getPhotographerOrderByGetCodeSelected(String getCodeSelect);

    public PhotographerOrder getPhotographerOrderByUserId(String userId);

    public PhotographerOrder getPhotographerOrderByUserIdAndPhotographersId(int userId,int photographersId);

    public List<PhotographerOrder> getPhotographerOrderWithoutAppraisaled(Integer orderId);

    /**
     * 分页查询评价过的
     * @param photographersId
     * @param pageNum
     * @param pageRecord
     * @param paginationInfo
     * @return
     */
    public List<PhotographerOrder> getPhotographerOrdersCommentedByPhotographersIdByCond(String photographersId,
                                                                                         int pageNum,int pageRecord,PaginationInfo paginationInfo);

    /**
     * 分页查询没有评价过的
     * @param photographersId
     * @param pageNum
     * @param pageRecord
     * @param paginationInfo
     * @return
     */
    public List<PhotographerOrder> getPhotographerOrdersUnCommentedByPhotographersIdByCond(String photographersId,
                                                                                         int pageNum,int pageRecord,PaginationInfo paginationInfo);

    /**
     *  通过订单状态查询订单
     * @param params
     * @param paginationInfo
     * @return
     */
    public List<PhotographerOrder> getPhotographerOrderByStatusAndPhotographerIdByCond(Map<String,Object> params,PaginationInfo paginationInfo);
}
