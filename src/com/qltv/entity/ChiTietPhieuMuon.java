/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.entity;

/**
 *
 * @author 84565
 */
public class ChiTietPhieuMuon {
    private int MaCTPM;
	private int MaPM;
	private int MaSach;
	private String NgayTra;
	private String GhiChu;

    public ChiTietPhieuMuon() {
        super();
    }

    public ChiTietPhieuMuon(int MaCTPM, int MaPM, int MaSach, String NgayTra, String GhiChu) {
        super();
        this.MaCTPM = MaCTPM;
        this.MaPM = MaPM;
        this.MaSach = MaSach;
        this.NgayTra = NgayTra;
        this.GhiChu = GhiChu;
    }

    public int getMaCTPM() {
        return MaCTPM;
    }

    public void setMaCTPM(int MaCTPM) {
        this.MaCTPM = MaCTPM;
    }

    public int getMaPM() {
        return MaPM;
    }

    public void setMaPM(int MaPM) {
        this.MaPM = MaPM;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int MaSach) {
        this.MaSach = MaSach;
    }

    public String getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(String NgayTra) {
        this.NgayTra = NgayTra;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuMuon [MaCTPM=" + MaCTPM + ", MaPM=" + MaPM + ", MaSach="
				+ MaSach + "NgayTra=" + NgayTra + ", GhiChu=" + GhiChu + "]";
    }
        

}
