package com.ps14483.project1_team3.model;

public class KhachHang {
    public String key;
    public String ten;
    public String sdt;

    public KhachHang(String ten, String sdt) {
        this.ten = ten;
        this.sdt = sdt;
    }

    public KhachHang() {
    }

    public KhachHang(String key, String ten, String sdt) {
        this.key = key;
        this.ten = ten;
        this.sdt = sdt;
    }
}
