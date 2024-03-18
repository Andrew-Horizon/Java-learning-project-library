package uiChat.server;

import uiChat.MyObjectStream.FileMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Server {
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String user = "root";
    public static String password = "2019_lhy";
    private HashMap<Socket,String> socketMap=new HashMap<>();
    private List<String> userList = Collections.synchronizedList(new ArrayList<String>());
    private ArrayList<FileMessage> fileList=new ArrayList<>();

    public void startServer() throws IOException {
        ServerSocket server=new ServerSocket(8888);
        System.out.println("服务端启动，等待连接");
        Socket socket;
        while(true){
            socket=server.accept();
            int clientPort=socket.getPort();
            System.out.println("已连接客户端端号："+clientPort);
            socketMap.put(socket,clientPort+"");
            ServerThread ServerThread=new ServerThread(socketMap,socket,userList,fileList);
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