
package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.Regex;
import BP2Project.DatabaseAccessClasses.*;
import com.mysql.cj.util.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class NewAdminFrame extends javax.swing.JFrame {

    public NewAdminFrame() {
        initComponents();
        txt_pass.setEchoChar('•');
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_create = new javax.swing.JButton();
        txt_pass = new javax.swing.JPasswordField();
        txt_repass = new javax.swing.JPasswordField();
        chk_showpass = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel4.setText("Reenter Pass:");

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel5.setText("Name:");

        txt_username.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_name.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_surname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel6.setText("Surname:");

        btn_create.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_create.setText("Create Account");
        btn_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createActionPerformed(evt);
            }
        });

        txt_pass.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_pass.setEchoChar('\u2022');

        txt_repass.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_repass.setEchoChar('\u2022');

        chk_showpass.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        chk_showpass.setText("Show Password");
        chk_showpass.setOpaque(false);
        chk_showpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_showpassActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel1.setText("Create Admin Account");

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel2.setText("Username:");

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel3.setText("Password:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_pass)
                            .addComponent(txt_username)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_name)
                            .addComponent(txt_surname)
                            .addComponent(txt_repass, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chk_showpass))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(btn_create, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chk_showpass)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_repass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(btn_create)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createActionPerformed
        String username = txt_username.getText();
        String password = txt_pass.getText();
        String repass = txt_repass.getText();
        String name = txt_name.getText();
        String surname = txt_surname.getText();

        //checking that fields are not empty
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || StringUtils.isEmptyOrWhitespaceOnly(name)
            || StringUtils.isEmptyOrWhitespaceOnly(surname) || StringUtils.isEmptyOrWhitespaceOnly(password)
            || StringUtils.isEmptyOrWhitespaceOnly(repass)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //regex to check validity of username and password (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(username) && !Regex.checkForIllegalCharsAndLength(password)){
            JOptionPane.showMessageDialog(this, "Your username or password contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of Name and Surname
        if(!Regex.checkNameSurname(name, surname)){
            JOptionPane.showMessageDialog(this, "Your Name and Surname must only contain letters and a maximum of 15 letters long.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //checking if passwords match
        if(password.compareTo(repass)!=0){
            JOptionPane.showMessageDialog(this, "The passwords do not match!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            //checking if the new username is unique
            if (!DB.isUnique(username, "Username", "Students") || !DB.isUnique(username, "Username", "Admins")) {
                JOptionPane.showMessageDialog(this, "This username is not unique");
                return;
            }

            //adds new admin account
            AdminDB.addNewAdmin(username, password, name, surname);
            
        } catch (NoSuchAlgorithmException|SQLException ex) {
            Logger.getLogger(StudentProfileFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "Admin account created successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_createActionPerformed

    private void chk_showpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_showpassActionPerformed
        if(chk_showpass.isSelected()){
            txt_pass.setEchoChar((char) 0);
        } else {
            txt_pass.setEchoChar('•');
        }
    }//GEN-LAST:event_chk_showpassActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_create;
    private javax.swing.JCheckBox chk_showpass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_name;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JPasswordField txt_repass;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
