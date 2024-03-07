
package BP2Project.DatabaseAccessClasses;

import java.sql.*;
import javax.swing.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

//This class manages connection and queries with the database
public abstract class DB {
    static Connection conn;
    static Statement statement;
    static PreparedStatement ps;
    static ResultSet rs;
    static ResultSetMetaData rsmd;
    
    //Checks if the item entered is unique in given column and table
    public static boolean isUnique(String item, String columnName, String fromTable) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT "+columnName+" FROM "+fromTable+" WHERE "+columnName+"='"+item+"'");
        boolean isUnique = !rs.next();
        statement.close();
        return isUnique;
    }
    
    //returns first cell in given query (GENERIC: can return any datatype)
    public static <T> T get1Cell(String query) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery(query);
        if(!rs.next()){
            statement.close();
            return null;
        }
        
        T obj = (T)rs.getObject(1);
        statement.close();
        
        return obj;
    }
    
    //executes update statement
    public static void execute(String query) throws SQLException{
        statement = conn.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }
    
    //updates 1 cell in the database (GENERIC: value and condition can be of any datatype)
    public static <T,K> void update1Cell(String table, String valColumn, T value, String condColumn, K condition) throws SQLException{
        execute("UPDATE "+table+" SET "+valColumn+"='"+value+"' WHERE "+condColumn+"='"+condition+"'");
    }
    
    //checks if given table is empty
    public static boolean isTableEmpty(String table) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM "+table+")");
        rs.next();
        int isEmpty = rs.getInt(1);
        return (isEmpty==0);
    }     
    
    //returns user's (student or admin) password
    public static String getUserPass(String table,String username) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT Password FROM "+table+" WHERE Username='"+username+"'");
        if(!rs.next()){
            statement.close();
            return null;
        }
        String pass = rs.getString("Password");
        statement.close();
        return pass;
    }
    
    //returns the table name that contains given username, returns null if the username is not found
    public static String findUsernamesTable(String username) throws SQLException{
        String fromStudents = get1Cell("SELECT Username FROM Students WHERE Username='"+username+"'");
        String fromAdmins = get1Cell("SELECT Username FROM Admins WHERE Username='"+username+"'");
        
        if(fromStudents!=null){
            return "Students";
        } else if(fromAdmins!=null){
            return "Admins";
        } else {
            return null;
        }
    }
    
    //initializes connection to the database, only runs once in the main method
    public static boolean initConnection(){
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "");
            return true;
        }catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Something went wrong while connecting to the database."
                    + " The program will exit.", "Fatal Error", JOptionPane.ERROR_MESSAGE); 
            return false;
        }
    }
    
}
