package web_chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressWarnings("serial")
public class KlientA extends JFrame{
    JTextArea odebraneWiadomosci;
    JTextField wiadomosc;
    BufferedReader czytelnik;
    PrintWriter pisarz;
    Socket gniazdo;

    public static void main(String[] args) {
        KlientA klient = new KlientA();
        klient.doDziela();
    }

    public void doDziela() {
        JFrame ramka = new JFrame("KlientA");
        JPanel panelGlowny = new JPanel();
        odebraneWiadomosci = new JTextArea(15, 30);
        odebraneWiadomosci.setLineWrap(true);
        odebraneWiadomosci.setWrapStyleWord(true);
        odebraneWiadomosci.setEditable(false);
        JScrollPane przewijanie = new JScrollPane(odebraneWiadomosci);
        przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        wiadomosc = new JTextField(20);
        JButton przyciskWyslij = new JButton("Wyślij");
        przyciskWyslij.addActionListener(new PrzyciskWyslijListener());
        panelGlowny.add(przewijanie);
        panelGlowny.add(wiadomosc);
        panelGlowny.add(przyciskWyslij);
        konfigurujKomunikacje();

        Thread watekOdbiorcy = new Thread(new OdbiorcaKomunikatow());
        watekOdbiorcy.start();

        ramka.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ramka.getContentPane().add(BorderLayout.CENTER, panelGlowny);
        ramka.setSize(400, 350);

        ramka.setVisible(true);


    } // koniec metody

    private void konfigurujKomunikacje() {
        try {
            gniazdo = new Socket("127.0.0.1", 5000);
            InputStreamReader czytelnikStrm = new InputStreamReader(gniazdo.getInputStream());
            czytelnik = new BufferedReader(czytelnikStrm);
            pisarz = new PrintWriter(gniazdo.getOutputStream());
            System.out.println("obsługa sieci przygotowana");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } // koniec metody

    public class PrzyciskWyslijListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            try {
                pisarz.println(wiadomosc.getText());
                pisarz.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            wiadomosc.setText("");
            wiadomosc.requestFocus();
        }
    } // koniec klasy wewntrznej

    public class OdbiorcaKomunikatow implements Runnable {
        public void run() {
            String wiadom;
            try {
                while ((wiadom = czytelnik.readLine()) != null) {
                    System.out.println("Odczytano: " + wiadom);
                    odebraneWiadomosci.append(wiadom + "\n");
                } // koniec while
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // koniec metody run()
    } // koniec klasy wewntrznej
}