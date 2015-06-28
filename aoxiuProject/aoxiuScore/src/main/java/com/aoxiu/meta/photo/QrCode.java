package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/5/30.
 */
public class QrCode {
    private int id;
    private int orderId;
    private String getUnselectedUrl;
    private String getselectedUrl;

    public QrCode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getGetUnselectedUrl() {
        return getUnselectedUrl;
    }

    public void setGetUnselectedUrl(String getUnselectedUrl) {
        this.getUnselectedUrl = getUnselectedUrl;
    }

    public String getGetselectedUrl() {
        return getselectedUrl;
    }

    public void setGetselectedUrl(String getselectedUrl) {
        this.getselectedUrl = getselectedUrl;
    }
}
