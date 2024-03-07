
package BP2Project.UtilAndMain;

import java.util.regex.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */
public abstract class Regex {
    static Pattern pat;
    static Matcher mat1, mat2;
    
    //regex to check validity of E-mail
    public static boolean checkEmail(String email){
        pat = Pattern.compile("[A-Za-z_\\.\\-]+[@][A-Za-z]+(\\.edu\\.tr)");
        mat1 = pat.matcher(email);
        return mat1.matches();
    }
    
    //regex to check validity of Name and Surname
    public static boolean checkNameSurname(String name, String surname){
        pat = Pattern.compile("[A-Za-zıİğĞüÜşŞçÇöÖ]{1,15}");
        mat1 = pat.matcher(name);
        mat2 = pat.matcher(surname);
        return (mat1.matches()&&mat2.matches());
    }
    
    //regex to check validity of Student ID
    public static boolean checkStudentID(String studentIDtext){
        pat = Pattern.compile("[0-9]{10}");
        mat1 = pat.matcher(studentIDtext);
        return mat1.matches();
    }
    
    //regex to check for illegal characters, and to check that length <= 15
    public static boolean checkForIllegalCharsAndLength(String original){
        pat = Pattern.compile("[^#\"\\$']{1,15}");
        mat1 = pat.matcher(original);
        return mat1.matches();
    }
    
    //converts <br> statements into new line characters (/n)
    public static String convertDBdescString(String dbString){
        pat = Pattern.compile("(<br>)");
        mat1 = pat.matcher(dbString);
        return mat1.replaceAll("\n");
    }
}
