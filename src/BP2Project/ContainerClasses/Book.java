
package BP2Project.ContainerClasses;

import javax.swing.ImageIcon;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public class Book {
    private int id;
    private long rentedTo;
    private String title, author, bio, description;
    private ImageIcon imageIcon;

    public Book(int id, String title, String author, String bio, String description, long rentedTo, ImageIcon imageIcon) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bio = bio;
        this.description = description;
        this.rentedTo = rentedTo;
        this.imageIcon = imageIcon;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBio() {
        return bio;
    }

    public String getDescription() {
        return description;
    }

    public long getRentedTo(){
        return rentedTo;
    }
    
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    
}
