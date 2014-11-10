/*
 * File: Powerup.java
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

public class Powerup extends GRect {
	public Powerup(double x, double y, int xsize, int ysize) {
		super(x,y,xsize,ysize);
		GRect powerup = new GRect(x, y, xsize, ysize);
	}
}