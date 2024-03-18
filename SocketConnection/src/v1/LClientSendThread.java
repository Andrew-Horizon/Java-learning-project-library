package v1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class LClientSendThread implements Runnable {
    private OutputStream outs;

    public LClientSendThread(OutputStream outs) {
        this.outs = outs;
    }

    public void sendMessage(OutputStream outs,String message) {
        try {
            outs.write(message.getBytes());
            outs.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void run() {
        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String str="客户端发送消息：";
                String s = scanner.nextLine();
                sendMessage(outs, str+s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
