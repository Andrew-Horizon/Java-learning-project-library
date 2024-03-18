package uiChat.UI;

import uiChat.MyObjectStream.MyObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class signupFrame extends JFrame {
    public static JLabel label2=new JLabel();
    private MyObjectOutputStream objectOutputStream;
    public signupFrame(MyObjectOutputStream objectOutputStream){
        this.objectOutputStream=objectOutputStream;
        setTitle("注册界面");
        setSize(440,330);
        setLayout(null);
        setLocationRelativeTo(null);

        setVisible(true);
    }
    public void paint(Graphics g){
        BufferedImage buffer;
        try {
            buffer = ImageIO.read(new File("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\white.png"));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        Graphics bufferG = buffer.getGraphics();
        ImageIcon background = new ImageIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\red.jpg");
        bufferG.drawImage(background.getImage(), 0, 0, 440, 130, null);
        bufferG.setColor(Color.BLACK);
        bufferG.drawString("用户名：",110,160);
        bufferG.drawString("密  码：",115,200);
        bufferG.drawString("确认密码：",105,240);
        g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
        JTextField usernameFields = new JTextField();
        usernameFields.setBounds(150,115,150,30);
        JTextField passwordFields = new JPasswordField();
        passwordFields.setBounds(150,155,150,30);
        JTextField matchPasswordFields = new JPasswordField();
        matchPasswordFields.setBounds(150,195,150,30);
        add(usernameFields);
        add(passwordFields);
        add(matchPasswordFields);
        label2.setBounds(310,195,100,30);
        add(label2);
        JButton signButton = new JButton("确认注册");
        add(signButton);
        signButton.setBounds(150,245,140,30);
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendMessage(objectOutputStream,"注册");
                    sendMessage(objectOutputStream,usernameFields.getText());
                    sendMessage(objectOutputStream,passwordFields.getText());
                    sendMessage(objectOutputStream,matchPasswordFields.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void sendMessage(ObjectOutputStream objectOutputStream, String message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }
}
