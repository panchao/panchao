package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.AlbumOrderDao;
import com.aoxiu.meta.photo.AlbumOrder;

import java.util.List;

/**
 * Created by panchao on 15/5/20.
 */
public class AlbumOrderDaoImpl extends BaseDao implements AlbumOrderDao {
    @Override
    public int insertBatches(List<AlbumOrder> albumOrders) {
        int count = 0;
        for(AlbumOrder tmp:albumOrders){
            count += insertOneData(tmp);
        }
//        return getSqlSession().insert("albumOrder.insertBatches",albumOrders);
        return count;
    }

    public int insertOneData(AlbumOrder albumOrder){
        return getSqlSession().insert("albumOrder.insertOneData",albumOrder);
    }
    @Override
    public List<AlbumOrder> selectAlbumOrder(String orderId) {
        return getSqlSession().selectList("albumOrder.selectAlbumOrder",orderId);
    }
}
