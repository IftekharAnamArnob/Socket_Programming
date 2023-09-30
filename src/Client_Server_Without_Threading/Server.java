package Client_Server_Without_Threading;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(22222);
        System.out.println("Server started...");

        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected...");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            try {
                // read from client
                Object Cmsg = ois.readObject();
                System.out.println("From Client: " + (String) Cmsg);

                String serverMsg = (String) Cmsg;
                serverMsg = serverMsg.toUpperCase();

                //send to client
                oos.writeObject(serverMsg);


            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
