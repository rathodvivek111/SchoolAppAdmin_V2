package com.schoolappadmin_v2.Models;

/**
 * Created by RATHOD'S on 6/10/2017.
 */

public class ModelParent {

    String id;
    String student_id;
    String father_name;
    String father_mobile;
    String mother_name;
    String mother_mobile;
    String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getFather_mobile() {
        return father_mobile;
    }

    public void setFather_mobile(String father_mobile) {
        this.father_mobile = father_mobile;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getMother_mobile() {
        return mother_mobile;
    }

    public void setMother_mobile(String mother_mobile) {
        this.mother_mobile = mother_mobile;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
