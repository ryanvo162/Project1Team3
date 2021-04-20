package com.ps14483.project1_team3.model;

public class ThucAn {
    public String key;

    public String ten;
    public int gia;
    public String img;
    public String chitiet;


    public ThucAn() {
    }

    public ThucAn(String key, String ten, int gia, String img, String chitiet) {
        this.key = key;
        this.ten = ten;
        this.gia = gia;
        this.img = img;
        this.chitiet = chitiet;
    }

    public ThucAn(String key, String ten, int gia, String img) {
        this.key = key;
        this.ten = ten;
        this.gia = gia;
        this.img = img;
    }


}

