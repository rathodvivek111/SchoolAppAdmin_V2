package com.schoolappadmin_v2.Models;

import java.io.Serializable;

/**
 * Created by RATHOD'S on 6/12/2017.
 */

public class ModelNews implements Serializable{

    private String id;
    private String sdate;
    private String title;
    private String news;
    private String readmoreurl;
    private String hotnews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getReadmoreurl() {
        return readmoreurl;
    }

    public void setReadmoreurl(String readmoreurl) {
        this.readmoreurl = readmoreurl;
    }

    public String getHotnews() {
        return hotnews;
    }

    public void setHotnews(String hotnews) {
        this.hotnews = hotnews;
    }
}
