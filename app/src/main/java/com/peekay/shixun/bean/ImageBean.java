package com.peekay.shixun.bean;

public class ImageBean {
    String url;
    int imgh;
    int imgw;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    int w;

    public int getImgh() {
        return imgh;
    }

    public void setImgh(int imgh) {
        this.imgh = imgh;
    }

    public int getImgw() {
        return imgw;
    }

    public void setImgw(int imgw) {
        this.imgw = imgw;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageBean(String url, int imgh, int imgw, int w) {
        this.url = url;
        this.imgh = imgh;
        this.imgw = imgw;
        this.w = w;
    }
}
