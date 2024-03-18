package v2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable{
    private HashMap<Socket,Integer> socketMap;
    private InputStream ins;
    private OutputStream outs;
    private Socket socket;
    public ServerThread(HashMap<Socket,Integer> socketMap,Socket socket){
        this.socketMap=socketMap;
        this.socket=socket;
        try{
            ins=socket.getInputStream();
            outs=socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void run(){
        System.out.println(Thread.currentThread().getName() + "启用:" + socket);
        String massage="连接成功";
        sendMessage(outs,socketMap.get(socket)+massage);
        while(true){
            String clientMessage=read(ins);
            String[] massageArray=clientMessage.split(" ");
            switch (massageArray[0]){
                case "all":
                    for(Socket s:socketMap.keySet()){
                        if(s!=socket){
                            try{
                                OutputStream outputStream=s.getOutputStream();
                                StringBuilder result = new StringBuilder();
                                for (int i = 1; i < massageArray.length; i++) {
                                    result.append(massageArray[i]);
                                }
                                clientMessage=result.toString();
                                sendMessage(outputStream,"客户端"+socketMap.get(socket)+"发来全体消息："+clientMessage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    break;
                default:
                    massageArray[0]=massageArray[0].replaceAll("[^\\d]", "");
                    int massageTowards=Integer.parseInt(massageArray[0]);
                    Collection<Integer> portList=socketMap.values();
                    if(!portList.contains(massageTowards)){
                        System.out.println("请输入正确的端口号");
                        break;
                    }
                    for(int port:portList){
                        if(massageTowards==port){
                            if(port!=socketMap.get(socket)){
                                try{
                                    OutputStream outputStream = null;
                                    for (Map.Entry<Socket, Integer> entry : socketMap.entrySet()) {
                                        if (entry.getValue().equals(port)) {
                                            outputStream=entry.getKey().getOutputStream();
                                            break;
                                        }
                                    }
                                    StringBuilder result = new StringBuilder();
                                    for (int i = 1; i < massageArray.length; i++) {
                                        result.append(massageArray[i]);
                                    }
                                    clientMessage=result.toString();
                                    sendMessage(outputStream,"客户端"+socketMap.get(socket)+"发来消息："+clientMessage);
                                    break;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
            }
        }


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
}
