package Multiple_Client_To_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        System.out.println("Client started");
        Socket socket = new Socket("127.0.0.1",22222);
        System.out.println("Client connected");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        while(true) {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();

            if(message.equals("exit")){
                break;
            }

            // sending to server

            oos.writeObject(message);

            try {
                //recieve from server
                Object fromserver = ois.readObject();
                System.out.println("From server " + (String) fromserver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        socket.close();



    }
}
