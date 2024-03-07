
package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.*;
import BP2Project.ContainerClasses.*;
import BP2Project.DatabaseAccessClasses.*;
import com.mysql.cj.util.StringUtils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public class LoginFrame extends javax.swing.JFrame {
    
    public LoginFrame() {
        initComponents();
        
        //sets tooltips
        txt_name.setToolTipText("Name must contain letters only");
        txt_surname.setToolTipText("Surname must contain letters only");
        txt_email.setToolTipText("Email must be in this format (____@___.edu.tr)");
    }

    //hides and shows password in both login and registration screens
    private void hideShowPass(JCheckBox check,JPasswordField pass){
        if(check.isSelected()){
            pass.setEchoChar((char) 0);
        } else {
            pass.setEchoChar('•');
        }
    }
    
    //performs login and opens the student frame if username is a student
    private void studentLogin(String username, String password) throws SQLException, NoSuchAlgorithmException, IOException{
        Student studentObj = StudentDB.getStudentObject(username);
        String correctPass = DB.getUserPass("Students", username);
        
        //hashes entered password and compares it the password from the database
        if(Encrypt.hash(password).compareTo(correctPass)!=0){
            JOptionPane.showMessageDialog(this, "Password Incorrect!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //checks if user is banned
        if(studentObj.getIsBanned()){
            JOptionPane.showMessageDialog(this, "This account has been banned! \n Please contact the "
                    + "University technical department for more information", "Ban Notice", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //launches student frame and closes login frame
        StudentHomeFrame studentF = new StudentHomeFrame(studentObj);
        studentF.setLocationRelativeTo(null);
        studentF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentF.setVisible(true);
        studentF.processMessages();
        this.dispose();
    }
    
    //performs login and opens the admin frame if username is an admin
    private void adminLogin(String username, String password) throws SQLException, NoSuchAlgorithmException, IOException{
        Admin adminObj = AdminDB.getAdminObj(username);
        String correctPass = DB.getUserPass("Admins", username);
        
        //hashes entered password and compares it the password from the database
        if(Encrypt.hash(password).compareTo(correctPass)!=0){
            JOptionPane.showMessageDialog(this, "Password Incorrect!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //launches admin frame and closes login frame
        AdminHomeFrame adminF = new AdminHomeFrame(adminObj);
        adminF.setLocationRelativeTo(null);
        adminF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminF.setVisible(true);
        this.dispose();
    }
    
    //performs logout function in both the student and the admin home frames
    public static void logout(JFrame currentF){
        int confirm = JOptionPane.showConfirmDialog(currentF, "Are you sure you want to Logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if(!(confirm == JOptionPane.YES_OPTION))
            return;
        
        //launches login frame and disposes of current frame
        LoginFrame loginF = new LoginFrame();
        loginF.setLocationRelativeTo(null);
        loginF.setVisible(true);
        currentF.dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabbed_pane = new javax.swing.JTabbedPane();
        login_pane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_usernameL = new javax.swing.JTextField();
        btn_login = new javax.swing.JButton();
        chk_showpassL = new javax.swing.JCheckBox();
        txt_passL = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        register_pane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txt_name = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        txt_usernameR = new javax.swing.JTextField();
        passR = new javax.swing.JPasswordField();
        rbn_male = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rbn_female = new javax.swing.JRadioButton();
        combo = new javax.swing.JComboBox<>();
        spinner = new javax.swing.JSpinner();
        lbl_dept = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        chk_showpassR = new javax.swing.JCheckBox();
        btn_register = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        frmtxt_studentid = new javax.swing.JFormattedTextField();
        txt_email = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Screen");
        setMinimumSize(new java.awt.Dimension(460, 750));

        tabbed_pane.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N

        login_pane.setBackground(new java.awt.Color(255, 255, 255));
        login_pane.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMinimumSize(new java.awt.Dimension(348, 182));
        jPanel1.setPreferredSize(new java.awt.Dimension(348, 182));

        jLabel1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel1.setText("Username");

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel2.setText("Password");

        txt_usernameL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_usernameL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passLActionPerformed(evt);
            }
        });

        btn_login.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        chk_showpassL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        chk_showpassL.setText("Show Password");
        chk_showpassL.setOpaque(false);
        chk_showpassL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk_showpassLStateChanged(evt);
            }
        });

        txt_passL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_passL.setToolTipText("");
        txt_passL.setEchoChar('\u2022');
        txt_passL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passLActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel11.setText("Login");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_usernameL, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_passL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chk_showpassL, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(btn_login)
                                .addGap(104, 104, 104))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel11)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_usernameL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_passL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk_showpassL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_login)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 88;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(188, 221, 255, 222);
        login_pane.add(jPanel1, gridBagConstraints);

        tabbed_pane.addTab("Login", login_pane);

        register_pane.setBackground(new java.awt.Color(255, 255, 255));
        register_pane.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setMinimumSize(new java.awt.Dimension(440, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(440, 600));

        txt_name.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_surname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_usernameR.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        passR.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        passR.setEchoChar('\u2022');

        buttonGroup1.add(rbn_male);
        rbn_male.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        rbn_male.setSelected(true);
        rbn_male.setText("Male");
        rbn_male.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel4.setText("Surname");

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel5.setText("Username");

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel6.setText("Password");

        buttonGroup1.add(rbn_female);
        rbn_female.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        rbn_female.setText("Female");
        rbn_female.setOpaque(false);

        combo.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Computer Engineering", "Civil Engineering", "Humanities and Social Sciences", "Applied Arts and Design", "Traditional Turkish Arts" }));

        spinner.setModel(new javax.swing.SpinnerNumberModel(20, 16, 100, 1));

        lbl_dept.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        lbl_dept.setText("Department");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel8.setText("Age");

        chk_showpassR.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        chk_showpassR.setText("Show Password");
        chk_showpassR.setOpaque(false);
        chk_showpassR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_showpassRActionPerformed(evt);
            }
        });

        btn_register.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_register.setText("Register");
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel7.setText("Student ID");

        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("10 Digits");

        try {
            frmtxt_studentid.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAAAAAAAAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        frmtxt_studentid.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_email.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel10.setText("E-mail");

        jLabel12.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel12.setText("Register");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbn_female)
                                        .addGap(190, 190, 190))
                                    .addComponent(rbn_male)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel8)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(chk_showpassR, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(passR)
                                        .addComponent(txt_usernameR, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txt_surname)
                                        .addComponent(txt_name)
                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(89, 89, 89))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_register)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(lbl_dept))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combo, 0, 225, Short.MAX_VALUE)
                                    .addComponent(frmtxt_studentid))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(24, 24, 24))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_usernameR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(passR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chk_showpassR)
                        .addGap(30, 30, 30)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel6))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(137, 137, 137)
                            .addComponent(jLabel3))))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbn_male)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbn_female)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_register)
                        .addGap(43, 43, 43)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frmtxt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dept))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -9;
        gridBagConstraints.ipady = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 181, 40, 179);
        register_pane.add(jPanel2, gridBagConstraints);

        tabbed_pane.addTab("Register", register_pane);

        getContentPane().add(tabbed_pane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chk_showpassRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_showpassRActionPerformed
        hideShowPass(chk_showpassR, passR);
    }//GEN-LAST:event_chk_showpassRActionPerformed

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        String username = txt_usernameR.getText();
        String password = String.valueOf(passR.getPassword());
        String name = txt_name.getText();
        String surname = txt_surname.getText();
        String email = txt_email.getText();
        int age = (int)spinner.getValue();
        boolean isMale = rbn_male.isSelected();
        String studentIDtext = frmtxt_studentid.getText();
        String dept = combo.getSelectedItem().toString();
        
        //checks for empty or whitespace fields
        if(StringUtils.isEmptyOrWhitespaceOnly(email) || StringUtils.isEmptyOrWhitespaceOnly(password) 
                || StringUtils.isEmptyOrWhitespaceOnly(surname) || StringUtils.isEmptyOrWhitespaceOnly(username) 
                || StringUtils.isEmptyOrWhitespaceOnly(studentIDtext) || StringUtils.isEmptyOrWhitespaceOnly(name)){
            
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of username and password (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(username)&&!Regex.checkForIllegalCharsAndLength(password)){
            JOptionPane.showMessageDialog(this, "Your username or password contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of Student ID
        if(!Regex.checkStudentID(studentIDtext)){
            JOptionPane.showMessageDialog(this, "Your Student ID must consist of 10 digits only.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of Name and Surname
        if(!Regex.checkNameSurname(name, surname)){
            JOptionPane.showMessageDialog(this, "Your Name and Surname must only contain letters and a maximum of 15 letters long.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of E-mail
        if(!Regex.checkEmail(email)){
            JOptionPane.showMessageDialog(this, "Your E-mail is not in the correct format (____@___.edu.tr)", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //converts studentID from String to Long
        long studentID = Long.parseLong(studentIDtext);
        
        try {
            //checks if the username is already registered in Students and Admins
            if((!DB.isUnique(username,"Username", "Students"))||(!DB.isUnique(username, "Username", "Admins"))){
                JOptionPane.showMessageDialog(this, "This username is already registered!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //checks if the StudentID is already registered in Students
            if(!DB.isUnique(studentID+"", "StudentID", "Students")){
                //checks if that student is banned
                if(StudentDB.isBanned(studentID)){
                    JOptionPane.showMessageDialog(this, "This student ID has been banned before, you cannot register again!"
                            + "\n Please contact the University technical department for more information", "You've been Banned!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "This Student ID is already registered!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                return;
            }
            //checks if the email is already registered
            if(!DB.isUnique(email, "Email", "Students")){
                JOptionPane.showMessageDialog(this, "This Email is already registered!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //adds a new student to the DB
            StudentDB.addNewStudent(studentID,username,password,name,surname,email,age,isMale,dept);
            
        } catch (SQLException | NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_registerActionPerformed

    private void chk_showpassLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk_showpassLStateChanged
        hideShowPass(chk_showpassL, txt_passL);
    }//GEN-LAST:event_chk_showpassLStateChanged

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        String username = txt_usernameL.getText();
        String password = String.valueOf(txt_passL.getPassword());
        
        //checks for empty fields
        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //regex to check validity of username and password (checks for illegal characters)
        if(!Regex.checkForIllegalCharsAndLength(username)||!Regex.checkForIllegalCharsAndLength(password)){
            JOptionPane.showMessageDialog(this, "Your username or password contains illegal characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            //finds the table that contains the username
            String table = DB.findUsernamesTable(username);
            if(table == null){
                //if the username was not found in either table
                JOptionPane.showMessageDialog(this, "Username not registered!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if(table.compareTo("Admins")==0){
                //if the username is an admin
                adminLogin(username,password);
            } else {
                //if the username is a student
                studentLogin(username,password);
            }
        } catch (SQLException | NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_loginActionPerformed

    private void txt_passLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passLActionPerformed
        btn_loginActionPerformed(evt);
    }//GEN-LAST:event_txt_passLActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_register;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chk_showpassL;
    private javax.swing.JCheckBox chk_showpassR;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JFormattedTextField frmtxt_studentid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel lbl_dept;
    private javax.swing.JPanel login_pane;
    private javax.swing.JPasswordField passR;
    private javax.swing.JRadioButton rbn_female;
    private javax.swing.JRadioButton rbn_male;
    private javax.swing.JPanel register_pane;
    private javax.swing.JSpinner spinner;
    private javax.swing.JTabbedPane tabbed_pane;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JPasswordField txt_passL;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_usernameL;
    private javax.swing.JTextField txt_usernameR;
    // End of variables declaration//GEN-END:variables
}
