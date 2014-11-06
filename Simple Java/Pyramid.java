/*
 * File: Pyramid.java
 * Author: Yorick de Boer 10786015
 * ------------------
 * This program draws a brick pyramid centred at the bottom of the window.
 */

import acm.program.*;
import acm.graphics.*;

public class Pyramid extends GraphicsProgram
{
	private static final int BRICK_WIDTH = 30;
	private static final int BRICK_HEIGHT = 12;
	private static final int BRICKS_IN_BASE = 12;
	
	public void run() {
		// Make the pyramid end on the bottom of te screen
		int towerheight = getHeight() - (BRICKS_IN_BASE * BRICK_HEIGHT);
		
		// Draw layers of bricks starting with the top brick
		for (int i = 0; i < BRICKS_IN_BASE; i++) {
			int y = (towerheight + i * BRICK_HEIGHT);
			int towerwidth = (getWidth() - (i + 1) * BRICK_WIDTH) / 2;
			
			// Draw a layer of bricks for every i
			for (int j = 0; j <= i; j++) {
            	int x = towerwidth + j * BRICK_WIDTH;
           		GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
           		add(brick);
			}
		}
	}
}
