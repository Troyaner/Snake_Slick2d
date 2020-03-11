package snake;

import org.newdawn.slick.*;

import java.util.Random;

public class SnakeGame extends BasicGame {
    private static final int windowWidth = 600;
    private static final int windowHeight = 600;

    private final int blockSize = 20;
    private int gameFieldX = blockSize;
    private int gameFieldY = blockSize;
    private int gameFieldWidth = windowWidth - gameFieldX * 2;
    private int gameFieldHeight = windowHeight - gameFieldY * 2;

    private Snake snake;
    private Block foodBlock;
    private Direction direction = Direction.STANDING;
    private Direction lastDirection = Direction.STANDING;
    private int speed = 0;

    public SnakeGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        snake = new Snake(gameFieldWidth, gameFieldHeight, blockSize);
        setRandomFoodBlock();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if (speed < 50) {
            speed++;
        } else {
            speed = 0;
            setHeadPosition();
            didHitFoodBlock();
            //didHitSnake();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        snake.draw(graphics);
        graphics.drawRect(gameFieldX, gameFieldY, gameFieldWidth, gameFieldHeight);
        graphics.drawRect(foodBlock.x, foodBlock.y, blockSize, blockSize);
    }

    public void setRandomFoodBlock() {
        Random random = new Random();
        int randomX = random.nextInt(this.gameFieldWidth) + blockSize;
        // round to the nearest 20 (because the blocksize is 20)
        randomX = Math.round(randomX / blockSize) * blockSize;

        int randomY = random.nextInt(this.gameFieldHeight) + blockSize;
        // round to the nearest 10
        randomY = Math.round(randomY / blockSize) * blockSize;

        foodBlock = new Block(randomX, randomY, blockSize);
    }

    public void didHitFoodBlock() {
        if (snake.head.x == foodBlock.x && snake.head.y == foodBlock.y) {
            snake.ate(foodBlock);
            setRandomFoodBlock();
        }
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
                if (newHeadPositionY > gameFieldHeight) {
                    newHeadPositionY = gameFieldY;
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
                if (newHeadPositionX > gameFieldWidth) {
                    newHeadPositionX = gameFieldX;
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
                if (this.direction != Direction.DOWN) {
                    this.direction = Direction.UP;
                }
                break;
            }
            case Input.KEY_DOWN: {
                if (this.direction != Direction.UP) {
                    this.direction = Direction.DOWN;
                }
                break;
            }
            case Input.KEY_LEFT: {
                if (this.direction != Direction.RIGHT) {
                    this.direction = Direction.LEFT;
                }
                break;
            }
            case Input.KEY_RIGHT: {
                if (this.direction != Direction.LEFT) {
                    this.direction = Direction.RIGHT;
                }
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
