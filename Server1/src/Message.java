
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilias
 */
public class Message implements Serializable {
    private String type;
    private String name;
    private String passwd;
    private Anouncer a;
    private Anouncement an;
    private Date d1,d2;
    private ArrayList<Anouncement> list;
    
    
    
    
    public Message(String type, String name, String passwd)
    {
        this.type=type;
        this.name = name;
        this.passwd = passwd;
        
    }
    public Message(String type)
    {
        this.type=type;
    }
     public Message(String type , Anouncer p)
    {
        this.type=type;
        this.a=p;
    }
     public Message(String type , ArrayList<Anouncement> p)
    {
        this.type=type;
        this.list=p;
    }
     public Message(String type , Anouncement an)
    {
        this.type=type;
        this.an=an;
    }
     //for date
     public Message(String type ,Date d1,Date d2)
    {
        this.type=type;
        this.d1=d1;
        this.d2=d2;
    }

   
    
    public String getType()
    {
        return type;
    }
    public ArrayList<Anouncement> getList()
    {
        return list;
    }
    
    public String getName()
    {
        return this.name;
    }
    public String getPass()
    {
        return this.passwd;
    }
    public  Anouncer getAnouncerObj()
    {
        return this.a;
    }
    
    public Anouncement getAnouncement()
    {
        return this.an;
    }
    public  Date getDate1()
    {
        return this.d1;
        
    }
    public  Date getDate2()
    {
        return this.d2;
        
    }
}
