/*
 * File: GraphicsHierarchy.java
 * Author: Yorick de Boer 10786015
 * ----------------------------
 * This program is a stub for the GraphicsHierarchy problem, which
 * draws a partial diagram of the acm.graphics hierarchy.
 */

import acm.program.*;
import acm.graphics.*;

public class GraphicsHierarchy extends GraphicsProgram
{
	static final int BOX_WIDTH = 100;
	static final int BOX_HEIGHT = 40;
	
	// Set the margin between the boxes
	static final int BOX_MARGIN = 25;

	public void run()
	{
		//Top box and label
		placeFirstBox();

		// Place four bottom boxesw with labels and put lines between bottomboxes and firstbox
		placeBottomBoxes();
	}

	// Place the top box with label
	private void placeFirstBox() {
		int boxcenter = (getWidth() - BOX_WIDTH) / 2;
		GRect topbox = new GRect(boxcenter, BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		placeLabel("GLabel", boxcenter, BOX_HEIGHT);
		add(topbox);
	}

	// Method which places a label
	private void placeLabel(String labeltext, int xbox, int ybox) {
		GLabel label = new GLabel(labeltext);
		// Adjust placing of label to the dimensions of the label
		double labelwidth = label.getWidth() / 2;
		double labelheight = label.getHeight() / 2;
		double labelx = xCenterLabelInBox(xbox, labelwidth);
		double labely = yCenterLabelInBox(ybox, labelheight);
		label.setLocation(labelx,labely);
		add(label);
	}

	// Method which places 4 bottom boxes with lines between the boxes and the top box
	private void placeBottomBoxes() {
		for (int i=0; i < 4; i++) {
			// Variables for x and y coordinates of the bottom boxes
			int xbottombox = xPlaceBottombox(i);
			int ybottombox = yPlaceBottombox();

			// Create a new box
			GRect bottombox = new GRect(xbottombox, ybottombox, BOX_WIDTH, BOX_HEIGHT);
			add(bottombox);

			// Change the label depending on the order from the left
			// Placing the label exactly in the center of the box
			if (i == 0) {
				placeLabel("GLabel", xbottombox, ybottombox);
			} else if (i == 1) {
				placeLabel("GLine", xbottombox, ybottombox);	
			} else if (i == 2) {
				placeLabel("GOval", xbottombox, ybottombox);
			} else {
				placeLabel("GRect", xbottombox, ybottombox);
			}
		// Place lines between boxes
		placeLines(xbottombox, ybottombox);
		}
	}

	// Draw a line between the top box and the other boxes
	private void placeLines(int xbottombox, int ybottombox) {
		GLine line = new GLine(getWidth() / 2, BOX_HEIGHT * 2, xbottombox + BOX_WIDTH / 2, ybottombox);
		add(line);		
	}

	// Methods which allign the bottom boxes to the center
	private int xPlaceBottombox(int boxnumber) {
		int boxcenter2 = (getWidth() - ((BOX_WIDTH * 4) + (BOX_MARGIN * 3))) / 2;
		int x = boxcenter2 + boxnumber * (BOX_WIDTH + BOX_MARGIN);
		return (x);
	}

	private int yPlaceBottombox() {
		int y = BOX_HEIGHT * 5;
		return (y);
	}

	// Methods which centers the label in a box relative from box placing
	private double xCenterLabelInBox(int xboxplacing, double labelwidth) {
		double xlabel = xboxplacing + ((BOX_WIDTH/ 2) - labelwidth);
		return (xlabel);
	}
	private double yCenterLabelInBox(int yboxplacing, double labelheight) {
		double ylabel = yboxplacing + (BOX_HEIGHT + labelheight ) / 2;
		return (ylabel);
	}
}
