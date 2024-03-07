
package BP2Project.ContainerClasses;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public class User {
    long id;
    String username, name, surname;
    
    public User(long id, String username, String name, String surname){
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
    }
    
    public long getID(){
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }
}
