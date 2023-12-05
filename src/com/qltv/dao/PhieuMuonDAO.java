/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.ChiTietPhieuMuon;
import com.qltv.entity.PhieuMuon;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class PhieuMuonDAO {
    public ResultSet rs;
    public String SELECT_ALL_SQL = "select * from PhieuMuon";
    public String SELECT_BY_DG = "select TenDocGia from DocGia where MaDocGia = ?";
    public String SELECT_BY_NV = "select TenNV from NhanVien where MaNV = ?";
    public String INSERT_SQL = "insert into PhieuMuon (MaNV,MaDocGia,NgayMuon,TinhTrang) values (?,?,?,?)";
    public String UPDATE_SQL = "update PhieuMuon set MaNV = ?, MaDocGia = ?, NgayMuon = ?, TinhTrang = ? where MaPM = ?";
    public String DELETE_SQL = "delete from PhieuMuon where MaPN = ?";
    public String SELECT_BY_NV_ID = "select MaNV from NhanVien where TenNV like '?'";
    public String SELECT_BY_DG_ID = "select MaDG from DocGia where TenDocGia like '?'";
    
	public void insert(PhieuMuon entity){
        XJdbc.update(INSERT_SQL, 
                entity.getMaNV(),
                entity.getMaDG(),
                entity.getNgayMuon(),
                entity.isTrangThai());
    }
    
    public void update(PhieuMuon entity){
        XJdbc.update(UPDATE_SQL, 
                entity.getMaNV(),
                entity.getMaDG(),
                entity.getNgayMuon(),
                entity.isTrangThai(),
                entity.getMaPM());
    }
    
    public void delete(int id){
        XJdbc.update(DELETE_SQL, id);
    }
    
    public ArrayList<PhieuMuon> selectAll(){
        return selectBySql(SELECT_ALL_SQL);
    }
    
    public ArrayList<String> selectDG(int id){
        return selectByDG(SELECT_BY_DG, id);
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
    
//    public int selectNCC1(String id){
//         List<Integer> list = selectByNCC1(SELECT_BY_NCC_ID, id);
//        if(!list.isEmpty()){
//            return list.get(0);
//        }
//        else{
//            return 0;
//        }
//    }
    
    protected ArrayList<String> selectByDG(String sql, Object...args){
        ArrayList<String> list = new ArrayList<>();
        try{
        try{
            rs = XJdbc.query(sql, args);
            while(rs.next()){
                String pn = rs.getString("TenDocGia");
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
    
    protected ArrayList<Integer> selectByDG1(String sql, Object... args) {
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

    public List<PhieuMuon> getPhieuNhapByNCC(int selectedNCC) {

    // Sử dụng PreparedStatement để tránh SQL Injection
    String sql = "SELECT * FROM PhieuMuon WHERE MaDocGia = ?";
    return selectBySql(sql, selectedNCC);
    }
    
    protected ArrayList<PhieuMuon> selectBySql(String sql, Object...args){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        try{
        try{
            rs = XJdbc.query(sql, args);
            while(rs.next()){
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPM(rs.getInt("MaPhieuMuon"));
                pm.setMaNV(rs.getInt("MaNV"));
                pm.setMaDG(rs.getInt("MaDocGia"));
                pm.setNgayMuon(rs.getString("NgayMuon"));
                pm.setTrangThai(rs.getBoolean("TrangThai"));
                
                list.add(pm);
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

