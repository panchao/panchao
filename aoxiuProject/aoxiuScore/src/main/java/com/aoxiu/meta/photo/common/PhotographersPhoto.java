package com.aoxiu.meta.photo.common;

/**
 * Created by panchao on 15/5/10.
 */
public class PhotographersPhoto {
    private String name;
    private String src;
    private String id;

    public PhotographersPhoto(String name, String src, String id) {
        this.name = name;
        this.src = src;
        this.id = id;
    }

    public PhotographersPhoto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
