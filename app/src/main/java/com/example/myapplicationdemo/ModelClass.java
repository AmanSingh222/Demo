package com.example.myapplicationdemo;

public class ModelClass {
    String  coin, imageurl;

    public ModelClass(String coin, String imageurl) {
        this.coin = coin;
        this.imageurl = imageurl;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
