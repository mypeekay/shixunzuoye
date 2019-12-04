package com.peekay.shixun.bean;

import java.util.List;

public class Lv_Home {
    /**
     * selfid : 256
     * address : 重庆南岸区长生桥水云山庄
     * commentUserRated : 5
     * ename : Chong Qing Nan An Shui Yun Shan Zhuang
     * id : 25103
     * name : 重庆南岸水云山庄
     * picture : https://m.tuniucdn.com/filebroker/cdn/online/ae/cb/aecb2238_w120_h120_c1_t0.jpg
     * region : 南岸区
     * typeName : 旅游度假区
     * distance : 11609973
     */

    private int selfid;
    private String address;
    private float commentUserRated;
    private String ename;
    private String id;
    private String name;
    private String picture;
    private String region;
    private String typeName;
    private String distance;

    public int getSelfid() {
        return selfid;
    }

    public void setSelfid(int selfid) {
        this.selfid = selfid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getCommentUserRated() {
        return commentUserRated;
    }

    public void setCommentUserRated(float commentUserRated) {
        this.commentUserRated = commentUserRated;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Lv_Home(int selfid, String address, float commentUserRated, String ename, String id, String name, String picture, String region, String typeName, String distance) {
        this.selfid = selfid;
        this.address = address;
        this.commentUserRated = commentUserRated;
        this.ename = ename;
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.region = region;
        this.typeName = typeName;
        this.distance = distance;
    }
}
