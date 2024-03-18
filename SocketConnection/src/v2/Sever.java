package v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Sever {
    private HashMap<Socket,Integer> socketMap=new HashMap<>();
    public void startServer() throws IOException {
        ServerSocket server=new ServerSocket(8888);
        System.out.println("服务端启动，等待连接");
        Socket socket;
        while(true){
            socket=server.accept();
            int clientPort=socket.getPort();
            System.out.println("已连接客户端端号："+clientPort);
            socketMap.put(socket,clientPort);
            ServerThread ServerThread=new ServerThread(socketMap,socket);
            new Thread(ServerThread).start();
        }
    }
    public static void main(String[] args){
        try{
            Sever sever=new Sever();
            sever.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
