/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram
{
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
	}

	/* Play game */
	private void playGame() {
		clearEndMessages();
		for (int i = 0; i < NTURNS; i++) {
			createBall();
			waitForClick();
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) {
    			vx = -vx;
    		}
    		vy = 3.0;
			// Keep moving the ball while the ball is inside de window
			while (ball.getY() < HEIGHT + 10) {
				refreshCounterLabels();
				moveBall();
				bounceWall();
       			collider = getCollidingObject();
				bouncePaddle();
				bounceBrick();
				if (brickcount.getValue() == 0) {
					break;
				}
				pause(SPEED);
			}
			if (brickcount.getValue() == 0) {
				removeAll();
				setWin();
				break;
			}
			ballcount.nextValue();
			refreshCounterLabels();
		}
		if (ballcount.getValue() == 0) {
			removeAll();
			setFail();
		}	
		waitForClick();
		removeAll();
		run();
	}

	/*
	 * SETUP METHODS
	 */

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

	private void setupBrickCounter() {
		brickcount = new Subtractor(NBRICK_ROWS * NBRICKS_PER_ROW);
	}

	private void setupBallCounter() {
		ballcount = new Subtractor(NTURNS);
	}

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

	/* CREATE */

	private void createBall() {
		ball = new GOval((WIDTH - BALL_RADIUS) /2, (HEIGHT - BALL_RADIUS)/ 2, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.white);
		add(ball);
	}


	/* MOVE */

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

	private void setBallCounterLabel(int countballs) {
		ballcounterlabel = new GLabel("Balls left: " +  ballcount.getValue(), 10, 20);
		ballcounterlabel.setColor(Color.white);
		add(ballcounterlabel);
	}

	private void setBrickCounterLabel(int countballs) {
		brickcounterlabel = new GLabel("Bricks left: " + brickcount.getValue(), WIDTH / 2, 20);
		brickcounterlabel.setColor(Color.white);
		add(brickcounterlabel);
	}	

	// Display win message
	private void setWin() {
		win = new GLabel("YOU MADE IT! Click to play again.", WIDTH / 2, HEIGHT / 2);
		win.setColor(Color.white);
		add(win);
	}

	// Display fail message
	private void setFail() {
		fail = new GCompound();
		GImage background = new GImage("images/fail.jpg");
		background.setSize(WIDTH,HEIGHT);
		GLabel failtext = new GLabel("Click to try again", WIDTH / 2, (HEIGHT / 3) * 2);
		failtext.setColor(Color.white);
		fail.add(background);
		fail.add(failtext);
		add(fail);
	}

	private void clearEndMessages(){
		if(fail!=null)
			remove(fail);
		if(win!=null)
			remove(win);
	}

	private void refreshCounterLabels() {
		if (ballcounterlabel != null && brickcounterlabel != null) {
			remove(ballcounterlabel);
			remove(brickcounterlabel);
		}
		setBallCounterLabel(ballcount.getValue());
		setBrickCounterLabel(brickcount.getValue());
	}

	AudioClip bounce = MediaTools.loadAudioClip("sounds/bounce.au");


	private GRect paddle;
	private GOval ball;
	private Brick brick;

	private GObject collider;

	private Subtractor brickcount;
	private Subtractor ballcount;
	private GLabel brickcounterlabel;
	private GLabel ballcounterlabel;
	

	private GLabel win;
	private GCompound fail;

	// Velocity of the ball
	private double vx, vy;

	// Random number generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
 }

