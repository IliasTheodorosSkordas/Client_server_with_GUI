
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_Main {

    private static ServerSocket serverSocket;
    private static Socket clientSocket = null;
    public static ArrayList<Anouncer> registered_users = new ArrayList<Anouncer>();
    //creating the 2 administrators and adding them in the arraylist
    private static Anouncer admin1 = new Anouncer("admin1", "1234");
    private static Anouncer admin2 = new Anouncer("admin2", "1234");
    public static ObjectOutputStream out_file;
    public static ObjectInputStream in_file;

    public static void main(String[] args) throws IOException {
        
        
        //edw dimiourgw ta 2 streams pou tha diaxeirizintai to arxeio twn anakoinwsewn kai sti sunexeia ta stelnw sto thread
        out_file = new ObjectOutputStream(new FileOutputStream("anouncements_file.txt"));
        in_file = new ObjectInputStream(new FileInputStream("anouncements_file.txt"));

        try {
            //edw dimiourgw tous 2 admins
            registered_users.add(admin2);
            registered_users.add(admin1);

            serverSocket = new ServerSocket(11111);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }

        while (true) {

            //accepting the clients and creating the threads
            clientSocket = serverSocket.accept();
            System.out.println("Accepted connection : " + clientSocket);
            
            Thread t = new Thread(new Client_Connection(clientSocket,out_file,in_file));
            //starting the thread
            t.start();

        }

    }

}
