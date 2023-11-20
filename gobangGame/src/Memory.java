import java.awt.*;

public class Memory implements boardData{
    private int x,y;
    private Color color;
    public Memory(int x,int y,Color color) {
        this.x=x;
        this.y=y;
        this.color=color;
    }
    public void redraw(Graphics2D g){
        if(color==Color.black){
            g.setColor(Color.black);
        }
        else{
            g.setColor(Color.white);
        }
        g.fillOval(x-diameter/2,y-diameter/2,diameter,diameter);
    }
}
