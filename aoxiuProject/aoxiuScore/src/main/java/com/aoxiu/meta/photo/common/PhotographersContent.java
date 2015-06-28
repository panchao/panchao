package com.aoxiu.meta.photo.common;


/**
 * Created by panchao on 15/5/10.
 */
public class PhotographersContent {
    private  String name;
    private  int id;

    public PhotographersContent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public PhotographersContent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
