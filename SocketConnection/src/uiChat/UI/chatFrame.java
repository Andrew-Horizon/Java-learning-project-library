package uiChat.UI;


import uiChat.MyObjectStream.FileMessage;
import uiChat.MyObjectStream.MyObjectInputStream;
import uiChat.MyObjectStream.MyObjectOutputStream;
import uiChat.client.ClientReadThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class chatFrame extends JFrame implements Runnable{
    private JPanel panel;
    private JPanel userPanel;
    private HashMap<String,JTextArea> textAreaMap=new HashMap<>();
    public static List<String> userList = Collections.synchronizedList(new ArrayList<String>());
    public static boolean a=false;
    private String myName;
    private MyObjectOutputStream outs;
    private MyObjectInputStream ins;
    private int number=0;
    public chatFrame(String myName,MyObjectOutputStream outs,MyObjectInputStream ins){
        this.myName=myName;
        this.outs=outs;
        this.ins=ins;
        setTitle(myName);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(814,637);//800+14,600+37
        setLocationRelativeTo(null);
        panel=new JPanel();
        panel.setLayout(null);

        userPanel = new JPanel();
        userPanel.setLayout(null);
        userPanel.setBounds(200,0,600,600);
        add(userPanel, BorderLayout.CENTER);

        add(panel,BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void run() {
        int i=0;
        ArrayList<String> usernameList=new ArrayList<>();
        ClientReadThread clientReadThread=new ClientReadThread(ins,textAreaMap,myName);
        new Thread(clientReadThread).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(true){
            if(number!=userList.size()){
                drawUI(i,usernameList);
                number=userList.size();
            }
        }
    }
    private void drawUI(int i,ArrayList<String> usernameList){
        synchronized(userList) {
            for (String username : userList) {
                if (i == 0) {
                    JTextArea textArea = new JTextArea();
                    textArea.setEditable(false);
                    textAreaMap.put("||group||", textArea);
                    mateListButton button = setButton("||group||", i);
                    panel.add(button);
                    validate();
                    i++;
                }
                if (!username.equals(myName)) {
                    if (!usernameList.contains(username)) {
                        usernameList.add(username);
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false);
                        textAreaMap.put(username, textArea);
                        mateListButton button = setButton(username, i);
                        panel.add(button);
                        validate();
                        i++;
                    }
                }
            }
        }
    }

    private mateListButton setButton(String username, int i) {
        mateListButton button = new mateListButton(username);
        button.setActionCommand(username);
        button.setBounds(0, 100 * i, 200, 100);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.removeAll();
                JTextArea textArea=textAreaMap.get(e.getActionCommand());
                JScrollPane scrollPane = new JScrollPane(textArea);//可滚动查看
                scrollPane.setBounds(0,0,600,400);
                userPanel.add(scrollPane);
                fileButton fileButton=new fileButton();
                fileButton.setBounds(0,400,50,50);
                fileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame jFrame=new JFrame();
                        jFrame.setTitle("文件选择");
                        jFrame.setSize(300, 200);
                        jFrame.setLocationRelativeTo(null);

                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(jFrame);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            FileMessage fileMessage=new FileMessage(selectedFile,myName,username);
                            System.out.println("文件类型为："+fileMessage.getFileType());
                            try {
                                sendFileMessage(outs,fileMessage);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            appendToText(myName+"发送文件："+fileMessage.getFileName()+"   大小："+fileMessage.getFileSize()+"Bytes", textArea);
                        }

                    }
                });
                userPanel.add(fileButton);
                JTextField text=new JTextField();
                text.setBounds(0, 450, 600, 150);
                text.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String message = text.getText();
                        try {
                            if(message.startsWith("接收")){
                                sendMessage(outs,myName+"@"+message);
                            }else {
                                sendMessage(outs, username + "@" + message);
                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        appendToText(myName+":"+message, textArea);
                        text.setText("");
                    }
                });
                userPanel.add(text);

                userPanel.revalidate();
                userPanel.repaint();
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setVisible(true);
        return button;
    }

    private void appendToText(String text,JTextArea textArea) {
        textArea.append(text + "\n");//换行
        textArea.setCaretPosition(textArea.getDocument().getLength());//自动滚动到最新一行
    }

    public void sendMessage(ObjectOutputStream objectOutputStream, String message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    public void sendFileMessage(ObjectOutputStream objectOutputStream, Object message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

}