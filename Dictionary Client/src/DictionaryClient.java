import org.json.JSONObject;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class DictionaryClient {
    // port number of the server
    final static int serverPort = 1234;

    public static void main(String[] args) throws UnknownHostException, IOException {

        JFrame homeFrame = new JFrame("Home");
        homeFrame.setContentPane(new ClientHome().getPanel());
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setResizable(false);
        homeFrame.pack();
        homeFrame.setVisible(true);

        InetAddress localhost = InetAddress.getLocalHost();
        Socket socket;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            socket = new Socket(localhost, serverPort);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            JOptionPane.showMessageDialog(null, "Connected");
        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null, "Connection Failed! Make sure the server is running!");
            System.exit(0);
        }


        DataOutputStream finalDataOutputStream = dataOutputStream;
        Thread sendRequestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // String clientRequest = Shared.requestString;
                    try {
                        String clientRequest = Shared.requestString;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }

                        if (clientRequest.contains("*")) {
                            System.out.println(clientRequest);
                            finalDataOutputStream.writeUTF(clientRequest);
                            Shared.requestString = "Empty";
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Problem Sending Request.");
                        break;
                    }
                }
            }
        });
        DataInputStream finalDataInputStream = dataInputStream;
        Thread readResponseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String response;
                        response = finalDataInputStream.readUTF();
                        if (response.contains("SEARCH*")) {
                            StringTokenizer stringTokenizer = new StringTokenizer(response, "*");
                            String operation = stringTokenizer.nextToken();
                            String wordJSON = stringTokenizer.nextToken();
                            JSONObject wordObject = new JSONObject(wordJSON);
                            String toDisplay = "";
                            toDisplay = toDisplay.concat("Word: " + wordObject.getString("word") + "\n");
                            toDisplay = toDisplay.concat("\n");
                            toDisplay = toDisplay.concat("Word Class: " + wordObject.getString("wordClass") + "\n");
                            toDisplay = toDisplay.concat("\n");
                            toDisplay = toDisplay.concat("Definition1: " + wordObject.getString("definition1") + "\n");
                            toDisplay = toDisplay.concat("\n");
                            toDisplay = toDisplay.concat("Definition2: " + wordObject.getString("definition2") + "\n");

                            // try{Thread.sleep(3000);}catch (InterruptedException ex) {}
                            ClientHome.wordDetails = toDisplay;
                            // JOptionPane.showMessageDialog(null, toDisplay);


                        } else {
                            JOptionPane.showMessageDialog(null, response);

                        }
                    } catch (EOFException ex) {
                        JOptionPane.showMessageDialog(null, "Server is down!");
                        //break;
                        System.exit(0);
                        break;

                        //Thread.currentThread().
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "IO Error occurred!");
                    }
                }
            }
        });
        sendRequestThread.start();
        readResponseThread.start();
    }


}
