
package BP2Project.DatabaseAccessClasses;

import BP2Project.ContainerClasses.Admin;
import BP2Project.UtilAndMain.Encrypt;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public abstract class AdminDB extends DB {
    
    //updates admin info
    public static void updateAdmin(long id, String username, String name, String surname) throws SQLException{
        execute("UPDATE Admins SET Username='"+username+"', Name='"+name+"', Surname='"+surname+"' WHERE AdminID='"+id+"'");
    }
    
    //adds new admin
    public static void addNewAdmin(String username, String password, String name, String surname) throws SQLException, NoSuchAlgorithmException{
        execute("INSERT INTO Admins (username,password,name,surname) VALUES ('"
                +username+"', '"+Encrypt.hash(password)+"', '"+name+"', '"+surname+"')");
    }
    
    //takes username as parameter and returns admin object from database
    public static Admin getAdminObj(String username) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+username+"'");
        if(!rs.next())
            return null;
        
        long id = rs.getLong("AdminID");
        String name = rs.getString("Name");
        String surname = rs.getString("Surname");
        
        statement.close();
        return new Admin(id,username,name,surname);
    }
    
    //takes id as parameter and returns admin object from database
    public static Admin getAdminObj(long id) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Admins WHERE AdminID='"+id+"'");
        if(!rs.next())
            return null;
        
        String username = rs.getString("Username");
        String name = rs.getString("Name");
        String surname = rs.getString("Surname");
        
        statement.close();
        return new Admin(id,username,name,surname);
    }
}
