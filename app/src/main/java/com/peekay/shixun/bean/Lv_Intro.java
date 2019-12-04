package com.peekay.shixun.bean;

public class Lv_Intro {
    String title,content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Lv_Intro(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
