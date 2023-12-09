/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.DocGia;
import com.qltv.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DocGiaDAO{
    
                                 
    public static ResultSet rs = null ; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO DocGia (TenDocGia,GioiTinh,DiaChi,Sdt) VALUES (?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE DocGia SET TenDocGia=?,GioiTinh=?,DiaChi=?,Sdt=? WHERE MaDocGia=?";
    public static String DELETE_SQL = "DELETE FROM DocGia WHERE MaDocGia=?";
    public static String DELETE_SQL_TTV = "DELETE FROM TheThuVien WHERE MaDocGia=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM DocGia";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM DocGia WHERE MaDocGia=?";

    public void insert(DocGia entity) {
        XJdbc.update(INSERT_SQL,
                entity.getTenDG(),
                entity.isGioiTinh(),
                entity.getDiaChi(),
                entity.getSoDT());
    }

    public void update(DocGia entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getTenDG(),
                entity.isGioiTinh(),
                entity.getDiaChi(),
                entity.getSoDT(),
                entity.getMaDG());
    }
    
    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public List<DocGia> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public DocGia selectByIds(int id) {
        List<DocGia> list = selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
        
    }
    
    

    protected ArrayList<DocGia> selectBySql(String sql, Object... args) {
        ArrayList<DocGia> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query( sql, args);
                while (rs.next()) {
                    DocGia dg = new DocGia();
                    dg.setMaDG(rs.getInt("MaDocGia"));
                    dg.setTenDG(rs.getString("TenDocGia"));
                    dg.setGioiTinh(rs.getBoolean("GioiTinh"));
                    dg.setDiaChi(rs.getString("DiaChi"));
                    dg.setSoDT(rs.getString("Sdt"));
                    System.out.println(rs.getInt("MaDocGia"));
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
    
    public String convertToTenDG(int maDG) {
    String tenDocGia = "";
    try {
        String sql = "SELECT TenDocGia FROM DocGia WHERE MaDocGia = ?";
        try (ResultSet resultSet = XJdbc.query(sql, maDG)) {
            if (resultSet.next()) {
                tenDocGia = resultSet.getString("TenDocGia");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return tenDocGia;
}
    public int convertToMaDG(String tenDG) {
    int maDG = 0;
    try {
        String sql = "SELECT MaDocGia FROM DocGia WHERE TenDocGia = ?";
        try (ResultSet resultSet = XJdbc.query(sql, tenDG)) {
            if (resultSet.next()) {
                maDG = resultSet.getInt("MaDocGia");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return maDG;
}

    
    public ArrayList<String> selectNXB(){
        String sql = "SELECT TenDocGia FROM DocGia";
        ArrayList<String> nhanVienList = new ArrayList<>();
        try  {
            rs = XJdbc.query(sql);
        
    // ArrayList để lưu giữ cặp mã nhân viên và tên nhân viên
    
    while (rs.next()) {
        String tenNV = rs.getString("TenDocGia");
        
        // Thêm cặp mã nhân viên và tên nhân viên vào ArrayList
        nhanVienList.add(tenNV);
    }
    }catch (SQLException ex) {
    ex.printStackTrace();
}
        return nhanVienList;
    }
}
