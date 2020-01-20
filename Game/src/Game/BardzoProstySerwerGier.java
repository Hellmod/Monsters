package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class BardzoProstySerwerGier {
    ArrayList strumienieWyjsciowe;
    public class ObslugaKlientow implements Runnable {
        BufferedReader czytelnik;
        Socket gniazdo;
        public ObslugaKlientow(Socket clientSocket) {
            try {
                gniazdo = clientSocket;
                InputStreamReader isReader = new InputStreamReader(gniazdo.getInputStream());
                czytelnik = new BufferedReader(isReader);

            } catch(Exception ex) {ex.printStackTrace();}
        } // koniec konstruktora
        public void run() {
            Player player;
            Integer x;
            Integer y=null;
            try {
               // player=czytelnik.isR
                /*
                while ((x = czytelnik.read()) != null) {
                    y=czytelnik.read();
                    System.out.println("Odczytano x: " + x);
                    System.out.println("Odczytano y: " + y);
                    //rozeslijDoWszystkich(wiadomosc);
                } // koniec ptli

                 */
            } catch(Exception ex) {ex.printStackTrace();}
        } // koniec metody
    } // koniec klasy wewntrznej
    public static void main (String[] args) {
        new BardzoProstySerwerGier().doRoboty();
    }
    public void doRoboty() {
        strumienieWyjsciowe = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(5000);
            while(true) {
                Socket gniazdoKlienta = serverSock.accept();
                PrintWriter pisarz = new PrintWriter(gniazdoKlienta.getOutputStream());
                strumienieWyjsciowe.add(pisarz);
                Thread t = new Thread(new ObslugaKlientow(gniazdoKlienta));
                t.start();
                System.out.println("mamy po??czenie");
            }
        } catch(Exception ex) {
            ex.printStackTrace ();
        }
    } // koniec metody

    public void rozeslijDoWszystkich(String message) {
        Iterator it = strumienieWyjsciowe.iterator();
        while(it.hasNext()) {
            try {
                PrintWriter pisarz = (PrintWriter) it.next();
                pisarz.println(message);
                pisarz.flush();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } // koniec ptli
    } // koniec metody
} // koniec klasy