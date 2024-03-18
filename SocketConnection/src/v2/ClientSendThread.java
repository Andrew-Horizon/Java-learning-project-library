package v2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class ClientSendThread implements Runnable{
    private OutputStream outs;

    public ClientSendThread(OutputStream outs){
        this.outs=outs;
    }

    public void sendMessage(OutputStream outs, String message) {
        try {
            outs.write(message.getBytes());
            outs.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        while(true){
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            sendMessage(outs, s);
        }
    }
}
