/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.NhaXuatBanDAO;
import com.qltv.entity.DocGia;
import com.qltv.entity.NhaXuatBan;
import com.qltv.entity.TheThuVien;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XImage;
import com.qltv.utils.XValidate;
import java.awt.Color;
import java.io.File;
import java.util.List;
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
 * @author Admin
 */
public class QLNhaXuatBan extends javax.swing.JPanel {

    NhaXuatBanDAO nxbdao = new NhaXuatBanDAO();
    int row = -1;
    /**
     * Creates new form QLNhaXuatBan
     */
    public QLNhaXuatBan() {
        initComponents();
        this.fillTable();
        lblTen.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVu.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
        viewTable();
    }

    void viewTable() {
        tblDSNXB.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblDSNXB.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblDSNXB.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDSNXB.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblDSNXB.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDSNXB.getModel();
        model.setRowCount(0);
        try {
            List<NhaXuatBan> list = nxbdao.selectAll();
            for (NhaXuatBan dg : list) {
                Object[] row = {
                    dg.getMa(),
                    dg.getTen(),
                    dg.getDiachi(),
                    dg.getSdt(),
                    dg.getHinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu độc giả!");
            e.printStackTrace();
        }
    }
    
    void edit() {
        try {
            int selectedRow = tblDSNXB.getSelectedRow();
            if (selectedRow >= 0) {
                int masp = (int) tblDSNXB.getValueAt(selectedRow, 0);
                NhaXuatBan model = nxbdao.selectById(masp);
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
    
    public void insert(){
        NhaXuatBan n = this.getForm();
        nxbdao.insert(n);
        this.fillTable();
        MsgBox.alert(this, "Thêm nhà xuất bản thành công!");
    }
    
       public void update(){
           try{
        NhaXuatBan n = this.getForm();
        System.out.println(n.toString());
        nxbdao.update(n);
        MsgBox.alert(this, "Cập nhật nhà xuất bản thành công!");
        this.fillTable();
           }catch(Exception e){
               e.printStackTrace();
           }
    }
    
    void deleteSP() {
        //lấy sản phẩm trên form, xóa sản phẩm theo maSP
        //xóa trắng form
        int maSP = Integer.parseInt(txtMa.getText());
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sản phẩm này ?")) {
            try {
                nxbdao.delete(maSP);
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
        this.setForm(new NhaXuatBan());
        lblAnh.setIcon(null);
        this.row = -1;
//        this.updateStatus();
        txtMa.setBackground(Color.white);
    }
    
    private void setForm(NhaXuatBan nxb) {
        txtMa.setText(nxb.getMa()+"");
        txtTen.setText(nxb.getTen());
        txtDiaChi.setText(nxb.getDiachi());
        txtSDT.setText(nxb.getSdt());
        lblAnh.setIcon(XImage.readIconNXB("NoImage.png"));
        if (nxb.getHinh() != null) {
            lblAnh.setToolTipText(nxb.getHinh());
            lblAnh.setIcon(XImage.readIconNXB(nxb.getHinh()));
        }
        
    }
    
    private NhaXuatBan getForm() {
        NhaXuatBan nxb = new NhaXuatBan();
        nxb.setMa((Integer.parseInt(txtMa.getText())));
        nxb.setTen(txtTen.getText());
        nxb.setDiachi(txtDiaChi.getText());
        nxb.setSdt(txtSDT.getText());
        nxb.setHinh(lblAnh.getToolTipText());
        return nxb;
    }
    
    private void selectIcon() {
        JFileChooser fc = new JFileChooser("logos");
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            XImage.saveIconNXB(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIconNXB(file.getName()); // đọc hình từ logos
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName()); // giữ tên hình trong tooltip
        }
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
        tblDSNXB = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblChucVu = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDSNXB.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        tblDSNXB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Tên nhà xuất bản", "Địa chỉ", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSNXB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSNXBMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDSNXB);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 410, 360));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 102, 0));
        jLabel1.setText("NHÀ XUẤT BẢN");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));

        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });
        add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 220, 310));

        txtTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 290, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Tên nhà xuất bản");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 290, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Địa chỉ");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, -1, -1));

        txtSDT.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, 290, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Số điện thoại");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Tìm kiếm");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 210, -1));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, 410, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Ảnh");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        btnSua.setBackground(new java.awt.Color(204, 153, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 530, -1, -1));

        btnXoa.setBackground(new java.awt.Color(204, 153, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 530, -1, -1));

        btnMoi.setBackground(new java.awt.Color(204, 153, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, -1, -1));

        btnThem.setBackground(new java.awt.Color(204, 153, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 530, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Mã nhà xuất bản");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        txtMa.setEditable(false);
        txtMa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMa.setText("0");
        txtMa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 290, 40));

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

    private void tblDSNXBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSNXBMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            this.row = tblDSNXB.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblDSNXBMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        selectIcon();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtSDT)
                &&XValidate.checkNullText(txtDiaChi)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkSDT(txtSDT)){
                    insert();
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtSDT)
                &&XValidate.checkNullText(txtDiaChi)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkSDT(txtSDT)){
                    update();
                }
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblDSNXB.getModel());
        tblDSNXB.setRowSorter(rowSorter);
        
        // Tạo RowFilter dựa trên nội dung tìm kiếm
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + txtTimKiem.getText());

        // Đặt RowFilter cho RowSorter
        rowSorter.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtTimKiemKeyReleased
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblTen;
    private javax.swing.JTable tblDSNXB;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
