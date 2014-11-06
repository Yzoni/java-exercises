/*
 * File: Rainbow.java
 * Author: Yorick de Boer 10786015
 * ------------------
 * This program is a stub for the Rainbow problem, which displays
 * a rainbow by adding consecutively smaller circles to the canvas.
 */

import acm.program.*;
import acm.graphics.GOval;
import java.awt.Color;

public class Rainbow extends GraphicsProgram
{
	public void run() {
	// Set size of the rainbow, depending on the size of the window
	double xsize = getWidth() * 1.5;
	double ysize = getHeight() * 1.5;

	double ycoor = ysize * 0.1;
 
 	// Set the background to cyan
	setBackground(Color.cyan);
		for (int i = 1; i < 8; i++) {

			// Allign the rainbow to center
			double xcoor = (getWidth() - xsize)/ 2;

			GOval circle = new GOval(xcoor, ycoor, xsize, ysize);
			circle.setFilled(true);
			
			// Set different color for every stripe
			if(i == 1) {
				circle.setFillColor(Color.red);
			} else if (i == 2) {
				circle.setFillColor(Color.orange);
			} else if (i == 3) {
				circle.setFillColor(Color.yellow);
			} else if (i == 4) {
				circle.setFillColor(Color.green);
			} else if (i == 5) {
				circle.setFillColor(Color.blue);
			} else if (i == 6) {
				circle.setFillColor(Color.magenta);
			//Set fill color of the last circle to cyan so it matches the background color
			} else {
				circle.setFillColor(Color.cyan);
			}
			
			add(circle);

			// Change the coordinates and size of the different circles so they appear
			// as stripes
			ycoor = ycoor + 20;

			xsize = xsize - 50;
			ysize = ysize - 50;
		}
	}
}
