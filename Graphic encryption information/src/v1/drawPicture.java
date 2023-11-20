package v1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class drawPicture {

    private Graphics g;

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
                int[][] Data=Array("C:\\Users\\罗浩洋\\Desktop\\JAVA\\Graphic encryption information\\src\\am.png");
                g=jf.getGraphics();
                String message=jtf.getText();
                byte[] messageByte=message.getBytes();
                int[][][] data=switch23(Data);
                BufferedImage BI=new BufferedImage(Data.length,Data[0].length,BufferedImage.TYPE_INT_RGB);
                for(int i=0;i<data.length;i++){
                    for(int j=0;j<data[i].length;j++){
                        int[] bytes=data[i][j];

                        if(i<messageByte.length){
                            bytes[0]=messageByte[i];
                        }
                        int value=(bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3];
                        Color color=new Color(bytes[1],bytes[2],bytes[3]);
                        g.setColor(color);
                        g.fillOval(i+250,j+100,3,3);

                        BI.setRGB(i,j,value);

                    }
                }
                try {
                    ImageIO.write(BI, "png", new File("new.png"));
                    System.out.println("1");
                }catch(Exception ef){
                    ef.printStackTrace();
                }
            }
        });
        JButton button2=new JButton("读取");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[][] Data=Array("new.png");
                int[][][] data=switch23(Data);
                for(int i=0;i<data.length;i++){
                    for(int j=0;j< data[i].length;j++){
                        int[] bytes=data[i][j];
                        if(i<10){
                            System.out.println(bytes[0]);
                        }else {
                            System.exit(0);
                        }
                    }
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
        drawPicture ui=new drawPicture();
        ui.drawUI();
    }
}