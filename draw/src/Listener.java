import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Listener implements ActionListener, MouseListener, MouseMotionListener {
    private Graphics2D gr;
    private Memory[] MemoryArr=new Memory[100000];
    public Memory[] getMemoryArr(){
        return MemoryArr;
    }
    private int number=0;
    private String button;
    private boolean judge=true;
    private int x1,y1,x2,y2,x3,y3,x4,y4,x5,y5;
    public void setGr(Graphics2D g){
        gr =g;
    }
    public Listener(Graphics2D gr){
        this.gr=gr;
    }
    public void actionPerformed(ActionEvent e) {
        button=e.getActionCommand();
        if(button==" ") {
            JButton jbu = (JButton) e.getSource();
            Color color = jbu.getBackground();
            gr.setColor(color);
        }
        else if("清空画布".equals(button)){
            DrawUI.clearCanvas();
        }
        else if(button.equals("粗线条")){
            gr.setStroke(new BasicStroke(4));
        }
        else if(button.equals("细线条")){
            gr.setStroke(new BasicStroke(2));
        }
    }
    public void mouseClicked(MouseEvent e) {
        x3=e.getX();
        y3=e.getY();
        if("三角形".equals(button)) {
            gr.drawLine(x1, y1, x3, y3);
            gr.drawLine(x2, y2, x3, y3);
            judge = true;
            Memory memory=new Memory(x1,y1,x2,y2,x3,y3,button,gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
        }
        else if("多边形".equals(button)){
            gr.drawLine(x3,y3,x2,y2);
            Memory memory=new Memory(x2,y2,x3,y3,button,gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
            if(x2==x3&&y2==y3){
                gr.drawLine(x1,y1,x3,y3);
                Memory memory1=new Memory(x1,y1,x3,y3,button,gr.getColor(),gr.getStroke());
                MemoryArr[number]=memory1;
                number++;
                judge=true;
            }
            x2=x3;
            y2=y3;
        }
    }
    public void mousePressed(MouseEvent e) {
        if(judge) {
            x1 = e.getX();
            y1 = e.getY();
            x5=x1;
            y5=y1;
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(judge){
            x2=e.getX();
            y2=e.getY();
        }
        int w=Math.abs(x1-x2);
        int h=Math.abs(y1-y2);
        if("直线".equals(button)){
            gr.drawLine(x1,y1,x2,y2);
            Memory memory=new Memory(x1,y1,x2,y2,button,gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
        }
        else if("矩形".equals(button)) {
            if(x1>x2&&y1>y2){
                gr.drawRect(x2,y2,w,h);
            }
            else if(x1>x2&&y1<y2){
                gr.drawRect(x2,y2-h,w,h);
            }
            else if(x1<x2&&y1>y2){
                gr.drawRect(x1,y1-h,w,h);
            }
            else {
                gr.drawRect(x1,y1,w,h);
            }
            Memory memory=new Memory(x1,y1,x2,y2,w,h,button,gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
        }
        else if("任意圆".equals(button)){
            if(x1>x2&&y1>y2){
                gr.drawOval(x2,y2,w,h);
            }
            else if(x1>x2&&y1<y2){
                gr.drawOval(x2,y2-h,w,h);
            }
            else if(x1<x2&&y1>y2){
                gr.drawOval(x1,y1-h,w,h);
            }
            else {
                gr.drawOval(x1,y1,w,h);
            }
            Memory memory=new Memory(x1,y1,x2,y2,w,h,button, gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
        }
        else if("三角形".equals(button)) {
            if(judge){
                gr.drawLine(x1,y1,x2,y2);
            }
            judge=false;
        }
        else if("多边形".equals(button)){
            if(judge){
                gr.drawLine(x1,y1,x2,y2);
                Memory memory=new Memory(x1,y1,x2,y2,button, gr.getColor(),gr.getStroke());
                MemoryArr[number]=memory;
                number++;
            }
            judge=false;
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {
        x4=e.getX();
        y4=e.getY();
        if("任意线条".equals(button)){
            gr.drawLine(x5,y5,x4,y4);
            Memory memory=new Memory(x4,y4,x5,y5,button,gr.getColor(),gr.getStroke());
            MemoryArr[number]=memory;
            number++;
            x5=x4;
            y5=y4;
        }
        else if("橡皮擦".equals(button)){
            gr.clearRect(x4-15,y4-15,30,30);
        }
    }
    public void mouseMoved(MouseEvent e) {
    }
}