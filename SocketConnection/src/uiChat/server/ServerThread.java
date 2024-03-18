package uiChat.server;

import uiChat.MyObjectStream.FileMessage;
import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.MyObjectStream.MyObjectOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class ServerThread implements Runnable{
    private HashMap<Socket,String> socketMap;
    private List<String> userList;
    private ArrayList<FileMessage> fileList;
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    private Socket socket;
    private String Name;
    public ServerThread(HashMap<Socket,String> socketMap, Socket socket, List<String> userList,ArrayList<FileMessage> fileList){
        this.socketMap=socketMap;
        this.socket=socket;
        this.userList=userList;
        this.fileList=fileList;
        try{
            ins=new MyObjectInputStream(socket.getInputStream());
            outs=new MyObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void run(){
        System.out.println(Thread.currentThread().getName() + "启用:" + socket);
        try {
            LS();//登录注册
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sendMessage(outs, userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Socket s:socketMap.keySet()){
            if(s!=socket){
                try{
                    MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
                    sendMessage(outputStream,"newUser"+"@"+Name);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        while(true){
            Object receivedMessage;
            try {
                receivedMessage=readMessage(ins);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if(receivedMessage instanceof String clientMessage) {
                String[] massageArray=clientMessage.split("@");
                boolean jump=false;
                for(FileMessage fileMessage:fileList){
                    String filename=fileMessage.getFileName();
                    if(massageArray[1].equals("接收"+filename)){
                        for(Socket s:socketMap.keySet()){
                            if(socketMap.get(s).equals(massageArray[0])){
                                try {
                                    MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
                                    sendMessage(outputStream,fileMessage);
                                    jump=true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            if(socketMap.get(s).equals(fileMessage.getSender())){
                                try {
                                    MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
                                    sendMessage(outputStream,fileMessage.getReceiver()+"@"+"已接收文件："+fileMessage.getFileName());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
                if (jump){
                    continue;
                }

                switch (massageArray[0]){
                    case "||group||":
                        for(Socket s:socketMap.keySet()){
                            if(s!=socket){
                                try{
                                    MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
                                    StringBuilder result = new StringBuilder();
                                    for (int i = 1; i < massageArray.length; i++) {
                                        result.append(massageArray[i]).append(" ");
                                    }
                                    clientMessage=result.toString();
                                    sendMessage(outputStream,"||group||"+"@"+socketMap.get(socket)+" "+"："+clientMessage);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        break;
                    default:
                        Collection<String> userList=socketMap.values();
                        if(!userList.contains(massageArray[0])){
                            System.out.println("请输入正确的用户名");
                            break;
                        }
                        for(String user:userList){
                            if(massageArray[0].equals(user)){
                                if(!user.equals(socketMap.get(socket))){
                                    try{
                                        MyObjectOutputStream outputStream = null;
                                        for (Map.Entry<Socket, String> entry : socketMap.entrySet()) {
                                            if (entry.getValue().equals(user)) {
                                                outputStream=new MyObjectOutputStream(entry.getKey().getOutputStream());
                                                break;
                                            }
                                        }
                                        StringBuilder result = new StringBuilder();
                                        for (int i = 1; i < massageArray.length; i++) {
                                            result.append(massageArray[i]);
                                        }
                                        clientMessage=result.toString();
                                        assert outputStream != null;
                                        sendMessage(outputStream,socketMap.get(socket)+"@"+"："+clientMessage);
                                        break;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                }
            }else if(receivedMessage instanceof FileMessage fileMessage){
                fileList.add(fileMessage);
                String receiver=fileMessage.getReceiver();
                switch(receiver){
                    case "||group||":
                        for(Socket s:socketMap.keySet()){
                            if(s!=socket){
                                try{
                                    MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
                                    sendMessage(outputStream,"||group||"+"@"+socketMap.get(socket)+"发送文件："+fileMessage.getFileName()+"   大小："+fileMessage.getFileSize()+"Bytes");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        break;
                    default:
                        Collection<String> userList=socketMap.values();
                        if(!userList.contains(receiver)){
                            System.out.println("请输入正确的用户名");
                            break;
                        }
                        for(String user:userList){
                            if(receiver.equals(user)){
                                if(!user.equals(socketMap.get(socket))){
                                    try{
                                        MyObjectOutputStream outputStream = null;
                                        for (Map.Entry<Socket, String> entry : socketMap.entrySet()) {
                                            if (entry.getValue().equals(user)) {
                                                outputStream=new MyObjectOutputStream(entry.getKey().getOutputStream());
                                                break;
                                            }
                                        }
                                        assert outputStream != null;
                                        sendMessage(outputStream,socketMap.get(socket)+"@"+"发送文件："+fileMessage.getFileName()+"   大小："+fileMessage.getFileSize()+"Bytes");
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
    }
    public void LS() throws IOException {//防止重复登录
        boolean b2=true;
        while(b2) {
            try {
                String ls = readLSMessage(ins);
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(Server.url, Server.user, Server.password);
                if (ls.equals("登录")) {
                    boolean b1=true;
                    String username = readLSMessage(ins);
                    while (b1) {
                        String password = readLSMessage(ins);
                        String querySQL = "SELECT COUNT(*) FROM userMessage WHERE username = ? AND password = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            if (count > 0) {
                                if(userList.contains(username)){
                                    sendMessage(outs, "用户已登录");
                                    b1=false;
                                }else {
                                    sendMessage(outs, "登陆成功");
                                    socketMap.put(socket, username);
                                    userList.add(username);
                                    Name = username;
                                    b1 = false;
                                    b2 = false;
                                }
                            } else {
                                String checkUsernameSQL = "SELECT COUNT(*) FROM userMessage WHERE username = ?";
                                try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameSQL)) {
                                    checkUsernameStatement.setString(1, username);
                                    try (ResultSet checkUsernameResultSet = checkUsernameStatement.executeQuery()) {
                                        if (checkUsernameResultSet.next()) {
                                            int usernameCount = checkUsernameResultSet.getInt(1);
                                            if (usernameCount > 0) {
                                                sendMessage(outs, "密码错误");
                                                b2=false;
                                            } else {
                                                sendMessage(outs, "用户名不存在");
                                                b1=false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (ls.equals("注册")) {
                    Statement statement = connection.createStatement();
                    String username;
                    String password1;
                    String password2;
                    while (true) {
                        username = readLSMessage(ins);
                        password1 = readLSMessage(ins);
                        password2 = readLSMessage(ins);
                        if (password1.equals(password2)) {
                            break;
                        } else {
                            sendMessage(outs, "密码不一致");
                        }
                    }
                    Random random = new Random();
                    int randomNumber = random.nextInt();
                    int id = Math.abs(randomNumber) % (int) Math.pow(10, 10);
                    String insertDataSQL = "INSERT INTO userMessage (id,username, password) VALUES ('" + id + "','" + username + "', '" + password1 + "')";
                    statement.executeUpdate(insertDataSQL);
                    sendMessage(outs, "注册成功");
                    statement.close();
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(ObjectOutputStream objectOutputStream, Object message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }
    public String readLSMessage(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (String) objectInputStream.readObject();
    }
    public Object readMessage(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }
}
