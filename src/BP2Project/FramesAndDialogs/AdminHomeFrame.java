package BP2Project.FramesAndDialogs;

import BP2Project.GraphicsClasses.ArrowPane;
import BP2Project.ContainerClasses.*;
import BP2Project.DatabaseAccessClasses.*;
import BP2Project.ContainerClasses.Admin;
import com.mysql.cj.util.StringUtils;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class AdminHomeFrame extends javax.swing.JFrame {

    private Admin admin;
    private DefaultListModel<EditRequest> editReqModel = new DefaultListModel<>();
    private int[] editReqModelIDs;
    private ArrayList<Book> bookArrLM;
    private DefaultTableModel modelLM;

    public AdminHomeFrame(Admin admin) throws SQLException, IOException {
        initComponents();
        this.admin = admin;

        //sets welcome label
        lbl_name_surnameER.setText(admin.getName() + " " + admin.getSurname());
        lbl_name_surnameLM.setText(admin.getName() + " " + admin.getSurname());
        //disables accept&reject buttons
        btn_acceptEditReq.setVisible(false);
        btn_rejectEditReq.setVisible(false);
        //sets popup menu
        list_editReq.setComponentPopupMenu(request_popup);

        refreshModels();
        refreshLibManTable();
    }

    //refreshes the library manager table
    private void refreshLibManTable() throws SQLException, IOException {
        Object[] result = BookDB.getLibraryBookTable();
        bookArrLM = (ArrayList<Book>) result[0];
        modelLM = (DefaultTableModel) result[1];

        table_libManager.setDefaultEditor(Object.class, null);
        table_libManager.setModel(modelLM);
    }

    //refreshes the edit request list editReqModel and ids array from the database
    private void refreshModels() throws SQLException {
        editReqModel = EditRequestDB.getEditReqListModel();
        editReqModelIDs = new int[editReqModel.getSize()];

        for (int i = 0; i < editReqModel.getSize(); i++) {
            editReqModelIDs[i] = editReqModel.getElementAt(i).getRequestID();
        }

        list_editReq.setModel((DefaultListModel) editReqModel);

        if (editReqModel.getSize() == 0) {
            emptyRequestInfo();
        }

        repaint();
    }

    //empties displayed request info (used when nothing is selected)
    private void emptyRequestInfo() {
        lbl_oldUsername.setText("");
        lbl_oldName.setText("");
        lbl_oldSurname.setText("");
        lbl_oldEmail.setText("");
        lbl_oldAge.setText("");
        lbl_oldIsMale.setText("");
        lbl_oldDepartment.setText("");

        lbl_newUsername.setText("");
        lbl_newName.setText("");
        lbl_newSurname.setText("");
        lbl_newEmail.setText("");
        lbl_newAge.setText("");
        lbl_newIsMale.setText("");
        lbl_newDepartment.setText("");

        btn_acceptEditReq.setVisible(false);
        btn_rejectEditReq.setVisible(false);
    }

    //fills request info of currently selected edit request
    private void fillRequestInfo(int requestID) throws SQLException {
        EditRequest editReq = EditRequestDB.getEditReqObj(requestID);
        Student oldStudent = StudentDB.getStudentObject(editReq.getStudentID());

        //fills current info section
        lbl_oldUsername.setText(oldStudent.getUsername());
        lbl_oldName.setText(oldStudent.getName());
        lbl_oldSurname.setText(oldStudent.getSurname());
        lbl_oldEmail.setText(oldStudent.getEmail());
        lbl_oldAge.setText("" + oldStudent.getAge());
        lbl_oldIsMale.setText(oldStudent.getIsMale() ? "Male" : "Female");
        lbl_oldDepartment.setText(oldStudent.getDepartment());

        //fills requested info section
        lbl_newUsername.setText(editReq.getNewUsername());
        lbl_newName.setText(editReq.getNewName());
        lbl_newSurname.setText(editReq.getNewSurname());
        lbl_newEmail.setText(editReq.getNewEmail());
        lbl_newAge.setText("" + editReq.getNewAge());
        lbl_newIsMale.setText(editReq.getNewIsMale() ? "Male" : "Female");
        lbl_newDepartment.setText(editReq.getNewDepartment());
    }

    //reject handling
    private void rejectRequest() {
        //Rejection dialog, a reason must be entered
        String reason = JOptionPane.showInputDialog(this, "Please enter a reason for rejection:", "Reject Request", JOptionPane.INFORMATION_MESSAGE);
        if (reason == null) {
            return;
        }
        if (reason.compareTo("") == 0) {
            JOptionPane.showMessageDialog(this, "You must enter a reason");
            return;
        }

        int index = list_editReq.getSelectedIndex();
        EditRequest reqObj = editReqModel.getElementAt(index);

        try {
            //sends rejection notification message
            MessageDB.createMessage(reqObj.getStudentID(), "Your Edit Request has been rejected, for the following reason:", reason);
            //rejects edit request and deletes request from database
            EditRequestDB.rejectEditRequest(reqObj.getRequestID());
            refreshModels();

            JOptionPane.showMessageDialog(this, "Edit request rejected successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);

            //if no requests are left, empty request info
            if (editReqModel.getSize() == 0) {
                emptyRequestInfo();
            } else {
                //checks what index should be selected
                if (index > 0) {
                    list_editReq.setSelectedIndex(index - 1);
                } else {
                    list_editReq.setSelectedIndex(index);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //accept handling
    private void acceptRequest() {
        if (list_editReq.getSelectedValue() == null) {
            return;
        }

        int index = list_editReq.getSelectedIndex();

        try {
            //adds notification message to database, and assigns it to relavent student
            MessageDB.createMessage(EditRequestDB.getEditReqObj(editReqModelIDs[index]).getStudentID(), "Your Edit Request has been accepted!", "");
            //applies edit request (updates student info and removes edit request)
            EditRequestDB.applyEditRequest(editReqModelIDs[index]);

            JOptionPane.showMessageDialog(this, "Edit request accepted!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            refreshModels();

            //if no requests are left, empty request info
            if (editReqModel.getSize() == 0) {
                emptyRequestInfo();
            } else {
                //checks what index should be selected
                if (index > 0) {
                    list_editReq.setSelectedIndex(index - 1);
                } else {
                    list_editReq.setSelectedIndex(index);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }

    private void fillLibManFields(int bookID, int rentInfoID) throws SQLException, IOException {
        Book book = BookDB.getBookObj(bookID);
        lbl_title.setText("<html>" + book.getTitle() + "</html>");
        lbl_author.setText(book.getAuthor());
        lbl_image.setIcon(book.getImageIcon());
        long rentedTo = book.getRentedTo();
        if (rentInfoID == -1) {
            lbl_isNotRented.setVisible(true);
            pane_studentRentInfo.setVisible(false);
            lbl_rentstartdate.setText("");
            lbl_rentdeadline.setText("");
        } else {
            lbl_isNotRented.setVisible(false);
            pane_studentRentInfo.setVisible(true);

            Student student = StudentDB.getStudentObject(rentedTo);
            lbl_studentIDLM.setText(rentedTo + "");
            lbl_nameLM.setText(student.getName());
            lbl_surnameLM.setText(student.getSurname());
            lbl_departmentLM.setText(student.getDepartment());

            RentInfo rentInfo = RentInfoDB.getRentInfoObj(rentInfoID);
            lbl_rentstartdate.setText(rentInfo.getRentTimestamp().toString());
            lbl_rentdeadline.setText(rentInfo.getDeadlineTimestamp().toString());
            if (rentInfo.getDeadlineTimestamp().compareTo(new Timestamp(System.currentTimeMillis())) <= 0) {
                lbl_rentdeadline.setForeground(Color.RED);
            } else {
                lbl_rentdeadline.setForeground(Color.BLACK);
            }
        }
    }

    private void addBookControl() {
        AddBookFrame frame = new AddBookFrame();
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //updating library manager
                    refreshLibManTable();
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frame.setVisible(true);
    }

    private void editBookControl() {
        int selectedRow = table_libManager.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EditBookFrame frame;
        try {
            Book book = BookDB.getBookObj(bookArrLM.get(selectedRow).getId());
            frame = new EditBookFrame(book);
            frame.setLocationRelativeTo(null);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        //updating library manager
                        refreshLibManTable();
                        int rentInfoID = RentInfoDB.getRentInfoIDofBook(book.getId());
                        fillLibManFields(book.getId(), rentInfoID);
                    } catch (SQLException | IOException ex) {
                        Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            frame.setVisible(true);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteBookControl() {
        int selectedRow = table_libManager.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Book selectedBook = BookDB.getBookObj(bookArrLM.get(selectedRow).getId());
            if (selectedBook.getRentedTo() != 0) {
                int response = JOptionPane.showConfirmDialog(this, "This book titled: " + selectedBook.getTitle()
                        + " is rented to someone, are you sure you want to delete it?", "Warning!", JOptionPane.YES_NO_OPTION);
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book titled '"
                        + selectedBook.getTitle() + "'?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            BookDB.deleteBook(selectedBook.getId());
            JOptionPane.showMessageDialog(this, "Book deleted Successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
            refreshLibManTable();

            int bookID = bookArrLM.get(0).getId();
            int rentInfoID = RentInfoDB.getRentInfoIDofBook(bookID);
            fillLibManFields(bookID, rentInfoID);
            table_libManager.setRowSelectionInterval(0, 0);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenu1 = new javax.swing.JMenu();
        request_popup = new javax.swing.JPopupMenu();
        mi_apply = new javax.swing.JMenuItem();
        mi_reject = new javax.swing.JMenuItem();
        library_popup = new javax.swing.JPopupMenu();
        mi_addBook = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mi_editBook = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mi_deleteBook = new javax.swing.JMenuItem();
        tabbedpane = new javax.swing.JTabbedPane();
        pane_editreq = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_libManager = new javax.swing.JTable();
        btn_addBook = new javax.swing.JButton();
        btn_editBook = new javax.swing.JButton();
        btn_deleteBook = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        lbl_name_surnameLM = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lbl_isNotRented = new javax.swing.JLabel();
        pane_studentRentInfo = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbl_studentIDLM = new javax.swing.JLabel();
        lbl_nameLM = new javax.swing.JLabel();
        lbl_surnameLM = new javax.swing.JLabel();
        lbl_departmentLM = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_rentstartdate = new javax.swing.JLabel();
        lbl_rentdeadline = new javax.swing.JLabel();
        btn_warning = new javax.swing.JButton();
        btn_ban = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();
        lbl_title = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        pane_libman = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        pane_editReqDetails = new javax.swing.JPanel();
        pane_oldInfo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_oldUsername = new javax.swing.JLabel();
        lbl_oldName = new javax.swing.JLabel();
        lbl_oldAge = new javax.swing.JLabel();
        lbl_oldEmail = new javax.swing.JLabel();
        lbl_oldIsMale = new javax.swing.JLabel();
        lbl_oldDepartment = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_oldSurname = new javax.swing.JLabel();
        pane_newInfo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_newUsername = new javax.swing.JLabel();
        lbl_newName = new javax.swing.JLabel();
        lbl_newSurname = new javax.swing.JLabel();
        lbl_newAge = new javax.swing.JLabel();
        lbl_newIsMale = new javax.swing.JLabel();
        lbl_newDepartment = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_newEmail = new javax.swing.JLabel();
        btn_acceptEditReq = new javax.swing.JButton();
        btn_rejectEditReq = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        arrowPane = new ArrowPane();
        arrowPane1 = new ArrowPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_editReq = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        lbl_name_surnameER = new javax.swing.JLabel();
        menubar = new javax.swing.JMenuBar();
        menu_profile = new javax.swing.JMenu();
        mi_createadmin = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mi_editProfile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mi_logout = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        mi_apply.setText("Accept Selected Request");
        mi_apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_applyActionPerformed(evt);
            }
        });
        request_popup.add(mi_apply);

        mi_reject.setText("Reject Selected Request");
        mi_reject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_rejectActionPerformed(evt);
            }
        });
        request_popup.add(mi_reject);

        mi_addBook.setText("Add Book");
        mi_addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_addBookActionPerformed(evt);
            }
        });
        library_popup.add(mi_addBook);
        library_popup.add(jSeparator3);

        mi_editBook.setText("Edit Book");
        mi_editBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_editBookActionPerformed(evt);
            }
        });
        library_popup.add(mi_editBook);
        library_popup.add(jSeparator4);

        mi_deleteBook.setText("Delete Book");
        mi_deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_deleteBookActionPerformed(evt);
            }
        });
        library_popup.add(mi_deleteBook);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Home");
        setMinimumSize(new java.awt.Dimension(1197, 1050));
        setPreferredSize(new java.awt.Dimension(1171, 1050));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        tabbedpane.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        pane_editreq.setBackground(new java.awt.Color(255, 255, 255));
        pane_editreq.setLayout(new java.awt.GridBagLayout());

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        table_libManager.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        table_libManager.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_libManager.setComponentPopupMenu(library_popup);
        table_libManager.setRowHeight(30);
        table_libManager.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_libManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                table_libManagerMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(table_libManager);

        btn_addBook.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_addBook.setText("Add Book");
        btn_addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addBookActionPerformed(evt);
            }
        });

        btn_editBook.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_editBook.setText("Edit Book");
        btn_editBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editBookActionPerformed(evt);
            }
        });

        btn_deleteBook.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_deleteBook.setText("Delete Book");
        btn_deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteBookActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel21.setText("Welcome:");

        lbl_name_surnameLM.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jSplitPane3.setDividerLocation(500);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jSplitPane3.setLeftComponent(jPanel4);

        jLabel23.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        jLabel23.setText("Renting Info:");

        lbl_isNotRented.setFont(new java.awt.Font("Cambria", 2, 18)); // NOI18N
        lbl_isNotRented.setText("This book is not rented");

        jLabel24.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel24.setText("Student ID:");

        jLabel25.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel25.setText("Name:");

        jLabel26.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel26.setText("Surname:");

        jLabel27.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel27.setText("Department:");

        jLabel29.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel29.setText("Rent Start Date:");

        lbl_studentIDLM.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_nameLM.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_surnameLM.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_departmentLM.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel28.setText("Rent Deadline:");

        lbl_rentstartdate.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_rentdeadline.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        btn_warning.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_warning.setText("Send Warning");
        btn_warning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_warningActionPerformed(evt);
            }
        });

        btn_ban.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_ban.setText("Ban Student");
        btn_ban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pane_studentRentInfoLayout = new javax.swing.GroupLayout(pane_studentRentInfo);
        pane_studentRentInfo.setLayout(pane_studentRentInfoLayout);
        pane_studentRentInfoLayout.setHorizontalGroup(
            pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_studentRentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addComponent(jLabel29))
                .addGap(24, 24, 24)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_studentRentInfoLayout.createSequentialGroup()
                        .addComponent(btn_warning)
                        .addGap(32, 32, 32)
                        .addComponent(btn_ban))
                    .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_departmentLM, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addComponent(lbl_surnameLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_nameLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_studentIDLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_rentstartdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_rentdeadline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        pane_studentRentInfoLayout.setVerticalGroup(
            pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_studentRentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_studentIDLM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_nameLM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_surnameLM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(lbl_departmentLM, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(lbl_rentstartdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lbl_rentdeadline, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_studentRentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_warning)
                    .addComponent(btn_ban))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel19.setText("Author:");

        lbl_author.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_title.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel18.setText("Title:");

        jLabel22.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        jLabel22.setText("Book Info:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pane_studentRentInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(41, 41, 41)
                                .addComponent(lbl_isNotRented, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                                    .addComponent(lbl_author, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_author, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(48, 48, 48)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(lbl_isNotRented))
                .addGap(18, 18, 18)
                .addComponent(pane_studentRentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel6);

        jLabel30.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel30.setText("Library Manager");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_name_surnameLM, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deleteBook)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editBook)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_addBook)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(lbl_name_surnameLM, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel30)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_deleteBook)
                        .addComponent(btn_editBook)
                        .addComponent(btn_addBook)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jSplitPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 22, 70, 24);
        pane_editreq.add(jPanel3, gridBagConstraints);

        tabbedpane.addTab("Library Manager", pane_editreq);

        pane_libman.setBackground(new java.awt.Color(255, 255, 255));
        pane_libman.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane1.setDividerLocation(350);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(1121, 886));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(1121, 886));

        pane_editReqDetails.setBackground(new java.awt.Color(249, 249, 249));

        pane_oldInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel4.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        jLabel2.setText("Current Info:");

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel5.setText("Name:");

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel6.setText("Surname:");

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel7.setText("Age:");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel8.setText("Gender:");

        jLabel9.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel9.setText("Department:");

        lbl_oldUsername.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_oldName.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_oldAge.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_oldEmail.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_oldIsMale.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_oldDepartment.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel16.setText("Email:");

        lbl_oldSurname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout pane_oldInfoLayout = new javax.swing.GroupLayout(pane_oldInfo);
        pane_oldInfo.setLayout(pane_oldInfoLayout);
        pane_oldInfoLayout.setHorizontalGroup(
            pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_oldInfoLayout.createSequentialGroup()
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_oldInfoLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2))
                    .addGroup(pane_oldInfoLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pane_oldInfoLayout.createSequentialGroup()
                                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_oldInfoLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)))
                        .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_oldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldName, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldAge, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldIsMale, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_oldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        pane_oldInfoLayout.setVerticalGroup(
            pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_oldInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_oldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_oldInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(pane_oldInfoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbl_oldName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_oldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(lbl_oldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lbl_oldAge, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(lbl_oldIsMale, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_oldInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_oldDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pane_newInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        jLabel3.setText("Requested Info:");

        jLabel10.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel10.setText("Username:");

        lbl_newUsername.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_newName.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_newSurname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_newAge.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_newIsMale.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_newDepartment.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel11.setText("Department:");

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel12.setText("Gender:");

        jLabel13.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel13.setText("Age:");

        jLabel14.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel14.setText("Surname:");

        jLabel15.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel15.setText("Name:");

        jLabel17.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel17.setText("Email:");

        lbl_newEmail.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout pane_newInfoLayout = new javax.swing.GroupLayout(pane_newInfo);
        pane_newInfo.setLayout(pane_newInfoLayout);
        pane_newInfoLayout.setHorizontalGroup(
            pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_newInfoLayout.createSequentialGroup()
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_newInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(pane_newInfoLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel17))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(15, 15, 15)
                        .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_newUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newName, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newAge, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newIsMale, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_newEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        pane_newInfoLayout.setVerticalGroup(
            pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_newInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pane_newInfoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addGroup(pane_newInfoLayout.createSequentialGroup()
                        .addComponent(lbl_newUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_newName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addComponent(lbl_newSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(lbl_newEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lbl_newAge, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(lbl_newIsMale, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_newInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_newDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btn_acceptEditReq.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_acceptEditReq.setText("Accept Request");
        btn_acceptEditReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acceptEditReqActionPerformed(evt);
            }
        });

        btn_rejectEditReq.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_rejectEditReq.setText("Reject Request");
        btn_rejectEditReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rejectEditReqActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel20.setText("Edit Request Manager");

        arrowPane.setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout arrowPaneLayout = new javax.swing.GroupLayout(arrowPane);
        arrowPane.setLayout(arrowPaneLayout);
        arrowPaneLayout.setHorizontalGroup(
            arrowPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );
        arrowPaneLayout.setVerticalGroup(
            arrowPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );

        arrowPane1.setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout arrowPane1Layout = new javax.swing.GroupLayout(arrowPane1);
        arrowPane1.setLayout(arrowPane1Layout);
        arrowPane1Layout.setHorizontalGroup(
            arrowPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        arrowPane1Layout.setVerticalGroup(
            arrowPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pane_editReqDetailsLayout = new javax.swing.GroupLayout(pane_editReqDetails);
        pane_editReqDetails.setLayout(pane_editReqDetailsLayout);
        pane_editReqDetailsLayout.setHorizontalGroup(
            pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_editReqDetailsLayout.createSequentialGroup()
                .addGroup(pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_editReqDetailsLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pane_editReqDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pane_oldInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pane_editReqDetailsLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(arrowPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_acceptEditReq)
                                    .addComponent(btn_rejectEditReq, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(arrowPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(pane_newInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pane_editReqDetailsLayout.setVerticalGroup(
            pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_editReqDetailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel20)
                .addGap(26, 26, 26)
                .addComponent(pane_oldInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pane_editReqDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(arrowPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pane_editReqDetailsLayout.createSequentialGroup()
                        .addComponent(btn_acceptEditReq)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_rejectEditReq))
                    .addComponent(arrowPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pane_newInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(pane_editReqDetails);

        jPanel5.setBackground(new java.awt.Color(249, 249, 249));

        list_editReq.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        list_editReq.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_editReqValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_editReq);

        jLabel1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel1.setText("Welcome:");

        lbl_name_surnameER.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_name_surnameER, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_name_surnameER, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -93;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(82, 25, 124, 20);
        pane_libman.add(jSplitPane1, gridBagConstraints);

        tabbedpane.addTab("Edit Request Manager", pane_libman);

        getContentPane().add(tabbedpane);

        menu_profile.setText("Profile");
        menu_profile.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N

        mi_createadmin.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        mi_createadmin.setText("Create Admin Account");
        mi_createadmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_createadminActionPerformed(evt);
            }
        });
        menu_profile.add(mi_createadmin);
        menu_profile.add(jSeparator2);

        mi_editProfile.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        mi_editProfile.setText("Edit Profile");
        mi_editProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_editProfileActionPerformed(evt);
            }
        });
        menu_profile.add(mi_editProfile);
        menu_profile.add(jSeparator1);

        mi_logout.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        mi_logout.setText("Logout");
        mi_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_logoutActionPerformed(evt);
            }
        });
        menu_profile.add(mi_logout);

        menubar.add(menu_profile);

        setJMenuBar(menubar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_acceptEditReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acceptEditReqActionPerformed
        acceptRequest();
    }//GEN-LAST:event_btn_acceptEditReqActionPerformed

    private void list_editReqValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_editReqValueChanged
        if (list_editReq.getSelectedValue() == null) {
            return;
        }

        //sets buttons visible
        btn_acceptEditReq.setVisible(true);
        btn_rejectEditReq.setVisible(true);

        int index = list_editReq.getSelectedIndex();

        try {
            //fills info of request at the selected index
            fillRequestInfo(editReqModelIDs[index]);
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }//GEN-LAST:event_list_editReqValueChanged

    private void mi_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_logoutActionPerformed
        LoginFrame.logout(this);
    }//GEN-LAST:event_mi_logoutActionPerformed

    private void mi_editProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_editProfileActionPerformed
        //launches admin profile frame
        AdminProfileFrame profileF = new AdminProfileFrame(admin);
        profileF.setLocationRelativeTo(null);
        profileF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //added window listener to detect when the window closes
        //so that the welcome label can stay updated after changing username
        profileF.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //updating admin object
                    admin = AdminDB.getAdminObj(admin.getID());
                    //refreshing welcome labels
                    lbl_name_surnameER.setText(admin.getName() + " " + admin.getSurname());
                    lbl_name_surnameLM.setText(admin.getName() + " " + admin.getSurname());
                    repaint();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        profileF.setVisible(true);
    }//GEN-LAST:event_mi_editProfileActionPerformed

    private void btn_rejectEditReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rejectEditReqActionPerformed
        rejectRequest();
    }//GEN-LAST:event_btn_rejectEditReqActionPerformed

    private void mi_rejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_rejectActionPerformed
        if (list_editReq.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "No Request Selected!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        rejectRequest();
    }//GEN-LAST:event_mi_rejectActionPerformed

    private void mi_applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_applyActionPerformed
        if (list_editReq.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "No Request Selected!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        acceptRequest();
    }//GEN-LAST:event_mi_applyActionPerformed

    private void table_libManagerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_libManagerMouseReleased
        if (table_libManager.getSelectedRow() < 0) {
            return;
        }
        try {
            //getting book and rentinfo ids
            int index = table_libManager.getSelectedRow();
            int bookID = bookArrLM.get(index).getId();
            int rentInfoID = RentInfoDB.getRentInfoIDofBook(bookID);
            //fills fields
            fillLibManFields(bookID, rentInfoID);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table_libManagerMouseReleased

    private void btn_addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addBookActionPerformed
        addBookControl();
    }//GEN-LAST:event_btn_addBookActionPerformed

    private void btn_editBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editBookActionPerformed
        editBookControl();
    }//GEN-LAST:event_btn_editBookActionPerformed

    private void btn_deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteBookActionPerformed
        deleteBookControl();
    }//GEN-LAST:event_btn_deleteBookActionPerformed

    private void mi_createadminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_createadminActionPerformed
        NewAdminFrame frame = new NewAdminFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_mi_createadminActionPerformed

    private void btn_warningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_warningActionPerformed
        long studentID = Long.parseLong(lbl_studentIDLM.getText());
        String name = lbl_nameLM.getText();
        String warningMsg = JOptionPane.showInputDialog(this, "Send a warning message to the student named: '" + name + "'", "Send Warning", JOptionPane.PLAIN_MESSAGE);
        // if cancel or X are clicked
        if (warningMsg == null) {
            return;
        } else if (StringUtils.isEmptyOrWhitespaceOnly(warningMsg)) {
            //if the warning message is empty
            JOptionPane.showMessageDialog(this, "You must enter a message!");
            return;
        }

        try {
            //adds new message to the database
            MessageDB.createMessage(studentID, "An Admin has sent you a warning!", warningMsg);
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "Warning message sent successfully!");
    }//GEN-LAST:event_btn_warningActionPerformed

    private void btn_banActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banActionPerformed
        long studentID = Long.parseLong(lbl_studentIDLM.getText());
        int index = table_libManager.getSelectedRow();
        String name = lbl_nameLM.getText();
        //confirm dialog JOptionPane
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to ban the student named: '" + name
                + "'\n Any books rented by this student will be returned to the library "
                + "\n(This cannot be undone!!)", "Ban Student", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            //bans student
            StudentDB.banStudent(studentID);
            refreshLibManTable();
            //updates current book's info
            int bookID = bookArrLM.get(index).getId();
            int rentInfoID = RentInfoDB.getRentInfoIDofBook(bookID);
            fillLibManFields(bookID, rentInfoID);
            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdminHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "The student named: '" + name + "' has been banned successfully.", "Ban Successful", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_banActionPerformed

    private void mi_addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_addBookActionPerformed
        addBookControl();
    }//GEN-LAST:event_mi_addBookActionPerformed

    private void mi_editBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_editBookActionPerformed
        editBookControl();
    }//GEN-LAST:event_mi_editBookActionPerformed

    private void mi_deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_deleteBookActionPerformed
        deleteBookControl();
    }//GEN-LAST:event_mi_deleteBookActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel arrowPane;
    private javax.swing.JPanel arrowPane1;
    private javax.swing.JButton btn_acceptEditReq;
    private javax.swing.JButton btn_addBook;
    private javax.swing.JButton btn_ban;
    private javax.swing.JButton btn_deleteBook;
    private javax.swing.JButton btn_editBook;
    private javax.swing.JButton btn_rejectEditReq;
    private javax.swing.JButton btn_warning;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_departmentLM;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JLabel lbl_isNotRented;
    private javax.swing.JLabel lbl_nameLM;
    private javax.swing.JLabel lbl_name_surnameER;
    private javax.swing.JLabel lbl_name_surnameLM;
    private javax.swing.JLabel lbl_newAge;
    private javax.swing.JLabel lbl_newDepartment;
    private javax.swing.JLabel lbl_newEmail;
    private javax.swing.JLabel lbl_newIsMale;
    private javax.swing.JLabel lbl_newName;
    private javax.swing.JLabel lbl_newSurname;
    private javax.swing.JLabel lbl_newUsername;
    private javax.swing.JLabel lbl_oldAge;
    private javax.swing.JLabel lbl_oldDepartment;
    private javax.swing.JLabel lbl_oldEmail;
    private javax.swing.JLabel lbl_oldIsMale;
    private javax.swing.JLabel lbl_oldName;
    private javax.swing.JLabel lbl_oldSurname;
    private javax.swing.JLabel lbl_oldUsername;
    private javax.swing.JLabel lbl_rentdeadline;
    private javax.swing.JLabel lbl_rentstartdate;
    private javax.swing.JLabel lbl_studentIDLM;
    private javax.swing.JLabel lbl_surnameLM;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPopupMenu library_popup;
    private javax.swing.JList<String> list_editReq;
    private javax.swing.JMenu menu_profile;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JMenuItem mi_addBook;
    private javax.swing.JMenuItem mi_apply;
    private javax.swing.JMenuItem mi_createadmin;
    private javax.swing.JMenuItem mi_deleteBook;
    private javax.swing.JMenuItem mi_editBook;
    private javax.swing.JMenuItem mi_editProfile;
    private javax.swing.JMenuItem mi_logout;
    private javax.swing.JMenuItem mi_reject;
    private javax.swing.JPanel pane_editReqDetails;
    private javax.swing.JPanel pane_editreq;
    private javax.swing.JPanel pane_libman;
    private javax.swing.JPanel pane_newInfo;
    private javax.swing.JPanel pane_oldInfo;
    private javax.swing.JPanel pane_studentRentInfo;
    private javax.swing.JPopupMenu request_popup;
    private javax.swing.JTabbedPane tabbedpane;
    private javax.swing.JTable table_libManager;
    // End of variables declaration//GEN-END:variables
}
