package snakegame.GreedySnake;

import java.awt.*;

//方格静态属性:大小size
public class Grid {


    public void draw(Graphics2D graphics2D) {
        //把竖线画出来
        //i每加一，x加十，x从零开始
        for (int i = 0; i < (Config.COLUMN); i++) {
            graphics2D.drawLine(Config.GRID_SIZE * i, 0,
                    Config.GRID_SIZE * i, Config.WINDOW_HEIGHT);
        }
        //横线同上
        for (int i = 0; i < (Config.LINE); i++) {
            graphics2D.drawLine(0, Config.GRID_SIZE * i,
                    Config.WINDOW_WIDTH, Config.GRID_SIZE * i);
        }
    }
}
