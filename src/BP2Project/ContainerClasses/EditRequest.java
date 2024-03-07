
package BP2Project.ContainerClasses;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public class EditRequest {
    int requestID, newAge;
    long studentID;
    String newUsername, newName, newSurname, newEmail, newDepartment;
    boolean newIsMale;
    
    public EditRequest(int requestID, long studentID, String newUsername, String newName, 
            String newSurname, String newEmail, int newAge, boolean newIsMale, String newDepartment) {
        
        this.requestID = requestID;
        this.studentID = studentID;
        this.newAge = newAge;
        this.newIsMale = newIsMale;
        this.newUsername = newUsername;
        this.newName = newName;
        this.newSurname = newSurname;
        this.newEmail = newEmail;
        this.newDepartment = newDepartment;
    }

    public String getNewEmail(){
        return newEmail;
    }
    
    public int getRequestID() {
        return requestID;
    }

    public long getStudentID() {
        return studentID;
    }

    public int getNewAge() {
        return newAge;
    }

    public boolean getNewIsMale() {
        return newIsMale;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getNewName() {
        return newName;
    }

    public String getNewSurname() {
        return newSurname;
    }

    public String getNewDepartment() {
        return newDepartment;
    }
    
    @Override
    public String toString(){
        return "ID: "+requestID+", Student ID: "+studentID;
    }
}
