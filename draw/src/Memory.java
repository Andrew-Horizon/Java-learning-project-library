import java.awt.*;
public class Memory {
    private Graphics2D g;
    public int x1,y1,x2,y2,x3,y3;
    public int[] X1=new int[10000];
    public int[] Y1=new int[10000];
    public int[] X2=new int[10000];
    public int[] Y2=new int[10000];
    public int number1=0;
    public int number2=0;
    public String name;
    public Color color;
    public Stroke stroke;
    public Memory(int x1, int y1, int x2, int y2, String name,Color color,Stroke stroke) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.name = name;
        this.color=color;
        this.stroke=stroke;
    }
    public Memory(int x1,int y1,int x2,int y2,int x3,int y3,String name,Color color,Stroke stroke){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.x3=x3;
        this.y3=y3;
        this.name=name;
        this.color=color;
        this.stroke=stroke;
    }
    public void redraw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(this.stroke);
        switch (name) {
            case "直线":
                g.drawLine(x1, y1, x2, y2);
                break;
            case"矩形":
                if(x1>x2&&y1>y2){
                    g.drawRect(x1,y1,x3,y3);
                }
                else if(x1>x2&&y1<y2){
                    g.drawRect(x2,y2-y3,x3,y3);
                }
                else if(x1<x2&&y1>y2){
                    g.drawRect(x1,y1-y3,x3,y3);
                }
                else {
                    g.drawRect(x1,y1,x3,y3);
                }
                break;
            case"任意圆":
                if(x1>x2&&y1>y2){
                    g.drawOval(x2,y2,x3,y3);
                }
                else if(x1>x2&&y1<y2){
                    g.drawOval(x2,y2-y3,x3,y3);
                }
                else if(x1<x2&&y1>y2){
                    g.drawOval(x1,y1-y3,x3,y3);
                }
                else {
                    g.drawOval(x1,y1,x3,y3);
                }
                break;
            case"三角形":
                g.drawLine(x1,y1,x2,y2);
                g.drawLine(x1,y1,x3,y3);
                g.drawLine(x2,y2,x3,y3);
                break;
            case "多边形":
                g.drawLine(x1,y1,x2,y2);
                break;
            case "任意线条":
                g.drawLine(x1,y1,x2,y2);
                break;
            }
    }
}