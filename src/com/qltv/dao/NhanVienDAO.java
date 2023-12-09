/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.NhanVien;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienDAO {
    public ResultSet rs;
    public static String INSERT_SQL = "insert into NhanVien (TenNV, NamSinh, GioiTinh, DiaChi, Sđt) values (?,?,?,?,?)";
    public static String UPDATE_SQL = "update NhanVien set TenNV = ?, NamSinh = ?, GioiTinh = ?, DiaChi = ?, Sđt = ? where MaNV = ?";
    public static String DELETE_SQL = "delete from NhanVien where MaNV = ?";
    public static String SELECT_ALL = "select * from NhanVien";
    public static String SELECT_BY_ID = "select * from NhanVien where MaNV = ?";
    
    public void insert(NhanVien entity) {
        XJdbc.update(INSERT_SQL,
                entity.getTen(),
                entity.getNam(),
                entity.isGiotinh(),
                entity.getDiachi(),
                entity.getSdt());
    }

    public void update(NhanVien entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getTen(),
                entity.getNam(),
                entity.isGiotinh(),
                entity.getDiachi(),
                entity.getSdt(),
                entity.getMa());
    }
    
    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public ArrayList<NhanVien> selectAll(){
        return selectBySql(SELECT_ALL);
    }
    
    public NhanVien selectById(int key) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID, key);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    protected ArrayList<NhanVien> selectBySql(String sql, Object... args) {
        ArrayList<NhanVien> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql,args);
                while (rs.next()) {
                    NhanVien dg = new NhanVien();
                    dg.setMa(rs.getInt(1));
                    dg.setTen(rs.getString(2));
                    dg.setNam(rs.getString(3));
                    dg.setGiotinh(rs.getBoolean(4));
                    dg.setDiachi(rs.getString(5));
                    dg.setSdt(rs.getString(6));
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
        String SELECT_ID = "select TenNV from NhanVien";
        return selectByName( SELECT_ID);
    }
    
    protected ArrayList<String> selectByName(String sql, Object... args) {
        ArrayList<String> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    String tenLoai = rs.getString("TenNV");
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
    
    public int convertToMaNV(String tenNV) {
    int maNV = 0;
    try {
        String sql = "SELECT MaNV FROM NhanVien WHERE TenNV = ?";
        try (ResultSet resultSet = XJdbc.query(sql, tenNV)) {
            if (resultSet.next()) {
                maNV = resultSet.getInt("MaNV");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return maNV;
}

public String convertToTenNV(int maNV) {
    String tenNV = "";
    try {
        String sql = "SELECT TenNV FROM NhanVien WHERE MaNV = ?";
        try (ResultSet resultSet = XJdbc.query(sql, maNV)) {
            if (resultSet.next()) {
                tenNV = resultSet.getString("TenNV");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return tenNV;
}

public static NhanVien getnhanvien(int ma) {
		try {
			String sql = "select TenNV from NhanVien where MaNV = ?";
			NhanVien nv = new NhanVien();
			Connection conn = XJdbc.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, ma);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				nv.setTen(rs.getString("TenNV"));
			}

			return nv;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
}
