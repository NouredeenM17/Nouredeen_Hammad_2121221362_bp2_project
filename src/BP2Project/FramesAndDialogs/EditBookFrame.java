
package BP2Project.FramesAndDialogs;

import BP2Project.UtilAndMain.Regex;
import BP2Project.ContainerClasses.Book;
import BP2Project.DatabaseAccessClasses.BookDB;
import com.mysql.cj.util.StringUtils;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class EditBookFrame extends javax.swing.JFrame {

    private File imgFile;
    private Book book;
    
    public EditBookFrame(Book book) {
        initComponents();
        this.book = book;
        fillFields();
    }

    //it fills form fields
    private void fillFields(){
        txt_title.setText(book.getTitle());
        txt_author.setText(book.getAuthor());
        txt_bio.setText(Regex.convertDBdescString(book.getBio()));
        txt_desc.setText(Regex.convertDBdescString(book.getDescription()));
        lbl_image.setIcon(book.getImageIcon());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_browse = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btn_edit = new javax.swing.JButton();
        txt_title = new javax.swing.JTextField();
        txt_author = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_bio = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_desc = new javax.swing.JTextArea();
        lbl_image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1027, 1000));
        setPreferredSize(new java.awt.Dimension(1027, 1000));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));

        jLabel11.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel11.setText("Edit Book");

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel12.setText("Cover Image:");

        jLabel13.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel13.setText("Title:");

        btn_browse.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_browse.setText("Browse");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel14.setText("Author:");

        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("Maximum File Size:");

        jLabel16.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel16.setText("Bio:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("70 KB");

        jLabel18.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel18.setText("Description:");

        btn_edit.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        txt_title.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_author.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        txt_bio.setColumns(20);
        txt_bio.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_bio.setLineWrap(true);
        txt_bio.setRows(5);
        jScrollPane3.setViewportView(txt_bio);

        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Rec. Dimensions");

        jLabel20.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("333x500");

        txt_desc.setColumns(20);
        txt_desc.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        txt_desc.setLineWrap(true);
        txt_desc.setRows(5);
        jScrollPane4.setViewportView(txt_desc);

        lbl_image.setMaximumSize(new java.awt.Dimension(350, 550));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(btn_browse))
                        .addComponent(jLabel14)
                        .addComponent(jLabel13))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(txt_author)
                            .addComponent(txt_title, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addComponent(lbl_image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(393, 393, 393))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addGap(136, 136, 136)
                                .addComponent(jLabel16)
                                .addGap(178, 178, 178)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(btn_browse)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        //sets a file extention filter to only be able to select image files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        
        //launches JFileChooser
        JFileChooser browse = new JFileChooser();
        browse.setFileFilter(filter);
        int input = browse.showOpenDialog(this);
        if(input!=JFileChooser.APPROVE_OPTION)
            return;

        imgFile = browse.getSelectedFile();
        //checks if file size >= 70KB
        if(imgFile.length()> 70000){
            JOptionPane.showMessageDialog(this, "File size too large, the maximum allowed size is 70KB!");
            imgFile = null;
            return;
        }
        try {
            //turns the File into an ImageIcon
            ImageIcon img = new ImageIcon(ImageIO.read(imgFile));
            //displays the ImageIcon on the frame
            lbl_image.setIcon(img);
        } catch (IOException ex) {
            Logger.getLogger(AddBookFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        String title = txt_title.getText();
        String author = txt_author.getText();
        String bio = txt_bio.getText();
        String desc = txt_desc.getText();
        //checks for empty fields
        if(StringUtils.isEmptyOrWhitespaceOnly(title)||StringUtils.isEmptyOrWhitespaceOnly(author)
            ||StringUtils.isEmptyOrWhitespaceOnly(bio)||StringUtils.isEmptyOrWhitespaceOnly(desc)){
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if(imgFile == null && lbl_image != null){
                //if image is not changed
                BookDB.updateBook(book.getId(),title,author,bio,desc);
            } else {
                //if a new image is selected
                BookDB.updateBook(book.getId(),title,author,bio,desc,imgFile);
            }
            
            JOptionPane.showMessageDialog(this, "Book edited successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(AddBookFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_edit;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JTextField txt_author;
    private javax.swing.JTextArea txt_bio;
    private javax.swing.JTextArea txt_desc;
    private javax.swing.JTextField txt_title;
    // End of variables declaration//GEN-END:variables
}
