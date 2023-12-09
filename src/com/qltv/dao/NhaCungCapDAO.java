/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.LoaiSach;
import com.qltv.entity.NhaCungCap;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhaCungCapDAO {
    
    public static PreparedStatement pr;
    public static ResultSet rs;
    public static String INSERT_SQL = "insert into NhaCungCap (TenNCC) values (?)";
    public static String UPDATE_SQL = "update NhaCungCap set TenNCC = ? where MaNCC = ?";
    public static String DELETE_SQL = "delete from NhaCungCap where MaNCC = ?";
    public static String SELECT_ALL_SQL = "select * from NhaCungCap";
    
    public void insert(NhaCungCap entity){
        XJdbc.update(INSERT_SQL, 
                    entity.getTenNCC());
    }
    
    public void update(NhaCungCap entity){
        XJdbc.update(UPDATE_SQL, 
                    entity.getTenNCC(),
                    entity.getMaNCC());
    }
    
    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public List<NhaCungCap> selectAll(){
        return selectById(SELECT_ALL_SQL);
    }
    
    public List<NhaCungCap> selectById(String sql, Object...args){
        ArrayList<NhaCungCap> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    NhaCungCap ncc = new NhaCungCap();
                    ncc.setMaNCC(rs.getInt("MaNCC"));
                    ncc.setTenNCC(rs.getString("TenNCC"));
                    list.add(ncc);
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
    
    public ArrayList<String> selectNXB(){
        String sql = "SELECT TenNCC FROM NhaCungCap";
        ArrayList<String> nhanVienList = new ArrayList<>();
        try  {
            rs = XJdbc.query(sql);
        
    // ArrayList để lưu giữ cặp mã nhân viên và tên nhân viên
    
    while (rs.next()) {
        String tenNV = rs.getString("TenNCC");
        
        // Thêm cặp mã nhân viên và tên nhân viên vào ArrayList
        nhanVienList.add(tenNV);
    }
    }catch (SQLException ex) {
    ex.printStackTrace();
}
        return nhanVienList;
    }
    
//    public Integer selectNCC(String id){
//        String SELECT_BY_NCC_ID = "select MaNCC from NhaCungCap where TenNCC like '?'";
//         List<Integer> list = selectByNCC1(SELECT_BY_NCC_ID, id);
//        if(!list.isEmpty()){
//            return list.get(0);
//        }
//        else{
//            return 0;
//        }
//    }
//    
//    protected ArrayList<Integer> selectByNCC1(String sql, Object... args) {
//    ArrayList<Integer> list = new ArrayList<>();
//    try {
//        try (ResultSet rs = XJdbc.query(sql, args)) {
//            while (rs.next()) {
//                int pn = rs.getInt(1);
//                list.add(pn);
//            }
//        }
//    } catch (SQLException ex) {
//        // Xử lý ngoại lệ, bạn có thể ném một ngoại lệ hoặc ghi log
//        // Nếu ném ngoại lệ, đảm bảo rằng ngoại lệ này được xử lý tại nơi gọi hàm
//        ex.printStackTrace(); // Hoặc sử dụng logging framework như SLF4J để ghi log
//    }
//    return list;
//}
    public int convertToMaNV(String tenNCC) {
    int maNCC = 0;
    try {
        String sql = "SELECT MaNCC FROM NhaCungCap WHERE TenNCC = ?";
        try (ResultSet resultSet = XJdbc.query(sql, tenNCC)) {
            if (resultSet.next()) {
                maNCC = resultSet.getInt("MaNCC");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return maNCC;
}
public String convertToTenNCC(int maNCC) {
    String tenNCC = "";
    try {
        String sql = "SELECT TenNCC FROM NhaCungCap WHERE MaNCC = ?";
        try (ResultSet resultSet = XJdbc.query(sql, maNCC)) {
            if (resultSet.next()) {
                tenNCC = resultSet.getString("TenNCC");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return tenNCC;
}

    public static NhaCungCap getNhacungcap(int ma) {
		try {
			NhaCungCap ncc = new NhaCungCap();
			String sql = "select TenNCC from NhaCungCap where MaNCC = ?"; 
			Connection conn = XJdbc.getConnection(); 
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, ma);
			ResultSet rs = pstm.executeQuery(); 
			if(rs.next()) {
				ncc.setTenNCC(rs.getString("TenNCC"));
			}
			System.out.println(ncc);
			return ncc; 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
    }

