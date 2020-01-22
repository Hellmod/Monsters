package GroupChatMP;

import java.io.*;
import java.net.*;
import java.util.*;
public class GroupChat {
    ArrayList outputStreams;
    public class ClientService implements Runnable {
        BufferedReader reader;
        Socket socket;
        public ClientService(Socket clientSocket) {
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch(Exception ex) {ex.printStackTrace();}
        }
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("message: " + message);
                    sendToEveryone(message);
                }
            } catch(Exception ex) {ex.printStackTrace();}
        }
    }
    public static void main (String[] args) {
        new GroupChat().letsStart();
    }
    public void letsStart() {
        outputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                outputStreams.add(writer);
                Thread t = new Thread(new ClientService(clientSocket));
                t.start();
                System.out.println("we have a connection");
            }
        } catch(Exception ex) {
            ex.printStackTrace ();
        }
    }
    public void sendToEveryone(String message) {
        Iterator iterator = outputStreams.iterator();
        while(iterator.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) iterator.next();
                writer.println(message);
                writer.flush();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}