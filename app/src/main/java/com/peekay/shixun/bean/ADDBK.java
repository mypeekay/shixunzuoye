package com.peekay.shixun.bean;

public class ADDBK {
    int pic;
    String item;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public ADDBK(int pic, String item) {
        this.pic = pic;
        this.item = item;
    }
}
