
import java.io.*;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Anouncement implements Serializable {

    private String anouncement;
    private String title;
    private Anouncer author;
    private Date date;

    public Anouncement(Anouncer a, String title,String keimeno)
    {
        this.title=title;
        this.author = a;
        this.anouncement = keimeno;
        this.date = getCurrentTimeStamp();
    }
        
    
    public static Date getCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
    
    public String toString()
    {
        return "<br><br>"+this.title+"<br>"+this.anouncement+"<br>"+this.author.toString();
    }
    
    public Date getAnDate() 
    {
        return date;
    }
    public String getAuthor()
    {
        return (this.author).getName();
    }

}
