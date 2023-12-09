/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.TheThuVien;
import com.qltv.utils.XJdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheThuVienDAO{

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO TheThuVien (NgayBatDau, NgayKetThuc,GhiChu,MaDocGia) VALUES (?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE TheThuVien SET NgayBatDau=?,NgayKetThuc=?,GhiChu=?,MaDocGia=? WHERE MaTheThuVien=?";
    public static String DELETE_SQL = "DELETE FROM TheThuVien WHERE MaTheThuVien=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM TheThuVien";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM TheThuVien WHERE MaDocGia=?";
    public static String SELECT_BY_ID_TTV = "SELECT * FROM TheThuVien WHERE MaTheThuVien=?";

    public void insert(TheThuVien entity) {
        XJdbc.update(INSERT_SQL,
                entity.getNgayBatDau(),
                entity.getNgayKetThuc(),
                entity.getGhiChu(),
                entity.getMadocgia());
    }

    public void update(TheThuVien entity) {
        XJdbc.update(UPDATE_SQL,
                 entity.getNgayBatDau(),
                entity.getNgayKetThuc(),
                entity.getGhiChu(),
                entity.getMadocgia(),
                entity.getMaTheThuVien());
    }

    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public List<TheThuVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
//    
//    @Override
public List<TheThuVien> selectByIds(int id) {
        List<TheThuVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public TheThuVien selectByIdTTV(int id) {
        List<TheThuVien> list = selectBySql(SELECT_BY_ID_TTV, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
        
    }

    protected List<TheThuVien> selectBySql(String sql, Object... args) {
        List<TheThuVien> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    TheThuVien entity = new TheThuVien();
                    entity.setMaTheThuVien(rs.getInt(1));
                    entity.setNgayBatDau(rs.getDate(2));
                    entity.setNgayKetThuc(rs.getDate(3));
                    entity.setGhiChu(rs.getString(4));
                    entity.setMadocgia(rs.getInt(5));
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
    
    
//    public static ArrayList<TheThuVien> getdanhsachTheThuVien() {
//		try {
//			String sql = "";
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			ResultSet rs = stmt.executeQuery();
//			ArrayList<TheThuVien> dsl = new ArrayList<TheThuVien>();
//			while (rs.next()) {
//				TheThuVien tv = new TheThuVien();
//				tv.setMaTheThuVien(rs.getInt("MaTheThuVien"));
//				tv.setNgayBatDau(rs.getDate("NgayBatDau"));
//				tv.setNgayKetThuc(rs.getDate("NgayKetThuc"));
//				tv.setGhiChu(rs.getString("GhiChu"));
//                                tv.setMadocgia(rs.getInt("MaDocGia"));
//				dsl.add(tv);
//			}
//
//			return dsl;
//		} catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//
//	}
    
    

        
    
//
//	public ArrayList<TheThuVien> getdanhsachthongtinTheThuVien() {
//		try {
//			String sql = "SELECT TheThuVien.MaTheThuVien,TheThuVien.NgayBatDau,TheThuVien.NgayKetThuc,TheThuVien.GhiChu, DocGia.MaDocGia FROM TheThuVien INNER JOIN DocGia ON DocGia.MaDocGia = TheThuVien.MaTheThuVien";
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			ResultSet rs = stmt.executeQuery();
//			ArrayList<TheThuVien> dsl = new ArrayList<TheThuVien>();
//			while (rs.next()) {
//				TheThuVien tv = new TheThuVien();
//				tv.setMaTheThuVien(rs.getInt(1));
//				tv.setNgayBatDau(rs.getString(2));
//				tv.setNgayKetThuc(rs.getString(3));
//                                tv.setGhiChu(rs.getString(4));
//				tv.setMadocgia(rs.getInt(5));
//
//				dsl.add(tv);
//			}
//
//			return dsl;
//		} catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//
//	}
//
//	// @SuppressWarnings("null")
//	public static int themthetv(TheThuVien ke) {
//		int i = -1;
//		String sql = "insert into TheThuVien (NgayBatDau ,NgayKetThuc,GhiChu,MaTheThuVien) values(?,?,?,?)";
//
//		try {
//
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setString(1, ke.getNgayBatDau());
//			pstm.setString(2, ke.getNgayKetThuc());
//			pstm.setString(3, ke.getGhiChu());
//			pstm.setInt(4, ke.getMaTheThuVien());
//
//			i = pstm.executeUpdate();
//			conn.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// dreturn null;
//		}
//
//		return i;
//	}
//
//	public static int suanTheThuVien(TheThuVien ke) {
//		int i = -1;
//		String sql = "update TheThuVien set NgayBatDau = ? , NgayKetThuc = ?, GhiChu = ?  where MaTheThuVien = ?";
//
//		try {
//
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setString(1, ke.getNgayBatDau());
//			pstm.setString(2, ke.getNgayKetThuc());
//			pstm.setString(3, ke.getGhiChu());
//			pstm.setInt(4, ke.getMaTheThuVien());
//
//			// System.out.println(ke.getViTri());
//			i = pstm.executeUpdate();
//			conn.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// dreturn null;
//		}
//
//		return i;
//	}
//
//	public static int xoaTheThuVien(TheThuVien ke) {
//		int i = -1;
//		String sql = "delete from TheThuVien where MaTheThuVien = ?";
//
//		try {
//
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setInt(1, ke.getMaTheThuVien());
//			// System.out.println(ke.getViTri());
//			i = pstm.executeUpdate();
//			conn.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// dreturn null;
//		}
//
//		return i;
//	}
//
//	public TheThuVien timTheThuVien(int ma) {
//
//		String sql = "select * from TheThuVien where MaTheThuVien = ?";
//		try {
//			TheThuVien tv = new TheThuVien();
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, ma);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				tv.setMaTheThuVien(rs.getInt(1));
//				tv.setNgayBatDau(rs.getString(2));
//				tv.setNgayKetThuc(rs.getString(3));
//				tv.setGhiChu(rs.getString(4));
//			}
//			return tv;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public static TheThuVien thongtinthe(int ma) {
//
//		String sql = "SELECT TheThuVien.MaTheThuVien,TheThuVien.NgayBatDau,TheThuVien.NgayKetThuc, DocGia.MaDocGia FROM TheThuVien INNER JOIN DocGia ON DocGia.MaDocGia = TheThuVien.MaTheThuVien WHERE DocGia.MaDocGia = ?";
//		try {
//			TheThuVien tv = new TheThuVien();
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, ma);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				tv.setMaTheThuVien(rs.getInt(1));
//				tv.setNgayBatDau(rs.getString(2));
//				tv.setNgayKetThuc(rs.getString(3));
//				tv.setMadocgia(rs.getInt("MaDocGia"));
//			}
//			return tv;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public static boolean check(int mathe) {
//		try {
//			String sql = "SELECT TheThuVien.MaTheThuVien,DocGia.MaDocGia FROM TheThuVien INNER JOIN DocGia ON TheThuVien.MaTheThuVien = ?";
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, mathe);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				return true;
//			}
//		} catch (Exception e) {
//			return false;
//		}
//		return false;
//	}
//
//	public static int giahanthe(int mathe, String ngay) {
//		int i = -1;
//		String sql = "update TheThuVien set NgayKetThuc = ? where MaTheThuVien = ?";
//
//		try {
//
//			Connection conn = XJdbc.getConnection();
//			PreparedStatement pstm = conn.prepareStatement(sql);
//			pstm.setString(1, ngay);
//			pstm.setInt(2, mathe);
//			
//			// System.out.println(ke.getViTri());
//			i = pstm.executeUpdate();
//			conn.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//
//		return i;
//	}
}
