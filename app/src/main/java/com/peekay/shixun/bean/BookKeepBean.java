package com.peekay.shixun.bean;

public class BookKeepBean {
    String title;
    String remark;
    Float money;
    int Type;
    String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BookKeepBean(String title, String remark, Float money, int type, String time) {
        this.title = title;
        this.remark = remark;
        this.money = money;
        Type = type;
        this.time = time;
    }
}
