package v4;

import v3.ImageMasker;
import v3.StringEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class drawPicture {

    private Graphics g;
    public void drawUI(){
        JFrame jf=new JFrame();
        jf.setSize(1000,1000);
        jf.setTitle("解密图片");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new FlowLayout());
        JButton button1=new JButton("写入");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ImageMasker imageMasker = new ImageMasker();
        String s="";
        try {
            System.out.print("请输入文件路径：");
            s = bufferedReader.readLine();
            imageMasker.read(s);
        }catch (IOException e) {
            e.printStackTrace();
        }
        String finalS = s;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[][] Data=Array(finalS);
                g=jf.getGraphics();
                int[][][] data=switch23(Data);
                for(int i=0;i<data.length;i++){
                    for(int j=0;j<data[i].length;j++){
                        int[] bytes=data[i][j];
                        Color color=new Color(bytes[1],bytes[2],bytes[3]);
                        g.setColor(color);
                        g.fillOval(i+250,j+100,3,3);
                    }
                }
                try {
                    System.out.print("输入数据(<=" + imageMasker.content.length / 8 + "):");
                    String a = bufferedReader.readLine();
                    boolean[] value = StringEncoder.encodeString(a);
                    for (int i = 0; i < value.length; i++)
                        imageMasker.content[i] = value[i];
                    for (int i = value.length; i < imageMasker.content.length; i++) {
                        imageMasker.content[i] = false;
                    }
                    System.out.print("输出文件路径:");
                    a = bufferedReader.readLine();
                    imageMasker.write(a);
                }catch (Exception ef){
                    ef.printStackTrace();
                }
            }
        });
        JButton button2=new JButton("读取");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.print(StringEncoder.decodeString(imageMasker.content).trim());
            }
        });
        jf.add(button1);
        jf.add(button2);
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
        drawPicture ui=new drawPicture();
        ui.drawUI();
    }
}