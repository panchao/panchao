package com.aoxiu.meta.photo.common;

/**
 * Created by panchao on 15/5/23.
 */
public class PhotographerOrderCommon {
   private  int orderId;
    private  int addWaterMark;
    private  String waterMarkType;
    private  int extraCount;
    private  double extraPricePerPhoto;
    private  int maxSelectCount;
    private  String createTime;
    private  String customerName;
    private  int customerId;
    private  String status;

    public PhotographerOrderCommon() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAddWaterMark() {
        return addWaterMark;
    }

    public void setAddWaterMark(int addWaterMark) {
        this.addWaterMark = addWaterMark;
    }

    public String getWaterMarkType() {
        return waterMarkType;
    }

    public void setWaterMarkType(String waterMarkType) {
        this.waterMarkType = waterMarkType;
    }

    public int getExtraCount() {
        return extraCount;
    }

    public void setExtraCount(int extraCount) {
        this.extraCount = extraCount;
    }

    public double getExtraPricePerPhoto() {
        return extraPricePerPhoto;
    }

    public void setExtraPricePerPhoto(double extraPricePerPhoto) {
        this.extraPricePerPhoto = extraPricePerPhoto;
    }

    public int getMaxSelectCount() {
        return maxSelectCount;
    }

    public void setMaxSelectCount(int maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
