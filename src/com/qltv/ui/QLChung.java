/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.KeSachDAO;
import com.qltv.dao.LoaiSachDAO;
import com.qltv.dao.NhaCungCapDAO;
import com.qltv.entity.DocGia;
import com.qltv.entity.KeSach;
import com.qltv.entity.LoaiSach;
import com.qltv.entity.NhaCungCap;
import com.qltv.entity.NhaXuatBan;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XImage;
import com.qltv.utils.XValidate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLChung extends javax.swing.JPanel {

    LoaiSachDAO lsdao = new LoaiSachDAO();
    KeSachDAO ksdao = new KeSachDAO();
    NhaCungCapDAO nccdao = new NhaCungCapDAO();
    int i = -1;

    public QLChung() {
        initComponents();
        this.fillTableLoai();
        this.fillTableKeSach();
        this.fillTableNCC();
    }

    //--------------------------------------------------------------------------//
    
    // -----LOẠI SÁCH---- //
    
    // Đổ dữ liệu lên bảng
    private void fillTableLoai() {
        DefaultTableModel model = (DefaultTableModel) tblLoai.getModel();
        model.setRowCount(0);
        try {
            List<LoaiSach> list = lsdao.selectAll();
            for (LoaiSach dg : list) {
                Object[] row = {
                    dg.getMaLoai(),
                    dg.getTenLoai()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu loại sách!");
            e.printStackTrace();
        }
    }
    
    // Click vào bảng để hiện lên form
    private void clickTableLoai(){
        int i = tblLoai.getSelectedRow();
        if(i > -1){
            txtLoai.setText((String) tblLoai.getValueAt(i, 1));
        }
    }
    
    // Thêm
    private void insertLS(){
        LoaiSach ls = this.getFormLS();
        lsdao.insert(ls);
        this.fillTableLoai();
        this.clearFormLS();
        MsgBox.alert(this, "Thêm loại sách thành công");
    }
    
    // Cập nhật loại sách
    private void updateLS(){
        LoaiSach ls = this.getFormLS1();
        lsdao.update(ls);
        this.fillTableLoai();
        this.clearFormLS();
        MsgBox.alert(this, "Cập nhật loại sách thành công");
    }
    
    // Xóa
    private void deleteLS() {
        int c = tblLoai.getSelectedRow();
        int id = (int) tblLoai.getValueAt(c, 0);
        lsdao.delete(id);
        this.fillTableLoai();
        this.clearFormLS();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormLS(){
            txtLoai.setText("");
    }
    
    // Lấy dữ liệu cho nút thêm
    private LoaiSach getFormLS(){
        LoaiSach ls = new LoaiSach();
        ls.setTenLoai(txtLoai.getText());
        return ls;
    }
    
    // Lấy dữ liệu cho nút cập nhật
    private LoaiSach getFormLS1(){
        LoaiSach ls = new LoaiSach();
        ls.setMaLoai((int) tblLoai.getValueAt(tblLoai.getSelectedRow(), 0));
        ls.setTenLoai(txtLoai.getText());
        return ls;
    }
        
    // ---------------------------------KỆ SÁCH-------------------------------- //
    
    // Đổ dữ liệu lên bảng
    public void fillTableKeSach(){
        DefaultTableModel model = (DefaultTableModel) tblKeSach.getModel();
        model.setRowCount(0);
        try{
            List<KeSach> list = ksdao.selectAll();
            for (KeSach dg : list) {
                Object[] row = {
                    dg.getMaKe(),
                    dg.getVitri()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu kệ sách!");
            e.printStackTrace();
        }
    }
    
    // Click vào bảng để hiện lên form
    private void clickTableKeSach(){
        int i = tblKeSach.getSelectedRow();
        if(i > -1){
            txtKeSach.setText((String) tblKeSach.getValueAt(i, 1));
        }
    }
    
    // Thêm
    private void insertKS(){
        KeSach ks = this.getFormKS();
        ksdao.insert(ks);
        this.fillTableKeSach();
        this.clearFormKS();
        MsgBox.alert(this, "Thêm kệ sách thành công");
    }
    
    // Sửa
    private void updateKS(){
        KeSach ks = this.getFormKS1();
        ksdao.update(ks);
        this.fillTableKeSach();
        this.clearFormKS();
        MsgBox.alert(this, "Cập nhật kệ sách thành công");
    }
    
    // Xóa
    private void deleteKS() {
        int c = tblKeSach.getSelectedRow();
        int id = (int) tblKeSach.getValueAt(c, 0);
        ksdao.delete(id);
        this.fillTableKeSach();
        this.clearFormKS();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormKS(){
            txtKeSach.setText("");
    }
    
    // Lấy dữ liệu cho nút thêm
    private KeSach getFormKS(){
        KeSach ks = new KeSach();
        ks.setVitri(txtKeSach.getText());
        return ks;
    }
     
    // Lấy dữ liệu cho nút sửa
    private KeSach getFormKS1(){
        KeSach ks = new KeSach();
        int maks = (int) tblKeSach.getValueAt(tblKeSach.getSelectedRow(), 0);
        ks.setMaKe(maks);
        ks.setVitri(txtKeSach.getText());
        return ks;
    }
    
    // ---------------------------------NHÀ CUNG CẤP-------------------------- //
    
    // Đổ dữ liệu lên bảng
    public void fillTableNCC(){
        DefaultTableModel model = (DefaultTableModel) tblNCC.getModel();
        model.setRowCount(0);
        try{
            List<NhaCungCap> list = nccdao.selectAll();
            for (NhaCungCap ncc : list) {
                Object[] row = {
                    ncc.getMaNCC(),
                    ncc.getTenNCC()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu nhà cung cấp!");
            e.printStackTrace();
        }
    }
    
    // Click vào bảng để hiện lên form
    private void clickTableNCC(){
        int i = tblNCC.getSelectedRow();
        if(i > -1){
            txtTenNCC.setText((String) tblNCC.getValueAt(i, 1));
        }
    }
    
    // Thêm
    private void insertNCC(){
        NhaCungCap ncc = this.getFormNCC();
        nccdao.insert(ncc);
        this.fillTableNCC();
        this.clearFormNCC();
        MsgBox.alert(this, "Thêm nhà cung cấp thành công");
    }
    
    // Sửa
    private void updateNCC(){
        NhaCungCap ncc = this.getFormNCC1();
        nccdao.update(ncc);
        this.fillTableNCC();
        this.clearFormNCC();
        MsgBox.alert(this, "Cập nhật nhà cung cấp thành công");
    }
    
    // Xóa
    private void deleteNCC() {
        int c = tblNCC.getSelectedRow();
        int id = (int) tblNCC.getValueAt(c, 0);
        nccdao.delete(id);
        this.fillTableNCC();
        this.clearFormNCC();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormNCC(){
            txtTenNCC.setText("");
    }
    
    // Lấy dữ liệu cho nút thêm
    private NhaCungCap getFormNCC(){
        NhaCungCap ncc = new NhaCungCap();
        ncc.setTenNCC(txtTenNCC.getText());
        return ncc;
    }
        
    // Lấy dữ liệu cho nút cập nhật
    private NhaCungCap getFormNCC1(){
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC((int) tblNCC.getValueAt(tblNCC.getSelectedRow(), 0));
        ncc.setTenNCC(txtTenNCC.getText());
        return ncc;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlChung = new com.qltv.swing.PanelBorder();
        tabs = new javax.swing.JTabbedPane();
        pnlLoaiSach = new javax.swing.JPanel();
        lblLoai = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoai = new javax.swing.JTable();
        txtLoai = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pnlKeSach = new javax.swing.JPanel();
        lblKeSach = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKeSach = new javax.swing.JTable();
        txtKeSach = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        pnlNCC = new javax.swing.JPanel();
        lblTenNCC = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNCC = new javax.swing.JTable();
        txtNCC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        pnlChung.setBackground(new java.awt.Color(255, 255, 255));
        pnlChung.setPreferredSize(new java.awt.Dimension(1100, 650));

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setForeground(new java.awt.Color(153, 102, 0));
        tabs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        pnlLoaiSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiSach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoai.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblLoai.setForeground(new java.awt.Color(102, 51, 0));
        lblLoai.setText("LOẠI SÁCH");
        pnlLoaiSach.add(lblLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 44, -1, -1));

        tblLoai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại", "Tên loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoai);

        pnlLoaiSach.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 275, 701, 319));

        txtLoai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlLoaiSach.add(txtLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 500, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel1.setText("Tên loại");
        pnlLoaiSach.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        jButton1.setBackground(new java.awt.Color(204, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton1.setText("Sửa");
        pnlLoaiSach.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, -1, -1));

        jButton2.setBackground(new java.awt.Color(204, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton2.setText("Xóa");
        pnlLoaiSach.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, -1, -1));

        jButton3.setBackground(new java.awt.Color(204, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton3.setText("Mới");
        pnlLoaiSach.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, -1, -1));

        jButton4.setBackground(new java.awt.Color(204, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton4.setText("Thêm");
        pnlLoaiSach.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 210, -1, -1));

        tabs.addTab(" LOẠI SÁCH", pnlLoaiSach);

        pnlKeSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlKeSach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblKeSach.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblKeSach.setForeground(new java.awt.Color(102, 51, 0));
        lblKeSach.setText("KỆ SÁCH");
        pnlKeSach.add(lblKeSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, -1));

        tblKeSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã kệ", "Vị trí"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKeSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKeSachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKeSach);

        pnlKeSach.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 275, 701, 319));

        txtKeSach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlKeSach.add(txtKeSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 500, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Vị trí");
        pnlKeSach.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        jButton9.setBackground(new java.awt.Color(204, 153, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton9.setText("Thêm");
        pnlKeSach.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, -1));

        jButton10.setBackground(new java.awt.Color(204, 153, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton10.setText("Sửa");
        pnlKeSach.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        jButton11.setBackground(new java.awt.Color(204, 153, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton11.setText("Xóa");
        pnlKeSach.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, -1, -1));

        jButton12.setBackground(new java.awt.Color(204, 153, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton12.setText("Mới");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        pnlKeSach.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, -1, -1));

        tabs.addTab("KỆ SÁCH", pnlKeSach);

        pnlNCC.setBackground(new java.awt.Color(255, 255, 255));
        pnlNCC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTenNCC.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblTenNCC.setForeground(new java.awt.Color(102, 51, 0));
        lblTenNCC.setText("TÊN NHÀ CUNG CẤP");
        pnlNCC.add(lblTenNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        tblNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã nhà cung cấp", "Tên nhà cung cấp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNCCMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNCC);

        pnlNCC.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 275, 701, 319));

        txtNCC.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNCC.add(txtNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 500, 50));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Tên nhà cung cấp");
        pnlNCC.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        jButton5.setBackground(new java.awt.Color(204, 153, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton5.setText("Sửa");
        pnlNCC.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, -1, -1));

        jButton6.setBackground(new java.awt.Color(204, 153, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton6.setText("Xóa");
        pnlNCC.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 153, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton7.setText("Mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        pnlNCC.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 210, -1, -1));

        jButton8.setBackground(new java.awt.Color(204, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton8.setText("Thêm");
        pnlNCC.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        tabs.addTab("NHÀ CUNG CẤP", pnlNCC);

        javax.swing.GroupLayout pnlChungLayout = new javax.swing.GroupLayout(pnlChung);
        pnlChung.setLayout(pnlChungLayout);
        pnlChungLayout.setHorizontalGroup(
            pnlChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        pnlChungLayout.setVerticalGroup(
            pnlChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
        clickTableLoai();
        }
    }//GEN-LAST:event_tblLoaiMouseClicked

    private void tblNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNCCMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            clickTableNCC();
        }
    }//GEN-LAST:event_tblNCCMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblKeSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeSachMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            clickTableKeSach();
        }
    }//GEN-LAST:event_tblKeSachMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblKeSach;
    private javax.swing.JLabel lblLoai;
    private javax.swing.JLabel lblTenNCC;
    private com.qltv.swing.PanelBorder pnlChung;
    private javax.swing.JPanel pnlKeSach;
    private javax.swing.JPanel pnlLoaiSach;
    private javax.swing.JPanel pnlNCC;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblKeSach;
    private javax.swing.JTable tblLoai;
    private javax.swing.JTable tblNCC;
    private javax.swing.JTextField txtKeSach;
    private javax.swing.JTextField txtLoai;
    private javax.swing.JTextField txtNCC;
    // End of variables declaration//GEN-END:variables
}
