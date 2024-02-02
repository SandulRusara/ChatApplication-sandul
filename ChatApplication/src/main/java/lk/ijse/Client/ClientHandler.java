package lk.ijse.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler {

    private Socket socket;
    private List<ClientHandler> client;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String msg = "";

    public ClientHandler(Socket socket, List<ClientHandler> client) throws IOException {

           this.socket = socket;
           this.client = client;
           this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
           this.dataInputStream = new DataInputStream(socket.getInputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (socket.isConnected()) {
                        msg = dataInputStream.readUTF(); //read client receive message

                        for (ClientHandler clientHandler : client){ //send message other client
                            if (clientHandler.socket.getPort() != socket.getPort()){
                                clientHandler.dataOutputStream.writeUTF(msg);
                                clientHandler.dataOutputStream.flush();
                            }
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
