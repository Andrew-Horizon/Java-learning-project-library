package uiChat.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class mateListButton extends JButton {
    private ImageIcon defaultIcon;
    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private ImageIcon selectedIcon;
    private boolean selected;
    private condition state;
    private enum condition{
        Default,
        Enter,
        Press,
        Select
    }

    public mateListButton(String username) {
        super();
        setSize(200,100);
        setLayout(new BorderLayout());
        JLabel label = new JLabel(username, SwingConstants.CENTER);
        add(label);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setDefaultIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\white.png");
        setEnteredIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\grey.png");
        setPressedIcon("C:\\Users\\罗浩洋\\Desktop\\JAVA\\SocketConnection\\green.png");
        selected = false;
        state = condition.Default;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (state == condition.Enter) {
                    if (pressedIcon != null) setIcon(pressedIcon);
                    state = condition.Press;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (state == condition.Press) {
                    if (enteredIcon != null) setIcon(enteredIcon);
                    state = condition.Enter;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == condition.Default) {
                    if (enteredIcon != null) setIcon(enteredIcon);
                    state = condition.Enter;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state != condition.Default) {
                    if (defaultIcon != null) setIcon(defaultIcon);
                    state = condition.Default;
                }
            }
        });
    }


    public void setDefaultIcon(Image image) {
        image = ImageHandler.imageResize(image, this.getWidth(), this.getHeight());
        defaultIcon = new ImageIcon(image);
        setIcon(defaultIcon);
    }

    public void setDefaultIcon(String path) {
        try {
            Image image = ImageIO.read(new File(path));
            setDefaultIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEnteredIcon(Image image) {
        image = ImageHandler.imageResize(image, this.getWidth(), this.getHeight());
        enteredIcon = new ImageIcon(image);
        setRolloverIcon(enteredIcon);
    }

    public void setEnteredIcon(String path) {
        try {
            Image image = ImageIO.read(new File(path));
            setEnteredIcon(image);
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

    public void setSelectedIcon(Image image) {
        image = ImageHandler.imageResize(image, this.getWidth(), this.getHeight());
        selectedIcon = new ImageIcon(image);
        setSelectedIcon(selectedIcon);
    }

    public void setSelectedIcon(String path) {
        try {
            Image image = ImageIO.read(new File(path));
            setSelectedIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
class ImageHandler {

    public static Image imageResize(Image image, int width, int height) {
        float scale = Math.min((float)width / image.getWidth(null), (float)height / image.getHeight(null));
        int w = (int)(image.getWidth(null) * scale);
        int h = (int)(image.getHeight(null) * scale);
        return image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }

    public static Image getCircularImage(Image source, int diameter) {
        Image resizeImage = ImageHandler.imageResize(source, diameter, diameter);
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g.setClip(circle);
        g.drawImage(resizeImage, 0, 0, null);
        g.dispose();
        return image;
    }

}

