import javax.swing.*;
import java.awt.*;
public class Draw {
    public void showUI(){
        JFrame jf=new JFrame();
        jf.setSize(1500,1000);
        jf.setTitle("山脉");
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        Graphics2D g= (Graphics2D) jf.getGraphics();
        MouseListener mouse=new MouseListener();
        mouse.setGr(g);
        jf.addMouseListener(mouse);
}
    public static void main(String[] args){
        Draw lo = new Draw();
        lo.showUI();
    }
}
