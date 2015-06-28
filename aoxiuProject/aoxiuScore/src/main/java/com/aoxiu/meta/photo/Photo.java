package com.aoxiu.meta.photo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by panchao on 15/5/2.
 */
public class Photo {
    private int photoId;
    private int photoContentId;
    private int photoType;
    private String photoNameOld;
    private String photoNameNew;
    private Date createTime;
    private String updateTimeStr;
    private String photoSrc;


    public Photo() {
    }


    public Photo(int photoContentId, int photoType, String photoNameOld, String photoNameNew, Date createTime,String photoSrc) {
        this.photoContentId = photoContentId;
        this.photoType = photoType;
        this.photoNameOld = photoNameOld;
        this.photoNameNew = photoNameNew;
        this.createTime = createTime;
        this.photoSrc = photoSrc;
    }

    public String getUpdateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.updateTimeStr = sdf.format(this.createTime);
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getPhotoContentId() {
        return photoContentId;
    }

    public void setPhotoContentId(int photoContentId) {
        this.photoContentId = photoContentId;
    }

    public int getPhotoType() {
        return photoType;
    }

    public void setPhotoType(int photoType) {
        this.photoType = photoType;
    }

    public String getPhotoNameOld() {
        return photoNameOld;
    }

    public void setPhotoNameOld(String photoNameOld) {
        this.photoNameOld = photoNameOld;
    }

    public String getPhotoNameNew() {
        return photoNameNew;
    }

    public void setPhotoNameNew(String photoNameNew) {
        this.photoNameNew = photoNameNew;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }
}
