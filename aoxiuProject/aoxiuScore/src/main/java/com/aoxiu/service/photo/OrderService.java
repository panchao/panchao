package com.aoxiu.service.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.PhotoContent;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.common.OrderDetail;
import com.aoxiu.meta.photo.common.PhotographerOrderCommon;
import com.aoxiu.meta.photo.common.PhotographersContent;
import com.aoxiu.meta.photo.common.PhotographersPhoto;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/1.
 */
public interface OrderService {
    public int updateOrder(String waterMark,String maxCount,String orderId,String getCode,String price);
    public Map<String,Object> addOrder(PhotographerOrder photographerOrder,String type);
    public int deleteOrder(String orderId,String type);
    public List<Photo> uploadPhotos(String jsonStr);

    public int addNewContent(String contentName,String orderId,String photoId);

    public Map<String,String> getPicturesUrl(String contentId);
    public Map<String,Object> getPhotoContentByCode(String getCode,String type);

    public boolean updateContent(String contentId,String contentName,String contentPhoto);

    public int deletePictures(String pictures);

    public int updatePicturesName(String jsonStr);

    public int addSelectedPictures(String ids);

    public Map<String ,String> getSelectedPhotos(String getCode);

    public List<PhotographerOrderCommon> getPhotographerOrders(String photographerId,int pageNum,
                                                               int recordPerPage,PaginationInfo paginationInfo);

    public Map<String,Object> getPhotographersContents(String photographersId);

    public List<PhotographersPhoto> getPhotosByAlbumId(String albumId);

    public List<PhotographersContent> getContentByAlbumId(String albumId);

    public List<PhotographersPhoto> getAlbumPictures(String getCode);

    public List<PhotographersContent> getPhotographersAlbumsByPhotographersId(String photographersId);

    public Map<String,Object> getEntrance(String orderId);

    /**
     * 是否生成了getCode, 包括精选的和原片的
     * @param type
     * @param orderId
     * @return
     */
    public String  isGeneratorGetCode(String orderId,int type) throws Exception;

    /**
     * 获得某个订单的主目录
     * @param orderId
     * @return
     */
    public PhotographersContent getMasterContents(String orderId);

    /**
     * 获得订单详情
     * @param orderId
     * @return
     */
    public OrderDetail getOrderDetailByOrderId(String orderId);

    /**
     * 获取订单主目录照片
     * @param orderId
     * @return
     */
    public List<PhotographersPhoto> getOrderMainContentPhotos(String orderId);

    /**
     * 通过客户编号获得订单详情
     * @param customerId
     * @return
     */
    public OrderDetail getOrderDetailByCustomerId(String customerId);

    /**
     *  搜索订单信息
     * @param key
     * @param type
     * @param photographersId
     * @param paginationInfo
     * @return
     */
    public List<PhotographerOrderCommon> searchOrdersByKey(String key, String type,String photographersId,PaginationInfo
            paginationInfo,int pageNum,int recordPerPage);

    /**
     * 通过客户编号获得精修片
     * @param customerId
     * @return
     */
    public List<Photo> getSelectedPhotosByCustomerId(String customerId,int pageNum,int recordPerPage,PaginationInfo paginationInfo);

    /**
     * 通过客户编号获得主目录
     * @param customerId
     * @return
     */
    public PhotographersContent getMasterContentsByCustomerId(String customerId);


}
