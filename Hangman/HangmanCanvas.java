/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.*;

public class HangmanCanvas extends GCanvas
{
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 288;
	private static final int BEAM_LENGTH = 115;
	private static final int ROPE_LENGTH = 14;
	private static final int HEAD_RADIUS = 29;
	private static final int BODY_LENGTH = 115;
	private static final int ARM_OFFSET_FROM_HEAD = 22;
	private static final int UPPER_ARM_LENGTH = 58;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 29;
	private static final int LEG_LENGTH = 86;
	private static final int FOOT_LENGTH = 22;

	private final int HALF_WIDTH = (getWidth() / 2);
	private final int HALF_HEIGTH = (getHeight() / 2);


	/** Resets the display so that only the scaffold appears */
	public void reset()
	{
		this.removeAll();
		drawScaffold();
	}

	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word)
	{
		if (guessed != null) remove(guessed);
		guessed = new GLabel(word);
		guessed.setLocation((getWidth() / 2) - (guessed.getWidth() / 2), (getHeight() / 2) + 100);
		add(guessed);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(String letter, int part) {
		drawIncorrectLetter(letter);
		switch (part) {
			case 1: 
				drawHead();
				break;
			case 2: 
				drawBody();
				break;
			case 3: 
				drawLeftArm();
				break;
			case 4: 
				drawRightArm();
				break;
			case 5: 
				drawLeftLeg();
				break;
			case 6:
				drawRightLeg();
				break;
			case 7: 
				drawLeftFoot();
				break;
			case 8: 
				drawRightFoot();
				break;
			default: throw new ErrorException("noteIncorrectGuess: Illegal index");		
		}
	}

	private void drawScaffold() {
		GCompound scaffold = new GCompound();
		GLine rope = new GLine(BEAM_LENGTH, 0, BEAM_LENGTH, ROPE_LENGTH);
		GLine beam = new GLine(0, 0, BEAM_LENGTH, 0);
		GLine height = new GLine(0, 0, 0, SCAFFOLD_HEIGHT);
		scaffold.add(rope);
		scaffold.add(beam);
		scaffold.add(height);
		scaffold.setLocation((getWidth() / 2) - BEAM_LENGTH, (getHeight() / 2) - SCAFFOLD_HEIGHT);
		add(scaffold);
	}

	private void drawHead() {
		GOval head = new GOval((getWidth() / 2) - HEAD_RADIUS, (getHeight() / 2) - HEAD_RADIUS * 2 - BODY_LENGTH - LEG_LENGTH - ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head);
	}

	private void drawBody() {
		GLine body = new GLine(getWidth() / 2, (getHeight() / 2) - BODY_LENGTH - LEG_LENGTH - ROPE_LENGTH, getWidth() / 2, (getHeight() / 2) - LEG_LENGTH);
		add(body);
	}

	private void drawLeftArm() {
		GCompound leftarm = new GCompound();
		GLine upperarm = new GLine(0, 0, UPPER_ARM_LENGTH, 0);
		GLine lowerarm = new GLine(0, 0, 0, LOWER_ARM_LENGTH);
		leftarm.add(upperarm);
		leftarm.add(lowerarm);
		leftarm.setLocation((getWidth() / 2) - UPPER_ARM_LENGTH, (getHeight() / 2) - LEG_LENGTH - BODY_LENGTH * 0.9);
		add(leftarm);
	}

	private void drawRightArm() {
		GCompound rightarm = new GCompound();
		GLine upperarm = new GLine(0, 0, UPPER_ARM_LENGTH, 0);
		GLine lowerarm = new GLine(UPPER_ARM_LENGTH, 0, UPPER_ARM_LENGTH, LOWER_ARM_LENGTH);
		rightarm.add(upperarm);
		rightarm.add(lowerarm);
		rightarm.setLocation((getWidth() / 2), (getHeight() / 2) - LEG_LENGTH - BODY_LENGTH * 0.9);
		add(rightarm);
	}

	private void drawLeftLeg() {
		GCompound leftleg = new GCompound();
		GLine hip = new GLine(0, 0, HIP_WIDTH, 0);
		GLine leg = new GLine(0, 0, 0, LEG_LENGTH);
		leftleg.add(hip);
		leftleg.add(leg);
		leftleg.setLocation((getWidth() / 2) - HIP_WIDTH, (getHeight() / 2) - LEG_LENGTH);
		add(leftleg);
	}

	private void drawRightLeg() {
		GCompound rightleg = new GCompound();
		GLine hip = new GLine(0, 0, HIP_WIDTH, 0);
		GLine leg = new GLine(HIP_WIDTH, 0, HIP_WIDTH, LEG_LENGTH);
		rightleg.add(hip);
		rightleg.add(leg);
		rightleg.setLocation((getWidth() / 2), (getHeight() / 2) - LEG_LENGTH);
		add(rightleg);	
	}

	private void drawRightFoot() {
		GLine rightfoot = new GLine((getWidth() / 2) - HIP_WIDTH - FOOT_LENGTH, (getHeight() / 2), (getWidth() / 2) - HIP_WIDTH, (getHeight() / 2));
		add(rightfoot);	
	}

	private void drawLeftFoot() {
		GLine leftfoot = new GLine((getWidth() / 2) + HIP_WIDTH, (getHeight() / 2), (getWidth() / 2) + HIP_WIDTH + FOOT_LENGTH, (getHeight() / 2));
		add(leftfoot);
	}	

	private void drawIncorrectLetter(String letter) {
		if (incorrectletter != null) remove(incorrectletter);
			incorrectletter = new GLabel("Tried incorrect letters: " + letter);
			incorrectletter.setLocation((getWidth() / 2) - (incorrectletter.getWidth() / 2), (getHeight() / 2) + 200);
			add(incorrectletter);
	}
	private GLabel guessed;
	private GLabel incorrectletter;
}
