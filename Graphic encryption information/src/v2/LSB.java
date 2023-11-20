package v2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LSB {
    public void drawUI(){
        JFrame jf=new JFrame();
        jf.setSize(1000,1000);
        jf.setTitle("解密图片");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new FlowLayout());
        JLabel label=new JLabel("存入信息：");
        JTextField jtf=new JTextField(30);
        JButton button1=new JButton("导入");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String message = jtf.getText();
                System.out.println(message);

                try {
                    BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\罗浩洋\\Desktop\\JAVA\\Graphic encryption information\\src\\am.png"));
                    int width = originalImage.getWidth();
                    int height = originalImage.getHeight();
                    byte[] messageBytes = message.getBytes("UTF-8");
                    if (messageBytes.length * 8 > width * height * 3) {
                        System.out.println("错误：消息太大，无法嵌入到图像中。");
                        return;
                    }
                    int messageIndex = 0;
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            int pixel = originalImage.getRGB(x, y);
                            int alpha = (pixel >> 24) & 0xFF;
                            int red = (pixel >> 16) & 0xFF;
                            int green = (pixel >> 8) & 0xFF;
                            int blue = pixel & 0xFF;
                            if (messageIndex < messageBytes.length * 8) {
                                int bit = (messageBytes[messageIndex / 8] >> (7 - (messageIndex % 8))) & 0x01;
                                red = (red & 0xFE) | bit;
                                messageIndex++;
                            }
                            if (messageIndex < messageBytes.length * 8) {
                                int bit = (messageBytes[messageIndex / 8] >> (7 - (messageIndex % 8))) & 0x01;
                                green = (green & 0xFE) | bit;
                                messageIndex++;
                            }
                            if (messageIndex < messageBytes.length * 8) {
                                int bit = (messageBytes[messageIndex / 8] >> (7 - (messageIndex % 8))) & 0x01;
                                blue = (blue & 0xFE) | bit;
                                messageIndex++;
                            }
                            int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                            originalImage.setRGB(x, y, newPixel);
                        }
                    }
                    ImageIO.write(originalImage, "png", new File("new.png"));
                    System.out.println("隐写完成。输出图像已保存");
                } catch (Exception ef) {
                    ef.printStackTrace();
                }
            }
        });
        JButton button2=new JButton("读取");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage steganographicImage = ImageIO.read(new File("new.png"));
                    int width = steganographicImage.getWidth();
                    int height = steganographicImage.getHeight();
                    StringBuilder extractedMessage = new StringBuilder();

                    int messageLength = 0;
                    int messageIndex = 0;
                    byte[] messageBytes = new byte[(width * height * 3) / 8];

                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            int pixel = steganographicImage.getRGB(x, y);
                            int alpha = (pixel >> 24) & 0xFF;
                            int red = (pixel >> 16) & 0xFF;
                            int green = (pixel >> 8) & 0xFF;
                            int blue = pixel & 0xFF;

                            if (messageIndex < messageBytes.length * 8) {
                                int bitRed = (red & 0x01);
                                messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | bitRed);
                                messageIndex++;
                            }

                            if (messageIndex < messageBytes.length * 8) {
                                int bitGreen = (green & 0x01);
                                messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | bitGreen);
                                messageIndex++;
                            }

                            if (messageIndex < messageBytes.length * 8) {
                                int bitBlue = (blue & 0x01);
                                messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | bitBlue);
                                messageIndex++;
                            }
                        }
                    }

                    extractedMessage.append(new String(messageBytes, "UTF-8"));
                    System.out.println("提取的信息是： " + extractedMessage.toString());
                } catch (Exception ef) {
                    ef.printStackTrace();
                }
            }
        });
        jf.add(button1);
        jf.add(button2);
        jf.add(label);
        jf.add(jtf);


        jf.setVisible(true);
    }
    public int[][] Array(String imageName) {
        File file = new File(imageName);
        BufferedImage BI = null;
        try {
            BI = ImageIO.read(file);
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        int width = BI.getWidth();
        int height = BI.getHeight();
        int[][] data = new int[width][height];

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                int v=BI.getRGB(i,j);
                data[i][j]=v;
            }
        }
        return data;
    }

    public int[][][] switch23(int[][] data){
        int width= data.length;;
        int height=data[0].length;
        int[][][] destData=new int[width][height][4];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                int v=data[i][j];
                int[] bytes = new int[4];
                bytes[0] = (v >> 24)& 0xFF;
                bytes[1] = (v >> 16)& 0xFF;
                bytes[2] = (v >> 8)& 0xFF;
                bytes[3] =  v& 0xFF;

                destData[i][j]=bytes;
            }
        }
        return destData;

    }
    public static void main(String[] args){
        LSB ui=new LSB();
        ui.drawUI();
    }
}