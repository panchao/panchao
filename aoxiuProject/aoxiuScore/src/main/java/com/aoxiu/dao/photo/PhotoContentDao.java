package com.aoxiu.dao.photo;

import com.aoxiu.meta.photo.Photo;
import com.aoxiu.meta.photo.PhotoContent;
import com.aoxiu.meta.photo.common.PhotographersContent;

import java.util.List;
import java.util.Map;

/**
 * Created by panchao on 15/5/2.
 */
public interface PhotoContentDao {
    public int insertPhotoContent(PhotoContent photoContent);
    public PhotoContent getPhotoContentById(String photoContentId);
    public List<PhotoContent> getPhotoContentsByOrderId(String orderId);
    public int updateContent(Map<String,Object> params);

    public List<PhotographersContent> getPhotographersContentsByOrderId(List<String> orderIds);

    public PhotoContent getOrderMasterContentByOrderId(String orderId);

    /**
     * 子目录
     * @param parentId
     * @return
     */
    public List<PhotographersContent> getPhotographersContentsByParentId(String parentId);

    /**
     * 获得主目录
     * @param orderId
     * @return
     */
    public PhotoContent getMainContent(String orderId);

}
