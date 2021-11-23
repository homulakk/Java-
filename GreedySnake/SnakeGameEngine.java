package snakegame.GreedySnake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeGameEngine extends GameEngine {
    Grid grid = new Grid();
    Snake snake = new Snake();
    Apple apple = new Apple();
    Game game = new Game();

    @Override
    public void updateLogic()
    {
        //把键盘事件对应的值传过来
        //键盘事件的逻辑在GameEngine中实现
        int keyCode = getKeyCode();
        //判断逻辑统一写在updateLogic中
       boolean isEatApple = snake.isEatApple(apple);
        //如果碰墙，gameOver
        if (snake.isAgainstWall()){
            game.gameOver("please press space to restart","You Lose");
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            snake.moveRight(isEatApple);
//            System.out.println("r");
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            snake.moveLeft(isEatApple);
//            System.out.println("l");
        }
        if (keyCode == KeyEvent.VK_UP) {
            snake.moveUp(isEatApple);
//            System.out.println("u");
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            snake.moveDown(isEatApple);
//            System.out.println("d");
        }
    }

    @Override
    //用画笔调用renderUI函数，相当于画笔
    public void renderUI(Graphics2D graphics2D) {
//       System.out.println("renderUI");
        if (Config.GRID_SWITCH) {
            grid.draw(graphics2D);
        }
        snake.draw(graphics2D);
        apple.draw(graphics2D);

    }
}

