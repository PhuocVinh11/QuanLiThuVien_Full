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
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

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
                    dg.getMaLoai(),
                    dg.getMaNXB(),
                    dg.getMaTG(),
                    dg.getNam(),
                    dg.getSoluong(),
                    dg.getMaKe(),
                    dg.getGhichu()
                };
                model.addRow(row);
        
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sách!");
            e.printStackTrace();
        }
        
    }
    
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
        cboKeSach.removeAllItems();
            List<String> data = nxbdao.selectById();
            for (String item : data) {
                cboKeSach.addItem(item);
        }
    }
    
    private void clickTableSach() {
        int i = tblSach.getSelectedRow();
        if (i > -1) {
            try {
                txtMa.setText(tblSach.getValueAt(i, 0) + "");
                txtTen.setText(tblSach.getValueAt(i, 1) + "");
                cboLoai.setSelectedItem(tblSach.getValueAt(i, 2));
                cboKeSach.setSelectedItem(tblSach.getValueAt(i, 3));
                cboTacGia.setSelectedItem(tblSach.getValueAt(i, 4));
                txtNam.setText(tblSach.getValueAt(i, 5) + "");
                txtSoLuong.setText(tblSach.getValueAt(i,6 )+ "");
                cboKeSach.setSelectedItem(tblSach.getValueAt(i, 7));
                txtGhiChu.setToolTipText(tblSach.getValueAt(i, 8)+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tabs.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọnvào bảng");
        }

    }
//    private void clickTable(){
//        int i = tblSach.getSelectedRow();
//        if(i > -1){
//            txt.setText(tblSach.getValueAt(i, 1));
//            
//        }
//    }
    
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
//        this.updateStatus();
    }

    private void edit() {
        String macd = (String) tblSach.getValueAt(this.row, 0);
        Sach cd = sdao.selectById(macd);
        this.setForm(cd);
        tabs.setSelectedIndex(0);
//        this.updateStatus();
        
    }
    
    private void setForm(Sach cd) {
        txtMa.setText(cd.getMa());
        txtTen.setText(cd.getTen());
        cboLoai.setSelectedItem(lsdao.selectByLoai(cd.getMaLoai()));
        cboKeSach.setSelectedItem(cd.getMaNXB());
        cboTacGia.setSelectedItem(cd.getMaTG());
        txtNam.setText(String.valueOf(cd.getNam()));
        txtSoLuong.setText(String.valueOf(cd.getSoluong()));
        cboKeSach.setSelectedItem(cd.getMaKe());
        txtGhiChu.setToolTipText(cd.getGhichu());
        lblAnh.setIcon(XImage.readIconCD("NoImage.png"));
        if (cd.getHinh() != null) {
            lblAnh.setToolTipText(cd.getHinh());
            lblAnh.setIcon(XImage.readIconCD(cd.getHinh()));
        }
    }

    private Sach getForm() {
        Sach s = new Sach();
        s.setMa(txtMa.getText());
        s.setTen(txtTen.getText());
        s.setMaLoai(lsdao.selectByLoai(String.valueOf(cboLoai.getSelectedItem()))+"");
        s.setMaNXB((String) cboKeSach.getSelectedItem());
        s.setMaTG((String) cboTacGia.getSelectedItem());
        s.setNam(Integer.parseInt(txtNam.getText()));
        s.setSoluong(Integer.parseInt(txtSoLuong.getText()));
        s.setMaKe((String) cboKeSach.getSelectedItem());
        s.setGhichu(txtGhiChu.getToolTipText());
        s.setHinh(lblAnh.getToolTipText());
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
        txtTen = new javax.swing.JTextField();
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
        txtMa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
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
        pnlSach.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 440, 360));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 102, 0));
        jLabel1.setText("SÁCH");
        pnlSach.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, -1));

        cboLoai.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        cboLoai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });
        pnlSach.add(cboLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 260, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Tên sách");
        pnlSach.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        cboTacGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
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

        cboKeSach.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
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

        cboNXB1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        cboNXB1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboNXB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNXB1ActionPerformed(evt);
            }
        });
        pnlSach.add(cboNXB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 260, 40));

        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 260, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Loại sách");
        pnlSach.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, -1, -1));

        txtTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 570, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Nhà xuất bản");
        pnlSach.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Năm xuất bản");
        pnlSach.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, -1, -1));

        txtNam.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlSach.add(txtNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 260, 40));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtGhiChu.setColumns(20);
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

        txtMa.setText("0");
        pnlSach.add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, -1, -1));

        tabs.addTab("THÔNG TIN", pnlSach);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jScrollPane2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

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

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 1020, 400));

        jTextField4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 350, 40));

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
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            clickTableSach();
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        prev();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        last();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
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
    private javax.swing.JTextField jTextField4;
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
    // End of variables declaration//GEN-END:variables
}
