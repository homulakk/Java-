package snakegame.GreedySnake;

import javax.swing.*;
import java.awt.*;


/**
 * 实现游戏初始化init()
 * 结束gameOver()
 * 和画板paint()方法调用renderUI
 * 三个函数
 */
public class Game extends JPanel{
   public static SnakeGameEngine engine;
    JFrame frame;

    //显示窗口
    public void init(String title, int WINDOW_WIDTH,
                     int WINDOW_HEIGHT, SnakeGameEngine engine){
        this.engine = engine;
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        Game类是JPanel的子类，paint在game类自己创建的面板中重写并覆盖
        所以要用Game类new一个面板的对象
        这样画笔画出来才是自己的画
         */
        Game game = new Game();
        game.addKeyListener(engine.keyListener);
        game.setFocusable(true);
        game.setPreferredSize(new Dimension(WINDOW_WIDTH,
                WINDOW_HEIGHT));

        frame.add(game);
        frame.pack();
        frame.setVisible(true);

        while(true){
            engine.updateLogic();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.repaint();
        }
    }

    public void gameOver(String message,String title){
    JOptionPane.showMessageDialog(frame,message,title,JOptionPane.YES_NO_OPTION);
    System.exit(ABORT);
    }

    @Override
    //画板，要画的内容
    public void paint(Graphics graphics) {
        super.paint(graphics);
        engine.renderUI((Graphics2D) graphics);
    }
}
