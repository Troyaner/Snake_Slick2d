package snake;

import org.newdawn.slick.Graphics;

public class Snake {
    Block head;
    private Block[] tail;

    public Snake(int gameFieldWidth, int gameFieldHeight, int blockSize) {
        head = new Block(gameFieldWidth / 2, gameFieldHeight / 2, blockSize);
        tail = new Block[gameFieldWidth * gameFieldHeight / blockSize];
    }

    public void draw(Graphics graphics) {
        head.draw(graphics);
        for (Block block : tail) {
            if (block != null) {
                block.draw(graphics);
            }
        }
    }

    public void move(Direction direction, int newPosition) {
        switch(direction) {
            case UP: {
                // update tail
                // update head
                this.head.y = newPosition;
                break;
            }
            case DOWN: {
                // update tail
                // update head
                this.head.y = newPosition;
                break;
            }
            case LEFT: {
                // update tail
                // update head
                this.head.x = newPosition;
                break;
            }
            case RIGHT: {
                // update tail
                // update head
                this.head.x = newPosition;
                break;
            }
        }
    }
}
