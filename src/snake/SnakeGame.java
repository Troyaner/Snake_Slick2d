package snake;

import org.newdawn.slick.*;

import java.util.Random;

public class SnakeGame extends BasicGame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private final int SPEED = 50;
    private final int BLOCK_SIZE = 20;

    private GameState state;

    private int gameFieldX = BLOCK_SIZE;
    private int gameFieldY = BLOCK_SIZE;
    private int gameFieldWidth = WINDOW_WIDTH - gameFieldX * 2;
    private int gameFieldHeight = WINDOW_HEIGHT - gameFieldY * 2;

    private Snake snake;
    private Block foodBlock;
    private Direction direction = Direction.STANDING;
    private int updateCounter = 0;

    private String instructionText = "";

    public SnakeGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.state = GameState.START;
        this.instructionText = "Press any arrow Button to start!";
        setInitialGameState();
    }

    private void setInitialGameState() {
        snake = new Snake(gameFieldWidth, gameFieldHeight, BLOCK_SIZE);
        setRandomFoodBlock();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        // the smaller the number is, the faster the game
        if (updateCounter < SPEED) {
            updateCounter++;
        } else {
            updateCounter = 0;
            setHeadPosition();
            didHitTail();
            didHitFoodBlock();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawRect(gameFieldX, gameFieldY, gameFieldWidth, gameFieldHeight);

        switch (this.state) {
            case START:
            case FINISHED: {
                graphics.drawString(this.instructionText, gameFieldWidth / 3, gameFieldHeight / 2);
                break;
            }
            case IN_PROGRESS: {
                snake.draw(graphics);
                graphics.drawRect(foodBlock.x, foodBlock.y, BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
        }
    }

    public void setRandomFoodBlock() {
        Random random = new Random();
        int randomX;
        int randomY;

        // generate new x, y position until we find a free spot
        do {
            randomX = random.nextInt(this.gameFieldWidth) + BLOCK_SIZE;
            randomY = random.nextInt(this.gameFieldHeight) + BLOCK_SIZE;

            // round to the blocksize
            randomX = Math.round(randomX / BLOCK_SIZE) * BLOCK_SIZE;
            randomY = Math.round(randomY / BLOCK_SIZE) * BLOCK_SIZE;
        } while (snake.collidesWithSnake(randomX, randomY));

        foodBlock = new Block(randomX, randomY, BLOCK_SIZE);
    }

    public void didHitFoodBlock() {
        if (snake.head.x == foodBlock.x && snake.head.y == foodBlock.y) {
            snake.ate(foodBlock);
            setRandomFoodBlock();
        }
    }

    public void didHitTail() {
        if (snake.hitTail()) {
            this.instructionText = "Your score is " + snake.getScore() + "!\nPress any arrow button to play again!";
            this.state = GameState.FINISHED;
            this.direction = Direction.STANDING;
            this.setInitialGameState();
        }
    }

    public void setHeadPosition() {
        switch(direction) {
            // calculate the new head position
            // only implemented here because the snake can go out on one side and
            // come back out on the other
            case UP: {
                int newHeadPositionY = snake.head.y - BLOCK_SIZE;
                if (newHeadPositionY < gameFieldY) {
                    newHeadPositionY = gameFieldHeight;
                }
                snake.move(Direction.UP, newHeadPositionY);
                break;
            }
            case DOWN: {
                int newHeadPositionY = snake.head.y + BLOCK_SIZE;
                if (newHeadPositionY > gameFieldHeight) {
                    newHeadPositionY = gameFieldY;
                }
                snake.move(Direction.DOWN, newHeadPositionY);
                break;
            }
            case LEFT: {
                int newHeadPositionX = snake.head.x - BLOCK_SIZE;
                if (newHeadPositionX < gameFieldX) {
                    newHeadPositionX = gameFieldWidth;
                }
                snake.move(Direction.LEFT, newHeadPositionX);
                break;
            }
            case RIGHT: {
                int newHeadPositionX = snake.head.x + BLOCK_SIZE;
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
            // TODO here is a bug that occurs when two buttons are pressed at the same time or in very fast succession
            case Input.KEY_UP: {
                this.state = GameState.IN_PROGRESS;
                // dont allow to go the same direction back
                if (this.direction != Direction.DOWN) {
                    this.direction = Direction.UP;
                }
                break;
            }
            case Input.KEY_DOWN: {
                this.state = GameState.IN_PROGRESS;
                if (this.direction != Direction.UP) {
                    this.direction = Direction.DOWN;
                }
                break;
            }
            case Input.KEY_LEFT: {
                this.state = GameState.IN_PROGRESS;
                if (this.direction != Direction.RIGHT) {
                    this.direction = Direction.LEFT;
                }
                break;
            }
            case Input.KEY_RIGHT: {
                this.state = GameState.IN_PROGRESS;
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
            container.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT,false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
