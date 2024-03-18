package v3.client;


import v3.MyObjectStream.MyObjectInputStream;
import v3.MyObjectStream.MyObjectOutputStream;

import java.io.*;
import java.util.HashMap;

public class ClientThread implements Runnable{
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    private HashMap<Integer,String> nameMap;
    public ClientThread(MyObjectInputStream ins, MyObjectOutputStream outs, HashMap<Integer,String> nameMap){
        this.ins=ins;
        this.outs=outs;
        this.nameMap=nameMap;
    }
    public String readMessage(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (String) objectInputStream.readObject();
    }

    public void run(){
        ClientSendThread clientSendThread=new ClientSendThread(outs);
        new Thread(clientSendThread).start();
        while (true) {
            try {
                Object receivedObject = ins.readObject();

                if (receivedObject instanceof String receiveMessage) {
                    System.out.println("服务端消息：" + receiveMessage);
                } else if (receivedObject instanceof HashMap) {
                    @SuppressWarnings("unchecked")
                    HashMap<Integer, String> receivedNameMap = (HashMap<Integer, String>) receivedObject;
                    nameMap=receivedNameMap;
                    System.out.println(nameMap.entrySet());
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
