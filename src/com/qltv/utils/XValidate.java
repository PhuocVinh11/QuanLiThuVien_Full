/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.utils;

import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.TaiKhoanDAO;
import com.qltv.entity.TaiKhoan;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class XValidate {
    
    static NhanVienDAO nvdao = new NhanVienDAO();
    static TaiKhoanDAO tkdao = new TaiKhoanDAO();
    TaiKhoan dstk = new TaiKhoan();
    
    // --- ĐĂNG NHẬP ----
    public static boolean checkNullText(JTextField txt) {
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Không được để trống!");
            return false;
        }
    }

    /*
     * Kiểm tra mật khẩu rỗng
     */
    public static boolean checkNullPass(JTextField txt) {
        if (txt.getText().length() > 0) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Không được để trống.");
            return false;
        }
    }
    
    /*
     * Kiểm thử tên Nhân Viên, Người Học
     */
    
    /*
    * Kiểm thử số điện thoại
    */
    
//    public static boolean isNumber(JTextField str){
//        try {
//            double number = Double.parseDouble(str.getText());
//                return true;
//                
//        } catch (Exception e) {
//            return false;
//        }
//      
    /*
        -----------SÁCH---------
        */ 
    public static boolean isNumber(JTextField text){
        try{
            int n = Integer.parseInt(text.getText());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean checkYear(JTextField text){
        String rgx = "[0-9]{4}";
        if (text.getText().matches(rgx)) {
            return true;
        } else {
            MsgBox.alert(text.getRootPane(), "Năm không đúng định dạng (****)");
            return false;
        }
    }
    
    public static boolean checkNumber(JTextField txt) {
        try {
            int n = Integer.parseInt(txt.getText());
            if (n > 0) {
                return true;
            } else {
                MsgBox.alert(txt.getRootPane(), txt.getToolTipText() + " phải lớn hơn 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            MsgBox.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số nguyên.");
            return false;
        }
    }
    
    /*
    ----------ĐỘC GIẢ------------
    */
    public static boolean checkName(JTextField txt) {
        String id = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,100}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Tên phải là tên có dấu hoặc không và từ 3 kí tự trở lên.");
            return false;
        }
    }
    
    public static boolean checkSDT(JTextField txt) {
        String id = txt.getText();
        String rgx = "(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|084|085|081|082|092|056|058|099|059)[0-9]{7}";
        if (id.matches(rgx)) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "SDT phải gồm 10 số\nĐúng đầu số của các nhà mạng");
            return false;
        }
    }
    
    public static boolean check20Ngay(JDateChooser txt, JDateChooser txt2) {
        Date date = XDate.toDate(txt.getDateFormatString());
        Date date2 = XDate.toDate(txt2.getDateFormatString());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);
        c2.setTime(date2);
        long a = (c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
        if (a >= 20) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Ngày kết thúc phải cách ngày bắt đầu 20 ngày.");
            return false;
        }
    }
    
    public static boolean checkNullDate(JDateChooser date) {
        if (date.getDateFormatString().trim().length() > 0) {
            return true;
        } else {
            MsgBox.alert(date.getRootPane(), "Không được để trống!");
            return false;
        }
    }
    
    /*
    ---------TÁC GIẢ-----------
    */
    
    
    /*
    ------NHÂN VIÊN----------
    */
    public static boolean checkTrungNV(JTextField txt) {
        if (nvdao.selectById(Integer.parseInt(txt.getText())) == null) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Mã chuyên đề '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }
    
    public static boolean checkPass(JTextField txt) {
        if (txt.getText().trim().length() > 3) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Password phải từ 4 - 20 kí tự!");
            return false;
        }
    }
    
    public static boolean checkTrungTK(JTextField txt) {
        if (tkdao.selectByIdTK(Integer.parseInt(txt.getText())) == null) {
            return true;
        } else {
            MsgBox.alert(txt.getRootPane(), "Mã tài khoản '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }
}
    

