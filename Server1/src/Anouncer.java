
import java.io.*;
import java.net.Socket;

public class Anouncer implements Serializable {

    private String name;
    private String password;

    public Anouncer(String name, String password) {
        this.name = name;
        this.password = password;

    }
    
    public String getName()
    {
        return this.name;
    }
    public String getPasswd()
    {
        return this.password;
    }
   

    
    @Override
    public String toString()
    {
        return "\nAuthor name: <b>"+this.name+"<b>";
    }

}
