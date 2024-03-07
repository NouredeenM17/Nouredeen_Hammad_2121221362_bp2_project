
package BP2Project.ContainerClasses;

import java.awt.Color;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public class Student extends User{
    private String email, department;
    private int age, editReqID;
    private boolean isMale, isBanned;
    private Color themeColor;

    public Student(long studentID, String username, String name, String surname, String email, int age, boolean isMale,
            String department, int editReqID, boolean isBanned, int R, int G, int B) {
        super(studentID, username,name,surname);
        this.email = email;
        this.department = department;
        this.age = age;
        this.editReqID = editReqID;
        this.isMale = isMale;
        this.isBanned = isBanned;
        this.themeColor = new Color(R,G,B);
    }
    
    public Color getThemeColor(){
        return themeColor;
    }
    
    public String getEmail(){
        return email;
    }

    public int getEditReqID() {
        return editReqID;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public boolean getIsBanned() {
        return isBanned;
    }
    
    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }
}
