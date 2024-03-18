package v3.server;

import v3.MyObjectStream.MyObjectInputStream;
import v3.MyObjectStream.MyObjectOutputStream;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerThread implements Runnable{
    private HashMap<Socket,Integer> socketMap;
    private HashMap<Integer,String> nameMap;
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    private Socket socket;
    public ServerThread(HashMap<Socket,Integer> socketMap,HashMap<Integer,String> nameMap,Socket socket){
        this.socketMap=socketMap;
        this.nameMap=nameMap;
        this.socket=socket;
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
        while(true){
            try {
                sendMessage(outs,nameMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String clientMessage= null;
            try {
                clientMessage = readMessage(ins);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String[] massageArray=clientMessage.split(" ");
            switch (massageArray[0]){
                case "all":
                    for(Socket s:socketMap.keySet()){
                        if(s!=socket){
                            try{
                                MyObjectOutputStream outputStream=new MyObjectOutputStream(s.getOutputStream());
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
                                    MyObjectOutputStream outputStream = null;
                                    for (Map.Entry<Socket, Integer> entry : socketMap.entrySet()) {
                                        if (entry.getValue().equals(port)) {
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
    public void LS() throws IOException {
        boolean b2=true;
        while(b2) {
            String massage = "请输入选择登录或注册";
            sendMessage(outs, socketMap.get(socket) + massage);
            try {
                String ls = readMessage(ins);
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(Server.url, Server.user, Server.password);
                if (ls.equals("登录")) {
                    boolean b1=true;
                    sendMessage(outs, "请输入用户名：");
                    String username = readMessage(ins);
                    while (b1) {
                        sendMessage(outs, "请输入密码：");
                        String password = readMessage(ins);
                        String querySQL = "SELECT COUNT(*) FROM userMessage WHERE username = ? AND password = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            if (count > 0) {
                                sendMessage(outs, "登陆成功");
                                nameMap.put(socketMap.get(socket),username);
                                b1=false;
                                b2=false;
                            } else {
                                String checkUsernameSQL = "SELECT COUNT(*) FROM userMessage WHERE username = ?";
                                try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameSQL)) {
                                    checkUsernameStatement.setString(1, username);
                                    try (ResultSet checkUsernameResultSet = checkUsernameStatement.executeQuery()) {
                                        if (checkUsernameResultSet.next()) {
                                            int usernameCount = checkUsernameResultSet.getInt(1);
                                            if (usernameCount > 0) {
                                                sendMessage(outs, "密码错误，请重新输入密码");
                                                b2=false;
                                            } else {
                                                sendMessage(outs, "用户名不存在，重新登录或注册");
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
                        sendMessage(outs, "请输入用户名：");
                        username = readMessage(ins);
                        sendMessage(outs, "请输入密码：");
                        password1 = readMessage(ins);
                        sendMessage(outs, "请再次输入密码：");
                        password2 = readMessage(ins);
                        if (password1.equals(password2)) {
                            break;
                        } else {
                            sendMessage(outs, "两次输入密码不一致，重新注册");
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
    public String readMessage(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (String) objectInputStream.readObject();
    }
}
