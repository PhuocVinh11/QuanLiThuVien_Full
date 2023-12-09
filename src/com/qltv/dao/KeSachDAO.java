/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.dao;

import static com.qltv.dao.KeSachDAO.DELETE_SQL;
import static com.qltv.dao.KeSachDAO.INSERT_SQL;
import static com.qltv.dao.KeSachDAO.UPDATE_SQL;
import com.qltv.entity.KeSach;
import com.qltv.entity.LoaiSach;
import com.qltv.entity.KeSach;
import com.qltv.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KeSachDAO {
    
    public static ResultSet  rs;
    public static String INSERT_SQL = "insert into KeSach (ViTri) values (?)";
    public static String UPDATE_SQL = "update KeSach set ViTri = ? where MaKe = ?";
    public static String DELETE_SQL = "delete from KeSach where MaKe = ?";
    public static String SELECT_ALL_SQL = "select * from KeSach";
    
    public void insert(KeSach entity){
        XJdbc.update(INSERT_SQL, 
                    entity.getVitri());
    }
    
    public void update(KeSach entity){
        XJdbc.update(UPDATE_SQL, 
                    entity.getVitri(),
                    entity.getMaKe());
    }
    
    public void delete(int key) {
        XJdbc.update(DELETE_SQL, key);
    }
    
    public List<KeSach> selectAll(){
        return selectBySql(SELECT_ALL_SQL);
    }
    
    protected ArrayList<KeSach> selectBySql(String sql, Object... args) {
        ArrayList<KeSach> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    KeSach ks = new KeSach();
                    ks.setMaKe(rs.getInt(1));
                    ks.setVitri(rs.getString(2));
                    list.add(ks);
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
        String SELECT_ID = "select ViTri from KeSach";
        return selectByName( SELECT_ID);
    }
    
    protected ArrayList<String> selectByName(String sql, Object... args) {
        ArrayList<String> list = new ArrayList<>();
        try {
            try {
                rs = XJdbc.query(sql);
                while (rs.next()) {
                    String tenLoai = rs.getString("ViTri");
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
    
    public String convertToViTri(int maKe) {
    String viTri = "";
    try {
        String sql = "SELECT ViTri FROM KeSach WHERE MaKe = ?";
        try (ResultSet resultSet = XJdbc.query(sql, maKe)) {
            if (resultSet.next()) {
                viTri = resultSet.getString("ViTri");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return viTri;
}
    public int convertToMaKe(String tenKS) {
    int maKS = 0;
    try {
        String sql = "SELECT MaKe FROM KeSach WHERE ViTri = ?";
        try (ResultSet resultSet = XJdbc.query(sql, tenKS)) {
            if (resultSet.next()) {
                maKS = resultSet.getInt("MaKe");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return maKS;
}
}
