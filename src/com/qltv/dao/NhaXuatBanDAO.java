/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.DocGia;
import com.qltv.entity.NhaXuatBan;
import com.qltv.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhaXuatBanDAO {
    public static ResultSet rs = null ; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO NhaXuatBan (TenNXB,DiaChi,Sđt,Hinh) VALUES (?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NhaXuatBan SET TenNXB=?,DiaChi=?,Sđt=?,Hinh=? WHERE MaNXB=?";
    public static String DELETE_SQL = "DELETE FROM NhaXuatBan WHERE MaNXB=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NhaXuatBan";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NhaXuatBan WHERE MaNXB=?";

    public void insert(NhaXuatBan entity) {
        XJdbc.update(INSERT_SQL,
                entity.getTen(),
                entity.getDiachi(),
                entity.getSdt(),
                entity.getHinh());
    }

    public void update(NhaXuatBan entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getTen(),
                entity.getDiachi(),
                entity.getSdt(),
                entity.getHinh(),
                entity.getMa());
    }

    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }

    public List<NhaXuatBan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public NhaXuatBan selectById(int id) {
        List<NhaXuatBan> list = selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    protected ArrayList<NhaXuatBan> selectBySql(String sql, Object... args) {
        ArrayList<NhaXuatBan> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql,args);
                while (rs.next()) {
                    NhaXuatBan dg = new NhaXuatBan();
                    dg.setMa(rs.getInt(1));
                    dg.setTen(rs.getString(2));
                    dg.setDiachi(rs.getString(3));
                    dg.setSdt(rs.getString(4));
                    dg.setHinh(rs.getString(5));
                    list.add(dg);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
//            throw new RuntimeException(ex);
        }
        return list;
    }
    
    public List<String> selectById() {
        String SELECT_ID = "select TenNXB from NhaXuatBan";
        return selectByName( SELECT_ID);
    }
    
    protected ArrayList<String> selectByName(String sql, Object... args) {
        ArrayList<String> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    String tenLoai = rs.getString("TenNXB");
                    list.add(tenLoai);
                                    }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
//            throw new RuntimeException(ex);
        }
        return list;
    }
    
    public String convertToTenNXB(int maNXB) {
    String tenNXB = "";
    try {
        String sql = "SELECT TenNXB FROM NhaXuatBan WHERE MaNXB = ?";
        try (ResultSet resultSet = XJdbc.query(sql, maNXB)) {
            if (resultSet.next()) {
                tenNXB = resultSet.getString("TenNXB");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return tenNXB;
}
    
    public int convertToMaNXB(String tenKS) {
    int maKS = 0;
    try {
        String sql = "SELECT MaNXB FROM NhaXuatBan WHERE TenNXB like ?";
        try (ResultSet resultSet = XJdbc.query(sql, tenKS)) {
            if (resultSet.next()) {
                maKS = resultSet.getInt("MaNXB");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return maKS;
}
}
