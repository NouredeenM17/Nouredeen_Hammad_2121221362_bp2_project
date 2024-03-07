
package BP2Project.DatabaseAccessClasses;

import BP2Project.ContainerClasses.Book;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public abstract class BookDB extends DB{
    
    //method that returns arraylist of bookinfo objects and a jtable model
    public static Object[] getLibraryBookTable() throws SQLException, IOException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Books");
        Object[] rowItems = new Object[3];
        DefaultTableModel model = new DefaultTableModel(0,3);
        Object[] colIdentifiers = new Object[3];
        colIdentifiers[0]="Title";
        colIdentifiers[1]="Author";
        colIdentifiers[2]="Availability";
        model.setColumnIdentifiers(colIdentifiers);
        ArrayList<Book> bookArr = new ArrayList<>();
        
        while(rs.next()){
            int id = rs.getInt("BookID");
            String title = rs.getString("Title");
            String author = rs.getString("Author");
            String bio = rs.getString("Bio");
            String desc = rs.getString("Description");
            long rentedTo = rs.getLong("RentedTo");
            Blob imgBlob = rs.getBlob("ImageBlob");
            
            bookArr.add(new Book(id,title,author,bio,desc,rentedTo,getImgIcon(imgBlob)));
            
            rowItems[0] = title;
            rowItems[1] = author;
            rowItems[2] = (rentedTo==0?"Available":"Rented");
            
            model.addRow(rowItems);
        }
        statement.close();
        Object[] result = new Object[2];
        result[0] = bookArr; result[1] = model;
        return result;
    }
    
    //adds a new book
    public static void addBook(String title, String author, String bio, String desc, File imgFile) throws SQLException, FileNotFoundException{
        ps = conn.prepareStatement("INSERT INTO Books (Title,Author,Bio,Description,ImageBlob) VALUES (?,?,?,?,?)");
        ps.setString(1, title); 
        ps.setString(2, author);
        ps.setString(3, processNewLines(bio)); 
        ps.setString(4, processNewLines(desc));
        InputStream in = new FileInputStream(imgFile);
        ps.setBlob(5, in);
        ps.executeUpdate();
    }
    
    //updates book
    public static void updateBook(int id, String title, String author, String bio, String desc, File imgFile) throws SQLException, FileNotFoundException{
        ps = conn.prepareStatement("UPDATE Books SET Title = ?, Author = ?, Bio = ?, Description = ?, ImageBlob = ? WHERE BookID = '"+id+"'");
        ps.setString(1, title); 
        ps.setString(2, author);
        ps.setString(3, processNewLines(bio)); 
        ps.setString(4, processNewLines(desc));
        InputStream in = new FileInputStream(imgFile);
        ps.setBlob(5, in);
        ps.executeUpdate();
    }
    
    //updates book
    public static void updateBook(int id, String title, String author, String bio, String desc) throws SQLException, FileNotFoundException{
        ps = conn.prepareStatement("UPDATE Books SET Title = ?, Author = ?, Bio = ?, Description = ? WHERE BookID = '"+id+"'");
        ps.setString(1, title); 
        ps.setString(2, author);
        ps.setString(3, processNewLines(bio)); 
        ps.setString(4, processNewLines(desc));
        ps.executeUpdate();
    }
    
    //takes book id and deletes that book
    public static void deleteBook(int id) throws SQLException, IOException{
        Book book = getBookObj(id);
        long rentedTo = Long.parseLong(get1Cell("SELECT RentedTo FROM Books WHERE BookID='"+id+"'"));
        if(rentedTo != 0){
            MessageDB.createMessage(rentedTo, "A book you rented has been deleted!", 
                    "The book titled: "+book.getTitle()+", has been deleted by an administrator");
            execute("DELETE FROM RentInfo WHERE BookID='"+id+"'");
        }
        execute("DELETE FROM Books WHERE BookID='"+id+"'");
    }
    
    //takes book id and returns the book object
    public static Book getBookObj(int id) throws SQLException, IOException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Books WHERE BookID='"+id+"'");
        if(!rs.next()){
            return null;
        }
        String title = rs.getString("Title");
        String author = rs.getString("Author");
        String bio = rs.getString("Bio");
        String desc = rs.getString("Description");
        long rentedTo = rs.getLong("RentedTo");
        Blob imgBlob = rs.getBlob("ImageBlob");
        statement.close();
        
        return new Book(id,title,author,bio,desc,rentedTo,getImgIcon(imgBlob));
    }
    
    //turns all new line characters (/n) into <br> so that new lines are preserved in the database
    private static String processNewLines(String original){
        String result = "";
        for (int i = 0; i < original.length(); i++) {
            if(original.charAt(i) == '\n'){
                result += "<br>";
            } else {
                result += original.charAt(i)+"";
            }
        }
        return result;
    }
    
    //takes a blob object and converts it into an ImageIcon object
    private static ImageIcon getImgIcon(Blob blob) throws IOException, SQLException{
        InputStream input = blob.getBinaryStream();
        BufferedImage bi = ImageIO.read(input);
        return new ImageIcon(bi);
    }
}
