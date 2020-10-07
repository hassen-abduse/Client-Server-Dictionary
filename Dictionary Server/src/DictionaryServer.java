import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class DictionaryServer {

    public static FileHandler fileHandler = new FileHandler();
    public static File JSONFile = new File("/home/ab/IdeaProjects/Dictionary Server/src/dictionary.json");

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1235);
        } catch (IOException e) {

            System.out.println("Couldn't Create ServerSocket: Check if another server instance is running.");
        }
        Socket socket;
        System.out.println("Dictionary server is running...");

        while (true) {

            socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("New Client connected " + socket);
            System.out.println("Creating a handler for the new client" + socket);
            ClientHandler clientHandler = new ClientHandler(socket, dataInputStream, dataOutputStream);

            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
        }

    }


}


class ClientHandler implements Runnable {

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    Socket clientSocket;

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.clientSocket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        // implementations for serving the clients
        String clientRequest;
        while (true) {
            synchronized (this) {
                try {
                    clientRequest = dataInputStream.readUTF();
                    System.out.println(clientRequest);
                    //System.out.println(clientRequest);
                    if ((clientRequest.contains("SEARCH*"))) {
                        //do the search operations
                        System.out.println("Search request has come");
                        StringTokenizer stringTokenizer = new StringTokenizer(clientRequest, "*");
                        String operation = stringTokenizer.nextToken();
                        System.out.println(operation);
                        String wordString = stringTokenizer.nextToken();
                        System.out.println(wordString);
                        JSONObject wordObject;
                        if (DictionaryServer.fileHandler.searchWord(wordString, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile)) == null) {
                            dataOutputStream.writeUTF("Word Not Found");
                        } else {
                            wordObject = new JSONObject(DictionaryServer.fileHandler.searchWord(wordString, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile)));
                            dataOutputStream.writeUTF("SEARCH*" + wordObject.toString());
                        }

                    } else if (clientRequest.contains(("ADD*"))) {
                        // do the add operations
                        System.out.println("Add request has come");
                        StringTokenizer stringTokenizer = new StringTokenizer(clientRequest, "*");
                        String operation = stringTokenizer.nextToken();
                        System.out.println(operation);
                        String wordJSON = stringTokenizer.nextToken();
                        JSONObject wordObject = new JSONObject(wordJSON);
                        if (DictionaryServer.fileHandler.searchWord(wordObject.getString("word"), DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile)) == null) {

                            String jsonString = DictionaryServer.fileHandler.addWord(wordObject, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile));
                            DictionaryServer.fileHandler.writeJSONFile(jsonString, DictionaryServer.JSONFile);
                            System.out.println(DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile));
                            dataOutputStream.writeUTF("Word added successfully!");

                        } else {
                            dataOutputStream.writeUTF("Duplicate word. Couldn't Add!");
                        }


                    } else if (clientRequest.contains("REMOVE*")) {
                        // do the remove operations
                        System.out.println("Remove request has come");
                        StringTokenizer stringTokenizer = new StringTokenizer(clientRequest, "*");
                        String operation = stringTokenizer.nextToken();
                        System.out.println(operation);
                        String wordString = stringTokenizer.nextToken();
                        JSONObject wordObject;
                        if (DictionaryServer.fileHandler.searchWord(wordString, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile)) == null) {
                            dataOutputStream.writeUTF("Word Not Found!");

                        } else {
                            wordObject = new JSONObject(DictionaryServer.fileHandler.searchWord(wordString, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile)));
                            String jsonString = DictionaryServer.fileHandler.removeWord(wordObject, DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile));
                            DictionaryServer.fileHandler.writeJSONFile(jsonString, DictionaryServer.JSONFile);
                            System.out.println(DictionaryServer.fileHandler.readJSONFile(DictionaryServer.JSONFile));
                            dataOutputStream.writeUTF("Word Removed Successfully!");
                        }
                    }
                    else {
                        dataOutputStream.writeUTF("ADMIN/Success/Abduselam/Assen/Developer/1/hello");
                    }


                } catch (EOFException ex) {
                    System.out.println("Client " + clientSocket + " Disconnected ");
                    break;
                } catch (IOException ex) {
                    System.out.println("IO Error occured.");
                    break;
                }
            }
        }

    }
}







