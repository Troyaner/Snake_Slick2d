package snake;

import org.newdawn.slick.Graphics;

public class Block {
    int x;
    int y;
    private int blockSize;

    public Block (int x, int y, int blockSize) {
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
    }

    public void draw(Graphics graphics) {
        graphics.drawRect(x, y, blockSize, blockSize);
    }
}
