package uiChat.client;


import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.MyObjectStream.MyObjectOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    public Client(Socket socket){
        this.socket=socket;
    }
    public void createSocket() throws IOException {
        outs=new MyObjectOutputStream(socket.getOutputStream());
        ins=new MyObjectInputStream(socket.getInputStream());

        ClientThread clientThread=new ClientThread(ins,outs);
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