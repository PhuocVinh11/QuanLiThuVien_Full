/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.ChiTietPhieuMuon;
import com.qltv.entity.ChiTietPhieuNhap;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.*;

/**
 *
 * @author 84565
 */
public class ChiTietPhieuMuonDAO {
    public ResultSet rs;
    public String SELECT_BY_ID = "select * from ChiTietPhieuMuon where MaPM = ?";
    public String SELECT_ALL = "select * from ChiTietPhieuMuon";
    public String UPDATE_SQL = "update ChiTietPhieuMuon set MaPM = ?, MaSach = ?, NgayTra = ?, GhiChu = ? where MaCTPM= ?";

    public void update(ChiTietPhieuMuon entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getMaCTPM(),
                entity.getMaSach(),
                entity.getNgayTra(),
                entity.getGhiChu(),
                entity.getMaPM());
    }

    public List<ChiTietPhieuMuon> selectAll() {
        return selectBySql(SELECT_ALL);
    }

    public String convertToTenSach(int ma) {
        String ten = "";
        try {
            String sql = "select TenSach from Sach where MaSach = ?";
            try (ResultSet resultSet = XJdbc.query(sql, ma)) {
                if (resultSet.next()) {
                    ten = resultSet.getString("TenSach");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ten;
    }

    public List<ChiTietPhieuMuon> selectByIds(int id) {
        List<ChiTietPhieuMuon> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    protected List<ChiTietPhieuMuon> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuMuon> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    ChiTietPhieuMuon entity = new ChiTietPhieuMuon();
                    entity.setMaCTPM(rs.getInt(1));
                    entity.setMaPM(rs.getInt(2));
                    entity.setMaSach(rs.getInt(3));
                    entity.setNgayTra(rs.getString(4));
                    entity.setGhiChu(rs.getString(5));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public int convertToMaSach(String tenNCC) {
        int maNCC = 0;
        try {
            String sql = "SELECT MaSach FROM Sach WHERE TenSach = ?";
            try (ResultSet resultSet = XJdbc.query(sql, tenNCC)) {
                if (resultSet.next()) {
                    maNCC = resultSet.getInt("MaSach");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maNCC;
    }
}
