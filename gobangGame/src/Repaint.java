import javax.swing.*;
import java.awt.*;

public class Repaint extends JFrame implements boardData{
    private Memory[] MemoryArr;
    public void setMemoryArr(Memory[] MemoryArr){
        this.MemoryArr=MemoryArr;
    }
    public void paint(Graphics g) {
        super.paint(g);
        if(ML.PVP==true){
            if(ML.number%2==0){
            Game.jl2.setText("请黑子落棋");
        } else{
            Game.jl2.setText("请白子落棋");
        }
        }
        Graphics2D gr=(Graphics2D)g;
        Color chessBoard=Color.getHSBColor(37.0f/360f,0.5f,0.5f);
        gr.setColor(chessBoard);
        gr.fillRect(300,35,600,600);
        gr.setColor(Color.black);
        gr.setStroke(new BasicStroke(5));
        gr.drawRect(300,35,600,600);
        gr.setStroke(new BasicStroke(1));
        for(int i=0;i<=14;i++){
            gr.drawLine(X0,Y0+gap*i,X1,Y0+gap*i);
            gr.drawLine(X0+gap*i,Y0,X0+gap*i,Y1);
        }
        for(int i=0;i<MemoryArr.length;i++){
            Memory memory = MemoryArr[i];
            if (memory != null) {
                memory.redraw(gr);
            }
        }
    }
}
