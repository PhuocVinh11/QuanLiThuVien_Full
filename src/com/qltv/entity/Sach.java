/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.entity;

/**
 *
 * @author Admin
 */
public class Sach {
    String ten,hinh,ghichu;
    int nam, soluong, maNXB, maLoai , maTG,maKe,ma;

    public Sach() {
    }

    public Sach(int ma, String ten, String hinh, String ghichu, int maLoai, int maNXB, int maTG, int maKe, int nam, int soluong) {
        this.ma = ma;
        this.ten = ten;
        this.hinh = hinh;
        this.ghichu = ghichu;
        this.maLoai = maLoai;
        this.maNXB = maNXB;
        this.maTG = maTG;
        this.maKe = maKe;
        this.nam = nam;
        this.soluong = soluong;
    }

    

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    public int getMaTG() {
        return maTG;
    }

    public void setMaTG(int maTG) {
        this.maTG = maTG;
    }

    public int getMaKe() {
        return maKe;
    }

    public void setMaKe(int maKe) {
        this.maKe = maKe;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    
    
    
    
}
