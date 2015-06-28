package com.aoxiu.meta.photo;

import java.math.BigDecimal;

/**
 * Created by panchao on 15/5/18.
 */
public class Album {
    private int id;
    private String albumName;
    private String albumSrc;
    private BigDecimal albumPrice;

    public Album() {
    }

    public Album(int id, String albumName, String albumSrc, BigDecimal albumPrice) {
        this.id = id;
        this.albumName = albumName;
        this.albumSrc = albumSrc;
        this.albumPrice = albumPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumSrc() {
        return albumSrc;
    }

    public void setAlbumSrc(String albumSrc) {
        this.albumSrc = albumSrc;
    }

    public BigDecimal getAlbumPrice() {
        return albumPrice;
    }

    public void setAlbumPrice(BigDecimal albumPrice) {
        this.albumPrice = albumPrice;
    }
}
