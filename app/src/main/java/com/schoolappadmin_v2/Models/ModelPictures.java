package com.schoolappadmin_v2.Models;

import java.io.Serializable;

/**
 * Created by RATHOD'S on 6/10/2017.
 */

public class ModelPictures implements Serializable {
    private String url, title, id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
