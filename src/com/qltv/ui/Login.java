package com.qltv.ui;

import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.TaiKhoanDAO;
import com.qltv.entity.NhanVien;
import com.qltv.entity.TaiKhoan;
import com.qltv.utils.Auth;
import com.qltv.utils.MsgBox;
import com.qltv.utils.XValidate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RAVEN
 */
public class Login extends javax.swing.JFrame {

    int temp;
    TaiKhoanDAO nvdao = new TaiKhoanDAO();

    /**
     * Creates new form Main
     */
    public Login() {
        initComponents();
    }

    private void login() {
        String manv = txtMaNV.getText();
        String mk = String.valueOf(txtMatKhau.getPassword());
        System.out.println("User = " + manv);
        System.out.println("Pass = " + mk);
        TaiKhoan nv = nvdao.selectByIds(manv);
        if (nv == null) {
            MsgBox.alert(this, "Sai tên đăng nhập!");
        } else if (!mk.equals(nv.getPass())) {
            MsgBox.alert(this, "Sai mật khẩu!");
        } else {
            System.out.println("Logged in successfully...");
            MsgBox.alert(this, "Đăng nhập thành công!");
            Auth.user = nv;
            this.dispose();
        }

    }

    private void exit() {
        if (MsgBox.confirm(this, "Bạn muốn kết thúc ứng dụng?")) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSlide1 = new com.qltv.swing.PanelSlide();
        jLabel1 = new javax.swing.JLabel();
        viewPass = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblQuenMK = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        panelSlide1.setBackground(new java.awt.Color(255, 255, 255));
        panelSlide1.setMinimumSize(new java.awt.Dimension(1000, 500));
        panelSlide1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(215, 163, 90));
        jLabel1.setText("Đăng nhập");
        panelSlide1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, -1, -1));

        viewPass.setBackground(new java.awt.Color(255, 255, 102));
        viewPass.setForeground(new java.awt.Color(204, 204, 204));
        viewPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qltv/icon/show.png"))); // NOI18N
        viewPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewPassMouseClicked(evt);
            }
        });
        panelSlide1.add(viewPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 260, 30, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qltv/icon/hinhnenlogin.jpg"))); // NOI18N
        panelSlide1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 500));

        lblQuenMK.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblQuenMK.setForeground(new java.awt.Color(215, 163, 90));
        lblQuenMK.setText("Quên mật khẩu ?");
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseExited(evt);
            }
        });
        panelSlide1.add(lblQuenMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 310, -1, -1));

        jLabel5.setForeground(new java.awt.Color(215, 163, 90));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qltv/icon/close_20px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        panelSlide1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, -1, -1));

        txtMaNV.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaNV.setText("lynh");
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        panelSlide1.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, 310, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Username");
        panelSlide1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel6.setText("Password");
        panelSlide1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, -1, -1));

        btnLogin.setBackground(new java.awt.Color(204, 153, 0));
        btnLogin.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        btnLogin.setText("Đăng nhập");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        panelSlide1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 350, 130, 40));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMatKhau.setText("123456");
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        panelSlide1.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 310, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSlide1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSlide1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewPassMouseClicked
        if (temp % 2 == 0) {
            String view = String.valueOf(txtMatKhau.getPassword()).trim();
            if (view.isEmpty()) {
                return;
            }
            txtMatKhau.setEchoChar((char) 0);
            viewPass.setIcon(new ImageIcon("D:\\quanlythuvien\\src\\com\\qltv\\icon\\dontshow.png"));
            temp++;
        } else {
            String view = String.valueOf(txtMatKhau.getPassword()).trim();
            if (view.isEmpty()) {
                return;
            }
            txtMatKhau.setEchoChar('\uf06c');
            viewPass.setIcon(new ImageIcon("D:\\quanlythuvien\\src\\com\\qltv\\icon\\show.png"));
            temp++;
        }
    }//GEN-LAST:event_viewPassMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        if (txtMaNV.getText().trim().length() > 0) {
            if (txtMatKhau.getPassword().length > 0){
                this.login();
                new Main().setVisible(true);
            } else {
                MsgBox.alert(this, "Không được để trống tên mật khẩu.");
            }
        } else {
            MsgBox.alert(this, "Không được để trống tên đăng nhập.");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void lblQuenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseClicked
        
    }//GEN-LAST:event_lblQuenMKMouseClicked

    private void lblQuenMKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseExited
        
    }//GEN-LAST:event_lblQuenMKMouseExited


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblQuenMK;
    private com.qltv.swing.PanelSlide panelSlide1;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JLabel viewPass;
    // End of variables declaration//GEN-END:variables
}
