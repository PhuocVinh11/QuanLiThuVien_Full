/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.ChiTietPhieuNhapDAO;
import com.qltv.dao.NhaCungCapDAO;
import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.PhieuNhapDAO;
import com.qltv.entity.ChiTietPhieuNhap;
import com.qltv.entity.NhaCungCap;
import com.qltv.entity.NhanVien;
import com.qltv.entity.PhieuNhap;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.qltv.dao.BookSelectionCallback;
import com.qltv.dao.SachDAO;
import com.qltv.entity.Sach;
import com.qltv.utils.ExportExcel;
import com.qltv.utils.WritePDF;
import javax.swing.table.TableModel;

/**
 *
 * @author Admin
 */
public class QLPhieuNhap extends javax.swing.JPanel implements BookSelectionCallback{

    SachDAO sdao = new SachDAO();
    PhieuNhapDAO pndao = new PhieuNhapDAO();
    NhaCungCapDAO nccdao = new NhaCungCapDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    ChiTietPhieuNhapDAO ctdao = new ChiTietPhieuNhapDAO();
    

    /**
     * Creates new form QLPhieuNhap
     */
    public QLPhieuNhap() {
        initComponents();
        init();
        this.fillTablePN();
        this.fillTableSach();
        this.fillComboBoxNCC();
        this.fillComboBoxNV();
        this.fillComboBoxLoc();
        

    }

    private void fillTablePN() {

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhap> list = pndao.selectAll();
            for (PhieuNhap dg : list) {
                Object[] row = {
                    dg.getMa(),
                    nccdao.convertToTenNCC(dg.getMancc()),
                    nvdao.convertToTenNV(dg.getManv()),
                    //                    dg.getMancc(),
                    //                    dg.getManv(),
                    dg.getNgaynhap(),
                    dg.getTong()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu phiếu nhập!");
            e.printStackTrace();
        }

    }

    private void fillTableCT() {

        DefaultTableModel model = (DefaultTableModel) tblCTPN.getModel();
        model.setRowCount(0);
        try {
            List<ChiTietPhieuNhap> list = ctdao.selectAll();
            for (ChiTietPhieuNhap tv : list) {
                Object[] row = {
                    tv.getMa(),
                    tv.getMapn(),
                    ctdao.convertToTenSach(tv.getMasach()),
                    tv.getGia(),
                    tv.getSoluong(),
                    tv.getThanhtien()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu phiếu nhập!");
            e.printStackTrace();
        }

    }

    private void fillComboBoxNCC() {
        cboNCC.removeAllItems();
        List<String> data = nccdao.selectNXB();
        for (String item : data) {
            cboNCC.addItem(item);
        }
    }

    private void fillComboBoxLoc() {
        combobox3.removeAllItems();
        List<String> data = nccdao.selectNXB();
        for (String item : data) {
            combobox3.addItem(item);
        }
    }

    private void fillComboBoxNV() {
        cboNV.removeAllItems();
        List<String> data = pndao.selectNXB();
        for (String item : data) {
            cboNV.addItem(item);
        }
    }

    private void insert() {
        PhieuNhap model = getForm();
        try {
            pndao.insert(model);
            this.fillTablePN();
//            this.clearForm();
            MsgBox.alert(this, "Thêm phiếu nhập mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm phiếu nhập mới thất bại!");
            e.printStackTrace();
        }

    }

    private void update() {
        PhieuNhap model = getFormPN1();
        try {
            pndao.update(model);
            this.fillTablePN();
//            this.clearForm();
            MsgBox.alert(this, "Cập nhật phiếu nhập mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật phiếu nhập mới thất bại!");
            e.printStackTrace();
        }

    }

    private void delete() {
        int c = jTable2.getSelectedRow();
        int id = (int) jTable2.getValueAt(c, 0);
        pndao.delete(id);
        this.fillTablePN();
        this.clearForm();
        MsgBox.alert(this, "Xóa thành công");
    }

    private void clearForm() {
        cboNCC.setSelectedIndex(0);
        cboNV.setSelectedIndex(0);
        txtTong.setText("");
        dateNgayNhap.setDate(XDate.now());
    }

    private PhieuNhap getForm() {
        PhieuNhap s = new PhieuNhap();
        try {
            Object selectedNV = cboNV.getSelectedItem();
            Object selectedNCC = cboNCC.getSelectedItem();

            if (selectedNV != null) {
                s.setManv(nvdao.convertToMaNV(selectedNV.toString()));
            }

            if (selectedNCC != null) {
                s.setMancc(nccdao.convertToMaNV(selectedNCC.toString()));
            }

            s.setNgaynhap(dateNgayNhap.getDate());
            s.setTong(Integer.parseInt(txtTong.getText()));
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ khi parse không thành công
            ex.printStackTrace();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác mà bạn quan tâm
            e.printStackTrace();
        }
        return s;
    }

    private PhieuNhap getFormPN1() {
        PhieuNhap s = new PhieuNhap();
        try {
            Object selectedNV = cboNV.getSelectedItem();
            Object selectedNCC = cboNCC.getSelectedItem();

            if (selectedNV != null) {
                s.setManv(nvdao.convertToMaNV(selectedNV.toString()));
            }

            if (selectedNCC != null) {
                s.setMancc(nccdao.convertToMaNV(selectedNCC.toString()));
            }
            s.setMa((int) jTable2.getValueAt(jTable2.getSelectedRow(), 0));
            s.setNgaynhap(dateNgayNhap.getDate());
            s.setTong(Integer.parseInt(txtTong.getText()));
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ khi parse không thành công
            ex.printStackTrace();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác mà bạn quan tâm
            e.printStackTrace();
        }
        return s;
    }

    private void clickTablePN() {
        int i = jTable2.getSelectedRow();
        if (i > -1) {
            try {
                cboNCC.setSelectedItem(jTable2.getValueAt(i, 1));
                cboNV.setSelectedItem(jTable2.getValueAt(i, 2));
                dateNgayNhap.setDate((java.util.Date) jTable2.getValueAt(i, 3));
                txtTong.setText(jTable2.getValueAt(i, 4).toString());
                txtMa.setText(jTable2.getValueAt(i, 0).toString());
                txtMa.setText(tblCTPN.getValueAt(0, 1).toString());
                txtSach.setText(tblCTPN.getValueAt(0, 2).toString());
                txtGia.setText(tblCTPN.getValueAt(0, 3).toString());
                txtSL.setText(tblCTPN.getValueAt(0, 4).toString());
                txtThanhTien.setText(tblCTPN.getValueAt(0, 5).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }

    private void clickTableCTPN() {
        PhieuNhap pn = new PhieuNhap();
        int i = tblCTPN.getSelectedRow();
        if (i > -1) {
            try {
                
                txtMa.setText(tblCTPN.getValueAt(i, 1).toString());
                txtSach.setText(tblCTPN.getValueAt(i, 2).toString());
                txtGia.setText(tblCTPN.getValueAt(i, 3).toString());
                txtSL.setText(tblCTPN.getValueAt(i, 4).toString());
                txtThanhTien.setText(tblCTPN.getValueAt(i, 5).toString());
                System.out.println(jTable2.getValueAt(i, 0).toString()+"");
                    System.out.println(String.valueOf(nccdao.convertToTenNCC(pn.getMancc())));
                    System.out.println(String.valueOf(nvdao.convertToTenNV(pn.getManv())));
                    System.out.println(pn.getTong());
                    System.out.println(pn.getNgaynhap());
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }
    
    
     @Override
    public void onBookSelected(String tenSach) {
        // Hiển thị tên sách trong JTextField
        txtSach.setText(tenSach);
    }
    
//    private void hienThiDuLieuPhieuNhap(Object maPhieuNhap) {
//    // Tìm kiếm và hiển thị dữ liệu của phiếu nhập có mã tương ứng
//    PhieuNhap phieuNhap = timPhieuNhapTheoMa(maPhieuNhap);
//    if (phieuNhap != null) {
//        // Hiển thị dữ liệu lên form
//        hienThiDuLieuPhieuNhapLenForm(phieuNhap);
//    }
//}
//private void hienThiDuLieuPhieuNhapTuChiTietPhieuNhap() {
//    int selectedRowChiTiet = tblCTPN.getSelectedRow();
//    if (selectedRowChiTiet >= 0) {
//        // Lấy mã phiếu nhập từ bảng chi tiết phiếu nhập
//        Object maPhieuNhap = tblCTPN.getValueAt(selectedRowChiTiet, 1);
//
//        // Hiển thị dữ liệu của phiếu nhập có mã tương ứng
//        hienThiDuLieuPhieuNhap(maPhieuNhap);
//    }
//}
//
//    
//    private PhieuNhap timPhieuNhapTheoMa(Object maPhieuNhap) {
//    // Viết logic để tìm kiếm phiếu nhập theo mã từ dữ liệu của bạn
//    // ...
//
//    // Giả sử có một danh sách danh sách phiếu nhập
//    List<PhieuNhap> danhSachPhieuNhap = pndao.selectAll(); // Lấy danh sách từ dữ liệu của bạn
//
//    // Gọi hàm tìm kiếm trong danh sách
//    return timPhieuNhapTrongDanhSach(danhSachPhieuNhap, (int) maPhieuNhap);
//}
//
//    private void hienThiDuLieuPhieuNhapLenForm(PhieuNhap phieuNhap) {
//    // Kiểm tra giá trị trước khi đặt vào ComboBox
//    System.out.println("TenNCC: " + nccdao.convertToTenNCC(phieuNhap.getMancc()));
//    System.out.println("TenNV: " + nvdao.convertToTenNV(phieuNhap.getManv()));
//
//    // Đặt giá trị vào ComboBox
//    cboNCC.setSelectedItem(nccdao.convertToTenNCC(phieuNhap.getMancc()));
//    cboNV.setSelectedItem(nvdao.convertToTenNV(phieuNhap.getManv()));
//    dateNgayNhap.setDate(phieuNhap.getNgaynhap());
//    txtTong.setText(String.valueOf(phieuNhap.getTong()));
//}
//    
//private PhieuNhap timPhieuNhapTrongDanhSach(List<PhieuNhap> danhSachPhieuNhap, int maPhieuNhap) {
//    // Thực hiện tìm kiếm trong danh sách phiếu nhập và trả về đối tượng PhieuNhap
//    for (PhieuNhap phieuNhap : danhSachPhieuNhap) {
//        if (phieuNhap.getMa() == maPhieuNhap) {
//            return phieuNhap;
//        }
//    }
//    return null; // Trả về null nếu không tìm thấy
//}


    public void getTableCT() {
        DefaultTableModel modelSP = (DefaultTableModel) tblCTPN.getModel();
        modelSP.setRowCount(0);
        int rown = jTable2.getSelectedRow();
        int ma = (int) jTable2.getValueAt(rown, 0);
        List<ChiTietPhieuNhap> listSP = new ArrayList<>();
        listSP = ctdao.selectByIds(ma);
        try {
            for (ChiTietPhieuNhap tv : listSP) {
                Object[] rows = new Object[]{
                    tv.getMa(),
                    tv.getMapn(),
                    ctdao.convertToTenSach(tv.getMasach()),
                    tv.getGia(),
                    tv.getSoluong(),
                    tv.getThanhtien()
                };
                modelSP.addRow(rows);
            }
        } catch (Exception e) {
        }

    }

    // Phương thức cập nhật chi tiết phiếu nhập
private void updateCT() {
    ChiTietPhieuNhap model = getFormCT1();
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

//// Phương thức xóa chi tiết phiếu nhập
//private void deleteCT() {
//    int maCT = Integer.parseInt(txtMaCT.getText());
//    try {
//        ctdao.delete(maCT);
//        this.getTableCT();
//        
//        // Gọi phương thức tính tổng thành tiền và hiển thị kết quả
//        updateTongThanhTien();
//        
//        MsgBox.alert(this, "Xóa chi tiết phiếu nhập thành công!");
//    } catch (Exception e) {
//        MsgBox.alert(this, "Xóa chi tiết phiếu nhập thất bại!");
//        e.printStackTrace();
//    }
//}

// Phương thức tính tổng thành tiền và hiển thị kết quả
//private void updateTongThanhTien() {
//    int tongThanhTien = tinhTongThanhTien(ctdao.selectAll());
//    // Sử dụng giá trị tongThanhTien theo nhu cầu của bạn, có thể hiển thị trên giao diện người dùng
//}

    private ChiTietPhieuNhap getFormCT1() {
    ChiTietPhieuNhap s = new ChiTietPhieuNhap();
    
    // Kiểm tra xem có hàng được chọn hay không
    int selectedRow = tblCTPN.getSelectedRow();
    if (selectedRow >= 0) {
        s.setMa((int) tblCTPN.getValueAt(selectedRow, 0));
        s.setMapn(Integer.parseInt(txtMa.getText()));
        s.setMasach(ctdao.convertToMaSach(txtSach.getText()));
        s.setGia(Integer.valueOf(txtGia.getText()));
        s.setSoluong(Integer.valueOf(txtSL.getText()));
        s.setThanhtien(Integer.valueOf(txtThanhTien.getText()));
    } else {
        // Xử lý trường hợp không có hàng nào được chọn
        MsgBox.alert(this, "Vui lòng chọn một hàng trong bảng chi tiết phiếu nhập!");
    }
    
    return s;
}


    public int thanhTienGia() {
        int gia = Integer.parseInt(txtGia.getText());
        int soluong = Integer.parseInt(txtSL.getText());
        int thanhtien = gia * soluong;
        txtThanhTien.setText("" + thanhtien);
        return thanhtien;
    }

    public int thanhTienSoLuong() {
        int gia = Integer.parseInt(txtGia.getText());
        int soluong = Integer.parseInt(txtSL.getText());
        int thanhtien = gia * soluong;
        txtThanhTien.setText("" + thanhtien);
        return thanhtien;
    }

    public int tinhTongThanhTienTheoPhieuNhap(int maPhieuNhap) {
    int tongThanhTien = 0;
    List<ChiTietPhieuNhap> chiTietList = ctdao.selectByIds(maPhieuNhap);

    for (ChiTietPhieuNhap chiTiet : chiTietList) {
        tongThanhTien += chiTiet.getThanhtien();
    }

    return tongThanhTien;
}

    private void filterByNCC(int selectedNCC) {
        // Gọi phương thức của DAO hoặc thực hiện các truy vấn để lấy dữ liệu đã lọc
        List<PhieuNhap> filteredData = pndao.getPhieuNhapBy(selectedNCC);

        // Xóa tất cả dữ liệu hiện tại trong bảng
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        // Thêm dữ liệu đã lọc vào bảng
        for (PhieuNhap phieuNhap : filteredData) {
            model.addRow(new Object[]{
                phieuNhap.getMa(),
                nccdao.convertToTenNCC(phieuNhap.getMancc()),
                nvdao.convertToTenNV(phieuNhap.getManv()),
                phieuNhap.getNgaynhap(),
                phieuNhap.getTong()
            });
        }
    }

    private void timkiemSach(){
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>
                (tblSach.getModel());
        tblSach.setRowSorter(rowSorter);
        RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + txtTimkiemSach.getText());
        rowSorter.setRowFilter(rowFilter);
    }

    private void clickTableSach() {
        Sach sach = new Sach();
        int i = tblSach.getSelectedRow();
        if (i > -1) {
            try {
                
                
                txtSach.setText(tblSach.getValueAt(i, 0).toString());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn vào bảng");
        }

    }
    
    private void fillTableSach(){
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            List<Sach> list = sdao.selectAll();
            for (Sach dg : list) {
                Object[] row = {
                    dg.getTen()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu phiếu nhập!");
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

        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblTen1 = new javax.swing.JLabel();
        lblChucVu1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        dateNgayNhap = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        cboNCC = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboNV = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtTong = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtSach = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTPN = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        combobox3 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTimkiemSach = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(153, 102, 0));

        jPanel8.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTen1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        lblTen1.setText("Tên đăng nhập:");

        lblChucVu1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        lblChucVu1.setText("Chức vụ: ");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(153, 102, 0));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1100, 650));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 650));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        dateNgayNhap.setBackground(new java.awt.Color(255, 255, 255));
        dateNgayNhap.setDateFormatString("yyyy-MM-dd");
        dateNgayNhap.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel1.setText("Ngày nhập");

        cboNCC.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setText("Nhà cung cấp");

        cboNV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        cboNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Nhân viên");

        txtTong.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTong.setText("0");
        txtTong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Tổng tiền");

        jButton2.setBackground(new java.awt.Color(204, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton2.setText("Sửa");

        jButton3.setBackground(new java.awt.Color(204, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton3.setText("In");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton4.setText("Xuất");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(204, 153, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton5.setText("Xóa");

        jButton6.setBackground(new java.awt.Color(204, 153, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton6.setText("Thêm");

        jButton7.setBackground(new java.awt.Color(204, 153, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton7.setText("Mới");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cboNCC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cboNV, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTong, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47))))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 14, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(153, 102, 0))); // NOI18N

        txtSach.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Sách");

        txtSL.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtSL.setText("0");
        txtSL.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSLActionPerformed(evt);
            }
        });

        txtGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtGia.setText("0");
        txtGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Số lượng");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Giá");

        txtMa.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMa.setText("0");
        txtMa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel9.setText("Mã");

        txtThanhTien.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtThanhTien.setText("0");
        txtThanhTien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel10.setText("Thành tiền");

        jButton8.setBackground(new java.awt.Color(204, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton8.setText("Thêm");

        jButton10.setBackground(new java.awt.Color(204, 153, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton10.setText("Xóa");

        jButton9.setBackground(new java.awt.Color(204, 153, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton9.setText("Sửa");

        jButton11.setBackground(new java.awt.Color(204, 153, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton11.setText("Mới");

        jButton12.setBackground(new java.awt.Color(204, 153, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jButton12.setText("Chọn sách");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addGap(65, 65, 65)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(113, 113, 113)
                                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(41, 41, 41)
                                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(113, 113, 113)))
                                .addGap(48, 48, 48))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(txtThanhTien)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 238, -1, 280));

        jTabbedPane1.addTab("PHIẾU NHẬP", jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCTPN.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        tblCTPN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã chi tiết", "Mã phiếu nhập", "Sách", "Giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTPNMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTPN);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 345));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 510, 410));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên", "Ngày nhập", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 345));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 510, 410));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel11.setText("Tìm kiếm");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 420, 30));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 360, 30));

        combobox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(combobox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 290, 30));

        jLabel3.setText("Nhà cung cấp");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jTabbedPane1.addTab("DANH SÁCH", jPanel4);

        jLabel12.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 102, 0));
        jLabel12.setText("Danh Sách Sách");

        txtTimkiemSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemSachKeyReleased(evt);
            }
        });

        jLabel13.setText("Tìm kiếm");

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "SÁCH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel12))
                            .addComponent(txtTimkiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(4, 4, 4)))
                .addComponent(txtTimkiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TÌM SÁCH", jPanel9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(615, Short.MAX_VALUE)
                .addComponent(lblTen1)
                .addGap(127, 127, 127)
                .addComponent(lblChucVu1)
                .addGap(163, 163, 163)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTen1)
                            .addComponent(lblChucVu1))))
                .addContainerGap(611, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            clickTablePN();
        }
        getTableCT();
    }//GEN-LAST:event_jTable2MouseClicked

    private void tblCTPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTPNMouseClicked
        // TODO add your handling code here:
        clickTableCTPN();
//        hienThiDuLieuPhieuNhapTuChiTietPhieuNhap();
    }//GEN-LAST:event_tblCTPNMouseClicked

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void txtSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSLActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        com.qltv.utils.ExportExcel ex = new ExportExcel();
	ex.xuatExcel(jTable2);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int i = jTable2.getSelectedRow();
				if (i > -1) {
					int ma = Integer.parseInt(jTable2.getValueAt(i, 0).toString());

					com.qltv.utils.WritePDF wpdf = new WritePDF();
					wpdf.xuatPDFPhieuNhap(tblCTPN, ma);

				} else {
					JOptionPane.showMessageDialog(null, "Bạn Chưa Click Vào Table Để Xuất Hoá Đơn");
				}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTimkiemSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemSachKeyReleased
        timkiemSach();
    }//GEN-LAST:event_txtTimkiemSachKeyReleased

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if(evt.getClickCount() == 2){
            clickTableSach();
        }
        getTableCT();
    }//GEN-LAST:event_tblSachMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboNCC;
    private javax.swing.JComboBox<String> cboNV;
    private javax.swing.JComboBox<String> combobox3;
    private com.toedter.calendar.JDateChooser dateNgayNhap;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblChucVu1;
    private javax.swing.JLabel lblTen1;
    private javax.swing.JTable tblCTPN;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSach;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimkiemSach;
    private javax.swing.JTextField txtTong;
    // End of variables declaration//GEN-END:variables

    void init() {
        try {
            lblTen1.setText("Tên đăng nhập: " + Auth.user.getUser());
            lblChucVu1.setText("Chức vụ: " + String.valueOf(Auth.user.isQuyen() ? "Nhân viên" : "Quản lý"));
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn phải đăng nhập trước khi sử dụng!");
        }
        dateNgayNhap.setDate(XDate.now());
    }
}
