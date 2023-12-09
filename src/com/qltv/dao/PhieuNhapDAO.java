/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.NhaCungCap;
import com.qltv.entity.NhanVien;
import com.qltv.entity.PhieuNhap;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class PhieuNhapDAO {
    public ResultSet rs;
    public String SELECT_ALL_SQL = "select * from PhieuNhap";
    public String SELECT_BY_NCC = "select TenNCC from NhaCungCap where MaNCC = ?";
    public String SELECT_BY_NV = "select TenNV from NhanVien where MaNV = ?";
    public String INSERT_SQL = "insert into PhieuNhap (MaNCC,MaNV,NgayNhap,TongTien) values (?,?,?,?)";
    public String UPDATE_SQL = "update PhieuNhap set MaNCC = ?, MaNV = ?, NgayNhap = ?, TongTien = ? where MaPhieuNhap = ?";
    public String DELETE_SQL = "delete from PhieuNhap where MaPhieuNhap = ?";
    public String SELECT_BY_NV_ID = "select MaNV from NhanVien where TenNV like '?'";
    public String SELECT_BY_NCC_ID = "select MaNCC from NhaCungCap where TenNCC like '?'";
    
    public void insert(PhieuNhap entity){
        XJdbc.update(INSERT_SQL, 
                entity.getMancc(),
                entity.getManv(),
                entity.getNgaynhap(),
                entity.getTong());
    }
    
    public void update(PhieuNhap entity){
        XJdbc.update(UPDATE_SQL, 
                entity.getMancc(),
                entity.getManv(),
                entity.getNgaynhap(),
                entity.getTong(),
                entity.getMa());
    }
    
    public void delete(int id){
        XJdbc.update(DELETE_SQL, id);
    }
    
    public ArrayList<PhieuNhap> selectAll(){
        return selectBySql(SELECT_ALL_SQL);
    }
    
    public ArrayList<String> selectNCC(int id){
        return selectByNCC(SELECT_BY_NCC, id);
    }
    
    public List<String> selectNXB(){
        String sql = "SELECT TenNV FROM NhanVien";
        ArrayList<String> nhanVienList = new ArrayList<>();
        try  {
            rs = XJdbc.query(sql);
        
    // ArrayList để lưu giữ cặp mã nhân viên và tên nhân viên
    
    while (rs.next()) {
//        int maNV = rs.getInt("MaNV");
        String tenNV = rs.getString("TenNV");
        
        // Thêm cặp mã nhân viên và tên nhân viên vào ArrayList
        nhanVienList.add(tenNV);
    }
    }catch (SQLException ex) {
    ex.printStackTrace();
}
        return nhanVienList;
    }
    
    public int selectNV(String id){
         List<Integer> list = selectByNV1(SELECT_BY_NV_ID, id);
        if(!list.isEmpty()){
            return list.get(0);
        }
        else{
            return 0;
        }
    }
    
    public List<PhieuNhap> getPhieuNhapBy(int selectedNCC) {

    // Sử dụng PreparedStatement để tránh SQL Injection
    String sql = "SELECT * FROM PhieuNhap WHERE MaNCC = ?";
    return selectBySql(sql, selectedNCC);
    }
    public static PhieuNhap getphieunhap(int ma) {
		try {
			String sql = "select * from phieunhap where MaPhieuNhap =?" ;
			Connection conn = XJdbc.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,ma);
			ResultSet rs = pstm.executeQuery();
			PhieuNhap pn = new PhieuNhap();
			if(rs.next()) {
				pn.setManv(rs.getInt("MaNV"));
				pn.setMa(rs.getInt("MaPhieuNhap"));
				pn.setMancc(rs.getInt("MaNCC"));
				pn.setNgaynhap(rs.getDate("NgayNhap"));
				
			}
			return pn;
		} catch (Exception e) {
			return null;
		}
	}
//    public int selectNCC1(String id){
//         List<Integer> list = selectByNCC1(SELECT_BY_NCC_ID, id);
//        if(!list.isEmpty()){
//            return list.get(0);
//        }
//        else{
//            return 0;
//        }
//    }
    
    
    
    protected ArrayList<String> selectByNCC(String sql, Object...args){
        ArrayList<String> list = new ArrayList<>();
        try{
        try{
            rs = XJdbc.query(sql, args);
            while(rs.next()){
                String pn = rs.getString("TenNCC");
                list.add(pn);
            }
        }finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    
    protected ArrayList<Integer> selectByNCC1(String sql, Object... args) {
    ArrayList<Integer> list = new ArrayList<>();
    try {
        try (ResultSet rs = XJdbc.query(sql, args)) {
            while (rs.next()) {
                int pn = rs.getInt(1);
                list.add(pn);
            }
        }
    } catch (SQLException ex) {
        // Xử lý ngoại lệ, bạn có thể ném một ngoại lệ hoặc ghi log
        // Nếu ném ngoại lệ, đảm bảo rằng ngoại lệ này được xử lý tại nơi gọi hàm
        ex.printStackTrace(); // Hoặc sử dụng logging framework như SLF4J để ghi log
    }
    return list;
}

    
    protected ArrayList<String> selectByNV(String sql, Object...args){
        ArrayList<String> list = new ArrayList<>();
        try{
        try{
            rs = XJdbc.query(sql, args);
            while(rs.next()){
                String pn = rs.getString("TenNV");
                list.add(pn);
            }
        }finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    
    protected ArrayList<Integer> selectByNV1(String sql, Object... args) {
    ArrayList<Integer> list = new ArrayList<>();
    try {
        try (ResultSet rs = XJdbc.query(sql, args)) {
            while (rs.next()) {
                int pn = rs.getInt(1);
                list.add(pn);
            }
        }
    } catch (SQLException ex) {
        System.out.println("Lỗi ở đây!");
        // Xử lý ngoại lệ, bạn có thể ném một ngoại lệ hoặc ghi log
        // Nếu ném ngoại lệ, đảm bảo rằng ngoại lệ này được xử lý tại nơi gọi hàm
        ex.printStackTrace(); // Hoặc sử dụng logging framework như SLF4J để ghi log
    }
    return list;
}

    
    protected ArrayList<PhieuNhap> selectBySql(String sql, Object...args){
        ArrayList<PhieuNhap> list = new ArrayList<>();
        try{
        try{
            rs = XJdbc.query(sql, args);
            while(rs.next()){
                PhieuNhap pn = new PhieuNhap();
                pn.setMa(rs.getInt(1));
                pn.setMancc(rs.getInt(2));
                pn.setManv(rs.getInt(3));
                pn.setNgaynhap(rs.getDate(4));
                pn.setTong(rs.getInt(5));
                
                list.add(pn);
            }
        }finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
