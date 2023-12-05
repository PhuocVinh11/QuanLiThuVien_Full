/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.NhaXuatBanDAO;
import com.qltv.entity.DocGia;
import com.qltv.entity.NhaXuatBan;
import com.qltv.entity.TheThuVien;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XImage;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLNhaXuatBan extends javax.swing.JPanel {

    NhaXuatBanDAO nxbdao = new NhaXuatBanDAO();
    int i = -1;
    /**
     * Creates new form QLNhaXuatBan
     */
    public QLNhaXuatBan() {
        initComponents();
        this.fillTable();
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
    
    public void clickTableNXB(){
        int i = tblDSNXB.getSelectedRow();
        if(i > -1){
            txtTen.setText((String) tblDSNXB.getValueAt(i, 1));
            txtDiaChi.setText((String) tblDSNXB.getValueAt(i, 2));
            txtSDT.setText((String) tblDSNXB.getValueAt(i, 3));
            lblAnh.setIcon(XImage.readIconCD("NoImage.png"));
        if (tblDSNXB.getValueAt(i, 4) != null) {
            lblAnh.setToolTipText((String) tblDSNXB.getValueAt(i, 4));
            lblAnh.setIcon(XImage.readIconCD((String) tblDSNXB.getValueAt(i, 4)));
        }
        }
    }
    
    public void insert(){
        NhaXuatBan n = this.getForm();
        nxbdao.insert(n);
        this.fillTable();
        MsgBox.alert(this, "Thêm nhà xuất bản thành công!");
    }
    
       public void updateNXB(){
           try{
        NhaXuatBan n = this.getForm1();
        System.out.println(n.toString());
        nxbdao.update(n);
        MsgBox.alert(this, "Cập nhật nhà xuất bản thành công!");
        this.fillTable();
           }catch(Exception e){
               e.printStackTrace();
           }
    }
    
    private void delete() {
        int c = tblDSNXB.getSelectedRow();
        int id = (int) tblDSNXB.getValueAt(c, 0);
        nxbdao.delete(id);
        this.fillTable();
        MsgBox.alert(this, "Xóa nhà xuất bản thành công");
    }
    
    public void clearForm(){
        txtTen.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        lblAnh.setIcon(null);
    }
    
//    private void setForm(NhaXuatBan nxb) {
//        txtTen.setText(nxb.getTen());
//        txtDiaChi.setText(nxb.getDiachi());
//        txtSDT.setText(nxb.getSdt());
//        lblAnh.setIcon(XImage.readIconCD(null));
//        if (nxb.getHinh() != null) {
//            lblAnh.setToolTipText(nxb.getHinh());
//            lblAnh.setIcon(XImage.readIconCD(nxb.getHinh()));
//        }
//        
//    }
    
    private NhaXuatBan getForm1() {
        NhaXuatBan nxb = new NhaXuatBan();
        nxb.setMa((int) tblDSNXB.getValueAt(tblDSNXB.getSelectedRow(), 0));
        nxb.setTen(txtTen.getText());
        nxb.setDiachi(txtDiaChi.getText());
        nxb.setSdt(txtSDT.getText());
        nxb.setHinh(lblAnh.getToolTipText());
        return nxb;
    }
    
    private NhaXuatBan getForm() {
        NhaXuatBan nxb = new NhaXuatBan();
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
            XImage.saveIconCD(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIconCD(file.getName()); // đọc hình từ logos
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
        txtTen3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDSNXB.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        tblDSNXB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã nhà xuất bản", "Tên nhà xuất bản", "Địa chỉ", "Số điện thoại", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, 410, 420));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 102, 0));
        jLabel1.setText("NHÀ XUẤT BẢN");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, -1));

        lblAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });
        add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 220, 280));

        txtTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 290, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Tên nhà xuất bản");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 290, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Địa chỉ");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, -1, -1));

        txtSDT.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 290, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Số điện thoại");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Tên nhà xuất bản");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 210, -1));

        txtTen3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(txtTen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 410, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Ảnh");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        btnSua.setBackground(new java.awt.Color(204, 153, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, -1, -1));

        btnXoa.setBackground(new java.awt.Color(204, 153, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, -1, -1));

        btnMoi.setBackground(new java.awt.Color(204, 153, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, -1, -1));

        btnThem.setBackground(new java.awt.Color(204, 153, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void tblDSNXBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSNXBMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            this.clickTableNXB();
        }
    }//GEN-LAST:event_tblDSNXBMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        selectIcon();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateNXB();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed
    

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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTable tblDSNXB;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTen3;
    // End of variables declaration//GEN-END:variables
}
