package snakegame.GreedySnake;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    LinkedList<Node> snake = new LinkedList<Node>();
    int headDirection = Config.HEAD_UP;

    //list方法要在方法体内才能调用
    public Snake() {

        snake.add(new Node(5, 5));
        snake.add(new Node(5, 6));
        snake.add(new Node(5, 7));
        snake.add(new Node(5, 8));
        snake.add(new Node(5, 9));
    }

    public void moveUp(boolean isEatApple) {
        if (headDirection == Config.HEAD_DOWN) {
            return;
        } else {
            snake.addFirst(new Node(snake.getFirst().x, snake.getFirst().y - 1));
            headDirection = Config.HEAD_UP;
            if (isEatApple == true) {
            } else {
                snake.removeLast();
            }
        }

    }

    public void moveDown(boolean isEatApple) {
        if (headDirection == Config.HEAD_UP) {
            return;
        } else {
            snake.addFirst(new Node(snake.getFirst().x, snake.getFirst().y + 1));
            headDirection = Config.HEAD_DOWN;
            if (isEatApple == true) {
            } else {
                snake.removeLast();
            }
        }

    }

    public void moveLeft(boolean isEatApple) {
        if (headDirection == Config.HEAD_RIGHT) {
            return;
        } else {
            snake.addFirst(new Node(snake.getFirst().x - 1, snake.getFirst().y));
            headDirection = Config.HEAD_LEFT;
            if (isEatApple == true) {
            } else {
                snake.removeLast();
            }
        }

    }

    public void moveRight(boolean isEatApple) {
        if (headDirection == Config.HEAD_LEFT) {
            return;
        } else {
            snake.addFirst(new Node(snake.getFirst().x + 1, snake.getFirst().y));
            headDirection = Config.HEAD_RIGHT;
            if (isEatApple == true) {
            } else {
                snake.removeLast();
            }
        }
    }

    public boolean isEatApple(Apple apple) {
        if (snake.getFirst().x == apple.getAppleX() &&
                snake.getFirst().y == apple.getAppley()) {
            apple.generateNext();
            return true;
        } else {
            return false;
        }
    }

    public boolean isAgainstWall(){
        //撞到左右的墙
        if (snake.getFirst().x == 0 ||
                snake.getFirst().x == Config.COLUMN ){
            return true;
        }
        //上下墙
        if (snake.getFirst().y == 0 ||
        snake.getFirst().y == Config.LINE){
            return true;
        }
        return false;
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < snake.size(); i++) {
            //蛇头
            if (i == 0) {
                graphics2D.setColor(Color.GREEN);
            } else {     //蛇身
                graphics2D.setColor(Color.black);
            }
            //snake里的node取出来draw
            snake.get(i).draw(graphics2D);
        }
    }
}


