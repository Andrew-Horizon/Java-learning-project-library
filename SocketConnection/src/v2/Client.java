package v2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private InputStream ins;
    private OutputStream outs;
    public Client(Socket socket){
        this.socket=socket;
    }
    public void createSocket() throws IOException {
        ins=socket.getInputStream();
        outs=socket.getOutputStream();
        ClientThread clientThread=new ClientThread(ins,outs);
        new Thread(clientThread).start();
        int clientPort=socket.getPort();
        System.out.println("已接入服务端端口号："+clientPort);
    }
    public InputStream getIns(){
        return ins;
    }
    public OutputStream getOuts(){
        return outs;
    }
    public static void main(String[] args) throws IOException {
        Socket newSocket=new Socket("127.0.0.1", 8888);
        Client client=new Client(newSocket);
        client.createSocket();
    }
}