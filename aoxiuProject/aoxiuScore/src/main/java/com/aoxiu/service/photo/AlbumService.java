package com.aoxiu.service.photo;

import com.aoxiu.meta.photo.Album;
import com.aoxiu.meta.photo.AlbumOrder;
import com.aoxiu.meta.photo.common.AlbumOrderCommon;

import java.util.List;

/**
 * Created by panchao on 15/5/18.
 */
public interface AlbumService {
    /**
     *
     * @return
     */
    public List<Album> getAllAlbums();

    /**
     *
     * @param ids
     * @return
     */
    public List<Album> getAlbumsByIds(List<String> ids);

    /**
     *
     * @param id
     * @return
     */
    public Album getAlbumById(String id);

    /**
     *
     * @param jsonStr
     * @return
     */
    public boolean submitOrder(String jsonStr);

    /**
     *  获得某个用户的相册订单
     * @param getCode
     * @return
     */
    public List<AlbumOrderCommon> gselectAlbumOrders(String getCode);
}
