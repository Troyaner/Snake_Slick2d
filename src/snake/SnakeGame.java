package snake;

import org.newdawn.slick.*;

public class SnakeGame extends BasicGame {
    private static final int windowWidth = 600;
    private static final int windowHeight = 600;
    private final int blockSize = 20;
    private Direction direction = Direction.STANDING;
    private int speed = 0;
    private int gameFieldX = 20;
    private int gameFieldY = 20;
    private int gameFieldWidth = windowWidth - gameFieldX * 2;
    private int gameFieldHeight = windowHeight - gameFieldY * 2;

    Snake snake;

    public SnakeGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        snake = new Snake(gameFieldWidth, gameFieldHeight, blockSize);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if (speed < 50) {
            speed++;
        } else {
            speed = 0;
            setHeadPosition();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        snake.draw(graphics);
        graphics.drawRect(gameFieldX, gameFieldY, gameFieldWidth, gameFieldHeight);
    }

    public void setRandomFoodRectangle() {

    }

    public void setHeadPosition() {
        switch(direction) {
            case UP: {
                int newHeadPositionY = snake.head.y - blockSize;
                if (newHeadPositionY < gameFieldY) {
                    newHeadPositionY = gameFieldHeight;
                }
                snake.move(Direction.UP, newHeadPositionY);
                break;
            }
            case DOWN: {
                int newHeadPositionY = snake.head.y + blockSize;
                if (newHeadPositionY >= gameFieldHeight) {
                    newHeadPositionY = 0;
                }
                snake.move(Direction.DOWN, newHeadPositionY);
                break;
            }
            case LEFT: {
                int newHeadPositionX = snake.head.x - blockSize;
                if (newHeadPositionX < gameFieldX) {
                    newHeadPositionX = gameFieldWidth;
                }
                snake.move(Direction.LEFT, newHeadPositionX);
                break;
            }
            case RIGHT: {
                int newHeadPositionX = snake.head.x + blockSize;
                if (newHeadPositionX >= gameFieldWidth) {
                    newHeadPositionX = 0;
                }
                snake.move(Direction.RIGHT, newHeadPositionX);
                break;
            }
            case STANDING: {
                // game hasn't started, don't move the head
                break;
            }
        }
    }

    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_UP: {
                this.direction = Direction.UP;
                break;
            }
            case Input.KEY_DOWN: {
                this.direction = Direction.DOWN;
                break;
            }
            case Input.KEY_LEFT: {
                this.direction = Direction.LEFT;
                break;
            }
            case Input.KEY_RIGHT: {
                this.direction = Direction.RIGHT;
                break;
            }
        }
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new SnakeGame("Snake"));
            container.setDisplayMode(windowWidth, windowHeight,false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}