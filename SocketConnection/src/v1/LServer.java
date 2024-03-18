package v1;

import java.net.ServerSocket;
import java.net.Socket;

public class LServer {
    public void createServer() throws Exception {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器启动，等待连接");
        Socket socket;
        while (true) {
            socket = server.accept(); // 阻塞
            LServerThread st = new LServerThread(socket);
            new Thread(st).start();
        }
    }

    public static void main(String[] args) {
        try {
            new LServer().createServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}