import javax.swing.*;
import java.awt.*;
public class MyFrame extends JFrame {
    private Memory[] MemoryArr;
    public void setMemoryArr(Memory[] MemoryArr){
        this.MemoryArr=MemoryArr;
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        for(int i=0;i<MemoryArr.length;i++){
            Memory memory = MemoryArr[i];
            if (memory != null) {
                memory.redraw(gr);
            }
        }
    }
}