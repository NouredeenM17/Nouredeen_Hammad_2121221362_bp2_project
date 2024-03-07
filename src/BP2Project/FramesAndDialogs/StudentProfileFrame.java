package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.Regex;
import BP2Project.DatabaseAccessClasses.*;
import BP2Project.ContainerClasses.Student;
import com.mysql.cj.util.StringUtils;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class StudentProfileFrame extends javax.swing.JFrame {
    
    private Student student;
    
    public StudentProfileFrame(Student student) {
        initComponents();
        this.student = student;
        
        txt_name.setToolTipText("Name must contain letters only");
        txt_surname.setToolTipText("Surname must contain letters only");
        txt_email.setToolTipText("Email must be in this format (____@___.edu.tr)");
        
        fillFields();
    }

    //fills fields with current student's info 
    private void fillFields() {
        txt_username.setText(student.getUsername());
        txt_name.setText(student.getName());
        txt_surname.setText(student.getSurname());
        txt_email.setText(student.getEmail());
        spinner.setValue(student.getAge());
        combo.setSelectedItem(student.getDepartment());
        if (student.getIsMale()) {
            rbn_male.setSelected(true);
        } else {
            rbn_female.setSelected(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btn_passreset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        spinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        btn_sendedit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_department = new javax.swing.JLabel();
        rbn_male = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        rbn_female = new javax.swing.JRadioButton();
        combo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profile Editor");
        setMinimumSize(new java.awt.Dimension(450, 550));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        btn_passreset.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_passreset.setText("Reset Password");
        btn_passreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_passresetActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel1.setText("Name");

        spinner.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        spinner.setModel(new javax.swing.SpinnerNumberModel(20, 16, 100, 1));

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel2.setText("Surname");

        btn_sendedit.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_sendedit.setText("Send Edit Request");
        btn_sendedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendeditActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel3.setText("Age");

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel4.setText("Gender");

        lbl_department.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        lbl_department.setText("Department");

        buttonGroup1.add(rbn_male);
        rbn_male.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        rbn_male.setText("Male");
        rbn_male.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel6.setText("Username");

        buttonGroup1.add(rbn_female);
        rbn_female.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        rbn_female.setText("Female");
        rbn_female.setOpaque(false);

        combo.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Computer Engineering", "Civil Engineering", "Humanities and Social Sciences", "Applied Arts and Design", "Traditional Turkish Arts" }));

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel5.setText("Email");

        txt_username.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_email.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_name.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_surname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel7.setText("Password");

        jLabel8.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel8.setText("Student Profile");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(lbl_department)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rbn_female)
                    .addComponent(txt_email)
                    .addComponent(txt_surname)
                    .addComponent(txt_name)
                    .addComponent(rbn_male)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_username)
                    .addComponent(btn_passreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(btn_sendedit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(40, 40, 40)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbn_male)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbn_female)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_department))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btn_sendedit)
                .addGap(39, 39, 39))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendeditActionPerformed
        String username = txt_username.getText();
        String name = txt_name.getText();
        String surname = txt_surname.getText();
        String email = txt_email.getText();
        int age = (int) spinner.getValue();
        String department = combo.getSelectedItem().toString();
        boolean isMale = rbn_male.isSelected();

        //checking that fields are not empty
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || StringUtils.isEmptyOrWhitespaceOnly(name)
                || StringUtils.isEmptyOrWhitespaceOnly(surname) || StringUtils.isEmptyOrWhitespaceOnly(email)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //checking if no changes were made to the starting values
        if (student.getUsername().compareTo(username) == 0 && student.getName().compareTo(name) == 0
                && student.getSurname().compareTo(surname) == 0 && student.getAge() == age
                && student.getIsMale() == isMale && student.getDepartment().compareTo(department) == 0
                && student.getEmail() == email) {

            JOptionPane.showMessageDialog(this, "No changes to your data were detected", "Edit Request couldn't be sent", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //regex to check validity of username (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(username)){
            JOptionPane.showMessageDialog(this, "Your username contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of Name and Surname
        if(!Regex.checkNameSurname(name, surname)){
            JOptionPane.showMessageDialog(this, "Your Name and Surname must only contain letters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of E-mail
        if(!Regex.checkEmail(email)){
            JOptionPane.showMessageDialog(this, "Your E-mail is not in the correct format (____@___.edu.tr)", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            //checking if the new username is unique
            if (student.getUsername().compareTo(username) != 0 && 
                    (!DB.isUnique(username, "Username", "Students") || !DB.isUnique(username, "Username", "Admins"))) {
                
                JOptionPane.showMessageDialog(this, "This username is not unique");
                return;
            }
            //checking if the new email is unique
            if (student.getEmail().compareTo(email) != 0 && !DB.isUnique(email, "Email", "Students")) {
                JOptionPane.showMessageDialog(this, "This email is not unique");
                return;
            }

            if (student.getEditReqID() == 0) {
                //if the student has no active requests, adds new edit request
                EditRequestDB.addEditRequest(student.getID(), username, name, surname, email, age, isMale, department);
                
                //updates private student object
                student = StudentDB.getStudentObject(student.getID());
                JOptionPane.showMessageDialog(this, "Edit Request has been sent to administrators successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //if the student has an active request, the student can choose to overwrite current request
                int input = JOptionPane.showConfirmDialog(this, "You already have an active Edit Request, do you want to overwrite it?", "Active Request Detected", JOptionPane.YES_NO_OPTION);
                if (input == JOptionPane.YES_OPTION) {
                    //overwrite edit request
                    EditRequestDB.updateEditRequest(EditRequestDB.getActiveEditReqID(student.getID()), username, name, surname, email, age, isMale, department);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentProfileFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sendeditActionPerformed

    private void btn_passresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_passresetActionPerformed
        //launches password reset dialog
        ResetPassDia dialog = new ResetPassDia(this, true, student);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_passresetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_passreset;
    private javax.swing.JButton btn_sendedit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_department;
    private javax.swing.JRadioButton rbn_female;
    private javax.swing.JRadioButton rbn_male;
    private javax.swing.JSpinner spinner;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
