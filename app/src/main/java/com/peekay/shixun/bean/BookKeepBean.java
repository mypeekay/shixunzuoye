package com.peekay.shixun.bean;

public class BookKeepBean {
    String title;
    String remark;
    Float money;
    int Type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BookKeepBean(String title, Float money, int type, String remark) {
        this.title = title;
        this.money = money;
        Type = type;
        this.remark = remark;
    }
}
