package com.schoolappadmin_v2.Models;

/**
 * Created by RATHOD'S on 6/10/2017.
 */

public class ModelGallery {
    private String url, title;
    private String gallery_id;

    public String getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(String gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
