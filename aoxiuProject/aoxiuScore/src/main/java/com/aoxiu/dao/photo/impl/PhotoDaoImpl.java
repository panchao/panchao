package com.aoxiu.dao.photo.impl;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.dao.BaseDao;
import com.aoxiu.dao.photo.PhotoDao;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.common.PhotographersContent;
import com.aoxiu.meta.photo.common.PhotographersPhoto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/2.
 */
public class PhotoDaoImpl extends BaseDao implements PhotoDao {
    @Override
    public int insertPhoto(Photo photo) {
        return getSqlSession().insert("photo.insertPhoto",photo);
    }

    @Override
    public int insertPhotoBatches(List<Photo> photoList) {
        return getSqlSession().insert("photo.insertPhotoBatches",photoList);
    }

    @Override
    public List<Photo> getPhotosByContentId(String contentId) {
        return getSqlSession().selectList("photo.getPhotosContentId",contentId);
    }

    @Override
    public Photo getPhotosByPhotoId(String photoId) {
        return getSqlSession().selectOne("photo.getPhotosByPhotoId",photoId);
    }

    @Override
    public int deletePhotos(List<String> photos) {
        return getSqlSession().delete("photo.deletePhotos",photos);
    }

    @Override
    public int updatePictures(List<Photo> photos) {
        return getSqlSession().update("photo.updatePictures",photos);
    }

    @Override
    public List<Photo> getSelectedPhotos(List<String> contentIds) {
        return getSqlSession().selectList("photo.getSelectedPhotos",contentIds);
    }

    @Override
    public List<PhotographersPhoto> getPhotosContentId(List<PhotographersContent> photographersContents) {
        return getSqlSession().selectList("photo.getPhotosContentId",photographersContents);
    }

    @Override
    public List<Photo> getSelectedPhotos(int contentId) {
        return getSqlSession().selectList("photo.getSelectedPhoto",contentId);
    }

    @Override
    public List<Photo> getSelectedPhotosByCond(int contentId, PaginationInfo paginationInfo, int pageNum, int recordPerPage) {
        Map<String,Object> params = new HashMap<>();
        params.put("contentId",contentId);
        paginationInfo.setCurrentPage(pageNum);
        paginationInfo.setRecordPerPage(recordPerPage);
        return selectPaginationList("photo.getSelectedPhotosByCond",params,paginationInfo);
    }

    @Override
    public List<Photo> getSelectedPhotosByCond(Map<String, Object> params, PaginationInfo paginationInfo) {
        paginationInfo.setRecordPerPage((Integer)params.get("recordPerPage"));
        paginationInfo.setCurrentPage((Integer)params.get("pageNum"));
        return selectPaginationList("photo.getSelectedPhotosByCond",params,paginationInfo);
    }

    @Override
    public List<Photo> getSelectedPhotosBatches(List<Integer> params) {
        return getSqlSession().selectList("photo.getSelectedPhotosBatches", params);
    }

    @Override
    public List<String> getSelectedPhotosNames(List<Integer> params) {
        return getSqlSession().selectList("photo.getSelectedPhotosNames",params);
    }

    @Override
    public List<Photo> getUnSelectPhotosBatches(List<Integer> contentIds) {
        return getSqlSession().selectList("photo.getUnSelectPhotosBatches",contentIds);
    }

}
