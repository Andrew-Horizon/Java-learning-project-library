package v1;

import v1.LServerSendThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class LServerThread implements Runnable {
    private Socket socket;
    private InputStream ins;
    private OutputStream outs;

    public LServerThread(Socket socket) {
        this.socket = socket;
        try {
            ins = socket.getInputStream();
            outs = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        System.out.println(Thread.currentThread().getName() + "启用:" + socket);
        try {
            String message = "连接成功";
            sendMessage(outs,message);

            while (true) {

                LServerSendThread sst = new LServerSendThread(ins);
                new Thread(sst).start();

                Scanner scanner = new Scanner(System.in);
                String str="服务端发送消息：";
                String s = scanner.nextLine();
                sendMessage(outs, str+s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
