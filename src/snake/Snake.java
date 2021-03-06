package snake;

import org.newdawn.slick.Graphics;

public class Snake {
    Block head;
    private Block[] tail;
    private int tailCounter;

    public Snake(int gameFieldWidth, int gameFieldHeight, int blockSize) {
        // initial position
        head = new Block(gameFieldWidth / 2, gameFieldHeight / 2, blockSize);

        // calculation of the maximum number of tail blocks
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

        // update the position of each tail block to the previous tail block, starting with the very last block
        while (tailBlocks >= 1) {
            tail[tailBlocks].x = tail[tailBlocks - 1].x;
            tail[tailBlocks].y = tail[tailBlocks - 1].y;
            tailBlocks--;
        }

        // the last block (the first block after the head) gets the head position
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

    public boolean hitTail() {
        for (Block tailBlock : this.tail) {
            if (tailBlock != null) {
                if (tailBlock.x == head.x && tailBlock.y == head.y) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getScore() {
        return this.tailCounter;
    }

    public boolean collidesWithSnake(int x, int y) {
        if (head.x == x && head.y == y) {
            return true;
        }
        for (Block tailBlock : this.tail) {
            if (tailBlock != null) {
                if (tailBlock.x == x && tailBlock.y == y) {
                    return true;
                }
            }
        }
        return false;
    }
}
