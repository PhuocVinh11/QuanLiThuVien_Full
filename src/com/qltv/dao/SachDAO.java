/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.LoaiSach;
import com.qltv.entity.NhaXuatBan;
import com.qltv.entity.Sach;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class SachDAO {
    
    public static ResultSet rs;
    public static String INSERT_SQL = "insert into Sach (TenSach,MaLoai,MaNXB,MaTacGia,NamXB,SoLuong,MaKe,HinhAnh,GhiChu values (?,?,?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "update Sach set TenSach = ?, MaLoai = ?, MaNXB = ?, MaTacGia = ?, NamXB = ?, SoLuong = ?, MaKe = ?, HinhAnh = ?, GhiChu = ? where MaSach = ?";
    public static String DELETE_SQL = "delete from Sach where MaSach = ?";
    public static String SELECT_BY_ID = "select * from Sach where MaSach = ?";
    public static String SELECT_BY_LOAI = "select TenLoai from Loai where MaLoai = ?";
    public static String SELECT_ALL_SQL = "select * from Sach ";
    
    public void insert(Sach entity){
        XJdbc.update(INSERT_SQL, 
                        entity.getTen(),
                        entity.getMaLoai(),
                        entity.getMaNXB(),
                        entity.getMaTG(),
                        entity.getNam(),
                        entity.getSoluong(),
                        entity.getMaKe(),
                        entity.getHinh(),
                        entity.getGhichu());
    }
    
    public void update(Sach entity){
        XJdbc.update(UPDATE_SQL, 
                        entity.getTen(),
                        entity.getMaLoai(),
                        entity.getMaNXB(),
                        entity.getMaTG(),
                        entity.getNam(),
                        entity.getSoluong(),
                        entity.getMaKe(),
                        entity.getHinh(),
                        entity.getGhichu(),
                        entity.getMa());
    }
    
    public void delete(int id){
        XJdbc.update(DELETE_SQL, id);
    }
    
    	public static int getsoluongsach(int masach) {
		try {
			int soluong = 0;
			Connection conn = XJdbc.getConnection();
			String sql = "select SoLuong from Sach where MaSach = ? ";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, masach);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				soluong = rs.getInt("SoLuong");
			}
			return soluong;
		} catch (Exception e) {
			return -1;
		}

	}
    
    public List<Sach> selectAll(){
        return selectBySql(SELECT_ALL_SQL);
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
                    String nxb = rs.getString("TenNXB");
                    list.add(nxb);
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
    
    public Sach selectById(String key) {
        List<Sach> list = selectBySql(SELECT_BY_ID, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    
    
    protected ArrayList<Sach> selectBySql(String sql, Object... args) {
        ArrayList<Sach> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    Sach s = new Sach();
                    s.setMa(rs.getString(1));
                    s.setTen(rs.getString(2));
                    s.setMaLoai(rs.getString(3));
                    s.setMaNXB(rs.getString(4));
                    s.setMaTG(rs.getString(5));
                    s.setNam(rs.getInt(6));
                    s.setSoluong(rs.getInt(7));
                    s.setMaKe(rs.getString(8));
//                    s.setHinh(rs.getString(9));
                    s.setGhichu(rs.getString(9));
                list.add(s);
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
    
    
}
