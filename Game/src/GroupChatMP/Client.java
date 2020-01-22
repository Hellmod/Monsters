package GroupChatMP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

@SuppressWarnings("serial")
public class Client extends JFrame {
    JTextArea recivedMessges;
    JTextField message;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    String name;

    Client(){
        this.name="no name";
    }

    Client(String name){
        this.name=name;
        letsStart();
    }

    public void letsStart() {
        JFrame frame = new JFrame("Welcome to group chat: " +name);
        JPanel mainPanel = new JPanel();
        recivedMessges = new JTextArea(15, 30);
        recivedMessges.setLineWrap(true);
        recivedMessges.setWrapStyleWord(true);
        recivedMessges.setEditable(false);
        JScrollPane scrolling = new JScrollPane(recivedMessges);
        scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrolling.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        message = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(scrolling);
        mainPanel.add(message);
        mainPanel.add(sendButton);
        configureCommunication();

        Thread receiverThread = new Thread(new MessageReceiver());
        receiverThread.start();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 350);

        frame.setVisible(true);


    }

    private void configureCommunication() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader readerStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(readerStream);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("network service is ready");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            try {
                writer.println(name+": "+message.getText());
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            message.setText("");
            message.requestFocus();
        }
    }

    public class MessageReceiver implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("message: " + message);
                    recivedMessges.append(message + "\n");
                    SaveHistory(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void SaveHistory(String message)
    {
        try {
            FileWriter save = new FileWriter("history_of_GroupChat.txt",true);
            save.append(message+"\n");
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}