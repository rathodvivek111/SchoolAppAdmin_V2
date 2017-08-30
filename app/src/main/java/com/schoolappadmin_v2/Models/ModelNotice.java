package com.schoolappadmin_v2.Models;

import java.io.Serializable;

/**
 * Created by RATHOD'S on 6/12/2017.
 */

public class ModelNotice implements Serializable {

    private String id;
    private String title;
    private String date;
    private String desc;
    private String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
