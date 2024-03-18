package v1;

import v1.LClientSendThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LClientThread implements Runnable {
    private InputStream ins;
    private OutputStream outs;

    public LClientThread(InputStream ins,OutputStream outs) {
        this.ins = ins;
        this.outs=outs;
    }

    public void sendMessage(OutputStream outs,String message) {
        try {
            outs.write(message.getBytes());
            outs.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read(InputStream is) {
        byte[] b = new byte[1024];
        try {
            is.read(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(b).trim();
    }

    public void run() {
        try {
            String message = "连接成功";
            sendMessage(outs,message);
            while (true) {
                LClientSendThread sst = new LClientSendThread(outs);
                new Thread(sst).start();

                String str = read(ins);
                System.out.println("服务端消息：" + str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
