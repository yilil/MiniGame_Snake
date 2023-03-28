package model;

import java.util.Random;

/**
 * @author Yidong Gan
 * @date 7/1/2023 2:10 pm
 * @note
 **/

import static view.GameWindow.UNIT_SIZE;
import static view.GameWindow.WINDOW_SIZE;

public class Fruit extends Item {

    public Fruit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Fruit generateFruit() {
        Random rand = new Random();
        int xFactor = rand.nextInt(WINDOW_SIZE / UNIT_SIZE);
        int yFactor = rand.nextInt(WINDOW_SIZE / UNIT_SIZE);
        return new Fruit(UNIT_SIZE * xFactor, UNIT_SIZE * yFactor);
    }
}
