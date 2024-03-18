package uiChat.UI;



import uiChat.MyObjectStream.MyObjectOutputStream;
import uiChat.client.ClientThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class loginFrame extends JFrame {
    public static JLabel label1=new JLabel();
    public static Boolean switchSend=true;
    private MyObjectOutputStream objectOutputStream;
    private void initComponents() {
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150,155,150,30);
        JTextField passwordField = new JPasswordField();
        passwordField.setBounds(150,190,150,30);
        add(usernameField);
        add(passwordField);
        label1.setBounds(310,190,100,30);
        add(label1);
        JButton loginButton = new JButton("登录");
        add(loginButton);
        loginButton.setBounds(150,240,140,30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(switchSend) {
                    try {
                        sendMessage(objectOutputStream, "登录");
                        sendMessage(objectOutputStream, usernameField.getText());
                        ClientThread.myName=usernameField.getText();
                        sendMessage(objectOutputStream, passwordField.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    try {
                        sendMessage(objectOutputStream, passwordField.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        JButton signupButton = new JButton("注册");
        add(signupButton);
        signupButton.setBounds(30,240,100,30);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientThread.s=false;
                signupFrame signupframe=new signupFrame(objectOutputStream);
            }
        });
    }
    public loginFrame(MyObjectOutputStream objectOutputStream){
        this.objectOutputStream=objectOutputStream;
        setTitle("开始界面");
        setSize(440,330);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();

        setVisible(true);
    }
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage buffer = null;
        try {
            buffer = ImageIO.read(new File("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\white.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Graphics bufferG = buffer.getGraphics();
        ImageIcon background = new ImageIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\red.jpg");
        bufferG.drawImage(background.getImage(), 0, 0, 440, 130, null);
        ImageIcon head=new ImageIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\qq.jpg");
        bufferG.drawImage(head.getImage(),185,95,70,70,null);
        bufferG.setColor(Color.BLACK);
        bufferG.drawString("用户名：",110,200);
        bufferG.drawString("密  码：",115,240);
        g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
        initComponents();
    }
    public void sendMessage(ObjectOutputStream objectOutputStream, String message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }
}
