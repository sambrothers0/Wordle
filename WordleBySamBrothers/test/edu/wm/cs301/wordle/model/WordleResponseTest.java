package edu.wm.cs301.wordle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wm.cs.cs301.wordle.model.*;

import java.awt.*;

class WordleResponseTest {
	WordleResponse w;
	
	@BeforeEach
	void setup() {
		w = new WordleResponse('a', Color.GREEN, Color.WHITE);
	}
	
	@Test
	void testGetChar() {	
		w = new WordleResponse('a', Color.GREEN, Color.WHITE);

		char expectedChar = w.getChar();
		char actualChar = 'a';
		
		assertEquals(expectedChar, actualChar);
	}
	
	@Test
	void testGetBackgroundColor() {		
		Color expectedColor = w.getBackgroundColor();
		Color actualColor = Color.GREEN;
		
		assertEquals(expectedColor, actualColor);
	}
	
	@Test
	void testGetForegroundColor() {		
		Color expectedColor = w.getForegroundColor();
		Color actualColor = Color.WHITE;
		
		assertEquals(expectedColor, actualColor);
	}
	
}
