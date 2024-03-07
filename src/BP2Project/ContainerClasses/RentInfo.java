package BP2Project.ContainerClasses;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class RentInfo {

    private int rentID, bookID;
    private long studentID;
    private Timestamp rentTimestamp, deadlineTimestamp;

    public RentInfo(int rentID, int bookID, long studentID, Timestamp rentTimestamp, int days, int hours, int minutes) {
        this.rentID = rentID;
        this.bookID = bookID;
        this.studentID = studentID;
        this.rentTimestamp = rentTimestamp;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, days);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minutes);
        
        this.deadlineTimestamp = new Timestamp(cal.getTime().getTime());
    }
    
    public RentInfo(int rentID, int bookID, long studentID, Timestamp rentTimestamp, Timestamp deadlineTimestamp) {
        this.rentID = rentID;
        this.bookID = bookID;
        this.studentID = studentID;
        this.rentTimestamp = rentTimestamp;
        this.deadlineTimestamp = deadlineTimestamp;
    }
    
    public int getRentID() {
        return rentID;
    }

    public int getBookID() {
        return bookID;
    }

    public long getStudentID() {
        return studentID;
    }

    public Timestamp getRentTimestamp() {
        return rentTimestamp;
    }

    public Timestamp getDeadlineTimestamp() {
        return deadlineTimestamp;
    }
}
