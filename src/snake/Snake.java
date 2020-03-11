package snake;

import org.newdawn.slick.Graphics;

public class Snake {
    Block head;
    private Block[] tail;
    private int tailCounter;

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
        updateTailBlocks();
        switch(direction) {
            case UP: {
                this.head.y = newPosition;
            }
            case DOWN: {
                this.head.y = newPosition;
                break;
            }
            case LEFT: {
                this.head.x = newPosition;
            }
            case RIGHT: {
                this.head.x = newPosition;
                break;
            }
        }
    }

    private void updateTailBlocks() {
        int tailBlocks = this.tailCounter - 1;
        while (tailBlocks >= 1) {
            tail[tailBlocks].x = tail[tailBlocks - 1].x;
            tail[tailBlocks].y = tail[tailBlocks - 1].y;
            tailBlocks--;
        }
        if (tailBlocks == 0) {
            Block firstTailBlock = tail[tailBlocks];
            if (firstTailBlock != null) {
                firstTailBlock.x = head.x;
                firstTailBlock.y = head.y;
            }
        }
    }

    public void ate(Block foodBlock) {
        this.tail[tailCounter] = foodBlock;
        tailCounter++;
    }
}
