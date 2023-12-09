/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltv.ui;

import com.qltv.dao.*;
import com.qltv.entity.*;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XDate;
import com.qltv.utils.XImage;
import com.qltv.utils.XValidate;
import java.awt.Color;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
public class QLSach extends javax.swing.JPanel {

    SachDAO sdao = new SachDAO();
    LoaiSachDAO lsdao = new LoaiSachDAO();
    KeSachDAO ksdao = new KeSachDAO();
    TacGiaDAO tgdao = new TacGiaDAO();
    NhaXuatBanDAO nxbdao = new NhaXuatBanDAO();
    int row= -1;
    /**
     * Creates new form Form_1
     */
    public QLSach() {
        initComponents();
        this.fillTable();
        init();
    }

    void init(){
        this.fillComboBoxLoaiSach();
        this.fillComboBoxKeSach();
        this.fillComboBoxNXB();
        this.fillComboBoxTacGia();
        try {
            lblTen.setText("Tên đăng nhập: " + Auth.user.getUser());
        lblChucVu.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Quản lý" : "Nhân viên"));
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn phải đăng nhập trước khi sử dụng!");
        }
    
    }
    
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            List<Sach> list = sdao.selectAll();
            for (Sach dg : list) {
                Object[] row = {
                    dg.getMa(),
                    dg.getTen(),
                    lsdao.convertToTenLoai(dg.getMaLoai()),
                    nxbdao.convertToTenNXB(dg.getMaNXB()),
                    tgdao.convertToTenTacGia(dg.getMaTG()),
                    dg.getNam(),
                    dg.getSoluong(),
                    ksdao.convertToViTri(dg.getMaKe()),
                    dg.getGhichu()
                };
                model.addRow(row);
        
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sách!");
            e.printStackTrace();
        }
        
    }
    
//    void fillTableSP() {
//        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
//        model.setRowCount(0);
//        try {
//            String keyword = txtTimKiem.getText();
//            List<Sach> list = sdao.selectByKeyword(keyword);
//            for (Sach dg : list) {
//                Object[] row = {
//                    dg.getMa(),
//                    dg.getTen(),
//                    lsdao.convertToTenLoai(dg.getMaLoai()),
//                    nxbdao.convertToTenNXB(dg.getMaNXB()),
//                    tgdao.convertToTenTacGia(dg.getMaTG()),
//                    dg.getNam(),
//                    dg.getSoluong(),
//                    ksdao.convertToViTri(dg.getMaKe()),
//                    dg.getGhichu()
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
//            e.printStackTrace();
//        }
//    }
    
    private void fillComboBoxLoaiSach() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai.getModel();
        model.removeAllElements();
//        cboLoai.removeAllItems();
            List<String> data = lsdao.selectById();
            for (String item : data) {
                cboLoai.addItem(item);
        }
    }
    
    private void fillComboBoxKeSach() {
        cboKeSach.removeAllItems();
            List<String> data = ksdao.selectById();
            for (String item : data) {
                cboKeSach.addItem(item);
        }
    }
    
    private void fillComboBoxTacGia() {
        cboTacGia.removeAllItems();
            List<String> data = tgdao.selectById();
            for (String item : data) {
                cboTacGia.addItem(item);
        }
    }
    
    private void fillComboBoxNXB() {
        cboNXB1.removeAllItems();
            List<String> data = nxbdao.selectById();
            for (String item : data) {
                cboNXB1.addItem(item);
        }
    }
    
    private void insert() {
        Sach model = getForm();
        try {
            sdao.insert(model);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Thêm sách mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm sách mới thất bại!");
            e.printStackTrace();
        }

    }

    private void update() {
        Sach model = getForm();
        try {
            sdao.update(model);
            this.fillTable();
            MsgBox.alert(this, "Cập nhật sách thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật sách thất bại!");
            e.printStackTrace();
        }
    }

    private void delete() {
        int c = tblSach.getSelectedRow();
        int id = (int) tblSach.getValueAt(c, 0);
        sdao.delete(id);
        this.fillTable();
        this.clearForm();
        MsgBox.alert(this, "Xóa thành công");
    }

    private void clearForm() {
        this.setForm(new Sach());
        this.row = -1;
        txtMa.setBackground(Color.white);
//        this.updateStatus();
    }

    void edit() {//điền thông tin đt sản phẩm lên form (theo vị trí row)
        try {
            int masp = (int) tblSach.getValueAt(this.row, 0);
            Sach model = sdao.selectByIds(masp);
            if (model != null) {
                this.setForm(model);
//                this.updateStatus();
                tabs.setSelectedIndex(0);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi click");
        }
    }
    
    private void setForm(Sach cd) {
        txtMa.setText(cd.getMa()+"");
        txtTen.setText(cd.getTen());
        cboLoai.setSelectedItem(lsdao.convertToTenLoai(cd.getMaLoai())+"");
        cboNXB1.setSelectedItem(nxbdao.convertToTenNXB(cd.getMaNXB())+"");
        cboTacGia.setSelectedItem(tgdao.convertToTenTacGia(cd.getMaTG())+"");
        txtNam.setText(String.valueOf(cd.getNam()));
        txtSoLuong.setText(String.valueOf(cd.getSoluong()));
        cboKeSach.setSelectedItem(ksdao.convertToViTri(cd.getMaKe())+"");
        txtGhiChu.setToolTipText(cd.getGhichu());
        lblAnh.setIcon(XImage.readIconCD("NoImage.png"));
        if (cd.getHinh() != null) {
            lblAnh.setToolTipText(cd.getHinh());
            lblAnh.setIcon(XImage.readIconCD(cd.getHinh()));
        }
    }

    private Sach getForm() {
        Sach s = new Sach();
        s.setMa(Integer.parseInt(txtMa.getText()));
        s.setTen(txtTen.getText());
        s.setMaLoai(lsdao.convertToMaLoai(cboLoai.getSelectedItem()+""));
        s.setMaNXB(nxbdao.convertToMaNXB(cboNXB1.getSelectedItem()+""));
        s.setMaTG(tgdao.convertToMaTG(cboTacGia.getSelectedItem()+""));
        s.setNam(Integer.parseInt(txtNam.getText()));
        s.setSoluong(Integer.parseInt(txtSoLuong.getText()));
        s.setMaKe(ksdao.convertToMaKe(""+cboKeSach.getSelectedItem()));
        s.setHinh(lblAnh.getToolTipText());
        s.setGhichu(txtGhiChu.getText());
        
        return s;
    }

    
    private void first() {
        this.row = 0;
        this.edit();
    }

    private void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    private void next() {
        if (this.row < tblSach.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    private void last() {
        this.row = tblSach.getRowCount() - 1;
        this.edit();
    }

//    private void updateStatus() {
//        boolean edit = (this.row >= 0);
//        boolean first = (this.row == 0);
//        boolean last = (this.row == tblSach.getRowCount() - 1);
//        // Trạng thái form
//        txtMaCD.setEditable(!edit);
//        btnThem.setEnabled(!edit);
//        btnSua.setEnabled(edit);
//        btnXoa.setEnabled(edit);
//
//        // Trạng thái điều hướng
//        btnFirst.setEnabled(edit && !first);
//        btnPrev.setEnabled(edit && !first);
//        btnNext.setEnabled(edit && !last);
//        btnLast.setEnabled(edit && !last);
//    }

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

        tabs = new javax.swing.JTabbedPane();
        pnlSach = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboTacGia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboKeSach = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboNXB1 = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblChucVu = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setForeground(new java.awt.Color(153, 102, 0));
        tabs.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        tabs.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N

        pnlSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pnlSach.setPreferredSize(new java.awt.Dimension(1100, 608));
        pnlSach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnh.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblAnh.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ảnh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI Semilight", 0, 18), new java.awt.Color(153, 102, 0))); // NOI18N
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });
        pnlSach.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 440, 340));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 102, 0));
        jLabel1.setText("SÁCH");
        pnlSach.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, -1));

        cboLoai.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboLoai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });
        pnlSach.add(cboLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 260, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Mã sách");
        pnlSach.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        cboTacGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboTacGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTacGiaActionPerformed(evt);
            }
        });
        pnlSach.add(cboTacGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 180, 260, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Tác giả");
        pnlSach.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Ghi chú");
        pnlSach.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, -1, -1));

        cboKeSach.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboKeSach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboKeSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKeSachActionPerformed(evt);
            }
        });
        pnlSach.add(cboKeSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 260, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Kệ sách");
        pnlSach.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 230, -1, -1));

        cboNXB1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboNXB1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboNXB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNXB1ActionPerformed(evt);
            }
        });
        pnlSach.add(cboNXB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 260, 40));

        txtSoLuong.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 260, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Loại sách");
        pnlSach.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, -1, -1));

        txtMa.setEditable(false);
        txtMa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMa.setText("0");
        txtMa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 330, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Nhà xuất bản");
        pnlSach.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Năm xuất bản");
        pnlSach.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, -1, -1));

        txtNam.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtNam.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 260, 40));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        pnlSach.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, 570, 70));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel9.setText("Số lượng");
        pnlSach.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        btnSua.setBackground(new java.awt.Color(204, 153, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        pnlSach.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 470, -1, -1));

        btnXoa.setBackground(new java.awt.Color(204, 153, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlSach.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 470, -1, -1));

        btnMoi.setBackground(new java.awt.Color(204, 153, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        pnlSach.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 470, -1, -1));

        btnThem.setBackground(new java.awt.Color(204, 153, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlSach.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, -1, -1));

        txtTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 570, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel11.setText("Tên sách");
        pnlSach.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        tabs.addTab("THÔNG TIN", pnlSach);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jScrollPane2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        tblSach.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Mã loại", "Mã NXB", "Mã tác giả", "Năm xuất bản", "Số lượng", "Mã kệ", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.setGridColor(new java.awt.Color(153, 153, 153));
        tblSach.setSelectionBackground(new java.awt.Color(102, 102, 255));
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSach);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 1020, 360));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 350, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel10.setText("Tìm kiếm");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, -1, -1));

        jButton5.setBackground(new java.awt.Color(204, 153, 0));
        jButton5.setText("<<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 150, -1, -1));

        jButton6.setBackground(new java.awt.Color(204, 153, 0));
        jButton6.setText(">>");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, -1, -1));

        jButton7.setBackground(new java.awt.Color(204, 153, 0));
        jButton7.setText(">|");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 150, -1, -1));

        jButton8.setBackground(new java.awt.Color(204, 153, 0));
        jButton8.setText("|<");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, -1, -1));

        tabs.addTab("DANH SÁCH", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 102, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblChucVu.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblChucVu.setText("Chức vụ: ");
        jPanel3.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, -1));

        lblTen.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblTen.setText("Tên đăng nhập:");
        jPanel3.add(lblTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1112, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        selectIcon();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
    }//GEN-LAST:event_cboLoaiActionPerformed

    private void cboTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTacGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTacGiaActionPerformed

    private void cboKeSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKeSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKeSachActionPerformed

    private void cboNXB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNXB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNXB1ActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblSach.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        prev();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        last();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtSoLuong)
                &&XValidate.checkNullText(txtNam)){
            if(XValidate.checkNumber(txtSoLuong)
                    &&XValidate.checkNumber(txtNam)){
                if(XValidate.checkYear(txtNam)){
                    insert();
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(XValidate.checkNullText(txtTen)
                &&XValidate.checkNullText(txtSoLuong)
                &&XValidate.checkNullText(txtNam)){
            if(XValidate.checkNumber(txtSoLuong)
                    &&XValidate.checkNumber(txtNam)){
                if(XValidate.checkYear(txtNam)){
                    update();
                }
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        first();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tblSach.getModel());
        tblSach.setRowSorter(rowSorter);
        
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
    private javax.swing.JComboBox<String> cboKeSach;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboNXB1;
    private javax.swing.JComboBox<String> cboTacGia;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblTen;
    private javax.swing.JPanel pnlSach;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
        
}
