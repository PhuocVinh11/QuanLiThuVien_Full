/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltv.ui;

import com.qltv.dao.TacGiaDAO;
import com.qltv.entity.TacGia;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XImage;
import com.qltv.utils.XValidate;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author RAVEN
 */
public class QLTacGia extends javax.swing.JPanel {

    TacGiaDAO tgdao = new TacGiaDAO();
    int row = -1;
    JFileChooser FileChooser = new JFileChooser(System.getProperty("user.dir") + "\\src\\main\\logos");

    /**
     * Creates new form Form_1
     */
    public QLTacGia() {
        initComponents();
        this.fillTable();
        viewTable();
        lblTen.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVu.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
    }

    void viewTable() {
        tblTacGia.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblTacGia.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblTacGia.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblTacGia.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblTacGia.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    /*
     ĐỔ DỮ LIỆU LÊN BẢNG
     */
    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblTacGia.getModel();
        model.setRowCount(0);
        try {
            List<TacGia> list = tgdao.SelectAll();
            for (TacGia dg : list) {
                Object[] row = {
                    dg.getMa(),
                    dg.getTen(),
                    dg.getNamsinh(),
                    dg.getQuequan(),
                    dg.getHinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sách!");
            e.printStackTrace();
        }

    }

    void edit() {
        try {
            int selectedRow = tblTacGia.getSelectedRow();
            if (selectedRow >= 0) {
                int masp = (int) tblTacGia.getValueAt(selectedRow, 0);
                TacGia model = tgdao.selectByIds(masp);
                if (model != null) {
                    this.setForm(model);
                }
            } else {
                MsgBox.alert(this, "Vui lòng chọn một hàng để chỉnh sửa.");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi!");
            e.printStackTrace();
        }
    }

    /*
    HÀM ĐỂ ĐƯA DỮ LIỆU TRÊN FORM
     */

    private void setForm(TacGia cd) {
        txtMaTG.setText(cd.getMa()+"");
        txtTen.setText(cd.getTen());
        txtNamSinh.setText(String.valueOf(cd.getNamsinh()));
        txtQueQuan.setText(cd.getQuequan());
        lblAnh.setIcon(XImage.readIconTG("NoImage.png"));
        if (cd.getHinh() != null) {
            lblAnh.setToolTipText(cd.getHinh());
            lblAnh.setIcon(XImage.readIconTG(cd.getHinh()));
        }
    }

    private TacGia getForm() {
        TacGia s = new TacGia();
        s.setMa(Integer.parseInt(txtMaTG.getText()));
        s.setTen(txtTen.getText());
        s.setNamsinh(Integer.parseInt(txtNamSinh.getText()));
        s.setHinh(lblAnh.getToolTipText());
        s.setQuequan(txtQueQuan.getText());
        return s;
    }
    /*
    CHỌN ẢNH
    */
private void selectIcon() {
        JFileChooser fc = new JFileChooser("logos");
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            XImage.saveIconTG(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIconTG(file.getName()); // đọc hình từ logos
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName()); // giữ tên hình trong tooltip
        }
    }

    
    void insert() {
        //lấy thông tin trên form để
        //thêm sản phẩm vào CSDL
        TacGia model = getForm();
        try {
            tgdao.insert(model);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm mới sản phẩm thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới sản phẩm thất bại !");
            e.printStackTrace();
        }
    }

    void updateSP() {
        //lấy thông tin trên form để
        //cập nhật nhanVien theo maKH
        TacGia model = getForm();
        try {
            tgdao.update(model);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Cập khách sản phẩm thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật sản phẩm thất bại !");
            e.printStackTrace();
        }
    }
    
    void deleteSP() {
        //lấy sản phẩm trên form, xóa sản phẩm theo maSP
        //xóa trắng form
        int maSP = Integer.parseInt(txtMaTG.getText());
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sản phẩm này ?")) {
            try {
                tgdao.delete(maSP);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Xóa sản phẩm thành công !");
            } catch (Exception e) {
                MsgBox.alert(this, "Không thể xóa sản phẩm !");
                return;
            }
        }
    }

    void clearForm() { //xóa trắng form
        this.setForm(new TacGia());
        lblAnh.setIcon(null);
        this.row = -1;
//        this.updateStatus();
        txtMaTG.setBackground(Color.white);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblTacGia = new javax.swing.JTable();
        lblTacGia = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNamSinh = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtQueQuan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaTG = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblChucVu = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        tblTacGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblTacGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        tblTacGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã tác giả", "Tên tác giả", "Năm sinh", "Quê quán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTacGia.setGridColor(new java.awt.Color(255, 255, 255));
        tblTacGia.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblTacGia.setSelectionForeground(new java.awt.Color(204, 153, 0));
        tblTacGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTacGiaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTacGia);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 370, 430));

        lblTacGia.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblTacGia.setForeground(new java.awt.Color(153, 102, 0));
        lblTacGia.setText("TÁC GIẢ");
        add(lblTacGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 250, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel1.setText("Tên tác giả");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, 110, -1));

        txtNamSinh.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtNamSinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNamSinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 250, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel2.setText("Năm sinh");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 110, -1));

        txtQueQuan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtQueQuan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtQueQuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 250, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel3.setText("Quê quán");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 290, 110, -1));

        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });
        jPanel1.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 300, 330));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Ảnh");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        txtMaTG.setEditable(false);
        txtMaTG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaTG.setText("0");
        txtMaTG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtMaTG, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 250, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel7.setText("Mã tác giả");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 110, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 630, 400));

        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, 370, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Thông tin");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 100, -1));

        jButton1.setBackground(new java.awt.Color(153, 102, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 600, 90, 30));

        jButton2.setBackground(new java.awt.Color(153, 102, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 600, 90, 30));

        jButton3.setBackground(new java.awt.Color(153, 102, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton3.setText("Mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 600, 90, 30));

        jButton4.setBackground(new java.awt.Color(153, 102, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton4.setText("Thêm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 600, 90, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel6.setText("Tìm kiếm");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 230, -1));

        jPanel2.setBackground(new java.awt.Color(153, 102, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblChucVu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblChucVu.setText("Chức vụ: ");
        jPanel2.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, -1));

        lblTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblTen.setText("Tên đăng nhập:");
        jPanel2.add(lblTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 56));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtNamSinh)
                &&XValidate.checkNullText(txtQueQuan)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkYear(txtNamSinh)){
                    updateSP();
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblTacGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTacGiaMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblTacGia.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblTacGiaMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        selectIcon();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtNamSinh)
                &&XValidate.checkNullText(txtQueQuan)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkYear(txtNamSinh)){
                    insert();
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deleteSP();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblTacGia.getModel());
        tblTacGia.setRowSorter(rowSorter);
        
        // Tạo RowFilter dựa trên nội dung tìm kiếm
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + txtTimKiem.getText());

        // Đặt RowFilter cho RowSorter
        rowSorter.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblTacGia;
    private javax.swing.JLabel lblTen;
    private javax.swing.JTable tblTacGia;
    private javax.swing.JTextField txtMaTG;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtQueQuan;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
