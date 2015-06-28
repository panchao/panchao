package com.aoxiu.dao.photo.impl;

import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.PhotoContentDao;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.PhotoContent;
import com.aoxiu.meta.photo.common.PhotographersContent;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/2.
 */
public class PhotoContentDaoImpl extends BaseDao implements PhotoContentDao {
    @Override
    public int insertPhotoContent(PhotoContent photoContent) {
        return getSqlSession().insert("photoContent.insertPhotoContent",photoContent);
    }

    @Override
    public PhotoContent getPhotoContentById(String photoContentId) {
        return getSqlSession().selectOne("photoContent.getPhotoContentById",photoContentId);
    }

    @Override
    public List<PhotoContent> getPhotoContentsByOrderId(String orderId) {
        return getSqlSession().selectList("photoContent.getPhotoContentsByOrderId",orderId);
    }

//    @Override
//    public int updateContentName(Map<String, Object> params) {
//        return getSqlSession().update("photoContent.updateContentName",params);
//    }

    @Override
    public int updateContent(Map<String, Object> params) {
        return getSqlSession().update("photoContent.updateContent",params);
    }

    @Override
    public List<PhotographersContent> getPhotographersContentsByOrderId(List<String> orderIds) {
        return getSqlSession().selectList("photoContent.getPhotographersContentsByOrderIds",orderIds);
    }

    @Override
    public PhotoContent getOrderMasterContentByOrderId(String orderId) {
        return getSqlSession().selectOne("photoContent.getOrderMasterContentByOrderId", orderId);    }

    @Override
    public List<PhotographersContent> getPhotographersContentsByParentId(String parentId) {
        return getSqlSession().selectList("photoContent.getPhotographersContentsByParentId", parentId);
    }

    @Override
    public PhotoContent getMainContent(String orderId) {
        return getSqlSession().selectOne("photoContent.getMainContent",orderId);
    }

//    @Override
//    public List<PhotographersContent> getPhotographersContentsByOrderIds(String photographersId) {
//        return getSqlSession().selectList("photoContent.getPhotographersContentsByOrderIds");
//    }

//    @Override
//    public int insertPhotoBatches(List<Photo> photos) {
//        return getSqlSession().insert("photoContent.insertPhotoBatches",photos);
//    }
}
