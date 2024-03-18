package v3.client;

import v3.MyObjectStream.MyObjectOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientSendThread implements Runnable{
    private MyObjectOutputStream outs;

    public ClientSendThread(MyObjectOutputStream outs){
        this.outs=outs;
    }

    public void sendMessage(ObjectOutputStream objectOutputStream, String message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    public void run(){
        while(true){
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            try {
                sendMessage(outs, s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
