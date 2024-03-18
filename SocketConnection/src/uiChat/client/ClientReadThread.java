package uiChat.client;

import uiChat.MyObjectStream.FileMessage;
import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.UI.chatFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ClientReadThread implements Runnable{
    private MyObjectInputStream ins;
    private HashMap<String, JTextArea> textAreaMap;
    private String myName;

    public ClientReadThread(MyObjectInputStream ins, HashMap<String, JTextArea> textAreaMap,String myName){
        this.ins=ins;
        this.textAreaMap=textAreaMap;
        this.myName=myName;
    }

    public String readMessage(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (String) objectInputStream.readObject();
    }

    private void appendToText(String text,JTextArea textArea) {
        textArea.append(text + "\n");//换行
        textArea.setCaretPosition(textArea.getDocument().getLength());//自动滚动到最新一行
    }

    public void run() {
        while (true) {
            Object receivedObject;
            try {
                receivedObject = ins.readObject();
                if(receivedObject instanceof String) {
                    String clientMessage = (String) receivedObject;
                    String[] massageArray = clientMessage.split("@");
                    switch (massageArray[0]) {
                        case "||group||":
                            StringBuilder result = new StringBuilder();
                            for (int i = 1; i < massageArray.length; i++) {
                                result.append(massageArray[i]);
                            }
                            clientMessage = result.toString();
                            appendToText(clientMessage,textAreaMap.get("||group||"));
                            break;
                        case "newUser":
                            chatFrame.userList.add(massageArray[1]);
                            System.out.println("新用户登录："+massageArray[1]);
                            break;
                        default:
                            clientMessage=clientMessage.replace("@","");
                            Collection<String> userList = textAreaMap.keySet();
                            for (String user : userList) {
                                if (massageArray[0].equals(user)) {
                                    appendToText(clientMessage,textAreaMap.get(user));
                                    break;
                                }
                            }
                    }
                }else if (receivedObject instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> receivedNameMap = (List<String>) receivedObject;
                    synchronized(chatFrame.userList) {
                        chatFrame.userList = receivedNameMap;
                    }
                    System.out.println("收到userList");
                    for(String s:chatFrame.userList){
                        System.out.println(s);
                    }
                    chatFrame.a=true;
                }else if(receivedObject instanceof FileMessage){
                    FileMessage fileMessage=(FileMessage) receivedObject;
                    String destinationPath = "C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\receivedFile";
                    saveFileFromMessage(fileMessage,destinationPath);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
    private void saveFileFromMessage(FileMessage fileMessage, String destinationPath) {
        if (fileMessage.getFileData() != null && fileMessage.getFileData().length > 0) {
            try (FileOutputStream fos = new FileOutputStream(destinationPath + File.separator + fileMessage.getFileName())) {
                fos.write(fileMessage.getFileData());
                System.out.println("文件已成功保存到: " + destinationPath);
            } catch (IOException e) {
                System.out.println("保存文件失败: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("空数据文件");
        }
    }
}
