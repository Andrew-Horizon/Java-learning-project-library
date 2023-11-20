import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ML extends MouseAdapter implements ActionListener,boardData {
    private mHashMap<Integer, Integer> scoreMap=new mHashMap<>();
    private Memory[] MemoryArr=new Memory[225];
    public static int number=0,a=0;
    private int aiX=0,aiY=0;
    private int[] n=new int[225];
    private int[] m=new int[225];
    private int[][] Arr=new int[15][15];
    private int[][] score=new int[15][15];

    private int[][] V=new int[15][15];
    public Memory[] getMemoryArr(){
        return MemoryArr;
    }
    private Graphics2D gr;
    private int x,y;
    private String button;
    public static boolean PVP =false;
    private boolean ai=false;
    private boolean reset=false;
    public ML(Graphics2D g) {
        gr=g;
    }
    public int scoreTable(int b,int w) {
        String str = String.format("%02d", b, w);
        int key=Integer.parseInt(str);
        System.out.println(key);
        Integer score = (Integer) scoreMap.get(key);
        if (score != null) {
            return score;
        }
        return 0;
    }

    public boolean machine()
    {
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                score[i][j] = 0;
            }
        }
        int bn = 0;
        int wn = 0;
        int tupleScoreTmp = 0;

        aiX = -1;
        aiY = -1;
        int maxScore = -1;

        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15 - 4; j++)
            {
                int k = j;
                while (k < j + 5)
                {
                    if (Arr[i][k] == 1)
                        bn++;
                    else if (Arr[i][k] == 2)
                        wn++;
                    k++;
                }

                tupleScoreTmp = scoreTable(bn, wn);

                for (k = j; k < j + 5; k++)
                {
                    score[i][k] += tupleScoreTmp;
                }

                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;
            }
        }

        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15 - 4; j++)
            {
                int k = j;
                while (k < j + 5)
                {
                    if (Arr[k][i] == 1)
                        bn++;
                    else if (Arr[k][i] == 2)
                        wn++;

                    k++;
                }

                tupleScoreTmp = scoreTable(bn, wn);

                for (k = j; k < j + 5; k++)
                {
                    score[k][i] += tupleScoreTmp;
                }

                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;

            }
        }

        for (int i = 15 - 1; i >= 4; i--)
        {
            for (int k = i, j = 0; j < 15 && k >= 0; j++, k--)
            {
                int m = k;
                int n = j;

                while (m > k - 5 && k - 5 >= -1)
                {
                    if (Arr[m][n] == 1)
                        bn++;
                    else if (Arr[m][n] == 2)
                        wn++;

                    m--;
                    n++;
                }

                if (m == k - 5)
                {
                    tupleScoreTmp = scoreTable(bn, wn);

                    for (m = k, n = j; m > k - 5; m--, n++)
                    {
                        score[m][n] += tupleScoreTmp;
                    }
                }

                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;
            }
        }
        for (int i = 1; i < 15; i++)
        {
            for (int k = i, j = 15 - 1; j >= 0 && k < 15; j--, k++)
            {
                int m = k;
                int n = j;
                while (m < k + 5 && k + 5 <= 15)
                {
                    if (Arr[n][m] == 1)
                        bn++;
                    else if (Arr[n][m] == 2)
                        wn++;

                    m++;
                    n--;
                }
                if (m == k + 5)
                {
                    tupleScoreTmp = scoreTable(bn, wn);
                    for (m = k, n = j; m < k + 5; m++, n--)
                    {
                        score[n][m] += tupleScoreTmp;
                    }
                }

                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;
            }
        }
        for (int i = 0; i < 15 - 4; i++)
        {
            for (int k = i, j = 0; j < 15 && k < 15; j++, k++)
            {
                int m = k;
                int n = j;

                while (m < k + 5 && k + 5 <= 15)
                {
                    if (Arr[m][n] == 1)
                        bn++;
                    else if (Arr[m][n] == 2)
                        wn++;

                    m++;
                    n++;
                }

                if (m == k + 5)
                {
                    tupleScoreTmp = scoreTable(bn, wn);
                    for (m = k, n = j; m < k + 5; m++, n++)
                    {
                        score[m][n] += tupleScoreTmp;
                    }
                }

                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;
            }
        }

        for (int i = 1; i < 15 - 4; i++)
        {
            for (int k = i, j = 0; j < 15 && k < 15; j++, k++)
            {
                int m = k;
                int n = j;
                while (m < k + 5 && k + 5 <= 15)
                {
                    if (Arr[n][m] == 1)
                        bn++;
                    else if (Arr[n][m] == 2)
                        wn++;

                    m++;
                    n++;
                }

                if (m == k + 5)
                {
                    tupleScoreTmp = scoreTable(bn, wn);

                    for (m = k, n = j; m < k + 5; m++, n++)
                    {
                        score[n][m] += tupleScoreTmp;
                    }
                }
                bn = 0;
                wn = 0;
                tupleScoreTmp = 0;
            }
        }
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                if (Arr[i][j] == 0 &&score[i][j] > maxScore)
                {
                    aiX = i;
                    aiY = j;
                    maxScore = score[i][j];
                }
            }
        }

        if (aiX != -1 && aiY != -1){
            return true;
        }
        return false;
    }

    public void mouseClicked(MouseEvent e) {
        if(PVP) {
            reset=true;
            Color color;
            if (a % 2 == 0) {
                gr.setColor(Color.black);
            } else {
                gr.setColor(Color.white);
            }
            color = gr.getColor();
            x = e.getX();
            y = e.getY();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (X0 + gap * i - diameter/2 < x & x < X0 + gap * i + diameter/2 & Y0 + gap * j - diameter/2 < y & y < Y0 + gap * j + diameter/2 & Arr[i][j] == 0) {
                        gr.fillOval(X0 + gap * i - diameter/2, Y0 + gap * j - diameter/2, diameter, diameter);
                        Memory memory = new Memory(X0 + gap * i, Y0 + gap * j, color);
                        MemoryArr[number] = memory;
                        n[number]=i;
                        m[number]=j;
                        if(color==Color.black) {
                            Arr[i][j] = 1;
                        }else{
                            Arr[i][j] = 2;
                        }
                        number++;
                        a++;
                        if(number%2==0){
                            Game.jl2.setText("请黑子落棋");
                        } else{
                            Game.jl2.setText("请白子落棋");
                        }
                        if(win(i,j,Arr[i][j]).equals("1子获胜")){
                            Game.jl2.setText("黑子获胜");
                            PVP =false;
                            Game.jf.add(Game.jbu2);
                            Game.jf.add(Game.jbu4);
                            Game.jf.add(Game.jbu7);
                            Game.jf.remove(Game.jbu3);
                            Game.jf.revalidate();
                            Game.jf.paint(gr);
                        }else if(win(i,j,Arr[i][j]).equals("2子获胜")){
                            Game.jl2.setText("白子获胜");
                            PVP =false;
                            Game.jf.add(Game.jbu2);
                            Game.jf.add(Game.jbu4);
                            Game.jf.add(Game.jbu7);
                            Game.jf.remove(Game.jbu3);
                            Game.jf.revalidate();
                            Game.jf.paint(gr);
                        }
                    }
                }
            }
        }
        else if(ai) {
            scoreMap.put(00, 7);
            scoreMap.put(01, 35);
            scoreMap.put(02, 800);
            scoreMap.put(03, 15000);
            scoreMap.put(04, 800000);
            scoreMap.put(10, 15);
            scoreMap.put(20, 15);
            scoreMap.put(30, 1800);
            scoreMap.put(40, 100000);
            reset=false;
            Color color;
            boolean aiStep=false;
            gr.setColor(Color.black);
            color = gr.getColor();
            x = e.getX();
            y = e.getY();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (X0 + gap * i - diameter / 2 < x & x < X0 + gap * i + diameter / 2 & Y0 + gap * j - diameter / 2 < y & y < Y0 + gap * j + diameter / 2 & Arr[i][j] == 0) {
                        gr.fillOval(X0 + gap * i - diameter / 2, Y0 + gap * j - diameter / 2, diameter, diameter);
                        Memory memory = new Memory(X0 + gap * i, Y0 + gap * j, color);
                        aiStep=true;
                        MemoryArr[number] = memory;
                        Arr[i][j] = 1;
                        number++;
                    }
                }
            }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if(win(i,j,Arr[i][j]).equals("1子获胜")){
                        Game.jl2.setText("黑子获胜");
                        ai =false;
                        Game.jf.add(Game.jbu2);
                        Game.jf.add(Game.jbu4);
                        Game.jf.add(Game.jbu7);
                        Game.jf.remove(Game.jbu3);
                        Game.jf.revalidate();
                        Game.jf.paint(gr);
                    }else if(win(i,j,Arr[i][j]).equals("2子获胜")){
                        Game.jl2.setText("白子获胜");
                        ai =false;
                        Game.jf.add(Game.jbu2);
                        Game.jf.add(Game.jbu4);
                        Game.jf.add(Game.jbu7);
                        Game.jf.remove(Game.jbu3);
                        Game.jf.revalidate();
                        Game.jf.paint(gr);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException r) {
                r.printStackTrace();
            }
            if(aiStep) {
                gr.setColor(Color.white);
                color = gr.getColor();
                if (machine()) {
                    gr.fillOval(X0 + gap * aiX - diameter / 2, Y0 + gap * aiY - diameter / 2, diameter, diameter);
                    Memory memory = new Memory(X0 + gap * aiX, Y0 + gap * aiY, color);
                    MemoryArr[number] = memory;
                    n[number]=aiX;
                    m[number]=aiY;
                    Arr[aiX][aiY] = 2;
                }
                number++;
            }
        }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if(win(i,j,Arr[i][j]).equals("1子获胜")){
                        Game.jl2.setText("黑子获胜");
                        ai =false;
                        Game.jf.add(Game.jbu2);
                        Game.jf.add(Game.jbu4);
                        Game.jf.add(Game.jbu7);
                        Game.jf.remove(Game.jbu3);
                        Game.jf.revalidate();
                        Game.jf.paint(gr);
                    }else if(win(i,j,Arr[i][j]).equals("2子获胜")){
                        Game.jl2.setText("白子获胜");
                        ai =false;
                        Game.jf.add(Game.jbu2);
                        Game.jf.add(Game.jbu4);
                        Game.jf.add(Game.jbu7);
                        Game.jf.remove(Game.jbu3);
                        Game.jf.revalidate();
                        Game.jf.paint(gr);
                    }
                }
            }
        }

    public String win(int x, int y, int color){
        int b = 1;
        int i = y - 1;
        while (i >= 0 && Arr[x][i] == color) {
            b++;
            i--;
        }
        i = y + 1;
        while (i < 15 && Arr[x][i] == color) {
            b++;
            i++;
        }
        if (b >= 5) {
            return color+"子获胜";
        }
        b = 1;
        int j = x - 1;
        while (j >= 0 && Arr[j][y] == color) {
            b++;
            j--;
        }
        j = x + 1;
        while (j < 15 && Arr[j][y] == color) {
            b++;
            j++;
        }
        if (b >= 5) {
            return color+"子获胜";
        }
        b = 1;
        i = y - 1;
        j = x - 1;
        while (i >= 0 && j >= 0 && Arr[j][i] == color) {
            b++;
            i--;
            j--;
        }
        i = y + 1;
        j = x + 1;
        while (i < 15 && j < 15 && Arr[j][i] == color) {
            b++;
            i++;
            j++;
        }
        if (b >= 5) {
            return color+"子获胜";
        }
        b = 1;
        i = y + 1;
        j = x - 1;
        while (i < 15 && j >= 0 && Arr[j][i] == color) {
            b++;
            i++;
            j--;
        }
        i = y - 1;
        j = x + 1;
        while (i >= 0 && j < 15 && Arr[j][i] == color) {
            b++;
            i--;
            j++;
        }
        if (b >= 5) {
            return color+"子获胜";
        }
        return "";
    }

    public void actionPerformed(ActionEvent e) {
        button=e.getActionCommand();
        if("开始游戏".equals(button)){
            Game.jl1.setText("选择游戏模式");
            Game.jf.remove(Game.jbu1);
            Game.jf.add(Game.jbu5);
            Game.jf.add(Game.jbu6);
            Game.jf.revalidate();
            Game.jf.paint(gr);
        }
        else if("模式选择".equals(button)){
            number=0;
            a=0;
            for(int i=0;i<225;i++){
                MemoryArr[i]=null;
            }
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    Arr[i][j]=0;
                }
            }
            Game.jl1.setText("选择游戏模式");
            Game.jl2.setText(" ");
            Game.jf.remove(Game.jbu1);
            Game.jf.remove(Game.jbu2);
            Game.jf.remove(Game.jbu4);
            Game.jf.remove(Game.jbu7);
            Game.jf.add(Game.jbu5);
            Game.jf.add(Game.jbu6);
            Game.jf.revalidate();
            Game.jf.paint(gr);
        }
        else if("PVP".equals(button)){
            Game.jl1.setText(" ");
            Game.jf.add(Game.jbu3);
            Game.jf.remove(Game.jbu5);
            Game.jf.remove(Game.jbu6);
            Game.jf.revalidate();
            Game.jf.paint(gr);
            PVP =true;
            Game.jl2.setText("请黑子落棋");
        }
        else if("AI".equals(button)){
            Game.jl1.setText(" ");
            Game.jf.add(Game.jbu3);
            Game.jf.remove(Game.jbu5);
            Game.jf.remove(Game.jbu6);
            Game.jf.revalidate();
            Game.jf.paint(gr);
            ai=true;

        }
        else if("重新开始".equals(button)){
            number=0;
            a=0;
            for(int i=0;i<225;i++){
                MemoryArr[i]=null;
            }
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    Arr[i][j]=0;
                }
            }
            Game.jf.remove(Game.jbu2);
            Game.jf.remove(Game.jbu4);
            Game.jf.add(Game.jbu3);
            Game.jf.revalidate();
            Game.jf.paint(gr);
            if(reset){
                PVP =true;
            }else{
                ai=true;
            }
            Game.jl2.setText("请黑子落棋");
        }
        else if("悔棋".equals(button)){
            for(int i=0;i<2;i++) {
                number--;
                a--;
                Arr[n[number]][m[number]] = 0;
                MemoryArr[number] = null;
            }
            Game.jf.revalidate();
            Game.jf.paint(gr);
        }
        else if("复盘".equals(button)){
            Game.jf.remove(Game.jbu2);
            Game.jf.remove(Game.jbu3);
            Game.jf.remove(Game.jbu4);
            Game.jl2.setText(" ");
            Game.jf.revalidate();
            Game.jf.paint(gr);
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
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException r) {
                        r.printStackTrace();
                    }
                    memory.redraw(gr);
                }
            }
            Game.jf.add(Game.jbu2);
            Game.jf.add(Game.jbu4);
            Game.jf.revalidate();
            Game.jf.paint(gr);
        }
    }
}