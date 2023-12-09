/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import com.qltv.entity.TaiKhoan;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TaiKhoanDAO {
    
    public static String INSERT_SQL = "insert into TaiKhoan (Username ,Password , Quyen, MaNV) values (?,?,?,?)";
    public static String UPDATE_SQL = "update TaiKhoan set Username = ?, Password = ?, Quyen = ?, MaNV = ? where MaTK = ? ";
    public static String DELETE_SQL = "delete from TaiKhoan inner join NhanVien on TaiKhoan.MaNV = NhanVien.MaNV where NhanVien.MaNV = ?";
    public static String DELETE_ID_SQL = "delete from TaiKhoan where MaTK = ?";
    public static String SELECT_ALL = "select * from TaiKhoan";
    public static String SELECT_BY_ID = "select * from TaiKhoan where Username = ?";
    public static String SELECT_BY_ID_TK = "select * from TaiKhoan where MaTK = ?";
    
    public void insert(TaiKhoan entity) {
        XJdbc.update(INSERT_SQL,
                entity.getUser(),
                entity.getPass(),
                entity.isQuyen(),
                entity.getManv());
    }

    public void update(TaiKhoan entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getUser(),
                entity.getPass(),
                entity.isQuyen(),
                entity.getManv(),
                entity.getMatk());
    }
    
    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public void deleteTK(int key) {
        XJdbc.update(DELETE_ID_SQL, key);
    }
    
    public ArrayList<TaiKhoan> SelectAll(){
        return SelectById(SELECT_ALL);
    }
    
    public TaiKhoan selectByIds(String id) {
        List<TaiKhoan> list = this.SelectById(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public TaiKhoan selectByIdTK(int id) {
        List<TaiKhoan> list = this.SelectById(SELECT_BY_ID_TK, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    protected ArrayList<TaiKhoan> SelectById(String sql, Object...args){
        ArrayList<TaiKhoan> list = new ArrayList<>();
        ResultSet rs = XJdbc.query(sql, args);
        try {
            try {
                
                while (rs.next()) {
                    TaiKhoan s = new TaiKhoan();
                    s.setMatk(rs.getInt("MaTK"));
                    s.setUser(rs.getString("Username"));
                    s.setPass(rs.getString("Password"));
                    s.setQuyen(rs.getBoolean("Quyen"));
                    s.setManv(rs.getInt("MaNV"));
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
    
    public void qmk(TaiKhoan model) {
        String sql="UPDATE TaiKhoan SET Passwword=? WHERE Username=?";
        XJdbc.update(sql, 
        model.getPass(), 
        model.getUser());  
    }
}
