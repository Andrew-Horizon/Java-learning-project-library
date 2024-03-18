package uiChat.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class fileButton extends JButton {
    private ImageIcon imagePicture;
    private ImageIcon pressedIcon;
    public fileButton(){
        super();
        setSize(50,50);
        setLayout(new BorderLayout());
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\fileSend(0).png");
        setPressedIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\fileSend(1).png");
    }
    private void setBackground(Image image){
        image = ImageHandler.imageResize(image, this.getWidth(), this.getHeight());
        imagePicture = new ImageIcon(image);
        setIcon(imagePicture);
    }
    private void setBackground(String path){
        try {
            Image image = ImageIO.read(new File(path));
            setBackground(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setPressedIcon(Image image) {
        image = ImageHandler.imageResize(image, this.getWidth(), this.getHeight());
        pressedIcon = new ImageIcon(image);
        setPressedIcon(pressedIcon);
    }

    public void setPressedIcon(String path) {
        try {
            Image image = ImageIO.read(new File(path));
            setPressedIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
