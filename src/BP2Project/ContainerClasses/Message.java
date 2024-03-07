
package BP2Project.ContainerClasses;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class Message {

    private int messageID;
    private long studentID;
    private String event, content;
    
    public Message(int messageID, long studentID, String event, String content){
        this.messageID = messageID;
        this.studentID = studentID;
        this.event = event;
        this.content = content;
    }
    
    public int getMessageID() {
        return messageID;
    }

    public long getStudentID() {
        return studentID;
    }

    public String getEvent() {
        return event;
    }

    public String getContent() {
        return content;
    }
}
