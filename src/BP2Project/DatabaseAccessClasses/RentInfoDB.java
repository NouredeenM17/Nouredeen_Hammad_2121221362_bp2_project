
package BP2Project.DatabaseAccessClasses;

import BP2Project.ContainerClasses.RentInfo;
import static BP2Project.DatabaseAccessClasses.DB.statement;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListModel;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public abstract class RentInfoDB extends DB{
    
    //takes RentInfoID and returns RentInfo object
    public static RentInfo getRentInfoObj(int id) throws SQLException, IOException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM RentInfo WHERE RentInfoID='"+id+"'");
        if(!rs.next()){
            return null;
        }
        int bookID = rs.getInt("BookID");
        long studentID = rs.getLong("StudentID");
        Timestamp rentTS = rs.getTimestamp("RentTimestamp");
        Timestamp deadlineTS = rs.getTimestamp("DeadlineTimestamp");
        statement.close();
        
        return new RentInfo(id,bookID,studentID,rentTS,deadlineTS);
    }
    
    //Calculates deadline timestamp according to duration, and creates a new RentInfo row
    public static void rentBook(int bookID, long studentID, Timestamp rentTimestamp, int days, int hours, int minutes) throws SQLException{
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, days);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minutes);
        Timestamp deadlineTimestamp = new Timestamp(cal.getTime().getTime());
        
        if(isTableEmpty("RentInfo")){
            //inserts new rent (if table is empty)
            ps = conn.prepareStatement("INSERT INTO RentInfo (BookID,StudentID,RentTimestamp,DeadlineTimestamp)"
                    + " SELECT '"+bookID+"', '"+studentID+"', ?, ? WHERE NOT EXISTS (SELECT * FROM RentInfo)");
        } else {
            //inserts new rent (if table is not empty)
            ps = conn.prepareStatement("INSERT INTO RentInfo (BookID,StudentID,RentTimestamp,DeadlineTimestamp)"
                + " VALUES ('"+bookID+"', '"+studentID+"', ?, ?)");
        }
        ps.setTimestamp(1, rentTimestamp);
        ps.setTimestamp(2, deadlineTimestamp);
        ps.executeUpdate();
        
        //updates the RentedTo column in the selected book
        execute("UPDATE Books SET RentedTo='"+studentID+"' WHERE BookID='"+bookID+"'");
    }
    
    //performs the book returning procedure
    public static void returnBook(int rentID) throws SQLException, IOException{
        RentInfo rentInfo = getRentInfoObj(rentID);
        execute("DELETE FROM RentInfo WHERE RentInfoID='"+rentID+"'");
        execute("UPDATE Books SET RentedTo='0' WHERE BookID='"+rentInfo.getBookID()+"'");
    }
    
    //takes a book id and returns the rentInfo id associated with it
    public static int getRentInfoIDofBook(int bookID) throws SQLException{
        Object result = get1Cell("SELECT RentInfoID FROM RentInfo WHERE BookID='"+bookID+"'");
        if(result == null){
            return -1;
        } else {
            return (int)result;
        }
    }
    
    //returns an integer ArrayList of all the RentInfoIDs
    public static ArrayList<Integer> getRentInfoIDs() throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT RentInfoID FROM RentInfo");
        ArrayList<Integer> result = new ArrayList<>();
        while(rs.next()){
            result.add(rs.getInt("RentInfoID"));
        }
        statement.close();
        return result;
    }
    
    //takes the StudentID as a parameter and returns the student's inventory list model 
    //and Integer ArrayLists of RentInfoIDs and BookIDs
    public static Object[] getInventoryListModel(long studentID) throws SQLException, IOException{
        
        ArrayList<Integer> rentInfoIDs = new ArrayList<>();
        ArrayList<Integer> bookIDs = new ArrayList<>();
        DefaultListModel model = new DefaultListModel();
        
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT RentInfoID,BookID FROM RentInfo WHERE StudentID='"+studentID+"'");
        while(rs.next()){
            int bookID = rs.getInt("BookID");
            int rentInfoID = rs.getInt("RentInfoID");
            
            rentInfoIDs.add(rentInfoID);
            bookIDs.add(bookID);
        }
        statement.close();
        
        for (int i = 0; i < bookIDs.size(); i++) {
            model.addElement(BookDB.getBookObj(bookIDs.get(i)).getTitle());
        }
        
        Object[] result = new Object[3];
        result[0] = bookIDs; result[1] = model; result[2] = rentInfoIDs;
        return result;
    }
    
}
