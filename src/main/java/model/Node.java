package model;

import static view.GameWindow.UNIT_SIZE;

/**
 * @author Yidong Gan
 * @date 7/1/2023 10:02 am
 * @note
 **/

public class Node {
    private int x;
    private int y;
    private int xVel = 0;
    private int yVel = 0;
    private Node next;
    private Node prev;

    public Node(int x, int y, Node next, Node prev) {
        this.x = x;
        this.y = y;
        this.next = next;
        this.prev = prev;
    }

    // 根据当前节点的移动方向，尾部添加新节点
    public Node append() {
        int x = this.x;
        int y = this.y;

        if (this.xVel < 0) {
            x += UNIT_SIZE;
        } else if (this.xVel > 0) {
            x -= UNIT_SIZE;
        }

        if (this.yVel < 0) {
            y += UNIT_SIZE;
        } else if (this.yVel > 0) {
            y -= UNIT_SIZE;
        }

        Node newTail = new Node(x, y, null, this);
        newTail.setxVel(this.xVel);
        newTail.setyVel(this.yVel);

        this.next = newTail;
        return newTail;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxVel() {
        return xVel;
    }

    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    public int getyVel() {
        return yVel;
    }

    public void setyVel(int yVel) {
        this.yVel = yVel;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
