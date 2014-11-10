/*
 * File: Brick.java
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

public class Brick extends GRect {
	public Brick(int x, int y, int xsize, int ysize) {
		super(x,y,xsize,ysize);
		GRect newbrick = new GRect(x, y, xsize, ysize);
	}
}

