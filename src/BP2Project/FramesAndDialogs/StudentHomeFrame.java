package BP2Project.FramesAndDialogs;

import BP2Project.ContainerClasses.*;
import BP2Project.DatabaseAccessClasses.*;
import java.awt.*;
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
public class StudentHomeFrame extends javax.swing.JFrame {

    private Student student;
    private ArrayList<Book> libBookArr;
    private ArrayList<Integer> hideModelIDs, invBookIDs, rentInfoIDs;
    private DefaultTableModel allModel;
    private DefaultTableModel hideModel;
    private DefaultListModel listModel;

    public StudentHomeFrame(Student student) throws SQLException, IOException {
        initComponents();
        this.student = student;

        //sets welcome labels
        lbl_name_surname.setText(student.getName() + " " + student.getSurname() + "!");
        lbl_name_surname1.setText(student.getName() + " " + student.getSurname() + "!");
        //disables rent button on launch
        btn_rent.setEnabled(false);
        //makes JTable uneditable
        table_library.setDefaultEditor(Object.class, null);

        refreshTheme();
        refresh();
    }

    //refreshes updated theme colors
    private void refreshTheme() throws SQLException {
        student = StudentDB.getStudentObject(student.getID());
        Color color = student.getThemeColor();

        jLabel1.setForeground(color);
        jLabel10.setForeground(color);
        jLabel11.setForeground(color);
        jLabel12.setForeground(color);
        jLabel2.setForeground(color);
        jLabel3.setForeground(color);
        jLabel4.setForeground(color);
        jLabel5.setForeground(color);
        jLabel6.setForeground(color);
        jLabel7.setForeground(color);
        jLabel8.setForeground(color);
        jLabel9.setForeground(color);
        jLabel13.setForeground(color);
        jLabel14.setForeground(color);
        jLabel15.setForeground(color);
        jLabel16.setForeground(color);
        btn_rent.setForeground(color);
        btn_return.setForeground(color);
    }

    //refreshes table contents
    private void refresh() throws SQLException, IOException {
        refreshLibTable();
        refreshInvList();
        repaint();
    }
    
    //refreshes the library table
    private void refreshLibTable() throws SQLException, IOException{
        Object[] result = BookDB.getLibraryBookTable();
        libBookArr = (ArrayList<Book>) result[0];
        allModel = (DefaultTableModel) result[1];

        hideModel = new DefaultTableModel(0, 3);
        hideModel.setColumnIdentifiers(new Object[]{"Title", "Author", "Availability"});
        hideModelIDs = new ArrayList<>();
        String[] row = new String[3];

        for (int i = 0; i < libBookArr.size(); i++) {
            Book current = libBookArr.get(i);
            if (current.getRentedTo() == 0) {
                row[0] = current.getTitle();
                row[1] = current.getAuthor();
                row[2] = "Available";
                hideModelIDs.add(current.getId());
                hideModel.addRow(row);
            }
        }

        if(chk_hideRented.isSelected()){
            table_library.setModel(hideModel);
        } else {
            table_library.setModel(allModel);
        }
    }
    
    //refreshes the inventory JList
    private void refreshInvList() throws SQLException, IOException{
        Object[] result = RentInfoDB.getInventoryListModel(student.getID());
        invBookIDs = (ArrayList<Integer>)result[0];
        listModel = (DefaultListModel)result[1];
        rentInfoIDs = (ArrayList<Integer>)result[2];
        list_inv.setModel(listModel);
    }

    //display notification messages
    public void processMessages() throws SQLException, IOException {
        //gets Message objects of this student
        ArrayList<Message> msgArr = MessageDB.getStudentMsgs(student.getID());
        Message msg;
        //display all messages in order
        for (int i = 0; i < msgArr.size(); i++) {
            msg = (Message) msgArr.get(i);
            JOptionPane.showMessageDialog(this, msg.getEvent() + "\n" + msg.getContent(), "Important Notification!", JOptionPane.INFORMATION_MESSAGE);
        }
        //clears messages from database
        MessageDB.useMessages(student.getID());
        
        showDeadlineAlerts();
    }
    
    //shows warning messages for rentals whose deadline has passed
    private void showDeadlineAlerts() throws SQLException, IOException{
        String bookTitles = "\n";
        for (int rentInfoID : rentInfoIDs){
            RentInfo rentInfo = RentInfoDB.getRentInfoObj(rentInfoID);
            Book book = BookDB.getBookObj(rentInfo.getBookID());
            if(rentInfo.getDeadlineTimestamp().compareTo(new Timestamp(System.currentTimeMillis()))<=0){
                bookTitles += book.getTitle()+"\n";
            }
        }
        
        if(bookTitles.length()<=1)
            return;
        
        JOptionPane.showMessageDialog(this, "You have exceeded the rent deadline deadline of the following book(s): \n"
                        + bookTitles + "\nPlease return the book(s) as soon as possible,"
                                + " or you risk being Banned!", "Warning!", JOptionPane.WARNING_MESSAGE);
    }

    //Fills Library fields with info from the bookID
    private void fillLibFields(int bookID) throws SQLException, IOException {
        Book book = BookDB.getBookObj(bookID);
        lbl_titleL.setText("<html>"+ book.getTitle() +"</html>");
        lbl_authorL.setText(book.getAuthor());
        lbl_bioL.setText("<html>"+ book.getBio() +"</html>");
        lbl_bookDescL.setText("<html>"+ book.getDescription() +"</html>");
        lbl_iconL.setIcon(book.getImageIcon());
        btn_rent.setEnabled(book.getRentedTo()==0);
    }
    
    //Fills inventory fields with info from the selected rent info and book
    private void fillInvFields(int bookID, int rentInfoID) throws SQLException, IOException{
        Book book = BookDB.getBookObj(bookID);
        lbl_titleI.setText("<html>"+ book.getTitle() +"</html>");
        lbl_authorI.setText(book.getAuthor());
        lbl_bioI.setText("<html>"+ book.getBio() +"</html>");
        lbl_bookDescI.setText("<html>"+ book.getDescription() +"</html>");
        lbl_iconI.setIcon(book.getImageIcon());
        
        RentInfo rentInfo = RentInfoDB.getRentInfoObj(rentInfoID);
        lbl_rentstart.setText(rentInfo.getRentTimestamp().toString());
        lbl_rentdeadline.setText(rentInfo.getDeadlineTimestamp().toString());
        if(rentInfo.getDeadlineTimestamp().compareTo(new Timestamp(System.currentTimeMillis()))<=0){
            lbl_rentdeadline.setForeground(Color.RED);
        } else {
            lbl_rentdeadline.setForeground(Color.BLACK);
        }
    }
    
    //empties inventory fields
    private void emptyInvFields(){
        lbl_titleI.setText("");
        lbl_authorI.setText("");
        lbl_bioI.setText("");
        lbl_bookDescI.setText("");
        lbl_iconI.setIcon(null);
        lbl_rentstart.setText("");
        lbl_rentdeadline.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pane_library = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_library = new javax.swing.JTable();
        chk_hideRented = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        lbl_name_surname1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        pane_infoL = new javax.swing.JPanel();
        lbl_titleL = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_bioL = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_authorL = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_iconL = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btn_rent = new javax.swing.JButton();
        spinner_days = new javax.swing.JSpinner();
        spinner_hours = new javax.swing.JSpinner();
        spinner_minutes = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pane_descL = new javax.swing.JPanel();
        lbl_bookDescL = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pane_myinventory = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_inv = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        lbl_name_surname = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_return = new javax.swing.JButton();
        lbl_bioI = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_authorI = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_titleI = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_iconI = new javax.swing.JLabel();
        lbl_rentstart = new javax.swing.JLabel();
        lbl_rentdeadline = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_bookDescI = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_editprofile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mi_theme = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mi_logout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Home");
        setMinimumSize(new java.awt.Dimension(1345, 1050));
        setPreferredSize(new java.awt.Dimension(1345, 1050));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jTabbedPane1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        pane_library.setBackground(new java.awt.Color(255, 255, 255));
        pane_library.setMinimumSize(new java.awt.Dimension(0, 0));
        pane_library.setPreferredSize(new java.awt.Dimension(1314, 850));
        pane_library.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setMaximumSize(new java.awt.Dimension(1288, 1101));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));

        table_library.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        table_library.setModel(new javax.swing.table.DefaultTableModel(
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
        table_library.setToolTipText("");
        table_library.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_library.setMaximumSize(new java.awt.Dimension(300, 160));
        table_library.setRowHeight(30);
        table_library.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_library.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                table_libraryMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(table_library);

        chk_hideRented.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        chk_hideRented.setText("Hide Rented");
        chk_hideRented.setOpaque(false);
        chk_hideRented.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk_hideRentedStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel8.setText("Welcome");

        lbl_name_surname1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane2.setMaximumSize(new java.awt.Dimension(800, 800));

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lbl_titleL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel9.setText("Title:");

        lbl_bioL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        lbl_bioL.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel11.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel11.setText("Biography:");

        lbl_authorL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel10.setText("Author:");

        lbl_iconL.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout pane_infoLLayout = new javax.swing.GroupLayout(pane_infoL);
        pane_infoL.setLayout(pane_infoLLayout);
        pane_infoLLayout.setHorizontalGroup(
            pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_infoLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_iconL, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_bioL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_titleL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pane_infoLLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_authorL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pane_infoLLayout.createSequentialGroup()
                        .addGroup(pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))
                        .addGap(0, 293, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pane_infoLLayout.setVerticalGroup(
            pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_infoLLayout.createSequentialGroup()
                .addGroup(pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pane_infoLLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_iconL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pane_infoLLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_titleL, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pane_infoLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(lbl_authorL, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_bioL, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGap(371, 371, 371))
        );

        jSplitPane1.setTopComponent(pane_infoL);

        btn_rent.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_rent.setText("Rent");
        btn_rent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rentActionPerformed(evt);
            }
        });

        spinner_days.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        spinner_days.setModel(new javax.swing.SpinnerNumberModel(0, 0, 14, 1));

        spinner_hours.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        spinner_hours.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));

        spinner_minutes.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        spinner_minutes.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        jLabel13.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel13.setText("Rent Duration:");

        jLabel14.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel14.setText("Days");

        jLabel15.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel15.setText("Hours");

        jLabel16.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel16.setText("Minutes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel15)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(160, 160, 160)
                        .addComponent(spinner_days, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_hours, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_minutes, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addComponent(btn_rent)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_rent)
                    .addComponent(spinner_days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinner_hours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinner_minutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        jSplitPane2.setLeftComponent(jSplitPane1);

        lbl_bookDescL.setFont(new java.awt.Font("Cambria", 0, 16)); // NOI18N
        lbl_bookDescL.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel12.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel12.setText("Book Description:");

        javax.swing.GroupLayout pane_descLLayout = new javax.swing.GroupLayout(pane_descL);
        pane_descL.setLayout(pane_descLLayout);
        pane_descLLayout.setHorizontalGroup(
            pane_descLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_descLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_descLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_bookDescL, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addGroup(pane_descLLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 299, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pane_descLLayout.setVerticalGroup(
            pane_descLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_descLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_bookDescL, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(pane_descL);

        jLabel18.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel18.setText("Library");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_name_surname1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chk_hideRented)
                        .addGap(9, 9, 9))
                    .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_name_surname1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(chk_hideRented)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1297;
        gridBagConstraints.ipady = 905;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(68, 12, 94, 9);
        pane_library.add(jPanel2, gridBagConstraints);

        jTabbedPane1.addTab("Library", pane_library);

        pane_myinventory.setBackground(new java.awt.Color(255, 255, 255));
        pane_myinventory.setLayout(new java.awt.GridBagLayout());

        jPanel7.setBackground(new java.awt.Color(249, 249, 249));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSplitPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane3.setDividerLocation(350);

        jPanel5.setBackground(new java.awt.Color(249, 249, 249));
        jPanel5.setMinimumSize(new java.awt.Dimension(399, 602));

        list_inv.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        list_inv.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_inv.setMaximumSize(new java.awt.Dimension(300, 700));
        list_inv.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_invValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_inv);

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel1.setText("Welcome");

        lbl_name_surname.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel17.setText("My Inventory");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lbl_name_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_name_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane3.setLeftComponent(jPanel5);

        jPanel6.setBackground(new java.awt.Color(249, 249, 249));

        jLabel6.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel6.setText("Rent Start Date:");

        jLabel7.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel7.setText("Rent Deadline:");

        btn_return.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        btn_return.setText("Return Book");
        btn_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_returnActionPerformed(evt);
            }
        });

        lbl_bioI.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        lbl_bioI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_bioI.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel4.setText("Biography:");

        lbl_authorI.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel3.setText("Author:");

        lbl_titleI.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel2.setText("Title:");

        lbl_iconI.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_rentstart.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        lbl_rentdeadline.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_iconI, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lbl_authorI, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_titleI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbl_rentdeadline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbl_rentstart, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(lbl_bioI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_return)
                        .addGap(27, 27, 27))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_return)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(lbl_rentstart, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(lbl_rentdeadline, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_titleI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lbl_authorI, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_iconI, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_bioI, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel6);

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel5.setText("Book Description:");

        lbl_bookDescI.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        lbl_bookDescI.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_bookDescI, javax.swing.GroupLayout.PREFERRED_SIZE, 1258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSplitPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_bookDescI, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = -133;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(54, 12, 71, 12);
        pane_myinventory.add(jPanel7, gridBagConstraints);

        jTabbedPane1.addTab("My Inventory", pane_myinventory);

        getContentPane().add(jTabbedPane1);

        jMenu1.setText("Profile");

        mi_editprofile.setText("Edit Profile");
        mi_editprofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_editprofileActionPerformed(evt);
            }
        });
        jMenu1.add(mi_editprofile);
        jMenu1.add(jSeparator1);

        mi_theme.setText("Change Theme");
        mi_theme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_themeActionPerformed(evt);
            }
        });
        jMenu1.add(mi_theme);
        jMenu1.add(jSeparator2);

        mi_logout.setText("Logout");
        mi_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_logoutActionPerformed(evt);
            }
        });
        jMenu1.add(mi_logout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mi_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_logoutActionPerformed
        LoginFrame.logout(this);
    }//GEN-LAST:event_mi_logoutActionPerformed

    private void mi_editprofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_editprofileActionPerformed
        //launches student profile frame
        StudentProfileFrame profileF = new StudentProfileFrame(student);
        profileF.setLocationRelativeTo(null);
        profileF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileF.setVisible(true);
    }//GEN-LAST:event_mi_editprofileActionPerformed

    private void mi_themeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_themeActionPerformed
        //chooses new theme color for this student
        Color selected = JColorChooser.showDialog(this, "Choose a Theme Color", student.getThemeColor());
        if(selected == null){
            return;
        }
        try {
            //updates database rgb values for this student
            StudentDB.updateThemeColor(student.getID(), selected);
            refreshTheme();
        } catch (SQLException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mi_themeActionPerformed

    private void list_invValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_invValueChanged
        if(list_inv.getSelectedIndex()<0)
            return;
        
        int bookID = invBookIDs.get(list_inv.getSelectedIndex());
        int rentInfoID = rentInfoIDs.get(list_inv.getSelectedIndex());
        try {
            fillInvFields(bookID,rentInfoID);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_list_invValueChanged

    private void btn_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_returnActionPerformed
        int index = list_inv.getSelectedIndex();
        if(index<0)
            return;
        
        try {
            //returns selected book
            RentInfoDB.returnBook(rentInfoIDs.get(list_inv.getSelectedIndex()));
            refresh();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //fills inventory fields according to the remaining items
        if(listModel.isEmpty()){
            emptyInvFields();
        } else {
            list_inv.setSelectedIndex((index==0)?index:index-1);
            try {
                fillInvFields(invBookIDs.get((index==0)?index:index-1),rentInfoIDs.get((index==0)?index:index-1));
            } catch (SQLException | IOException ex) {
                Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_returnActionPerformed

    private void btn_rentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rentActionPerformed
        int bookID;
        int days = (int)spinner_days.getValue();
        int hours = (int)spinner_hours.getValue();
        int minutes = (int)spinner_minutes.getValue();
        //checks if the duration is 0,0,0
        if(days==0 && hours==0 && minutes==0){
            JOptionPane.showMessageDialog(this, "Please specify a rent duration!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //gets current system time
        Timestamp rentTimestamp = new Timestamp(System.currentTimeMillis());
        
        if (chk_hideRented.isSelected()) {
            int selectedRow = table_library.getSelectedRow();
            bookID = hideModelIDs.get(selectedRow);
        } else {
            int index = table_library.getSelectedRow();
            bookID = libBookArr.get(index).getId();
        }
        try {
            //creates a new rentinfo row in the database
            RentInfoDB.rentBook(bookID, student.getID(), rentTimestamp, days, hours, minutes);
            refresh();
            btn_rent.setEnabled(false);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "Book Rented Successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_rentActionPerformed

    private void chk_hideRentedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk_hideRentedStateChanged
        if (chk_hideRented.isSelected()) {
            table_library.setModel(hideModel);
        } else {
            table_library.setModel(allModel);
        }
    }//GEN-LAST:event_chk_hideRentedStateChanged

    private void table_libraryMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_libraryMouseReleased
        if (table_library.getSelectedRow() < 0) {
            return;
        }
        try {
            if (chk_hideRented.isSelected()) {
                int selectedRow = table_library.getSelectedRow();
                fillLibFields(hideModelIDs.get(selectedRow));
            } else {
                int index = table_library.getSelectedRow();
                fillLibFields(libBookArr.get(index).getId());
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(StudentHomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table_libraryMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_rent;
    private javax.swing.JButton btn_return;
    private javax.swing.JCheckBox chk_hideRented;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_authorI;
    private javax.swing.JLabel lbl_authorL;
    private javax.swing.JLabel lbl_bioI;
    private javax.swing.JLabel lbl_bioL;
    private javax.swing.JLabel lbl_bookDescI;
    private javax.swing.JLabel lbl_bookDescL;
    private javax.swing.JLabel lbl_iconI;
    private javax.swing.JLabel lbl_iconL;
    private javax.swing.JLabel lbl_name_surname;
    private javax.swing.JLabel lbl_name_surname1;
    private javax.swing.JLabel lbl_rentdeadline;
    private javax.swing.JLabel lbl_rentstart;
    private javax.swing.JLabel lbl_titleI;
    private javax.swing.JLabel lbl_titleL;
    private javax.swing.JList<String> list_inv;
    private javax.swing.JMenuItem mi_editprofile;
    private javax.swing.JMenuItem mi_logout;
    private javax.swing.JMenuItem mi_theme;
    private javax.swing.JPanel pane_descL;
    private javax.swing.JPanel pane_infoL;
    private javax.swing.JPanel pane_library;
    private javax.swing.JPanel pane_myinventory;
    private javax.swing.JSpinner spinner_days;
    private javax.swing.JSpinner spinner_hours;
    private javax.swing.JSpinner spinner_minutes;
    private javax.swing.JTable table_library;
    // End of variables declaration//GEN-END:variables
}
