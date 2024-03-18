package uiChat.client;


import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.MyObjectStream.MyObjectOutputStream;
import uiChat.UI.chatFrame;
import uiChat.UI.loginFrame;
import uiChat.UI.signupFrame;

import java.io.IOException;

public class ClientThread implements Runnable{
    public static Boolean s=true;
    public static String myName;
    private MyObjectInputStream ins;
    private MyObjectOutputStream outs;
    public ClientThread(MyObjectInputStream ins, MyObjectOutputStream outs){
        this.ins=ins;
        this.outs=outs;
    }

    public void run(){
        loginFrame frame=new loginFrame(outs);
        while (true) {
            try {
                Object receivedObject = ins.readObject();
                if (receivedObject instanceof String receiveMessage) {
                    if(s) {
                        loginFrame.label1.setText(receiveMessage);
                        if(receiveMessage.equals("登陆成功")){
                            try {
                                for(int i=3;i>0;i--){
                                    loginFrame.label1.setText("登陆成功请等待."+i);
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            frame.dispose();
                            chatFrame Frame=new chatFrame(myName,outs,ins);
                            new Thread(Frame).start();
                            break;
                        }
                    }else{
                        signupFrame.label2.setText(receiveMessage);
                    }
                    if(receiveMessage.equals("密码错误")){
                        loginFrame.switchSend=false;
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
