
package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.*;
import BP2Project.ContainerClasses.*;
import BP2Project.DatabaseAccessClasses.DB;
import com.mysql.cj.util.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

//This is the password reset dialog, it works for both students and admins
//because it takes a generic type (extends user, so can only be student or admin) on launch
public class ResetPassDia <T extends User> extends javax.swing.JDialog {
    
    private T user;
    
    public ResetPassDia(JFrame parent, boolean modal, T user) {
        super(parent, modal);
        this.user = user;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        txt_newPassReset = new javax.swing.JPasswordField();
        txt_newPassConfirm = new javax.swing.JPasswordField();
        txt_oldPassReset = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        jLabel9.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel9.setText("Confirm New Password");

        btn_reset.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_reset.setText("Reset Password");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        txt_newPassReset.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_newPassReset.setEchoChar('\u2022');

        txt_newPassConfirm.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_newPassConfirm.setEchoChar('\u2022');

        txt_oldPassReset.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel5.setText("Old Password");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel8.setText("New Password");

        jLabel2.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel2.setText("Reset Password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_oldPassReset)
                            .addComponent(txt_newPassReset)
                            .addComponent(txt_newPassConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_oldPassReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_newPassReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_newPassConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(btn_reset)
                .addGap(27, 27, 27))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        String oldPass = txt_oldPassReset.getText();
        String newPass = String.valueOf(txt_newPassReset.getPassword());
        String confirmPass = String.valueOf(txt_newPassConfirm.getPassword());

        //checks for empty or only whitespace fields
        if(StringUtils.isEmptyOrWhitespaceOnly(oldPass)||StringUtils.isEmptyOrWhitespaceOnly(newPass)||StringUtils.isEmptyOrWhitespaceOnly(confirmPass)){
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //regex to check validity of password (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(newPass)){
            JOptionPane.showMessageDialog(this, "Your password contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //checks if new and confirm passwords match
        if(newPass.compareTo(confirmPass)!=0){
            JOptionPane.showMessageDialog(this, "Passwords don't match", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            //checks if user is a student or an admin 
            if(user instanceof Student){
                //checks if the old password is correct
                if(Encrypt.hash(oldPass).compareTo(DB.getUserPass("Students", user.getUsername()))!=0){
                JOptionPane.showMessageDialog(this, "Old password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                }
                //applies the new password to the student's row in the database
                DB.update1Cell("Students", "Password", Encrypt.hash(newPass), "StudentID", user.getID());
            } else {
                //checks if the old password is correct
                if(Encrypt.hash(oldPass).compareTo(DB.getUserPass("Admins", user.getUsername()))!=0){
                JOptionPane.showMessageDialog(this, "Old password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                }
                //applies the new password to the admin's row in the database
                DB.update1Cell("Admins", "Password", Encrypt.hash(newPass), "AdminID", user.getID());
            }
            JOptionPane.showMessageDialog(this, "Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (NoSuchAlgorithmException | SQLException ex) {
            Logger.getLogger(StudentProfileFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_resetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reset;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_newPassConfirm;
    private javax.swing.JPasswordField txt_newPassReset;
    private javax.swing.JTextField txt_oldPassReset;
    // End of variables declaration//GEN-END:variables
}
