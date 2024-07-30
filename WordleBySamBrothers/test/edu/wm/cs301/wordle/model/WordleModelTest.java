package edu.wm.cs301.wordle.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import edu.wm.cs.cs301.wordle.model.*;

class WordleModelTest {
	private WordleModel model = new WordleModel();

	
	@Test
	void testGenerateCurrentWordEmptyListException() {
		model.setWordList(new ArrayList<>());
		assertThrows(IllegalArgumentException.class, () -> model.generateCurrentWord(),
				"Excpected IllegalArgumentException to be thrown");
	}
	
	@Test
	void testGenerateCurrentWordInvalidWordList() {
		ArrayList<String> wordList = new ArrayList<String>(); 
		wordList.add(null);
		model.setWordList(wordList);

		assertThrows(NullPointerException.class, () -> model.generateCurrentWord(), 
				"Excpected NullPointerException to be thrown");
	}
	
	@Test
	void testGenerateCurrentWordExpectedList() {
		List<String> wordList = new ArrayList<String>(Arrays.asList("train", "plane", "truck"));
		model.setWordList(wordList);
		model.generateCurrentWord();
		assertTrue(Arrays.asList("train", "plane", "truck").contains(model.getCurrentWord().toLowerCase()));
	}

	@Test
	void testGetCurrentWord() {
		model.setWordList(new ArrayList<String>(Arrays.asList("train")));
		model.generateCurrentWord();
		String currentWord = model.getCurrentWord().toLowerCase();
		assertEquals("train", currentWord);

	}
	
	@Test
	void testSetCurrentColumn() {
		model.setCurrentColumn('X');
		WordleResponse currentResponse = model.getWordleGrid()[0][0];
		assertEquals('X', currentResponse.getChar());
	}
	
	@Test
	void testSetCurrentColumnNumber() {
		model.setCurrentColumn('X');
		assertEquals(0, model.getCurrentColumn());
	}
	
	@Test
	void testBackspaceEmptyRow() {
		model.backspace();
		assertEquals(-1, model.getCurrentColumn());
	}
	
	@Test
	void testBackspaceCurrentChar() {
		model.setCurrentColumn('Y');
		model.setCurrentColumn('X');
		model.backspace();
		WordleResponse firstResponse = model.getWordleGrid()[0][0];
		assertEquals('Y', firstResponse.getChar());
	}
	
	@Test
	void testBackspaceColumn() {
		model.setCurrentColumn('X');
		model.backspace();
		assertEquals(-1, model.getCurrentColumn());
	}
	
	@Test
	void testBackspaceColumnRetainPreviousGuesses() {
		for (int j = 0; j < model.getColumnCount(); j++) {
			model.setCurrentColumn('X');
		}
		model.setCurrentRow();
		model.backspace();
		
		char expectedChar = 'X';
		char actualChar = model.getWordleGrid()[0][3].getChar();
		
		assertEquals(expectedChar, actualChar);
	}
	
	
	@Test
	void testGetCurrentRowFirstGuessException() {
		assertThrows(IndexOutOfBoundsException.class, () -> model.getCurrentRow(), 
				"Expected IndexOutOfBoundsException to be thrown");
	}
	
	@Test
	void testgetCurrentRowLaterGuess() {
		for (int j = 0; j < model.getColumnCount(); j++) {
			model.setCurrentColumn('X');
		}
		model.setCurrentRow();
		
		WordleResponse[] expectedRow = new WordleResponse[model.getColumnCount()];
	
		for (int j = 0; j < model.getColumnCount(); j++) {
			expectedRow[j] = new WordleResponse('X', AppColors.GRAY, AppColors.GRAY);
		}
		
		WordleResponse[] actualRow = model.getCurrentRow();
		
		assertEquals(expectedRow[1].getChar(), actualRow[1].getChar());
	}

	
	@Test
	void testGetCurrentRowNumber() {
		for (int j = 0; j < model.getColumnCount(); j++) {
			model.setCurrentColumn('X');
		}
		model.setCurrentRow();
		
		assertEquals(0, model.getCurrentRowNumber());
	}
	
	@Test
	void testSetCurrentRowEarlyGuess() {
		for (int j = 0; j < model.getColumnCount(); j++) {
			model.setCurrentColumn('X');
		}
		assertEquals(true, model.setCurrentRow());
	}
	
	@Test
	void testSetCurrentRowLastGuess() {
		for (int i = 0; i < model.getMaximumRows()-1; i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				model.setCurrentColumn('X');
			}
			model.setCurrentRow();
		}
		assertEquals(false, model.setCurrentRow());
	}
	
	@Test
	void testGetWordleGridWidth() {
		int columnCount = model.getColumnCount();

		assertEquals(columnCount, model.getWordleGrid()[0].length);
	}
	
	@Test
	void testGetWordleGridHeight() {
		int maximumRows = model.getMaximumRows();

		assertEquals(maximumRows, model.getWordleGrid().length);
	}
	
	@Test
	void testGetMaximumRows() {
		assertEquals(6, model.getMaximumRows());
	}
	
	@Test
	void testGetColumnCount() {
		assertEquals(5, model.getColumnCount());
	}
	
	@Test
	void testGetCurrentColumnEmptyRow() {
		assertEquals(-1, model.getCurrentColumn());
	}
	
	@Test
	void testGetCurrentColumnMiddleOfRow() {
		model.setCurrentColumn('x');
		model.setCurrentColumn('x');
		assertEquals(1, model.getCurrentColumn());
	}
	
	@Test
	void testGetCurrentColumnLastColumn() {
		for (int j = 0; j < model.getColumnCount(); j++) {
			model.setCurrentColumn('X');
		}
		assertEquals(model.getColumnCount()-1, model.getCurrentColumn());
	}
	
	@Test
	void testGetTotalWordCountEmptyWordList() {
		List<String> wordList = new ArrayList<String>();
		model.setWordList(wordList);
		
		int expectedCount = 0;
		int actualCount = model.getTotalWordCount();
		
		assertEquals(expectedCount, actualCount);
	}
	
	@Test
	void testGetTotalWordCountNonEmptyWordList() {
		List<String> wordList = new ArrayList<String>(Arrays.asList("run", "fly", "swim"));
		model.setWordList(wordList);
		
		int expectedCount = 3;
		int actualCount = model.getTotalWordCount();
		
		assertEquals(expectedCount, actualCount);
	}
}
