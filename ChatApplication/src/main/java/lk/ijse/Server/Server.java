package lk.ijse.Server;

import lk.ijse.Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static Server server;
    private ServerSocket serverSocket;
    private Socket socket;
    private List<ClientHandler> client = new ArrayList<>();

    private Server() throws IOException {
        serverSocket = new ServerSocket(3009);
    }

    public static Server getInstance() throws IOException {
        return (server == null) ? server = new Server() : server;
        //return server!=null? server : (server=new Server());
    }

    public void makeSocket() throws IOException {
        while (!serverSocket.isClosed()){
            socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket , client);
            client.add(clientHandler);
        }
    }
}
