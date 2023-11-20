import javax.swing.*;
import java.awt.*;
public class DrawUI {
    public static MyFrame jf = new MyFrame();
    public static Graphics G=jf.getGraphics();
    private Graphics2D g;
    public void showUI() {
        jf.setSize(1000, 900);
        jf.setTitle("画板");
        jf.setLayout(new FlowLayout());
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] text = {"直线", "矩形", "三角形", "多边形","任意圆","任意线条","橡皮擦","清空画布"};
        Listener mouse=new Listener(g);
        for (int i = 0; i < text.length; i++) {
            JButton jbu = new JButton(text[i]);
            jf.add(jbu);
            jbu.addActionListener(mouse);
        }
        JButton blue = new JButton(" ");
        blue.setBackground(Color.blue);
        blue.setPreferredSize(new Dimension(30, 30));
        jf.add(blue);
        JButton black = new JButton(" ");
        black.setBackground(Color.black);
        black.setPreferredSize(new Dimension(30, 30));
        jf.add(black);
        JButton lineSize2 = new JButton("细线条");
        jf.add(lineSize2);
        JButton lineSize1 = new JButton("粗线条");
        jf.add(lineSize1);
        jf.setVisible(true);
        blue.addActionListener(mouse);
        black.addActionListener(mouse);
        lineSize1.addActionListener(mouse);
        lineSize2.addActionListener(mouse);
        g = (Graphics2D) jf.getGraphics();
        mouse.setGr(g);
        jf.addMouseListener(mouse);
        jf.addMouseMotionListener(mouse);
        jf.setMemoryArr(mouse.getMemoryArr());
    }
    public static void clearCanvas() {
        G = jf.getGraphics();
        G.clearRect(0, 62, jf.getWidth(), jf.getHeight());
    }
    public static void main(String[] args) {
        DrawUI ui = new DrawUI();
        ui.showUI();
    }
}
