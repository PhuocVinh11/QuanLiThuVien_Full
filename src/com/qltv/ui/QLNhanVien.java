/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.TaiKhoanDAO;
import com.qltv.entity.NhanVien;
import com.qltv.entity.TaiKhoan;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XDate;
import com.qltv.utils.XValidate;
import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLNhanVien extends javax.swing.JPanel {

    NhanVienDAO nvdao = new NhanVienDAO();
    TaiKhoanDAO tkdao = new TaiKhoanDAO();
    /**
     * Creates new form QLNhanVien
     */
    public QLNhanVien() {
        initComponents();
        fillTableNV();
        fillTableTK();
        lblTen.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVu.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
    }

    // -----------------------------NHÂN VIÊN---------------------------------- //
    
    // Đổ dữ liệu lên bảng
    public void fillTableNV(){
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try{
            List<NhanVien> list = nvdao.selectAll();
            for (NhanVien dg : list) {
                Object[] row = {
                    dg.getMa(),
                    dg.getTen(),
                    dg.getNam(),
                    dg.isGiotinh() ? "Nam" : "Nữ",
                    dg.getDiachi(),
                    dg.getSdt()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sách!");
            e.printStackTrace();
        }
        
    }
    
    // click để lấy dữ liệu từ bảng lên form
    private void clickTableNV() {
        int i = tblNhanVien.getSelectedRow();
        if (i > -1) {
            try {
                txtHoTen.setText(tblNhanVien.getValueAt(i, 1) + "");
                txtNamSinh.setText(tblNhanVien.getValueAt(i, 2) + "");
                if (String.valueOf(tblNhanVien.getValueAt(i, 3)) == "Nam") {
                    rdoNam.setSelected(true);
                } else if (String.valueOf(tblNhanVien.getValueAt(i, 3)).equals("Nữ")) {
                    rdoNu.setSelected(true);
                }
                txtDiaChi.setText(tblNhanVien.getValueAt(i, 4) + "");
                txtSDT.setText(tblNhanVien.getValueAt(i, 5) + "");
                txtMaTK.setText(tblNhanVien.getValueAt(i, 0) + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }
        
    }
    
    // Thêm
    private void insertNV(){
        NhanVien ls = this.getFormNV();
        nvdao.insert(ls);
        this.fillTableNV();
        this.clearFormNV();
        MsgBox.alert(this, "Thêm nhân viên thành công");
    }
    
    // Cập nhật loại sách
    private void updateNV(){
        NhanVien ls = this.getFormNV1();
        nvdao.update(ls);
        this.fillTableNV();
        this.clearFormNV();
        MsgBox.alert(this, "Cập nhật nhân viên thành công");
    }
    
    // Xóa
    private void deleteNV() {
        int c = tblNhanVien.getSelectedRow();
        int id = (int) tblNhanVien.getValueAt(c, 0);
        nvdao.delete(id);
        this.fillTableNV();
        this.clearFormNV();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormNV(){
            txtHoTen.setText("");
            txtNamSinh.setText("");
            txtDiaChi.setText("");
            txtSDT.setText("");
            buttonGroup2.clearSelection();
    }
    
    // Lấy dữ liệu cho nút thêm
    private NhanVien getFormNV(){
        NhanVien nv = new NhanVien();
        nv.setTen(txtHoTen.getText());
        nv.setNam(txtNamSinh.getText());
        nv.setGiotinh(rdoNam.isSelected());
        nv.setDiachi(txtDiaChi.getText());
        nv.setSdt(txtSDT.getText());
        return nv;
    }
    
    // Lấy dữ liệu cho nút cập nhật
    private NhanVien getFormNV1(){
        NhanVien nv = new NhanVien();
        nv.setMa( Integer.parseInt(tblNhanVien.getValueAt(tblNhanVien.getSelectedRow(), 0) + ""));
        nv.setTen(txtHoTen.getText());
        nv.setNam(txtNamSinh.getText());
        nv.setGiotinh(rdoNam.isSelected());
        nv.setDiachi(txtDiaChi.getText());
        nv.setSdt(txtSDT.getText());
        return nv;
    }
    
    // ------------------------------ TÀI KHOẢN ------------------------------- //
    
    // Đổ dữ liệu lên bảng
    public void fillTableTK() {
        DefaultTableModel modelSP = (DefaultTableModel) tblTaiKhoan.getModel();
        modelSP.setRowCount(0);
        tabs.setSelectedIndex(1);
        List<TaiKhoan> listSP = new ArrayList<>();
        listSP = tkdao.SelectAll();
        try {
            for (TaiKhoan tv : listSP) {
                Object[] rows = new Object[]{
                    tv.getMatk(),
                    tv.getUser(),
                    tv.getPass(),
                    tv.isQuyen() ? "Quản lý" : "Nhân viên",
                    tv.getManv()
                };
                modelSP.addRow(rows);
            }
            
        } catch (Exception e) {
        }

    }
    
    // Lây dữ liệu từ bảng lên form
    private void clickTableTK() {
        int i = tblTaiKhoan.getSelectedRow();
        if (i > -1) {
            try {
                txtMaTK.setText(tblTaiKhoan.getValueAt(i, 0) + "");
                txtUser.setText(tblTaiKhoan.getValueAt(i, 1) + "");
                txtMatKhau.setText(tblTaiKhoan.getValueAt(i, 2) + "");
                if (String.valueOf(tblTaiKhoan.getValueAt(i, 3)) == "Quản lý") {
                    rdoQuanLy.setSelected(true);
                } else if (String.valueOf(tblTaiKhoan.getValueAt(i, 3)).equals("Nhân viên")) {
                    rdoNhanVien.setSelected(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }
    
    // Thêm
    private void insertTK(){
        TaiKhoan tk = this.getFormTK();
        tkdao.insert(tk);
        this.fillTableTK();
        this.clearFormTK();
        MsgBox.alert(this, "Thêm tài khoản thành công");
    }
    
    // Cập nhật loại sách
    private void updateTK(){
        TaiKhoan tk = this.getFormTK1();
        tkdao.update(tk);
        this.fillTableTK();
        this.clearFormTK();
        MsgBox.alert(this, "Cập nhật tài khoản thành công");
    }
    
    // Xóa
    private void deleteTK() {
        int c = tblTaiKhoan.getSelectedRow();
        int id = (int) tblTaiKhoan.getValueAt(c, 0);
        tkdao.delete(id);
        this.fillTableTK();
        this.clearFormTK();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormTK(){
            txtMaTK.setText("");
            txtUser.setText("");
            txtMatKhau.setText("");
            buttonGroup1.clearSelection();
    }
    
    // Lấy dữ liệu cho nút thêm
    private TaiKhoan getFormTK(){
        TaiKhoan tk = new TaiKhoan();
       tk.setUser(txtUser.getText());
        tk.setPass(txtMatKhau.getText());
        tk.setQuyen(rdoQuanLy.isSelected());
        tk.setManv(Integer.parseInt(txtMaTK.getText()));
        return tk;
    }
    
    // Lấy dữ liệu cho nút cập nhật
    private TaiKhoan getFormTK1(){
        TaiKhoan tk = new TaiKhoan();
        tk.setMatk(Integer.parseInt(txtMaTK.getText()));
        tk.setUser(txtUser.getText());
        tk.setPass(txtMatKhau.getText());
        tk.setQuyen(rdoQuanLy.isSelected());
        tk.setManv(Integer.parseInt(txtMaTK.getText()));
        return tk;
    }
    
    private boolean kiemTraTrungMa(int ma) {
       List<TaiKhoan> dstk = tkdao.SelectAll();
        for (TaiKhoan item : dstk) {
            if (item.getMatk() == ma) {
                MsgBox.alert(this, "Mã đã tồn tại!");
                return false; // Mã đã tồn tại
            }
        }
        return true; // Mã chưa tồn tại
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        pnlQLNhanVien = new com.qltv.swing.PanelBorder();
        tabs = new javax.swing.JTabbedPane();
        pnlNhanVien = new javax.swing.JPanel();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNamSinh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlChung = new com.qltv.swing.PanelBorder();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pnlTaiKhoan = new javax.swing.JPanel();
        lblTen = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        lblTaiKhoan = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        txtMaTK = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txtPass1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtHoTen2 = new javax.swing.JTextField();

        pnlQLNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLNhanVien.setPreferredSize(new java.awt.Dimension(1100, 630));

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setForeground(new java.awt.Color(153, 102, 0));
        tabs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabs.setPreferredSize(new java.awt.Dimension(300, 80));

        pnlNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnlNhanVien.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pnlNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNu.setText("Nữ");
        pnlNhanVien.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, -1, -1));

        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNam.setText("Nam");
        pnlNhanVien.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, -1, -1));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Năm sinh", "Giới tính", "Địa chỉ", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        pnlNhanVien.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 570, 420));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 102, 0));
        jLabel2.setText("NHÂN VIÊN");
        pnlNhanVien.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        txtHoTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtHoTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNhanVien.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 270, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel1.setText("Họ & tên");
        pnlNhanVien.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, -1));

        txtNamSinh.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtNamSinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNhanVien.add(txtNamSinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 270, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Năm sinh");
        pnlNhanVien.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Địa chỉ");
        pnlNhanVien.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNhanVien.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 270, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Giới tính");
        pnlNhanVien.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, -1, -1));

        txtSDT.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNhanVien.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, 270, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Số điện thoại");
        pnlNhanVien.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlNhanVien.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 72, 280, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Tìm kiếm");
        pnlNhanVien.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        pnlChung.setBackground(new java.awt.Color(255, 255, 255));
        pnlChung.setPreferredSize(new java.awt.Dimension(1100, 650));

        jButton1.setBackground(new java.awt.Color(204, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton3.setText("Mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton4.setText("Thêm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChungLayout = new javax.swing.GroupLayout(pnlChung);
        pnlChung.setLayout(pnlChungLayout);
        pnlChungLayout.setHorizontalGroup(
            pnlChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChungLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jButton4)
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addGap(28, 28, 28)
                .addComponent(jButton2)
                .addGap(28, 28, 28)
                .addComponent(jButton3)
                .addContainerGap(681, Short.MAX_VALUE))
        );
        pnlChungLayout.setVerticalGroup(
            pnlChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChungLayout.createSequentialGroup()
                .addContainerGap(529, Short.MAX_VALUE)
                .addGroup(pnlChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        pnlNhanVien.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tabs.addTab("NHÂN VIÊN", pnlNhanVien);

        pnlTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        pnlTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnlTaiKhoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblTen.setForeground(new java.awt.Color(102, 51, 0));
        lblTen.setText("Tên nhân viên: ");
        pnlTaiKhoan.add(lblTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        lblChucVu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblChucVu.setForeground(new java.awt.Color(102, 51, 0));
        lblChucVu.setText("Chức vụ:  Quản lý");
        pnlTaiKhoan.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Chức vụ");
        pnlTaiKhoan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        buttonGroup1.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoQuanLy.setText("Quản lý");
        pnlTaiKhoan.add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, -1, -1));

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        pnlTaiKhoan.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, -1, -1));

        lblTaiKhoan.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblTaiKhoan.setForeground(new java.awt.Color(153, 102, 0));
        lblTaiKhoan.setText("TÀI KHOẢN");
        pnlTaiKhoan.add(lblTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 200, -1));

        tblTaiKhoan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã tài khoản", "Username", "Password", "Quyền", "Mã nhân viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTaiKhoan);

        pnlTaiKhoan.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 580, -1));

        txtMaTK.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaTK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtMaTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 270, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel9.setText("Mã tài khoản");
        pnlTaiKhoan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        txtUser.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 270, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel10.setText("Username");
        pnlTaiKhoan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 270, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel11.setText("Password");
        pnlTaiKhoan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jButton5.setBackground(new java.awt.Color(204, 153, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton5.setText("Sửa");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, -1, -1));

        jButton6.setBackground(new java.awt.Color(204, 153, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton6.setText("Xóa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 153, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton7.setText("Mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, -1, -1));

        jButton8.setBackground(new java.awt.Color(204, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton8.setText("Thêm");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, -1, -1));

        txtPass1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtPass1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtPass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 270, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel12.setText("Password");
        pnlTaiKhoan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel13.setText("Tìm kiếm");
        pnlTaiKhoan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, -1, -1));

        txtHoTen2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtHoTen2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtHoTen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 270, 40));

        tabs.addTab("TÀI KHOẢN", pnlTaiKhoan);

        javax.swing.GroupLayout pnlQLNhanVienLayout = new javax.swing.GroupLayout(pnlQLNhanVien);
        pnlQLNhanVien.setLayout(pnlQLNhanVienLayout);
        pnlQLNhanVienLayout.setHorizontalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        pnlQLNhanVienLayout.setVerticalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            clickTableNV();
        }
        if(evt.getClickCount() == 2){
            clickTableNV();
            tabs.setSelectedIndex(1);
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void tblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            clickTableTK();
        }
    }//GEN-LAST:event_tblTaiKhoanMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearFormNV();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        insertNV();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateNV();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteNV();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        insertTK();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        updateTK();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        deleteTK();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        clearFormTK();
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JLabel lblTen;
    private com.qltv.swing.PanelBorder pnlChung;
    private javax.swing.JPanel pnlNhanVien;
    private com.qltv.swing.PanelBorder pnlQLNhanVien;
    private javax.swing.JPanel pnlTaiKhoan;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtHoTen2;
    private javax.swing.JTextField txtMaTK;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtPass1;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
