/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.entity;

/**
 *
 * @author Admin
 */
public class PhieuMuon {
    int maPM,maNV,maDG;
    boolean trangThai;
    String ngayMuon;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, int maNV, int maDG, boolean trangThai, String ngayMuon) {
        this.maPM = maPM;
        this.maNV = maNV;
        this.maDG = maDG;
        this.trangThai = trangThai;
        this.ngayMuon = ngayMuon;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaDG() {
        return maDG;
    }

    public void setMaDG(int maDG) {
        this.maDG = maDG;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    
    
    
}
