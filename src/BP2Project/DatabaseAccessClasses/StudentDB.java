
package BP2Project.DatabaseAccessClasses;

import BP2Project.UtilAndMain.Encrypt;
import BP2Project.ContainerClasses.Student;
import java.awt.Color;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public abstract class StudentDB extends DB{
    //takes studentID as parameter and returns student object from database
    public static Student getStudentObject(long studentID) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Students WHERE StudentID='"+studentID+"'");
        if(!rs.next())
            return null;
            
        String username = rs.getString("Username");
        String name = rs.getString("Name");
        String surname = rs.getString("Surname");
        String email = rs.getString("Email");
        int age = rs.getInt("Age");
        boolean isMale = (rs.getInt("isMale")==1);
        String department = rs.getString("Department");
        int editReqID = rs.getInt("EditRequestID");
        boolean isBanned = (rs.getInt("IsBanned")==1);
        int r = rs.getInt("ThemeColorR");
        int g = rs.getInt("ThemeColorG");
        int b = rs.getInt("ThemeColorB");
        
        statement.close();
        return new Student(studentID,username,name,surname,email,age,isMale,department,editReqID,isBanned,r,g,b);
    }
    
    //(overload method) takes username as parameter and returns student object from database
    public static Student getStudentObject(String username) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Students WHERE Username='"+username+"'");
        if(!rs.next())
            return null;
        
        long studentID = rs.getLong("StudentID");
        String name = rs.getString("Name");
        String surname = rs.getString("Surname");
        String email = rs.getString("Email");
        int age = rs.getInt("Age");
        boolean isMale = (rs.getInt("isMale")==1);
        String department = rs.getString("Department");
        int editReqID = rs.getInt("EditRequestID");
        boolean isBanned = (rs.getInt("IsBanned")==1);
        int r = rs.getInt("ThemeColorR");
        int g = rs.getInt("ThemeColorG");
        int b = rs.getInt("ThemeColorB");
        
        statement.close();
        return new Student(studentID,username,name,surname,email,age,isMale,department,editReqID,isBanned,r,g,b);
    }
    
    //updates a student's theme color choice
    public static void updateThemeColor(long studentID, Color color) throws SQLException{
        int R = color.getRed();
        int G = color.getGreen();
        int B = color.getBlue();
        update1Cell("Students","ThemeColorR",R,"StudentID",studentID);
        update1Cell("Students","ThemeColorG",G,"StudentID",studentID);
        update1Cell("Students","ThemeColorB",B,"StudentID",studentID);
    }
    
    //adds a new student to database
    public static void addNewStudent(long studentID, String username, String password, String name,
            String surname, String email, int age, boolean isMale, String department) 
            throws SQLException, NoSuchAlgorithmException{
        
        execute("INSERT INTO Students (StudentID,Username,Password,Name,Surname,Email,Age,IsMale,Department) VALUES ('"
                    +studentID+"', '"+username+"', '"+(Encrypt.hash(password))+"', '"+name+"', '"+surname+"', '"
                    +email+"', '"+age+"', '"+(isMale?1:0)+"', '"+department+"')");
    }
    
    //checks whether the given studentID is banned or not
    public static boolean isBanned(long studentID) throws SQLException{
        int result = get1Cell("SELECT IsBanned FROM Students WHERE StudentID='"+studentID+"'");
        return (result == 1);
    }
    
    //bans student
    public static void banStudent(long studentID) throws SQLException{
        //sets student status as banned
        update1Cell("Students", "IsBanned", 1, "StudentID", studentID);
        
        //gets this student's rented bookIDs
        ArrayList<Integer> bookIDs = new ArrayList<>();
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT BookID FROM RentInfo WHERE StudentID='"+studentID+"'");
        while(rs.next()){
            bookIDs.add(rs.getInt("BookID"));
        }
        statement.close();
        
        //updates books' RentedTo field
        for (int bookID : bookIDs) {
            update1Cell("Books", "RentedTo", 0, "BookID", bookID);
        }
        
        //Deletes all rent info linked to the studentID
        execute("DELETE FROM RentInfo WHERE StudentID='"+studentID+"'");
    }
}
