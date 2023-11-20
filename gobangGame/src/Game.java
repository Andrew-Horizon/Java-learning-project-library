import javax.swing.*;
import java.awt.*;

public class Game {
    public static Repaint jf=new Repaint();
    public static JLabel jl1=new JLabel("请点击开始游戏");
    public static JLabel jl2=new JLabel("  ");
    public static JButton jbu1=new JButton("开始游戏");
    public static JButton jbu2=new JButton("重新开始");
    public static JButton jbu3=new JButton("悔棋");
    public static JButton jbu4=new JButton("复盘");
    public static JButton jbu5=new JButton("PVP");
    public static JButton jbu6=new JButton("AI");
    public static JButton jbu7=new JButton("模式选择");
    public void showUI(){
        jf.setTitle("五子棋游戏");
        jf.setSize(1200,900);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jl1.setBounds(50,300,240,35);
        jl1.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jf.add(jl1);
        jl2.setBounds(950,300,300,35);
        jl2.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jf.add(jl2);
        jbu1.setBounds(500,700,200,35);
        jbu1.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jf.add(jbu1);
        jbu2.setBounds(500,700,200,35);
        jbu2.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbu3.setBounds(200,700,100,35);
        jbu3.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbu4.setBounds(900,700,100,35);
        jbu4.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbu5.setBounds(450,700,100,35);
        jbu5.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbu6.setBounds(650,700,100,35);
        jbu6.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbu7.setBounds(100,700,200,35);
        jbu7.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jf.setVisible(true);
        Graphics2D g= (Graphics2D) jf.getGraphics();
        ML mouse=new ML(g);
        jbu1.addActionListener(mouse);
        jbu2.addActionListener(mouse);
        jbu3.addActionListener(mouse);
        jbu4.addActionListener(mouse);
        jbu5.addActionListener(mouse);
        jbu6.addActionListener(mouse);
        jbu7.addActionListener(mouse);
        jf.addMouseListener(mouse);
        jf.setMemoryArr(mouse.getMemoryArr());
    }
    public static void main(String[] args) {
        Game game=new Game();
        game.showUI();
    }
}
