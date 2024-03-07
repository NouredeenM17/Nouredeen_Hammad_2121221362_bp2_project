
package BP2Project.DatabaseAccessClasses;

import BP2Project.ContainerClasses.Message;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public abstract class MessageDB extends DB{
    
    //creates a new notification message
    public static void createMessage(long studentID, String event, String content) throws SQLException{
        if(isTableEmpty("Messages")){
            //inserts new message (if table is empty)
            ps = conn.prepareStatement("INSERT INTO Messages(StudentID,Event,Content) SELECT ?,?,? WHERE NOT EXISTS (SELECT *FROM Messages)");
        } else {
            //inserts new message (if table is not empty)
            ps = conn.prepareStatement("INSERT INTO Messages (StudentID,Event,Content) VALUES (?,?,?)");
        }
        ps.setLong(1, studentID);
        ps.setString(2, event);
        ps.setString(3, content);
        ps.executeUpdate();
    }
    
    //takes messageID as parameter and returns message object from database
    public static Message getMessageObject(int messageID) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Messages WHERE MessageID='"+messageID+"'");
        rs.next();
        
        long studentID = rs.getLong("StudentID");
        String event = rs.getString("Event");
        String content = rs.getString("Content");
        
        statement.close();
        return new Message(messageID,studentID,event,content);
    }
    
    //takes student ID as parameter and returns this student's messages as an ArrayList of Message objects
    public static ArrayList<Message> getStudentMsgs(long studentID) throws SQLException{
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM Messages WHERE StudentID='"+studentID+"'");
        
        ArrayList<Message> msgObjects = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("MessageID");
            String event = rs.getString("Event");
            String content = rs.getString("Content");
            msgObjects.add(new Message(id,studentID,event,content));
        }
        statement.close();
        return msgObjects;
    }
    
    //deletes the given student's messages (when the student sees the messages)
    public static void useMessages(long studentID) throws SQLException{
        execute("DELETE FROM Messages WHERE StudentID='"+studentID+"'");
    }
    
}
