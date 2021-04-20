package com.ps14483.project1_team3.model;

public class Hoadon {
    public String key;
    public String loaisp;
    public String tensp;
    public String tenkh;
    public String ngaymua;
    public int  giamua;

    public Hoadon() {
    }

    public Hoadon(String key, String loaisp, String tensp, String tenkh, String ngaymua, int giamua) {
        this.key = key;
        this.loaisp = loaisp;
        this.tensp = tensp;
        this.tenkh = tenkh;
        this.ngaymua = ngaymua;
        this.giamua = giamua;
    }

    public Hoadon(String loaisp, String tensp, String tenkh, String ngaymua, int giamua) {
        this.loaisp = loaisp;
        this.tensp = tensp;
        this.tenkh = tenkh;
        this.ngaymua = ngaymua;
        this.giamua = giamua;
    }
}
