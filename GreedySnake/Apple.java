package snakegame.GreedySnake;

import java.awt.*;
import java.util.Random;

public class Apple {
    Random random = new Random();
    Node apple ;

    public Apple() {
        this.apple = new Node(random.nextInt(Config.COLUMN), random.nextInt(Config.LINE));
    }

    public int getAppleX() {
//        System.out.println(appleX);
        return apple.x;
    }

    public int getAppley() {
//        System.out.println(appley);
        return apple.y;
    }

    public void draw (Graphics2D graphics2D){
        graphics2D.setColor(Color.red);
        apple.draw(graphics2D);

    }

    public void generateNext() {
        apple =  new Node(random.nextInt(Config.COLUMN), random.nextInt(Config.LINE));
    }
}
