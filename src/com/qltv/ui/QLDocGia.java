/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltv.ui;

import com.qltv.dao.DocGiaDAO;
import com.qltv.dao.TheThuVienDAO;
import com.qltv.entity.DocGia;
import com.qltv.entity.TheThuVien;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author
 */
public class QLDocGia extends javax.swing.JPanel {

    DocGiaDAO dgdao = new DocGiaDAO();
            DocGia dg = new DocGia();
    TheThuVienDAO ttvdao = new TheThuVienDAO();
    int row = -1;

    /**
     * Creates new form Form_1
     */
    public QLDocGia() {
        initComponents();
        this.fillTableDG();
    }

    // Form ĐỘC GIẢ
    //Tải dữ liệu
    private void fillTableDG() {
        DefaultTableModel model = (DefaultTableModel) tblDSDocGia.getModel();
        model.setRowCount(0);
        try {
            List<DocGia> list = dgdao.selectAll();
            for (DocGia dg : list) {
                Object[] row = {
                    dg.getMaDG(),
                    dg.getTenDG(),
                    dg.isGioiTinh() ? "Nam" : "Nữ",
                    dg.getDiaChi(),
                    dg.getSoDT()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu độc giả!");
            e.printStackTrace();
        }
    }
        


    

    private void clickTableDocGia() {
        int i = tblDSDocGia.getSelectedRow();
        if (i > -1) {
            try {
                txtTenDG2.setText(tblDSDocGia.getValueAt(i, 1) + "");
                txtSoDT.setText(tblDSDocGia.getValueAt(i, 4) + "");
                txtDiaChi.setText(tblDSDocGia.getValueAt(i, 3) + "");
                if (String.valueOf(tblDSDocGia.getValueAt(i, 2)) == "Nam") {
                    rdoNam.setSelected(true);
                } else if (String.valueOf(tblDSDocGia.getValueAt(i, 2)).equals("Nữ")) {
                    rdoNu.isSelected();
                }
                txtMaDG.setText(tblDSDocGia.getValueAt(i, 0)+"");
                
                if(String.valueOf(tblDSDocGia.getValueAt(i, 0)).equals(String.valueOf(tblDSTheTV.getValueAt(0, 4)))){
                dateNgayKT.setDate(XDate.toDate(tblDSTheTV.getValueAt(0, 2).toString(), "dd-MM-yyyy"));
                dateNgayBD.setDate(XDate.toDate(tblDSTheTV.getValueAt(0, 1).toString(), "dd-MM-yyyy"));
                jTextArea2.setText(tblDSTheTV.getValueAt(0, 3) + "");
                }
            } catch (Exception e) {

            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọnvào bảng");
        }

    }
    
    private void insertDG() {
//        if (checkfrominsert1()) {
//            if(checkformInsert()){
        dg = this.getFormDG();
        dgdao.insert(dg);
        this.fillTableDG();
        MsgBox.alert(this, "Thêm thành công");
//        }
//        }
    }

    private void updateDG() {
//        if(checkfrominsert1()){
//         if(checkMSP()){
        dg = this.getFormDG();
        System.out.println(dg.toString());
        dgdao.update(dg);
        System.out.println(dg);
        this.fillTableDG();
        MsgBox.alert(this, "Cập nhật thành công");
//        }
//        }
    }

    void clearformDG() {
        txtMaDG.setText("");
        txtTenDG2.setText("");
        txtSoDT.setText("");
        txtDiaChi.setText("");
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);
    }

    private void deleteDG() {
        int c = tblDSDocGia.getSelectedRow();
        int id = (int) tblDSDocGia.getValueAt(c, 0);
//        dgdao.deleteTTV(id);
        dgdao.delete(id);
        this.fillTableDG();
        this.clearformDG();
//        this.getTableTheThuVien();
        MsgBox.alert(this, "Xóa thành công");
    }

    private DocGia getFormDG() {
        dg.setTenDG(txtDiaChi.getText());
        dg.setGioiTinh(rdoNam.isSelected());
        dg.setDiaChi(txtDiaChi.getText());
        dg.setSoDT(txtSoDT.getText());
        return dg;
    }
    
        private void fillTableTTV() {
        DefaultTableModel model = (DefaultTableModel) tblDSTheTV.getModel();
        model.setRowCount(0);
        try {
            List<TheThuVien> list = ttvdao.selectAll();
            for (TheThuVien ttv : list) {
                Object[] row = {
                    ttv.getMaTheThuVien(),
                    ttv.getNgayBatDau(),
                    ttv.getNgayKetThuc(),
                    ttv.getGhiChu(),
                    ttv.getMadocgia()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu độc giả!");
            e.printStackTrace();
        }
    }
        
    public void getTableTheThuVien() {
        DefaultTableModel modelSP = (DefaultTableModel) tblDSTheTV.getModel();
        modelSP.setRowCount(0);
        int rown = tblDSDocGia.getSelectedRow();
        int ma = (int) tblDSDocGia.getValueAt(rown, 0);
        List<TheThuVien> listSP = new ArrayList<>();
        listSP = ttvdao.selectByIds(ma);
        try {
            for (TheThuVien tv : listSP) {
                Object[] rows = new Object[]{
                    tv.getMaTheThuVien(),
                    XDate.toString(tv.getNgayBatDau(),"dd-MM-yyyy"),
                    XDate.toString(tv.getNgayKetThuc(),"dd-MM-yyyy"),
                    tv.getGhiChu(),
                    tv.getMadocgia()
                };
                modelSP.addRow(rows);
            }
        } catch (Exception e) {
        }

    }

    private void clickTableTheThuVien() {
        int i = tblDSTheTV.getSelectedRow();
        if (i > -1) {
            try {
                txtMaDG.setText(tblDSTheTV.getValueAt(i, 4) + "");
                dateNgayKT.setDate(XDate.toDate(tblDSTheTV.getValueAt(i, 2).toString(), "dd-MM-yyyy"));
                dateNgayBD.setDate(XDate.toDate(tblDSTheTV.getValueAt(i, 1).toString(), "dd-MM-yyyy"));
                jTextArea2.setText(tblDSTheTV.getValueAt(i, 3) + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọnvào bảng");
        }

    }

    private void insertTTV() {
//        if (checkfrominsert1()) {
//            if(checkformInsert()){
        TheThuVien ttv = this.getFormTTV();
        ttvdao.insert(ttv);
        this.fillTableTTV();
        this.clearForm();
        MsgBox.alert(this, "Thêm thành công");
//        }
//        }
    }

    private void updateTTV() {
//        if(checkfrominsert1()){
//         if(checkMSP()){
        TheThuVien ttv = this.getFormTTV();
        System.out.println(ttv.toString());
        ttvdao.update(ttv);
        this.fillTableTTV();
        this.clearForm();
        MsgBox.alert(this, "Cập nhật thành công");
//        }
//        }
    }

    private void clearForm() {
        this.setForm(new TheThuVien());
        this.row = -1;
    }
    
    private void setForm(TheThuVien cd) {
        dateNgayBD.setDate(cd.getNgayBatDau());
        dateNgayKT.setDate(cd.getNgayKetThuc());
        jTextArea2.setText(cd.getGhiChu());
    }

    private void deleteTTV() {
        int c = tblDSTheTV.getSelectedRow();
        int id = (int) tblDSTheTV.getValueAt(c, 0);
        ttvdao.delete(id);
        this.fillTableTTV();
        this.clearForm();
        MsgBox.alert(this, "Xóa thành công");
    }

    private TheThuVien getFormTTV() {
        TheThuVien ttv = new TheThuVien();
        ttv.setNgayBatDau(dateNgayBD.getDate());
        ttv.setNgayKetThuc(dateNgayKT.getDate());
        ttv.setGhiChu(jTextArea2.getText());
        ttv.setMadocgia(Integer.parseInt(txtMaDG.getText()));
        return ttv;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgGioiTinh = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dateNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dateNgayBD = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMaDG = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        btnMoiTTV = new javax.swing.JButton();
        btnThemTTV = new javax.swing.JButton();
        btnXoaTTV = new javax.swing.JButton();
        btnSuaTTV = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtSoDT = new javax.swing.JTextField();
        txtTenDG2 = new javax.swing.JTextField();
        btnSuaDG = new javax.swing.JButton();
        btnXoaDG = new javax.swing.JButton();
        btnMoiDG = new javax.swing.JButton();
        btnThemDG = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDSDocGia = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDSTheTV = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1100, 650));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 650));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(153, 102, 0));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thẻ thư viện", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateNgayKT.setForeground(new java.awt.Color(153, 153, 153));
        dateNgayKT.setDateFormatString("dd-MM-yyyy");
        dateNgayKT.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel4.add(dateNgayKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 300, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Ngày kết thúc");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 130, -1));

        dateNgayBD.setForeground(new java.awt.Color(153, 153, 153));
        dateNgayBD.setDateFormatString("dd-MM-yyyy");
        dateNgayBD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel4.add(dateNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 310, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Ngày bắt đầu");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 120, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel1.setText("Ghi chú");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 70, -1));

        txtMaDG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaDG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMaDG.setEnabled(false);
        jPanel4.add(txtMaDG, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 320, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel9.setText("Mã độc giả");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextArea2);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 300, 40));

        btnMoiTTV.setBackground(new java.awt.Color(204, 153, 0));
        btnMoiTTV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoiTTV.setText("Mới");
        btnMoiTTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiTTVActionPerformed(evt);
            }
        });
        jPanel4.add(btnMoiTTV, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, -1, -1));

        btnThemTTV.setBackground(new java.awt.Color(204, 153, 0));
        btnThemTTV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThemTTV.setText("Thêm");
        btnThemTTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTTVActionPerformed(evt);
            }
        });
        jPanel4.add(btnThemTTV, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, -1, -1));

        btnXoaTTV.setBackground(new java.awt.Color(204, 153, 0));
        btnXoaTTV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoaTTV.setText("Xóa");
        btnXoaTTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTTVActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoaTTV, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 140, -1, -1));

        btnSuaTTV.setBackground(new java.awt.Color(204, 153, 0));
        btnSuaTTV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSuaTTV.setText("Sửa");
        btnSuaTTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTTVActionPerformed(evt);
            }
        });
        jPanel4.add(btnSuaTTV, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 102, 0));
        jLabel5.setText("ĐỘC GIẢ");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Độc giả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btgGioiTinh.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNam.setText("Nam");
        jPanel10.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 60, -1));

        btgGioiTinh.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNu.setText("Nữ");
        jPanel10.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Giới tính");
        jPanel10.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 153, 70, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Tên độc giả");
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Địa chỉ");
        jPanel10.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Số điện thoại");
        jPanel10.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel10.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 320, 40));

        txtSoDT.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSoDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel10.add(txtSoDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 320, 40));

        txtTenDG2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTenDG2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel10.add(txtTenDG2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 320, 40));

        btnSuaDG.setBackground(new java.awt.Color(204, 153, 0));
        btnSuaDG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSuaDG.setText("Sửa");
        btnSuaDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDGActionPerformed(evt);
            }
        });
        jPanel10.add(btnSuaDG, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, -1));

        btnXoaDG.setBackground(new java.awt.Color(204, 153, 0));
        btnXoaDG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoaDG.setText("Xóa");
        btnXoaDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDGActionPerformed(evt);
            }
        });
        jPanel10.add(btnXoaDG, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 140, -1, -1));

        btnMoiDG.setBackground(new java.awt.Color(204, 153, 0));
        btnMoiDG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoiDG.setText("Mới");
        btnMoiDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiDGActionPerformed(evt);
            }
        });
        jPanel10.add(btnMoiDG, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, -1, -1));

        btnThemDG.setBackground(new java.awt.Color(204, 153, 0));
        btnThemDG.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThemDG.setText("Thêm");
        btnThemDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDGActionPerformed(evt);
            }
        });
        jPanel10.add(btnThemDG, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(416, 416, 416))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ĐỘC GIẢ", jPanel2);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách độc giả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDSDocGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        tblDSDocGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã độc giả", "Họ & tên", "Giới tính", "Địa chỉ", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSDocGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSDocGiaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDSDocGia);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 900, 177));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 960, 230));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách độc giả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDSTheTV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        tblDSTheTV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã thẻ", "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú", "Mã độc giả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSTheTV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSTheTVMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDSTheTV);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 900, 177));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 960, 237));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel10.setText("Tìm kiếm");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 320, 40));

        jTabbedPane1.addTab("DANH SÁCH", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDSDocGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSDocGiaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            clickTableDocGia();
        }
        getTableTheThuVien();
    }//GEN-LAST:event_tblDSDocGiaMouseClicked

    private void tblDSTheTVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSTheTVMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            clickTableTheThuVien();
        }
    }//GEN-LAST:event_tblDSTheTVMouseClicked

    private void btnMoiDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiDGActionPerformed
        clearformDG();
    }//GEN-LAST:event_btnMoiDGActionPerformed

    private void btnThemDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDGActionPerformed
        insertDG();
    }//GEN-LAST:event_btnThemDGActionPerformed

    private void btnThemTTVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTTVActionPerformed
        insertTTV();
    }//GEN-LAST:event_btnThemTTVActionPerformed

    private void btnSuaTTVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTTVActionPerformed
        updateTTV();
    }//GEN-LAST:event_btnSuaTTVActionPerformed

    private void btnXoaTTVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTTVActionPerformed
        deleteTTV();
    }//GEN-LAST:event_btnXoaTTVActionPerformed

    private void btnMoiTTVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiTTVActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiTTVActionPerformed

    private void btnSuaDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDGActionPerformed
        updateDG();
    }//GEN-LAST:event_btnSuaDGActionPerformed

    private void btnXoaDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDGActionPerformed
        deleteDG();
    }//GEN-LAST:event_btnXoaDGActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgGioiTinh;
    private javax.swing.JButton btnMoiDG;
    private javax.swing.JButton btnMoiTTV;
    private javax.swing.JButton btnSuaDG;
    private javax.swing.JButton btnSuaTTV;
    private javax.swing.JButton btnThemDG;
    private javax.swing.JButton btnThemTTV;
    private javax.swing.JButton btnXoaDG;
    private javax.swing.JButton btnXoaTTV;
    private com.toedter.calendar.JDateChooser dateNgayBD;
    private com.toedter.calendar.JDateChooser dateNgayKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblDSDocGia;
    private javax.swing.JTable tblDSTheTV;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaDG;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTenDG2;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
