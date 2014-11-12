/*
 * File: Breakout.java
 * -------------------
 * Name: Y. de Boer
 * 
 * This file is the implements the game Breakout
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 2;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 1;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
		(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Number of turns */
	private static final int SPEED = 10;

	/** Runs the Breakout program. */
	public void run() {
		setupGame();
		playGame();
	}

	/* Create board */
	private void setupGame() {
		setBackground(Color.black);
		setupBrickCounter();
		setupBallCounter();
		setupBricks();
		setupPaddle();
		addMouseListeners();
		setupXVelocity();
		setupYVelocity();
	}

	/* Play game */
	private void playGame() {
		// Incase of restart clear all messages
		clearEndMessages();
		// Loop  for amount of turns
		for (int i = 0; i < NTURNS; i++) {
			createBall();
			waitForClick();
			// Keep the ball moving while the ball is inside de window
			while (ball.getY() < HEIGHT + 10) {
				refreshCounterLabels();
				moveBall();
				bounceWall();
				// Get object name of colliding object
				collider = getCollidingObject();
				bouncePaddle();
				bounceBrick();
				// If all bricks are broken stop moving the ball
				if (brickcount.getValue() == 0) {
					break;
				}
				// Speed of the game
				pause(SPEED);
			}
			// If all bricks are broken, display win message 
			if (brickcount.getValue() == 0) {
				removeAll();
				setWin();
				break;
			}
			// Decrease amount of reserve balls if ball got under paddle
			ballcount.nextValue();
			refreshCounterLabels();
		}
		// If all reserve balls are used, and another ball is need display lose message
		if (ballcount.getValue() == 0) {
			removeAll();
			setFail();
		}	
		waitForClick();
		// Make the board empty
		removeAll();
		run();
	}

	/*
	 * SETUP METHODS
	 */

	private double setupXVelocity() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		return (vx);
	}

	private double setupYVelocity() {
		vy = 3.0;
		return (vy);
	}

	// Draw lines of bricks
	private void setupBricks() {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			int y = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * i;
			//Draw a layer of bricks
			for (int j = 0; j <= NBRICKS_PER_ROW; j++) {
				int x= (j - 1) * (BRICK_WIDTH + BRICK_SEP); 
				brick = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if (i == 0 || i == 1) {
					brick.setColor(Color.red);
				} else if (i == 2 || i == 3) {
					brick.setColor(Color.orange);
				} else if (i == 4 || i == 5) {
					brick.setColor(Color.yellow);
				} else if (i == 6 || i == 7) {
					brick.setColor(Color.green);
				} else {
					brick.setColor(Color.cyan);
				}
				add(brick);
			}
		}
	}

	// Create a brick counter
	private void setupBrickCounter() {
		brickcount = new Subtractor(NBRICK_ROWS * NBRICKS_PER_ROW);
	}

	// Create a ball counter
	private void setupBallCounter() {
		ballcount = new Subtractor(NTURNS);
	}

	// Draw the paddle
	private void setupPaddle() {
		int x = 0;
		int y = HEIGHT - (PADDLE_HEIGHT + PADDLE_Y_OFFSET);
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.white);
		add(paddle);
	}


	/*
	 * PLAY METHODS
	 */

	// Draw a new ball
	private void createBall() {
		ball = new GOval((WIDTH - BALL_RADIUS) /2, (HEIGHT - BALL_RADIUS)/ 2, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.white);
		add(ball);
	}

	// Move paddle on mouse move
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < (WIDTH - PADDLE_WIDTH)) {
			paddle.move(e.getX() - paddle.getX(), 0);
		}
	}

	// Move ball
	private void moveBall() {
		ball.move(vx, vy);
	}


	/* COLISSION */

	// Bounce back from wall
	private void bounceWall() {
		// When ball reaches left or right wall
		if ((ball.getX() - vx <= 0 && vx < 0 )|| (ball.getX() + vx >= (WIDTH - BALL_RADIUS*2) && vx>0)) {
			vx = -vx;
		}
		// When ball reaches the ceiling
		if (ball.getY() - vy <= 0 && vy < 0) {
			vy = -vy;
		}
	}

	// Bounce off the paddle
	private void bouncePaddle() {
		// If paddle bounce back
		if (collider == paddle) {
			vy = -vy;
		}	
	}

	// Bounce back when hitting a brick
	private void bounceBrick() {
		if (collider instanceof Brick) {
			remove(collider);
			bounce.play();
			brickcount.nextValue();
			vy = -vy;
		}
	}

	// Get the object where the object is bouncing from
	private GObject getCollidingObject() {
		if((getElementAt(ball.getX(), ball.getY())) != null) {
			return getElementAt(ball.getX(), ball.getY());
		}
		else if (getElementAt( (ball.getX() + BALL_RADIUS * 2), ball.getY()) != null ){
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
		}
		else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != null ){
			return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		}
		else if(getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null ){
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
		} else {
			return null;
		}
	}


	/* MESSAGES */

	// Draw the ball counter label
	private void setBallCounterLabel(int countballs) {
		ballcounterlabel = new GLabel("Balls left: " +  ballcount.getValue(), 10, 20);
		ballcounterlabel.setColor(Color.white);
		add(ballcounterlabel);
	}

	// Draw the brick counter label
	private void setBrickCounterLabel(int countballs) {
		brickcounterlabel = new GLabel("Bricks left: " + brickcount.getValue(), WIDTH / 2, 20);
		brickcounterlabel.setColor(Color.white);
		add(brickcounterlabel);
	}	

	// Draw win message
	private void setWin() {
		win = new GCompound();
		GImage background = new GImage("images/win.gif");
		background.setSize(WIDTH, HEIGHT);
		GLabel wintext = new GLabel("Click to play again!");
		wintext.setLocation(30, 30);
		wintext.setColor(Color.white);
		wintext.setFont("Arial-bold-20");
		win.add(background);
		win.add(wintext);
		add(win);
	}

	// Draw fail message
	private void setFail() {
		fail = new GCompound();
		GImage background = new GImage("images/fail.gif");
		background.setSize(WIDTH,HEIGHT);
		GLabel failtext = new GLabel("Click to try again...");
		failtext.setLocation(30, 30);
		failtext.setColor(Color.white);
		failtext.setFont("Arial-bold-20");
		fail.add(background);
		fail.add(failtext);
		add(fail);
	}

	// Remove fail or win message
	private void clearEndMessages(){
		if(fail!=null)
			remove(fail);
		if(win!=null)
			remove(win);
	}

	// Update and draw the couter labels
	private void refreshCounterLabels() {
		if (ballcounterlabel != null && brickcounterlabel != null) {
			remove(ballcounterlabel);
			remove(brickcounterlabel);
		}
		setBallCounterLabel(ballcount.getValue());
		setBrickCounterLabel(brickcount.getValue());
	}

	AudioClip bounce = MediaTools.loadAudioClip("sounds/bounce.au");

	// Board objects
	private GRect paddle;
	private GOval ball;
	private Brick brick;
	private GObject collider;

	// Counters
	private Subtractor brickcount;
	private Subtractor ballcount;

	// Score labels
	private GLabel brickcounterlabel;
	private GLabel ballcounterlabel;
	
	// End messages
	private GCompound win;
	private GCompound fail;

	// Velocity of the ball
	private double vx, vy;

	// Random number generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
}

