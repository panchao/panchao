package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/5/2.
 */
public class PhotoContent {
    private int photoContentId;
    private String contentName;
    //目录封面
    private int contentPhotoId;
    private int orderId;
    private int contentParentId;

    public PhotoContent() {
    }

    public PhotoContent( String contentName, int contentPhotoId, int orderId, int contentParentId) {
        this.contentName = contentName;
        this.contentPhotoId = contentPhotoId;
        this.orderId = orderId;
        this.contentParentId = contentParentId;
    }

    public int getContentParentId() {
        return contentParentId;
    }

    public void setContentParentId(int contentParentId) {
        this.contentParentId = contentParentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPhotoContentId() {
        return photoContentId;
    }

    public void setPhotoContentId(int photoContentId) {
        this.photoContentId = photoContentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getContentPhotoId() {
        return contentPhotoId;
    }

    public void setContentPhotoId(int contentPhotoId) {
        this.contentPhotoId = contentPhotoId;
    }
}
