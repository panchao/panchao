package com.aoxiu.service.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.PhotographerOrder;

import java.util.List;

/**
 * Created by panchao on 15/4/29.
 */
public interface PhotographerOrderService {
    public List<PhotographerOrder> selectCustomers(String id,String type,int pageNum,
                                                   int recordPerPage,PaginationInfo paginationInfo);
    public boolean deleteOrder(String orderId,String photographersId,String type);
    public boolean updateOrder(PhotographerOrder photographerOrder,String type);
    public boolean addOrder(PhotographerOrder photographerOrder,String type);
}
