
package BP2Project.DatabaseAccessClasses;

import BP2Project.ContainerClasses.EditRequest;
import java.sql.SQLException;
import javax.swing.DefaultListModel;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public abstract class EditRequestDB extends DB{
    
    //return a student's active edit request id
    public static int getActiveEditReqID(long studentID) throws SQLException{
        int editReqID = get1Cell("SELECT RequestID FROM EditRequests WHERE StudentID='"+studentID+"'");
        return editReqID;
    }
    
    //takes requestID as parameter and returns edit request object from database
    public static EditRequest getEditReqObj(int requestID) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM EditRequests WHERE RequestID='"+requestID+"'");
        if(!rs.next())
            return null;
        
        long StudentID = rs.getLong("StudentID");
        String newUsername = rs.getString("NewUsername");
        String newName = rs.getString("NewName");
        String newSurname = rs.getString("NewSurname");
        String newEmail = rs.getString("NewEmail");
        int newAge = rs.getInt("NewAge");
        boolean newIsMale = (rs.getInt("NewIsMale")==1);
        String newDepartment = rs.getString("NewDepartment");
        
        statement.close();
        return new EditRequest(requestID,StudentID,newUsername,newName,newSurname,newEmail,newAge,newIsMale,newDepartment);
    }
    
    //takes studentID as parameter and returns edit request object from database
    public static EditRequest getEditReqObj(long studentID) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM EditRequests WHERE StudentID='"+studentID+"'");
        if(!rs.next())
            return null;
        
        int requestID = rs.getInt("RequestID");
        String newUsername = rs.getString("NewUsername");
        String newName = rs.getString("NewName");
        String newSurname = rs.getString("NewSurname");
        String newEmail = rs.getString("NewEmail");
        int newAge = rs.getInt("NewAge");
        boolean newIsMale = (rs.getInt("NewIsMale")==1);
        String newDepartment = rs.getString("NewDepartment");
        
        statement.close();
        return new EditRequest(requestID,studentID,newUsername,newName,newSurname,newEmail,newAge,newIsMale,newDepartment);
    }
    
    //rejects edit request
    public static void rejectEditRequest(int requestID) throws SQLException{
        EditRequest editReqObj = getEditReqObj(requestID);
        //removes request id from the student's row
        update1Cell("Students", "EditRequestID", 0, "StudentID", editReqObj.getStudentID());
        //deletes edit request
        execute("DELETE FROM EditRequests WHERE RequestID='"+requestID+"'");
    }
    
    //accepts edit request
    public static void applyEditRequest(int requestID) throws SQLException{
        EditRequest editReqObj = getEditReqObj(requestID);
        //applies requested data changes to student
        execute("UPDATE Students SET Username='"+editReqObj.getNewUsername()+"', Name='"
                +editReqObj.getNewName()+"', Surname='"+editReqObj.getNewSurname()+"', "
                + "Email='"+editReqObj.getNewEmail()+"', Age='"+editReqObj.getNewAge()
                +"', IsMale='"+(editReqObj.getNewIsMale()?1:0)+"', Department='"
                +editReqObj.getNewDepartment()+"' WHERE StudentID='"+editReqObj.getStudentID()+"'");
        
        //removes request id from the student's row
        update1Cell("Students", "EditRequestID", 0, "StudentID", editReqObj.getStudentID());
        //deletes edit request
        execute("DELETE FROM EditRequests WHERE RequestID='"+requestID+"'");
    }
    
    //updates edit request (when a student overwrites current request)
    public static void updateEditRequest(int requestID,String newUsername,String newName,String newSurname,
            String newEmail,int newAge, boolean newIsMale, String newDepartment) throws SQLException{
        
        execute("UPDATE EditRequests SET NewUsername='"+newUsername+"', NewName='"+newName+"', NewSurname='"+newSurname+"', NewEmail='"+newEmail+"', "
                + "NewAge='"+newAge+"', NewIsMale='"+(newIsMale?1:0)+"', NewDepartment='"+newDepartment+"' WHERE RequestID='"+requestID+"'");
    }
    
    //adds a new edit request
    public static void addEditRequest(long studentID,String newUsername,String newName,String newSurname,
            String newEmail, int newAge, boolean newIsMale, String newDepartment) throws SQLException{
        
        if(isTableEmpty("EditRequests")){
            //inserts new edit request (if table is empty)
            execute("INSERT INTO EditRequests(StudentID,NewUsername,NewName,NewSurname,NewEmail,NewAge,NewIsMale,NewDepartment)"
                    + " SELECT '"+studentID+"', '"+newUsername+"', '"+newName+"', '"+newSurname+"', '"+newEmail+"', '"+newAge+
                "', '"+(newIsMale?1:0)+"', '"+newDepartment+"' WHERE NOT EXISTS (SELECT *FROM EditRequests)");
        } else {
            //inserts new edit request (if table is not empty)
            execute("INSERT INTO EditRequests (StudentID,NewUsername,NewName,NewSurname,NewEmail,NewAge,NewIsMale,NewDepartment)"
                + " VALUES ('"+studentID+"', '"+newUsername+"', '"+newName+"', '"+newSurname
                +"', '"+newEmail+"', '"+newAge+"', '"+(newIsMale?1:0)+"', '"+newDepartment+"')");
        }
        //adds the new request's id to student's row 
        execute("UPDATE Students SET EditRequestID='"+getActiveEditReqID(studentID)+"' WHERE StudentID='"+studentID+"'");
    }
    
    //returns a list model of all active edit requests (list of EditRequest objects)
    public static DefaultListModel<EditRequest> getEditReqListModel() throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM EditRequests");
        DefaultListModel<EditRequest> model = new DefaultListModel<>();
        
        while(rs.next()){
            int requestID = rs.getInt("RequestID");
            long studentID = rs.getLong("StudentID");
            String username = rs.getString("NewUsername");
            String name = rs.getString("NewName");
            String surname = rs.getString("NewSurname");
            String email = rs.getString("NewEmail");
            int age = rs.getInt("NewAge");
            boolean isMale = (rs.getInt("NewIsMale")==1);
            String depart = rs.getString("NewDepartment");
            
            model.addElement(new EditRequest(requestID,studentID,username,name,surname,email,age,isMale,depart));
        }
        
        return model;
    }
    
}
