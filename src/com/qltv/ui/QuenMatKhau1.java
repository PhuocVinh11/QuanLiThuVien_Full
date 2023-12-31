/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qltv.ui;

import com.qltv.dao.NhanVienDAO;
import com.qltv.dao.TaiKhoanDAO;
import com.qltv.entity.NhanVien;
import com.qltv.entity.TaiKhoan;
import com.qltv.utils.MsgBox;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Toolkit;
import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class QuenMatKhau1 extends javax.swing.JFrame {

    /**
     * Creates new form QuenMatKhau1
     */
    public QuenMatKhau1() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("QUÊN MẬT KHẨU");
        txtPass.setToolTipText("Mật khẩu mới của bạn !");
        txtRecode.setToolTipText("Nhập mã xác nhận được gửi đến email của bạn !");
        randCode();
    }

    public void clearForm() {
        txtRecode.setText("");
        txtPass.setText("");
        txtRepass.setText("");
    }

    public void randCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String randomCode = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        lblCode.setText(randomCode);
        System.out.println(lblCode.getText());

    }
    
    public void sendMail() {
        final String username = "vinhnppc06300@fpt.edu.vn";
        final String password = "";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.fpt.edu.vn");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        //Đăng nhập gmail
        // Create a mail session
        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
//            @Override
            protected javax.mail.PasswordAuthentication getTextAuthentication() {
                javax.mail.PasswordAuthentication mail = new javax.mail.PasswordAuthentication(username, password);
                return mail;
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(txtEmail.getText()));

            // Set the email subject and content
            message.setSubject("Thay doi mat khau !");
            message.setText("Ma xac nhan cua ban la : " + lblCode.getText());

            // Send the message
            Transport.send(message);
            MsgBox.alert(this, "Email đã được gửi !");
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
    }
        private String hashPassword(String password) {
        try {
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[16];
//            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte[] hashedPassword = md.digest(password.getBytes());

//             Combine salt and hashed password and encode in Base64
//            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedHashedPassword = Base64.getEncoder().encodeToString(hashedPassword);

            return encodedHashedPassword;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateForm() {  // Kiểm tra dữ liệu nhập vào 
        if (txtUsername.getText().isEmpty() || txtRecode.getText().isEmpty()
                || txtPass.getText().isEmpty() || txtRepass.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtRepass = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtRecode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        btnChangePass = new javax.swing.JButton();
        btnSendCode = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblCode = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 102, 0));
        jLabel1.setText("QUÊN MẬT KHẨU");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, -1, -1));

        txtRepass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtRepass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRepass, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 325, 31));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 102, 0));
        jLabel2.setText("Đăng nhập");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, -1, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 230, 31));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel4.setText("Xác nhận");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, -1, -1));

        txtUsername.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 325, 31));

        txtRecode.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtRecode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRecode, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 325, 31));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel5.setText("Email");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel7.setText("Mã xác nhận");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        txtPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        txtPass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 325, 31));

        btnChangePass.setBackground(new java.awt.Color(153, 102, 0));
        btnChangePass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        btnChangePass.setText("Lấy lại mật khẩu");
        btnChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePassActionPerformed(evt);
            }
        });
        jPanel1.add(btnChangePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 390, 210, 30));

        btnSendCode.setBackground(new java.awt.Color(153, 102, 0));
        btnSendCode.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        btnSendCode.setText("Gửi");
        btnSendCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendCodeActionPerformed(evt);
            }
        });
        jPanel1.add(btnSendCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 150, -1, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel8.setText("Mật khẩu mới");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, -1, -1));
        jPanel1.add(lblCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 350, 60, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel3.setText("Tên đăng nhập");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePassActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {

            TaiKhoanDAO dao = new TaiKhoanDAO();

            try {
                //So sánh mật khẩu có trùng hay không ?

                String s1 = new String(txtPass.getText());
                String s2 = new String(txtRepass.getText());

                if (s1.equals(s2) == false) {
                    MsgBox.alert(this, "Mật khẩu không khớp");
                    txtRepass.setBackground(Color.red);
                    return;
                }

                //So sánh mã xác nhận có trùng hay không ?
                String s3 = lblCode.getText();
                String s4 = txtRecode.getText();

                if (s3.equals(s4) == false) {
                    MsgBox.alert(this, "Mã xác nhận không khớp");
                    txtRecode.setBackground(Color.red);
                    return;
                }

                TaiKhoan nv = dao.selectByIds(txtUsername.getText()); // Tìm kiếm mã người dùng
                if (nv == null) {
                    MsgBox.showErrorDialog(this, "Tên đăng nhập không hợp lệ", "LỖI !");
                    txtUsername.setBackground(Color.red);
                } else {
                    int i = JOptionPane.showConfirmDialog(this, "Bạn có muốn thay đổi mật khẩu không ?",
                            "THÔNG BÁO !", JOptionPane.YES_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (i == 0) {
                        String mk = hashPassword(new String(txtPass.getText()));
                        //câu lệnh để thay đổi mật khẩu
                        try {
                            TaiKhoan user = new TaiKhoan();
                            user.setUser(txtUsername.getText());
                            user.setPass(mk);

                            dao.qmk(user);

                            MsgBox.alert(this, "Mật khẩu đã được thay đổi !");
                            clearForm();
                        } catch (Exception e) {
                            MsgBox.alert(this, "LỖI !!!\n" + e.getMessage());
                            //            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.showErrorDialog(this, e.getMessage(), "LỖI");
            }
        } else {
            MsgBox.alert(this, "Bạn chưa nhập đầy đủ thông tin");
            return;
        }
    }//GEN-LAST:event_btnChangePassActionPerformed

    private void btnSendCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendCodeActionPerformed
        // TODO add your handling code here:
        if (txtEmail.getText().isEmpty() == false) {
            sendMail();
        } else {
            MsgBox.alert(this, "Vui lòng nhập địa chỉ email của bạn để lấy lại mật khẩu !");
        }
    }//GEN-LAST:event_btnSendCodeActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.hide();
    }//GEN-LAST:event_jLabel2MouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(QuenMatKhau1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(QuenMatKhau1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(QuenMatKhau1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(QuenMatKhau1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new QuenMatKhau1().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePass;
    private javax.swing.JButton btnSendCode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCode;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtRecode;
    private javax.swing.JTextField txtRepass;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
