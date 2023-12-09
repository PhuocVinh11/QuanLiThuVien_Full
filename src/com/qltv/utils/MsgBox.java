/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MsgBox {
    //Hiển thị thông báo cho người dùng
    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "",
                JOptionPane.INFORMATION_MESSAGE);
    }

     //Hiển thị thông báo và yêu cầu người dùng xác nhận
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    //Hiển thị thông báo yêu cầu nhập dữ liệu
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, "",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
    Hiển thị thông báo và yêu cầu người dùng xác nhận
    @param parent là cửa sổ chứa thông báo
    @param message là thông báo
    */
    public static void showErrorDialog(Component parent, String content, String title){
        JOptionPane.showMessageDialog(parent, content, title, JOptionPane.ERROR_MESSAGE);
    }
}
