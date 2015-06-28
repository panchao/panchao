package com.aoxiu.service.photo.impl;

import com.aoxiu.dao.photo.AlbumDao;
import com.aoxiu.dao.photo.AlbumOrderDao;
import com.aoxiu.dao.photo.PhotographerOrderDao;
import com.aoxiu.meta.photo.Album;
import com.aoxiu.meta.photo.AlbumOrder;
import com.aoxiu.meta.photo.PhotographerOrder;
import com.aoxiu.meta.photo.common.AlbumOrderCommon;
import com.aoxiu.service.photo.AlbumService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.PostVMInitHook;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panchao on 15/5/18.
 */
public class AlbumServiceImpl implements AlbumService {
    @Resource
    private AlbumDao albumDao;
    @Resource
    private PhotographerOrderDao photographerOrderDao;
    @Resource
    private AlbumOrderDao albumOrderDao;
    @Override
    public List<Album> getAllAlbums() {
        return albumDao.getAlbums();
    }

    @Override
    public List<Album> getAlbumsByIds(List<String> ids) {
        return albumDao.getAlbumsByIds(ids);
    }

    @Override
    public Album getAlbumById(String id) {
        return albumDao.getAlbum(id);
    }

    @Override
    public boolean submitOrder(String jsonStr) {
//        JSONObject jsonObject = JSONObject.fromObject(jsonStr.toUpperCase());
        JSONArray jsonArray = JSONArray.fromObject(jsonStr.toUpperCase());
        if(jsonArray == null){
            return false;
        }
        int size = jsonArray.size();
        List<AlbumOrder> albumOrders = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        for(int i = 0; i < jsonArray.size();i++){
            JSONObject tempObject = jsonArray.getJSONObject(i);
            String userId = tempObject.getString("USERID");
            PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCode(userId);
            AlbumOrder albumOrder = new AlbumOrder();
            albumOrder.setOrderId(photographerOrder.getOrderId());
//            albumOrder.setOrderId(1);
            albumOrder.setAlbumId(tempObject.getInt("ALBUMID"));
            albumOrder.setAlbumType(tempObject.getString("ALBUMTYPE"));
            albumOrder.setPhotoId(tempObject.getInt("PHOTOID"));
            albumOrder.setAlbumCount(tempObject.getInt("ALBUMCOUNT"));
            albumOrder.setIsPaid("N");
            albumOrders.add(albumOrder);
        }
        return size == albumOrderDao.insertBatches(albumOrders)?true:false;
    }

    @Override
    public List<AlbumOrderCommon> gselectAlbumOrders(String getCode) {
        PhotographerOrder photographerOrder = photographerOrderDao.getPhotographerOrderByGetCode(getCode);
        if(photographerOrder == null){
            return null;
        }
        List<AlbumOrder> albumOrders = albumOrderDao.selectAlbumOrder(photographerOrder.getOrderId() + "");
        if(albumOrders != null){
            List<AlbumOrderCommon> albumOrderCommons = new ArrayList<>();
            for(AlbumOrder albumOrder:albumOrders){
                AlbumOrderCommon albumOrderCommon = new AlbumOrderCommon();
                Album album = albumDao.getAlbum(albumOrder.getAlbumId() + "");
                albumOrderCommon.setAlbumCount(albumOrder.getAlbumCount());
                albumOrderCommon.setAlbumId(albumOrder.getAlbumId());
                albumOrderCommon.setId(albumOrder.getId());
                albumOrderCommon.setOrderId(albumOrder.getOrderId());
                albumOrderCommon.setAlbumName(album.getAlbumName());
                albumOrderCommon.setAlbumType(albumOrder.getAlbumType());
                albumOrderCommons.add(albumOrderCommon);
            }
            return albumOrderCommons;
        }
        return null;
    }
}
