
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Client_Connection implements Runnable {

    private boolean available = true;

    private Socket clientSocket;
    private ObjectOutputStream out_file;
    private ObjectInputStream in_file;
    private ObjectInputStream serverInputStream;
    private ObjectOutputStream serverOutputStream;
    private Message m;

    public Client_Connection(Socket clientSocket, ObjectOutputStream out_file, ObjectInputStream in_file) {
        this.clientSocket = clientSocket;
        this.out_file = out_file;
        this.in_file = in_file;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {

        try {

            serverInputStream = new ObjectInputStream(clientSocket.getInputStream());
            serverOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            while (true) {

                Message obj = (Message) serverInputStream.readObject();
                System.out.println("oserver lamvanei" + obj.getType());

                if (obj.getType().equals("START")) {
                    boolean ok = false;
                    //apantaw WAITING me tin dimiourgia enos antikimenou tupou message
                    serverOutputStream.writeObject(new Message("WAITING"));
                    m = (Message) serverInputStream.readObject();
                    //an o tupos tu message einai CONNECT
                    if (m.getType().equals("CONNECT")) {
                        System.out.println("Client tries to connect");
                        System.out.println("name: " + m.getName() + "\npass : " + m.getPass());
                        //elegxw an uparxei o xristis stin lista tou server
                        for (int i = 0; i < Server_Main.registered_users.size(); i++) {
                            System.out.println(Server_Main.registered_users.get(i).getName());
                            if ((Server_Main.registered_users.get(i).getName()).equals(m.getName()) && (Server_Main.registered_users.get(i).getPasswd()).equals(m.getPass())) {
                                serverOutputStream.writeObject(new Message("OK"));
                                System.out.println("Client connected..");
                                ok = true;

                            }
//                        
                        }
                        if (ok == false) {
                            serverOutputStream.writeObject(new Message("NOT OK"));
                        }
                        serverOutputStream.flush();
//an o tupos tu message einai REGISTER
                    } else if (m.getType().equals("REGISTER")) {
                        Server_Main.registered_users.add(m.getAnouncerObj());

                        //elegxw an ontws mpike o xristis stin arraylist
                        for (int i = 0; i < Server_Main.registered_users.size(); i++) {
                            System.out.println(Server_Main.registered_users.get(i).toString());
                        }
                        serverOutputStream.writeObject(new Message("ΟΚ"));
                        serverOutputStream.flush();
//an o tupos tu message einai CREATE
                    } else if (m.getType().equals("CREATE")) {
                        CREATE();

                        //an o tupos tu message einai VIEW
                    } else if (m.getType().equals("VIEW")) {
                        VIEW();
                        serverOutputStream.flush();

                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Client_Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//sugxronismeni methodos me tin VIEW()

    public synchronized void VIEW() throws IOException {
        ArrayList<Anouncement> list = new ArrayList<Anouncement>();
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client_Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        available = false;

        Date d1, d2, d3;
        d1 = m.getDate1();
        d2 = m.getDate2();
        Anouncement an;

        try {
            while ((an = (Anouncement) in_file.readObject()) != null) {
                d3 = an.getAnDate();
                if ((d3).after(d1) && ((d3)).before(d2)) {
                    list.add(an);
                    System.out.println(an.toString());
                }
            }
        } catch (EOFException ex) {
            System.out.println("EOF");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverOutputStream.writeObject(new Message("END", list));
        in_file.close();

        available = true;
        notifyAll();

    }

    //sugxronismeni methodos me tin VIEW()
    public synchronized void CREATE() throws IOException {

        while (available == false) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client_Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        available = false;

        //saving the anouncement
        System.out.println("Saving a new anouncement..");
        out_file.writeObject(m.getAnouncement());
        out_file.flush();
        serverOutputStream.writeObject(new Message("ΟΚ"));
        serverOutputStream.flush();
        System.out.println("O server esteile ok.");
        available = true;
        notifyAll();

    }

}
