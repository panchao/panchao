package com.aoxiu.service.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Photo;

import java.util.List;

/**
 * Created by panchao on 15/5/31.
 */
public interface PhotoService {
    /**
     *  通过订单编号获得精修片列表
     * @param orderId
     * @return
     */
    public List<Photo> getSelectedPhotosByOrderId(String orderId);

    /**
     * 分页通过客户ID获得客户精修片
     * @param customerId
     * @param pageNum
     * @param recordPerPage
     * @param paginationInfo
     * @return
     */
    public List<Photo> getSlectedPhotosByCustomerId(String customerId,
                                                    int pageNum,int recordPerPage,PaginationInfo paginationInfo);

    /**
     * 通过客户编号获得精修片名字
     * @param customerId
     * @return
     */
    public String getSelectedPhotosNamesByCustomerId(String customerId);

    /**
     * 获得原片
     * @param customerId
     * @return
     */
    public List<Photo> getUnSelectPhotosByCustomerId(String customerId);

}
