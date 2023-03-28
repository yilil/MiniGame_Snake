package model;

import javafx.scene.input.KeyCode;

import java.util.List;

import static view.GameWindow.UNIT_SIZE;
import static view.GameWindow.WINDOW_SIZE;

/**
 * @author Yidong Gan
 * @date 7/1/2023 10:02 am
 * @note
 **/


public class Snake {
    Node head;
    Node tail;
    public KeyCode nextDirection;
    public KeyCode directionStack;

    // 蛇的出生点在屏幕正中央，面向右边
    public Snake() {
        head = new Node(WINDOW_SIZE / 2, WINDOW_SIZE / 2, null, null);
        head.setxVel(1);
        tail = head.append().append().append();
        this.changeDirection(KeyCode.RIGHT);
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void changeDirection(KeyCode direction) {
        if (direction == KeyCode.LEFT && head.getxVel() <= 0) {
            head.setxVel(-UNIT_SIZE);
            head.setyVel(0);
        } else if (direction == KeyCode.RIGHT && head.getxVel() >= 0) {
            head.setxVel(UNIT_SIZE);
            head.setyVel(0);
        } else if (direction == KeyCode.UP && head.getyVel() <= 0) {
            head.setxVel(0);
            head.setyVel(-UNIT_SIZE);
        } else if (direction == KeyCode.DOWN && head.getyVel() >= 0) {
            head.setxVel(0);
            head.setyVel(UNIT_SIZE);
        }
    }

    public int tick(List<Fruit> fruits) {
        if (isDead()) {
            System.exit(0);
        }

        if (nextDirection != null) {
            this.changeDirection(nextDirection);
            nextDirection = null;
        } else if (directionStack != null) {
            this.changeDirection(directionStack);
            directionStack = null;
        }


        Fruit eatenFruit = null;
        for (Fruit fruit : fruits) {
            if (fruit.getX() == head.getX() && fruit.getY() == head.getY()) {
                tail = tail.append();
                eatenFruit = fruit;
                break;
            }
        }

        if (eatenFruit != null) {
            fruits.remove(eatenFruit);
        }

        move();

        return eatenFruit == null ? 0 : 1;
    }

    public void move() {
        Node node = tail;
        while(node != head){
            Node preNode = node.getPrev();
            node.setX(preNode.getX());
            node.setY(preNode.getY());
            node.setxVel(preNode.getxVel());
            node.setyVel(preNode.getyVel());

            node = preNode;
        }

        head.setX(head.getX() + head.getxVel());
        head.setY(head.getY() + head.getyVel());
    }

    public boolean isDead() {
        // 判断是否撞到墙壁
        if (head.getX() < 0 || (head.getX() + UNIT_SIZE) > WINDOW_SIZE || head.getY() < 0 || (head.getY() + UNIT_SIZE) > WINDOW_SIZE) {
            return true;
        }

        // 判断是否撞到自身
        Node node = head.getNext();
        while (node != null) {
            if (node.getX() == head.getX() && node.getY() == head.getY()) {
                return true;
            }
            node = node.getNext();
        }

        return false;
    }
}