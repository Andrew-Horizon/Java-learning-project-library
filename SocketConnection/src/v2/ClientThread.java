package v2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientThread implements Runnable{
    private InputStream ins;
    private OutputStream outs;
    public ClientThread(InputStream ins,OutputStream outs){
        this.ins=ins;
        this.outs=outs;
    }
    public String read(InputStream ins) {
        byte[] b = new byte[1024];
        try {
            ins.read(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(b).trim();
    }

    public void run(){
        ClientSendThread clientSendThread=new ClientSendThread(outs);
        new Thread(clientSendThread).start();
        while (true) {
            ClientSendThread sst = new ClientSendThread(outs);
            new Thread(sst).start();
            String receiveMessage = read(ins);
            System.out.println("服务端消息：" + receiveMessage);

        }
    }
}
