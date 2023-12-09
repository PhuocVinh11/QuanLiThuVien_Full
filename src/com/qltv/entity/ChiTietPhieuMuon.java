/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.entity;

import java.util.Date;

/**
 *
 * @author 84565
 */
public class ChiTietPhieuMuon {
    int ma, maPM, maSach, soLuong;
    Date ngayTra;
    boolean trangThai;

    public ChiTietPhieuMuon() {
    }

    public ChiTietPhieuMuon(int ma, int maPM, int maSach, int soLuong, Date ngayTra) {
        this.ma = ma;
        this.maPM = maPM;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.ngayTra = ngayTra;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

//    @Override
//    public String toString() {
//        return "ChiTietPhieuMuon [MaCTPM=" + MaCTPM + ", MaPM=" + MaPM + ", MaSach="
//				+ MaSach + "NgayTra=" + NgayTra + ", GhiChu=" + GhiChu + "]";
//    }
        

}
