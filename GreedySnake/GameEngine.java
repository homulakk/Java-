package snakegame.GreedySnake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 模板类，有两个未实现的抽象方法
 * updateLogic
 * renderUI
 */
public abstract class GameEngine {
    KeyListener keyListener;
    int keyCode;

    public GameEngine() {
        this.keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyCode = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

    }
    public int getKeyCode(){
        return keyCode;
    }


    //这个方法要根据游戏逻辑，灵活更新
    public abstract void updateLogic();

    //界面生成，更新的方法

    public abstract void renderUI(Graphics2D graphics2D);
}
