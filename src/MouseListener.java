import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import static java.lang.Math.abs;
import static java.lang.Math.max;
public class MouseListener implements java.awt.event.MouseListener {
    private Graphics2D gr;
    private double cut = 0.5;
    public void setGr(Graphics2D g){
        gr=g;
    }
    public void draw(int x1, int y1, int x2, int y2, int n) {
        System.out.println(1);
        if(Math.abs(x2 - x1) <= 1 || n == 0) {
            gr.drawLine(x1,y1,x2,y2);
        }else {
            int x=(x2+x1)/2;
            int y=(y1+y2)/2;
            Random rand = new Random();
            int num = rand.nextInt(n*2) - n;
            n = (int)(n*cut);
            draw(x,y+num,x2,y2,n);
            draw(x1,y1,x,y+num,n);
        }
    }
    public void mouseClicked(MouseEvent e){
        int x1 = 10, y1 =200 , x2 = 1490, y2 = 400, m = 250;
        draw(x1,y1,x2,y2,m);
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}