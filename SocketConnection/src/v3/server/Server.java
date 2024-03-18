package v3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String user = "root";
    public static String password = "2019_lhy";
    private HashMap<Socket,Integer> socketMap=new HashMap<>();
    private HashMap<Integer,String> nameMap=new HashMap<>();
    public void startServer() throws IOException {
        ServerSocket server=new ServerSocket(8888);
        System.out.println("服务端启动，等待连接");
        Socket socket;
        while(true){
            socket=server.accept();
            int clientPort=socket.getPort();
            System.out.println("已连接客户端端号："+clientPort);
            socketMap.put(socket,clientPort);
            ServerThread ServerThread=new ServerThread(socketMap,nameMap,socket);
            new Thread(ServerThread).start();
        }
    }
    public static void main(String[] args){
        try{
            Server server =new Server();
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}