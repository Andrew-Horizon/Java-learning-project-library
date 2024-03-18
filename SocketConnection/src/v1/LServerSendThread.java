package v1;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LServerSendThread implements Runnable {
    public InputStream ins;

    public LServerSendThread(InputStream ins) {
        this.ins = ins;
    }

    public void run() {
        while (true) {
            String str = read(ins);
            System.out.println("客户端消息：" + str);
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
}
