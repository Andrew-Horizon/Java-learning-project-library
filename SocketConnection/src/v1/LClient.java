package v1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LClient {
    public void createClient() throws Exception{
        Socket socket = new Socket("127.0.0.1", 8888);
        System.out.println("连接到服务器");
        InputStream ins = socket.getInputStream();
        OutputStream outs = socket.getOutputStream();
        LClientThread receiveThread = new LClientThread(ins,outs);
        new Thread(receiveThread).start();
    }


    public static void main(String[] args){
        try {
            new LClient().createClient();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}