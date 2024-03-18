package v3.client;


import v3.MyObjectStream.MyObjectInputStream;
import v3.MyObjectStream.MyObjectOutputStream;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private Socket socket;
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    private HashMap<Integer,String> nameMap;
    public Client(Socket socket){
        this.socket=socket;
    }
    public void createSocket() throws IOException {
        outs=new MyObjectOutputStream(socket.getOutputStream());
        ins=new MyObjectInputStream(socket.getInputStream());

        ClientThread clientThread=new ClientThread(ins,outs,nameMap);
        new Thread(clientThread).start();
        int clientPort=socket.getPort();
        System.out.println("已接入服务端端口号："+clientPort);
    }

    public static void main(String[] args) throws IOException {
        Socket newSocket=new Socket("127.0.0.1", 8888);
        Client client=new Client(newSocket);
        client.createSocket();
    }
}