
package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.Regex;
import BP2Project.ContainerClasses.Admin;
import BP2Project.DatabaseAccessClasses.AdminDB;
import BP2Project.DatabaseAccessClasses.DB;
import com.mysql.cj.util.StringUtils;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class AdminProfileFrame extends javax.swing.JFrame {

    private Admin admin;
    
    public AdminProfileFrame(Admin admin) {
        initComponents();
        this.admin = admin;
        
        fillFields();
    }

    //Fills profile fields
    private void fillFields() {
        txt_username.setText(admin.getUsername());
        txt_name.setText(admin.getName());
        txt_surname.setText(admin.getSurname());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_surname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btn_passreset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        btn_confirm = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        txt_surname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel7.setText("Password");

        btn_passreset.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_passreset.setText("Reset Password");
        btn_passreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_passresetActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel1.setText("Name");

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel2.setText("Surname");

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel6.setText("Username");

        txt_username.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_name.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        btn_confirm.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_confirm.setText("Confirm");
        btn_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel3.setText("Admin Profile");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(txt_surname)
                            .addComponent(txt_name)
                            .addComponent(btn_passreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btn_confirm)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btn_passreset))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addComponent(btn_confirm)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_passresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_passresetActionPerformed
        //launches reset password dialog
        ResetPassDia dialog = new ResetPassDia(this, true, admin);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_passresetActionPerformed

    private void btn_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmActionPerformed
        String username = txt_username.getText();
        String name = txt_name.getText();
        String surname = txt_surname.getText();

        //checking that fields are not empty
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || StringUtils.isEmptyOrWhitespaceOnly(name)
            || StringUtils.isEmptyOrWhitespaceOnly(surname)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //regex to check validity of username (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(username)){
            JOptionPane.showMessageDialog(this, "Your username contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //checking if no changes were made to the starting values
        if (admin.getUsername().compareTo(username) == 0 && admin.getName().compareTo(name) == 0
            && admin.getSurname().compareTo(surname) == 0) {
            
            JOptionPane.showMessageDialog(this, "No changes to your data were detected", "Edit Request couldn't be sent", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            //checking if the new username is unique
            if (admin.getUsername().compareTo(username) != 0 && 
                    (!DB.isUnique(username, "Username", "Students") || !DB.isUnique(username, "Username", "Admins"))) {
                
                JOptionPane.showMessageDialog(this, "This username is not unique");
                return;
            }

            //updates admin data in the database
            AdminDB.updateAdmin(admin.getID(), username, name, surname);
            //updates the private admin object in the frame
            admin = AdminDB.getAdminObj(username);
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentProfileFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "Profile edited successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_confirmActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_confirm;
    private javax.swing.JButton btn_passreset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
