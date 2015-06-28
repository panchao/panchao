package com.aoxiu.dao.photo;

import com.aoxiu.common.PaginationInfo;
import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.common.PhotographersContent;
import com.aoxiu.meta.photo.common.PhotographersPhoto;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/2.
 */
public interface PhotoDao {
    public int insertPhoto(Photo photo);
    public int insertPhotoBatches(List<Photo> photoList);
    public List<Photo> getPhotosByContentId(String contentId);
    public Photo getPhotosByPhotoId(String photoId);

    public int deletePhotos(List<String> photos);

    public int updatePictures(List<Photo> photos);
    public List<Photo> getSelectedPhotos(List<String> contentIds);

    public  List<PhotographersPhoto> getPhotosContentId(List<PhotographersContent> photographersContents);

    public List<Photo> getSelectedPhotos(int contentId);

    /**
     * 分页获取
     * @param contentId
     * @param paginationInfo
     * @param pageNum
     * @param recordPerPage
     * @return
     */
    public List<Photo> getSelectedPhotosByCond(int contentId,PaginationInfo paginationInfo,int pageNum,int recordPerPage);

    /**
     * 分页获取精修例表
     * @param params
     * @param paginationInfo
     * @return
     */
    public List<Photo> getSelectedPhotosByCond(Map<String,Object> params,PaginationInfo paginationInfo);

    public List<Photo> getSelectedPhotosBatches(List<Integer> params);

    /**
     * 获得精修片名字
     * @param params
     * @return
     */
    public List<String> getSelectedPhotosNames(List<Integer> params);

    /**
     * 获得原片
     * @param contentIds
     * @return
     */
    public List<Photo> getUnSelectPhotosBatches(List<Integer> contentIds);
}
