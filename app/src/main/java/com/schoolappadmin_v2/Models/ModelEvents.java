package com.schoolappadmin_v2.Models;

import java.io.Serializable;

/**
 * Created by RATHOD'S on 6/12/2017.
 */

public class ModelEvents implements Serializable {

    private String id;
    private String sdate;
    private String edate;
    private String title;
    private String organized_by;
    private String venue;
    private String theme;
    private String url;

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

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganized_by() {
        return organized_by;
    }

    public void setOrganized_by(String organized_by) {
        this.organized_by = organized_by;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
