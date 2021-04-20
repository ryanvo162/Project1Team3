package com.ps14483.project1_team3.model;

public class Meo {
    public String key;

    public String ten;
    public String maulong;
    public int gia;
    public String img;
    public String chitiet;

    public Meo(String key, String ten, String maulong, int gia, String img, String chitiet) {
        this.key = key;
        this.ten = ten;
        this.maulong = maulong;
        this.gia = gia;
        this.img = img;
        this.chitiet = chitiet;
    }

    public Meo() {
    }

    public Meo(String key, String ten, String maulong, int gia, String img) {
        this.key = key;
        this.ten = ten;
        this.maulong = maulong;
        this.gia = gia;
        this.img = img;
    }

}
