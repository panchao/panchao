package com.aoxiu.meta.photo;

import java.util.Date;

/**
 * Created by panchao on 15/4/28.
 */
public class PhotographerOrder {
    //订单编号
    private int orderId;
    // 客户编号
    private Integer userId;
    //摄影师编号
    private Integer photographersId;
    //是否添加水印 1表示添加，2表示不添加
    private Integer addWaterMark;
    //设置的最大精修照片数
    private Integer maxSelectCount;
    //赠送的相册
    private Integer giftAlbumsType;
    //获取原照片编码
    private String getCode;
    private String getCodeSelected;
    //超出最大精专照片数，超出的每一张的钱
    private Double pricePerPhoto;
    //订单的类型，1表示摄影师建的，2表示婚庆建的
    private Integer selectPhotoType;
    //订单状态
    private Integer orderStep;

    //水印类型
    private String watermarkType;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否评价 'N' 没有评价，'Y' 已评价
    private Character isAppraisaled;

    public PhotographerOrder() {
    }

    public Character getIsAppraisaled() {
        return isAppraisaled;
    }

    public void setIsAppraisaled(Character isAppraisaled) {
        this.isAppraisaled = isAppraisaled;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPhotographersId() {
        return photographersId;
    }

    public void setPhotographersId(Integer photographersId) {
        this.photographersId = photographersId;
    }

    public Integer getAddWaterMark() {
        return addWaterMark;
    }

    public void setAddWaterMark(Integer addWaterMark) {
        this.addWaterMark = addWaterMark;
    }

    public Integer getMaxSelectCount() {
        return maxSelectCount;
    }

    public void setMaxSelectCount(Integer maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }

    public Integer getGiftAlbumsType() {
        return giftAlbumsType;
    }

    public void setGiftAlbumsType(Integer giftAlbumsType) {
        this.giftAlbumsType = giftAlbumsType;
    }

    public String getGetCode() {
        return getCode;
    }

    public void setGetCode(String getCode) {
        this.getCode = getCode;
    }

    public String getGetCodeSelected() {
        return getCodeSelected;
    }

    public void setGetCodeSelected(String getCodeSelected) {
        this.getCodeSelected = getCodeSelected;
    }

    public Double getPricePerPhoto() {
        return pricePerPhoto;
    }

    public void setPricePerPhoto(Double pricePerPhoto) {
        this.pricePerPhoto = pricePerPhoto;
    }

    public Integer getSelectPhotoType() {
        return selectPhotoType;
    }

    public void setSelectPhotoType(Integer selectPhotoType) {
        this.selectPhotoType = selectPhotoType;
    }

    public Integer getOrderStep() {
        return orderStep;
    }

    public void setOrderStep(Integer orderStep) {
        this.orderStep = orderStep;
    }

    public String getWatermarkType() {
        return watermarkType;
    }

    public void setWatermarkType(String watermarkType) {
        this.watermarkType = watermarkType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
