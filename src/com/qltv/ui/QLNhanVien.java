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
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class QLNhanVien extends javax.swing.JPanel {

    NhanVienDAO nvdao = new NhanVienDAO();
    NhanVien nv = new NhanVien();
    TaiKhoanDAO tkdao = new TaiKhoanDAO();
    int row = -1;
    /**
     * Creates new form QLNhanVien
     */
    public QLNhanVien() {
        initComponents();
        fillTableNV();
        fillTableTK();
        rdoNam.setSelected(true);
        rdoNhanVien.setSelected(true);
        txtTenDN.setText(Auth.user.getUser());
        lblTen.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVu.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
        lblTenNV.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVuNV.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
    }

    // -----------------------------NHÂN VIÊN---------------------------------- //
    
    // Đổ dữ liệu lên bảng
    public void fillTableNV(){
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try{
            List<NhanVien> list = nvdao.selectAll();
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMa(),
                    nv.getTen(),
                    nv.getNam(),
                    nv.isGiotinh() ? "Nam" : "Nữ",
                    nv.getDiachi(),
                    nv.getSdt()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sách!");
            e.printStackTrace();
        }
        
    }
    
    // click để lấy dữ liệu từ bảng lên form
    
    void editNV() {//điền thông tin đt sản phẩm lên form (theo vị trí row)
        try {
            int masp = (int) tblNhanVien.getValueAt(this.row, 0);
            NhanVien model = nvdao.selectById(masp);
            if (model != null) {
                this.setForm(model);
//                this.updateStatus();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi click");
        }
        rdoNam.setSelected(true);
        
    }
    
    private void setForm(NhanVien nxb) {
        txtMa.setText(nxb.getMa()+"");
        txtTen.setText(nxb.getTen());
        txtDiaChi.setText(nxb.getDiachi());
        txtSDT.setText(nxb.getSdt());
        txtNamSinh.setText(nxb.getNam());
        if(nxb.isGiotinh()== true){
            rdoNam.setSelected(true);
        }else if(nxb.isGiotinh() == false){
            rdoNu.setSelected(true);
        }
        txtMaNV1.setText(nxb.getMa()+"");
    }
    
    private NhanVien getForm() {
        NhanVien nxb = new NhanVien();
        nxb.setMa((Integer.parseInt(txtMa.getText())));
        nxb.setTen(txtTen.getText());
        nxb.setDiachi(txtDiaChi.getText());
        nxb.setSdt(txtSDT.getText());
        nxb.setNam(txtNamSinh.getText());
        boolean giotinh = true;
        if (rdoNam.isSelected()) {
            giotinh = true;
        }else if(rdoNu.isSelected()){
            giotinh=false;
        }
        nxb.setGiotinh(giotinh);
        return nxb;
    }
    
     private void insert() {
        nv = this.getForm();
        nvdao.insert(nv);
        this.fillTableNV();
        MsgBox.alert(this, "Thêm thành công");
    }

    private void update() {
//        if(checkfrominsert1()){
//         if(checkMSP()){
        nv = this.getForm();
        nvdao.update(nv);
        System.out.println(nv);
        this.fillTableNV();
        MsgBox.alert(this, "Cập nhật thành công");
//        }
//        }
    }

    void clearform() {
        this.setForm(new NhanVien());
        this.row = -1;
        rdoNam.setSelected(true);
    }

    private void delete() {
        int c = tblNhanVien.getSelectedRow();
        int id = (int) tblNhanVien.getValueAt(c, 0);
        nvdao.delete(id);
        int c1 = tblTaiKhoan.getSelectedRow();
        int id1 = (int) tblTaiKhoan.getValueAt(c1, 4);
        tkdao.delete(id1);
        this.fillTableNV();
        this.fillTableTK();
        this.clearform();
//        this.getTableTheThuVien();
        
        MsgBox.alert(this, "Xóa thành công");
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

    void editTK() {//điền thông tin đt sản phẩm lên form (theo vị trí row)
        try {
            String masp = (String) tblTaiKhoan.getValueAt(this.row, 1);
            TaiKhoan model = tkdao.selectByIds(masp);
            if (model != null) {
                this.setFormTK(model);
//                this.updateStatus();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi click");
        }
        rdoQuanLy.setSelected(true);
        
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
        TaiKhoan tk = this.getFormTK();
        tkdao.update(tk);
        this.fillTableTK();
        this.clearFormTK();
        MsgBox.alert(this, "Cập nhật tài khoản thành công");
    }
    
    // Xóa
    private void deleteTK() {
        int c = tblTaiKhoan.getSelectedRow();
        int id = (int) tblTaiKhoan.getValueAt(c, 0);
        tkdao.deleteTK(id);
        this.fillTableTK();
        this.clearFormTK();
        MsgBox.alert(this, "Xóa thành công");
    }
    
    // Làm mới
        public void clearFormTK(){
            txtMaTK.setText("0");
            txtUser.setText("");
            txtMatKhau.setText("");
            buttonGroup1.clearSelection();
            txtMaNV1.setText("0");
    }
    
    private void setFormTK(TaiKhoan nxb) {
        txtMaNV1.setText(nxb.getManv()+"");
        txtUser.setText(nxb.getUser());
        txtMatKhau.setText(nxb.getPass());
        if(nxb.isQuyen()== true){
            rdoQuanLy.setSelected(true);
        }else if(nxb.isQuyen()== false){
            rdoNhanVien.setSelected(true);
        }
        txtMaTK.setText(nxb.getMatk()+"");
    }    
        
    // Lấy dữ liệu cho nút thêm
    private TaiKhoan getFormTK(){
        TaiKhoan tk = new TaiKhoan();
       tk.setUser(txtUser.getText());
        tk.setPass(txtMatKhau.getText());
        tk.setManv(Integer.parseInt(txtMaNV1.getText()));
        tk.setMatk(Integer.parseInt(txtMaTK.getText()));
        boolean giotinh = true;
        if (rdoQuanLy.isSelected()) {
            giotinh = true;
        }else if(rdoNhanVien.isSelected()){
            giotinh=false;
        }
        tk.setQuyen(giotinh);
        return tk;
    }

    private void doiMatKhau() {
        try {
            String manv = txtTenDN.getText();
            String matKhau = new String(txtMatKhau1.getText());
            String matKhauMoi = new String(txtMatKhau2.getText());
            String xacNhanMK = new String(txtMatKhau3.getText());

            if(!manv.equalsIgnoreCase(Auth.user.getUser())){
                MsgBox.alert(this, "Sai tên đăng nhập!");
            }
            else if (!matKhau.equals(Auth.user.getPass())) {
                MsgBox.alert(this, "Sai mật khẩu hiện tại!");
            } else if (!matKhauMoi.equals(xacNhanMK)) {
                MsgBox.alert(this, "Xác nhận mật khẩu không đúng!");
            } else {
                Auth.user.setPass(matKhauMoi);
                tkdao.update(Auth.user);
                MsgBox.alert(this, "Đổi mật khẩu thành công!");
            }
            this.fillTableTK();
            this.clearFormDoiMK();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void clearFormDoiMK(){
        txtMatKhau1.setText("");
        txtMatKhau2.setText("");
        txtMatKhau3.setText("");
    }
//    private boolean kiemTraTrungMa(int ma) {
//       List<TaiKhoan> dstk = tkdao.SelectAll();
//        for (TaiKhoan item : dstk) {
//            if (item.getMatk() == ma) {
//                MsgBox.alert(this, "Mã đã tồn tại!");
//                return false; // Mã đã tồn tại
//            }
//        }
//        return true; // Mã chưa tồn tại
//    }
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
        pnlTaiKhoan = new javax.swing.JPanel();
        lblTenNV = new javax.swing.JLabel();
        lblChucVuNV = new javax.swing.JLabel();
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
        jLabel13 = new javax.swing.JLabel();
        txtTimTK = new javax.swing.JTextField();
        txtMaNV1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pnlNhanVien = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlChung = new com.qltv.swing.PanelBorder();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNamSinh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTenDN = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtMatKhau1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtMatKhau2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtMatKhau3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblChucVu = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();

        pnlQLNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLNhanVien.setPreferredSize(new java.awt.Dimension(1100, 630));

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setForeground(new java.awt.Color(153, 102, 0));
        tabs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabs.setPreferredSize(new java.awt.Dimension(300, 80));

        pnlTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        pnlTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnlTaiKhoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTenNV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(102, 51, 0));
        lblTenNV.setText("Tên nhân viên: ");
        pnlTaiKhoan.add(lblTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        lblChucVuNV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblChucVuNV.setForeground(new java.awt.Color(102, 51, 0));
        lblChucVuNV.setText("Chức vụ:  Quản lý");
        pnlTaiKhoan.add(lblChucVuNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel5.setText("Chức vụ");
        pnlTaiKhoan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        buttonGroup1.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoQuanLy.setText("Quản lý");
        pnlTaiKhoan.add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, -1, -1));

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        pnlTaiKhoan.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, -1, -1));

        lblTaiKhoan.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        lblTaiKhoan.setForeground(new java.awt.Color(153, 102, 0));
        lblTaiKhoan.setText("TÀI KHOẢN");
        pnlTaiKhoan.add(lblTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 200, -1));

        tblTaiKhoan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
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

        pnlTaiKhoan.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 580, 410));

        txtMaTK.setEditable(false);
        txtMaTK.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaTK.setText("0");
        txtMaTK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtMaTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 270, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel9.setText("Mã nhân viên");
        pnlTaiKhoan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        txtUser.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 270, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel10.setText("Username");
        pnlTaiKhoan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 80, -1));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 270, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel11.setText("Password");
        pnlTaiKhoan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        jButton5.setBackground(new java.awt.Color(204, 153, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton5.setText("Sửa");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

        jButton6.setBackground(new java.awt.Color(204, 153, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton6.setText("Xóa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 153, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton7.setText("Mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 490, -1, -1));

        jButton8.setBackground(new java.awt.Color(204, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton8.setText("Thêm");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        pnlTaiKhoan.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel13.setText("Tìm kiếm");
        pnlTaiKhoan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, -1, -1));

        txtTimTK.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimTK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimTKKeyReleased(evt);
            }
        });
        pnlTaiKhoan.add(txtTimTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 270, 40));

        txtMaNV1.setEditable(false);
        txtMaNV1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaNV1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlTaiKhoan.add(txtMaNV1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 270, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel12.setText("Mã tài khoản");
        pnlTaiKhoan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        tabs.addTab("TÀI KHOẢN", pnlTaiKhoan);

        pnlNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnlNhanVien.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pnlNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlChung.setBackground(new java.awt.Color(255, 255, 255));
        pnlChung.setPreferredSize(new java.awt.Dimension(1100, 650));
        pnlChung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(204, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnlChung.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, -1, -1));

        jButton2.setBackground(new java.awt.Color(204, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnlChung.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, -1, -1));

        jButton3.setBackground(new java.awt.Color(204, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton3.setText("Mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pnlChung.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 460, -1, -1));

        jButton4.setBackground(new java.awt.Color(204, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton4.setText("Thêm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        pnlChung.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, -1, -1));

        txtTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlChung.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 430, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel1.setText("Địa chỉ");
        pnlChung.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel3.setText("Năm sinh");
        pnlChung.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, -1, -1));

        txtNamSinh.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtNamSinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlChung.add(txtNamSinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, 200, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel4.setText("Mã");
        pnlChung.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        txtMa.setEditable(false);
        txtMa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMa.setText("0");
        txtMa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlChung.add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 200, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel7.setText("Số điện thoại");
        pnlChung.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        txtSDT.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlChung.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 200, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel6.setText("Giới tính");
        pnlChung.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, -1, -1));

        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNam.setText("Nam");
        pnlChung.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, -1, -1));

        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        rdoNu.setText("Nữ");
        pnlChung.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 390, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel14.setText("Họ & tên");
        pnlChung.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlChung.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 430, 40));

        tblNhanVien.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
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

        pnlChung.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 520, 380));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        pnlChung.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 280, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 1, 16)); // NOI18N
        jLabel8.setText("Tìm kiếm");
        pnlChung.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 102, 0));
        jLabel2.setText("NHÂN VIÊN");
        pnlChung.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlChung, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 66, Short.MAX_VALUE))
        );

        pnlNhanVien.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tabs.addTab("NHÂN VIÊN", pnlNhanVien);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 102, 0));
        jLabel15.setText("ĐỔI MẬT KHẨU");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 34, -1, -1));

        txtTenDN.setEditable(false);
        txtTenDN.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtTenDN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtTenDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 279, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel16.setText("Tên đăng nhập");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, -1, -1));

        txtMatKhau1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtMatKhau1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtMatKhau1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 279, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel17.setText("Mật khẩu cũ");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        txtMatKhau2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtMatKhau2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtMatKhau2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 279, 40));

        jLabel18.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel18.setText("Mật khẩu mới");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, -1));

        txtMatKhau3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtMatKhau3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtMatKhau3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 279, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel19.setText("Xác nhận mật khẩu mới");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, -1, -1));

        jButton9.setBackground(new java.awt.Color(204, 153, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jButton9.setText("Làm mới");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 100, -1));

        jButton10.setBackground(new java.awt.Color(204, 153, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jButton10.setText("Đổi mật khẩu");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, -1, -1));

        tabs.addTab("ĐỔI MẬT KHẨU", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 102, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblChucVu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblChucVu.setText("Chức vụ: ");
        jPanel3.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, -1));

        lblTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblTen.setText("Tên đăng nhập:");
        jPanel3.add(lblTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        javax.swing.GroupLayout pnlQLNhanVienLayout = new javax.swing.GroupLayout(pnlQLNhanVien);
        pnlQLNhanVien.setLayout(pnlQLNhanVienLayout);
        pnlQLNhanVienLayout.setHorizontalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLNhanVienLayout.setVerticalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNhanVienLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        
        if(evt.getClickCount() == 1){
            this.row = tblNhanVien.getSelectedRow();
            editNV();
        }
        if(evt.getClickCount() == 2){
            this.row = tblNhanVien.getSelectedRow();
            editNV();
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void tblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            this.row = tblTaiKhoan.getSelectedRow();
            this.editTK();
        }
    }//GEN-LAST:event_tblTaiKhoanMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearform();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(XValidate.checkNullText(txtMa)
                &&XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtDiaChi)
                &&XValidate.checkNullText(txtNamSinh)
                &&XValidate.checkNullText(txtSDT)){
            if(XValidate.checkTrungNV(txtMa)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkYear(txtNamSinh)){
                    if(XValidate.checkSDT(txtSDT)){
                        insert();
                    }
                }
            }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(XValidate.checkNullText(txtMa)
                &&XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtDiaChi)
                &&XValidate.checkNullText(txtNamSinh)
                &&XValidate.checkNullText(txtSDT)){
            if(XValidate.checkName(txtTen)){
                if(XValidate.checkYear(txtNamSinh)){
                    if(XValidate.checkSDT(txtSDT)){
                        update();
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        delete();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(XValidate.checkNullText(txtUser)
                &&XValidate.checkNullText(txtMatKhau)){
            if(XValidate.checkPass(txtMatKhau)){
            if(XValidate.checkTrungTK(txtMaNV1)){
                insertTK();
            }
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(XValidate.checkNullText(txtUser)
                &&XValidate.checkNullText(txtMatKhau)){
                if(XValidate.checkPass(txtMatKhau)){
                                    updateTK();

                }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        deleteTK();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        clearFormTK();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTimTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimTKKeyReleased
        // TODO add your handling code here:
         TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblNhanVien.getModel());
        tblNhanVien.setRowSorter(rowSorter);
        
        // Tạo RowFilter dựa trên nội dung tìm kiếm
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + txtTimTK.getText());

        // Đặt RowFilter cho RowSorter
        rowSorter.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtTimTKKeyReleased

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
         TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblTaiKhoan.getModel());
        tblTaiKhoan.setRowSorter(rowSorter);
        
        // Tạo RowFilter dựa trên nội dung tìm kiếm
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + txtTimKiem.getText());

        // Đặt RowFilter cho RowSorter
        rowSorter.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        doiMatKhau();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        clearFormDoiMK();
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblChucVuNV;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTenNV;
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
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaNV1;
    private javax.swing.JTextField txtMaTK;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtMatKhau1;
    private javax.swing.JTextField txtMatKhau2;
    private javax.swing.JTextField txtMatKhau3;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimTK;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
