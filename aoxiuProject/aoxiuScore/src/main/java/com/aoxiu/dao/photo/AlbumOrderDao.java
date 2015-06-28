package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.AlbumOrder;

import java.util.List;

/**
 * Created by panchao on 15/5/20.
 */
public interface AlbumOrderDao {

    /**
     *
     * @param albumOrders
     * @return
     */
    public int insertBatches(List<AlbumOrder> albumOrders);

    /**
     *
     * @param orderId
     * @return
     */
    public List<AlbumOrder> selectAlbumOrder(String orderId);
}
