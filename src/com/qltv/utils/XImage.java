package com.qltv.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author duyplus
 */
public class XImage {

    /*
     * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
     */
//    public static Image getAppIcon() {
//        String file = "/com/edusys/icons/fpt.png";
//        return new ImageIcon(XValidate.class.getResource(file)).getImage();
//    }

    /*
     * Sao chép file ảnh chuyên đề vào thư mục logos
     */
    public static void saveIconCD(File src) {
        File file = new File("logos", src.getName());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(file.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

//    /*
//     * Đọc hình ảnh logo chuyên đề
//     */
    public static ImageIcon readIconCD(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(440, 360, Image.SCALE_DEFAULT));
    }
    
    /*
     * Sao chép file ảnh chuyên đề vào thư mục logos
     */
    public static void saveIconNH(File src) {
        File dst = new File("logos/avatars", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Đọc hình ảnh logo chuyên đề
     */
    public static ImageIcon readIconNH(String fileName) {
        File path = new File("logos/avatars", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(440, 360, Image.SCALE_DEFAULT));
    }
    
    
    
    public static void saveIconTG(File src) {
        File dst = new File("logos/tacgia", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ImageIcon readIconTG(String fileName) {
        File path = new File("logos/tacgia", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(300, 330, Image.SCALE_DEFAULT));
    }
    
    public static void saveIconNXB(File src) {
        File dst = new File("logos/nxb", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ImageIcon readIconNXB(String fileName) {
        File path = new File("logos/nxb", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(220, 310, Image.SCALE_DEFAULT));
    }
}
