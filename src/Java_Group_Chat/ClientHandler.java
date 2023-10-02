package Java_Group_Chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{


    public static ArrayList<ClientHandler>clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUSername;

    public ClientHandler(Socket socket){

        try {
            this.socket=socket;

            this.bufferedWriter=new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUSername=bufferedReader.readLine();
            clientHandlers.add(this);
            boradcastMessage("SERVER: "+ clientUSername + " has enterted the chat!");
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);

        }
    }

    @Override
    public void run() {

        String messageFromClient;

        while(socket.isConnected()){

            try{
                messageFromClient = bufferedReader.readLine();
                boradcastMessage(messageFromClient);
            } catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;

            }

        }

    }

    public void boradcastMessage(String messageToSend){
        for(ClientHandler clientHandler: clientHandlers){
            try {
                if(!clientHandler.clientUSername.equals(clientUSername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e){
                 closeEverything(socket,bufferedReader,bufferedWriter);


            }

        }


    }

   public void removeCLientHandler(){
        clientHandlers.remove(this);
        boradcastMessage("SERVER: "+clientUSername + " has left the chat!");

   }

   public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeCLientHandler();

        try {
            if(bufferedReader!= null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

   }

}
