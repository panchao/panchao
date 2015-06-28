package com.aoxiu.meta.photo.common;

/**
 * Created by panchao on 15/6/4.
 */
public class OrderDetail {
    private int orderId;
    private String customerName;
    private String phoneNumber;
    private String orderType;
    private int selectedCount;
    private double pricePerPhoto;
    private String status;
    private String selectedUrl;
    private String unSelectedUrl;
    private int photographerId;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public double getPricePerPhoto() {
        return pricePerPhoto;
    }

    public void setPricePerPhoto(double pricePerPhoto) {
        this.pricePerPhoto = pricePerPhoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelectedUrl() {
        return selectedUrl;
    }

    public void setSelectedUrl(String selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public String getUnSelectedUrl() {
        return unSelectedUrl;
    }

    public void setUnSelectedUrl(String unSelectedUrl) {
        this.unSelectedUrl = unSelectedUrl;
    }
}
