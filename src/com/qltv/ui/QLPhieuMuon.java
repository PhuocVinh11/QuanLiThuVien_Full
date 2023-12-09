/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.ChiTietPhieuMuonDAO;
import com.qltv.dao.DocGiaDAO;
import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.PhieuMuonDAO;
import com.qltv.entity.ChiTietPhieuMuon;
import com.qltv.entity.PhieuMuon;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLPhieuMuon extends javax.swing.JPanel {
    PhieuMuonDAO pmdao = new PhieuMuonDAO();
    DocGiaDAO dgdao = new DocGiaDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    ChiTietPhieuMuonDAO ctdao = new ChiTietPhieuMuonDAO();
    /**
     * Creates new form QLPhieuMuon
     */
    public QLPhieuMuon() {
        initComponents();
        
        this.fillTablePM();
        this.fillComboBoxDG();
        this.fillNV();
        
        dateNgayMuon.setDate(XDate.now());
    }
    
    private void fillTablePM() {

        DefaultTableModel model = (DefaultTableModel) tblPM.getModel();
        model.setRowCount(0);
        try {
            List<PhieuMuon> list = pmdao.selectAll();
            for (PhieuMuon dg : list) {
                Object[] row = {
                    
                    nvdao.convertToTenNV(dg.getMaNV()),
                    dgdao.convertToTenDG(dg.getMaDG()),
                    //                    dg.getMancc(),
                    //                    dg.getManv(),
                    dg.getNgayMuon(),
                    dg.isTrangThai(),
                    dg.getMaPM()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu phiếu nhập!");
            e.printStackTrace();
        }

    }
    
        private void fillTableCT() {

        DefaultTableModel model = (DefaultTableModel) tblPMCT.getModel();
        model.setRowCount(0);
        try {
            List<ChiTietPhieuMuon> list = ctdao.selectAll();
            for (ChiTietPhieuMuon tv : list) {
                Object[] row = {
                    tv.getMa(),
                    tv.getMaPM(),
                    ctdao.convertToTenSach(tv.getMaSach()),
                    tv.getSoLuong(),
                    tv.getNgayTra(),

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu phiếu nhập!");
            e.printStackTrace();
        }

    }
        private void fillComboBoxDG() {
        cboDocGia.removeAllItems();
        List<String> data = dgdao.selectNXB();
        for (String item : data) {
            cboDocGia.addItem(item);
        }
    }
        private void fillNV() {
        txtNhanVien.setText(Auth.user.getUser());
        List<String> data = pmdao.selectNXB();
        for (String item : data) {
            cboDocGia.addItem(item);
        }
    }
        
        private void insert() {
        PhieuMuon model = getForm();
        try {
            pmdao.insert(model);
            this.fillTablePM();
//            this.clearForm();
            MsgBox.alert(this, "Thêm phiếu mượn mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm phiếu mượn mới thất bại!");
            e.printStackTrace();
        }

    }
        private void update() {
        PhieuMuon model = getFormPM1();
        try {
            pmdao.update(model);
            this.fillTablePM();
//            this.clearForm();
            MsgBox.alert(this, "Cập nhật phiếu mượn mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật phiếu mượn mới thất bại!");
            e.printStackTrace();
        }

    }

    private void delete() {
        int c = tblPM.getSelectedRow();
        int id = (int) tblPM.getValueAt(c, 0);
        pmdao.delete(id);
        this.fillTablePM();
        this.clearForm();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    private void clearForm() {
        txtNhanVien.setText(txtNhanVien.getText());
        cboDocGia.setSelectedIndex(0);
        dateNgayMuon.setDate(XDate.now());
    }

    private PhieuMuon getForm() {
        PhieuMuon s = new PhieuMuon();
        try {
            Object selectedNV = txtNhanVien.getText();
            Object selectedNCC = cboDocGia.getSelectedItem();

            if (selectedNV != null) {
                s.setMaNV(nvdao.convertToMaNV(selectedNV.toString()));
            }

            if (selectedNCC != null) {
                s.setMaDG(nvdao.convertToMaNV(selectedNCC.toString()));
            }

            s.setNgayMuon(dateNgayMuon.getDateFormatString());
            s.isTrangThai();
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ khi parse không thành công
            ex.printStackTrace();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác mà bạn quan tâm
            e.printStackTrace();
        }
        return s;
    }
    
    private PhieuMuon getFormPM1() {
        PhieuMuon s = new PhieuMuon();
        try {
            Object selectedNV = txtNhanVien.getText();
            Object selectedDG = cboDocGia.getSelectedItem();

            if (selectedNV != null) {
                s.setMaNV(nvdao.convertToMaNV(selectedNV.toString()));
            }

            if (selectedDG != null) {
                s.setMaDG(dgdao.convertToMaDG(selectedDG.toString()));
            }
            s.setMaPM((int) tblPM.getValueAt(tblPM.getSelectedRow(), 0));
            s.setNgayMuon(dateNgayMuon.getDateFormatString());
            s.isTrangThai();
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ khi parse không thành công
            ex.printStackTrace();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác mà bạn quan tâm
            e.printStackTrace();
        }
        return s;
    }
    
    private void clickTablePM() {
        int i = tblPM.getSelectedRow();
        if (i > -1) {
            try {
                txtNhanVien.setText(tblPM.getValueAt(i, 0)+"");
                cboDocGia.setSelectedItem(tblPM.getValueAt(i,1));
                dateNgayMuon.setDate((java.util.Date) tblPM.getValueAt(i, 2));
                if(tblPM.getValueAt(i, 3)=="Đã mượn"){
                    rdoMuon.isSelected();
                }else if(tblPM.getValueAt(i, 3)=="Đã trả"){
                    rdoTra.isSelected();
                }
                txtMaPM.setText(tblPM.getValueAt(i, 4).toString());
                txtMaPM.setText(tblPMCT.getValueAt(0, 1).toString());
                txtTenSach.setText(tblPMCT.getValueAt(0,2).toString());
                txtSoLuong.setText(tblPMCT.getValueAt(0, 3).toString());
                dateNgayTra.setDate((Date) tblPMCT.getValueAt(0, 4));
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }
    private void clickTableCTPM() {
        PhieuMuon pm = new PhieuMuon();
        int i = tblPMCT.getSelectedRow();
        if (i > -1) {
            try {
                
                txtMaPM.setText(tblPMCT.getValueAt(i, 1).toString());
                txtTenSach.setText(tblPMCT.getValueAt(i, 2).toString());
                txtSoLuong.setText(tblPMCT.getValueAt(i, 3).toString());
                dateNgayTra.setDate((Date) tblPMCT.getValueAt(i, 4));
                
                System.out.println(tblPM.getValueAt(i, 0).toString()+"");
                    System.out.println(String.valueOf(dgdao.convertToTenDG(pm.getMaDG())));
                    System.out.println(String.valueOf(nvdao.convertToTenNV(pm.getMaNV())));
                    System.out.println(pm.isTrangThai());
//                    System.out.println(pm.getNgayMuon());
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }
    public void getTableCT() {
        DefaultTableModel modelSP = (DefaultTableModel) tblPMCT.getModel();
        modelSP.setRowCount(0);
        int rown = tblPM.getSelectedRow();
        int ma = (int) tblPM.getValueAt(rown, 0);
        List<ChiTietPhieuMuon> listSP = new ArrayList<>();
        listSP = ctdao.selectByIds(ma);
        try {
            for (ChiTietPhieuMuon tv : listSP) {
                Object[] rows = new Object[]{
                    tv.getMa(),
                    tv.getMaPM(),
                    ctdao.convertToTenSach(tv.getMaSach()),
                    tv.getSoLuong(),
                    tv.getNgayTra()
                    
                };
                modelSP.addRow(rows);
            }
        } catch (Exception e) {
        }

    }
    
    private ChiTietPhieuMuon getFormCT1() {
    ChiTietPhieuMuon s = new ChiTietPhieuMuon();
    
    // Kiểm tra xem có hàng được chọn hay không
    int selectedRow = tblPMCT.getSelectedRow();
    if (selectedRow >= 0) {
        s.setMa((int) tblPMCT.getValueAt(selectedRow, 0));
        s.setMaPM(Integer.parseInt(txtMaPM.getText()));
        s.setMaSach(ctdao.convertToMaSach(txtTenSach.getText()));
        
        s.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        s.setNgayTra(dateNgayTra.getDate());
    } else {
        // Xử lý trường hợp không có hàng nào được chọn
        MsgBox.alert(this, "Vui lòng chọn một hàng trong bảng chi tiết phiếu mượn!");
    }
    
    return s;
}
    
    private void updateCT() {
    ChiTietPhieuMuon model = getFormCT1();
    try {
        ctdao.update(model);
        this.getTableCT();
        
        // Gọi phương thức tính tổng thành tiền và hiển thị kết quả
//        updateTongThanhTien();
        
        MsgBox.alert(this, "Cập nhật chi tiết phiếu nhập mới thành công!");
    } catch (Exception e) {
        MsgBox.alert(this, "Cập nhật chi tiết phiếu nhập mới thất bại!");
        e.printStackTrace();
    }
}
    private void filterByDG(int selectedDG) {
        // Gọi phương thức của DAO hoặc thực hiện các truy vấn để lấy dữ liệu đã lọc
        List<PhieuMuon> filteredData = pmdao.getPhieuMuonByDG(selectedDG);

        // Xóa tất cả dữ liệu hiện tại trong bảng
        DefaultTableModel model = (DefaultTableModel) tblPM.getModel();
        model.setRowCount(0);

        // Thêm dữ liệu đã lọc vào bảng
        for (PhieuMuon PhieuMuon : filteredData) {
            model.addRow(new Object[]{
                PhieuMuon.getMaPM(),
                
                nvdao.convertToTenNV(PhieuMuon.getMaNV()),
                dgdao.convertToTenDG(PhieuMuon.getMaDG()),
                PhieuMuon.getNgayMuon(),
                PhieuMuon.isTrangThai()
            });
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

        btnTrangThai = new javax.swing.ButtonGroup();
        panelBorder1 = new com.qltv.swing.PanelBorder();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        dateNgayMuon = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        rdoMuon = new javax.swing.JRadioButton();
        rdoTra = new javax.swing.JRadioButton();
        txtNhanVien = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboDocGia = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnSuaPM = new javax.swing.JButton();
        btnXoaPM = new javax.swing.JButton();
        btnMoiPM = new javax.swing.JButton();
        btnThemPM = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        txtTenSach = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMaPM = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnThemCTPM = new javax.swing.JButton();
        btnMoiCTPM = new javax.swing.JButton();
        btnXoaCTPM = new javax.swing.JButton();
        btnSuaCTPM = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPMCT = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPM = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        dateTu = new com.toedter.calendar.JDateChooser();
        dateDen = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelBorder1.setPreferredSize(new java.awt.Dimension(1100, 630));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(153, 102, 0));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1100, 650));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phiếu mượn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateNgayMuon.setBackground(new java.awt.Color(255, 255, 255));
        dateNgayMuon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        dateNgayMuon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.add(dateNgayMuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 263, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel1.setText("Trạng thái");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        btnTrangThai.add(rdoMuon);
        rdoMuon.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoMuon.setText("Đã mượn");
        jPanel2.add(rdoMuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, -1, -1));

        btnTrangThai.add(rdoTra);
        rdoTra.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoTra.setText("Đã trả");
        jPanel2.add(rdoTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, -1, -1));

        txtNhanVien.setEditable(false);
        txtNhanVien.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtNhanVien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 270, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Ngày mượn");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Độc giả");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, -1, -1));

        cboDocGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboDocGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDocGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(cboDocGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 280, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel9.setText("Nhân viên");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        btnSuaPM.setBackground(new java.awt.Color(204, 153, 0));
        btnSuaPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSuaPM.setText("Sửa");
        jPanel2.add(btnSuaPM, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, -1, -1));

        btnXoaPM.setBackground(new java.awt.Color(204, 153, 0));
        btnXoaPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoaPM.setText("Xóa");
        jPanel2.add(btnXoaPM, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, -1, -1));

        btnMoiPM.setBackground(new java.awt.Color(204, 153, 0));
        btnMoiPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoiPM.setText("Mới");
        jPanel2.add(btnMoiPM, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, -1, -1));

        btnThemPM.setBackground(new java.awt.Color(204, 153, 0));
        btnThemPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThemPM.setText("Thêm");
        btnThemPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPMActionPerformed(evt);
            }
        });
        jPanel2.add(btnThemPM, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết phiếu mượn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        dateNgayTra.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        dateNgayTra.setToolTipText("");

        txtTenSach.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTenSach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel10.setText("Tên sách");

        txtMaPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaPM.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel11.setText("Mã phiếu mượn");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel12.setText("Số lượng");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel13.setText("Ngày trả");

        btnThemCTPM.setBackground(new java.awt.Color(204, 153, 0));
        btnThemCTPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThemCTPM.setText("Thêm");

        btnMoiCTPM.setBackground(new java.awt.Color(204, 153, 0));
        btnMoiCTPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoiCTPM.setText("Mới");

        btnXoaCTPM.setBackground(new java.awt.Color(204, 153, 0));
        btnXoaCTPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoaCTPM.setText("Xóa");

        btnSuaCTPM.setBackground(new java.awt.Color(204, 153, 0));
        btnSuaCTPM.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSuaCTPM.setText("Sửa");

        jButton10.setBackground(new java.awt.Color(204, 153, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton10.setText("Chọn sách");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(163, 163, 163))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPM, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemCTPM)
                    .addComponent(btnSuaCTPM)
                    .addComponent(btnXoaCTPM)
                    .addComponent(btnMoiCTPM))
                .addGap(529, 529, 529))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10)))
                            .addComponent(jLabel10))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(txtMaPM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnThemCTPM)
                        .addGap(11, 11, 11)
                        .addComponent(btnSuaCTPM)
                        .addGap(11, 11, 11)
                        .addComponent(btnXoaCTPM)
                        .addGap(11, 11, 11)
                        .addComponent(btnMoiCTPM)))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 3, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 51, 0));
        jLabel3.setText("PHIẾU MƯỢN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 925, Short.MAX_VALUE))
                .addGap(42, 42, 42))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(457, 457, 457)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PHIẾU MƯỢN", jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phiếu mượn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPMCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblPMCT);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 345));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 510, 410));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phiếu mượn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nhân viên", "Độc giả", "Ngày mượn", "Trạng thái", "MaPM"
            }
        ));
        jScrollPane3.setViewportView(tblPM);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 345));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 510, 410));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16))); // NOI18N

        dateTu.setBackground(new java.awt.Color(255, 255, 255));
        dateTu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        dateDen.setBackground(new java.awt.Color(255, 255, 255));
        dateDen.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Từ");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("đến");

        btnLoc.setBackground(new java.awt.Color(204, 153, 0));
        btnLoc.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnLoc.setText("Lọc");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateTu, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateDen, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnLoc)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addComponent(dateTu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateDen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 480, -1, 80));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jPanel4.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, 290, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Tìm kiếm");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, -1, -1));

        jTabbedPane1.addTab("DANH SÁCH", jPanel4);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemPMActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnMoiCTPM;
    private javax.swing.JButton btnMoiPM;
    private javax.swing.JButton btnSuaCTPM;
    private javax.swing.JButton btnSuaPM;
    private javax.swing.JButton btnThemCTPM;
    private javax.swing.JButton btnThemPM;
    private javax.swing.ButtonGroup btnTrangThai;
    private javax.swing.JButton btnXoaCTPM;
    private javax.swing.JButton btnXoaPM;
    private javax.swing.JComboBox<String> cboDocGia;
    private com.toedter.calendar.JDateChooser dateDen;
    private com.toedter.calendar.JDateChooser dateNgayMuon;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private com.toedter.calendar.JDateChooser dateTu;
    private javax.swing.JButton jButton10;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.qltv.swing.PanelBorder panelBorder1;
    private javax.swing.JRadioButton rdoMuon;
    private javax.swing.JRadioButton rdoTra;
    private javax.swing.JTable tblPM;
    private javax.swing.JTable tblPMCT;
    private javax.swing.JTextField txtMaPM;
    private javax.swing.JTextField txtNhanVien;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
