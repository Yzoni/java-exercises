/*
 * File: Brick.java
 * -------------------
 * Name: Y. de Boer
 * 
 * Class to create a new brick object. Needed so instanceof can be used on 
 * a Brick object in Breakout.java.
 */

import acm.graphics.*;

public class Brick extends GRect {
	public Brick(int x, int y, int xsize, int ysize) {
		super(x,y,xsize,ysize);
		GRect newbrick = new GRect(x, y, xsize, ysize);
	}
}

