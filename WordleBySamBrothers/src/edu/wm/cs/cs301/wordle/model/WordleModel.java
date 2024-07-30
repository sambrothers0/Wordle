package edu.wm.cs.cs301.wordle.model;

import java.awt.Color;
import java.util.*;


import edu.wm.cs.cs301.wordle.controller.ReadWordsRunnable;

public class WordleModel {
	
	private char[] currentWord, guess, previousGuess;
	
	private int columnCount, maximumRows;
	private int currentColumn, currentRow;
	
	private String mode;
	
	private List<String> wordList;
	
	private final Random random;
	
	private final Statistics statistics;
	
	private WordleResponse[][] wordleGrid;
	
	public WordleModel() {
		this.currentColumn = -1;
		this.currentRow = 0;
		this.columnCount = 5;
		this.maximumRows = 6;
		this.mode = "normal";
		this.random = new Random();
		
		createWordList();
		
		this.wordleGrid = initializeWordleGrid();
		this.guess = new char[columnCount];
		this.statistics = new Statistics();
	}
	
	private void createWordList() {
		ReadWordsRunnable runnable = new ReadWordsRunnable(this);
		Thread wordListThread = new Thread(runnable);
		wordListThread.start();
		
		try {
			wordListThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public void initialize() {
		this.wordleGrid = initializeWordleGrid();
		this.currentColumn = -1;
		this.currentRow = 0;
		generateCurrentWord();
		this.guess = new char[columnCount];
	}

	public void generateCurrentWord() {
		String word = wordList.get(getRandomIndex());
		this.currentWord = word.toUpperCase().toCharArray();
		
		System.out.println("DEBUG: Current word set to " + word);
	}

	public String getCurrentWord() {
		return String.valueOf(this.currentWord);
	}

	private int getRandomIndex() {
		int size = wordList.size();
		return random.nextInt(size);
	}
	
	private WordleResponse[][] initializeWordleGrid() {
		WordleResponse[][] wordleGrid = new WordleResponse[maximumRows][columnCount];

		for (int row = 0; row < wordleGrid.length; row++) {
			for (int column = 0; column < wordleGrid[row].length; column++) {
				wordleGrid[row][column] = null;
			}
		}

		return wordleGrid;
	}
	
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	
	public void setCurrentColumn(char c) {
		currentColumn++;
		currentColumn = Math.min(currentColumn, (columnCount - 1));
		guess[currentColumn] = c;
		wordleGrid[currentRow][currentColumn] = new WordleResponse(c,
				Color.WHITE, Color.BLACK);
	}
	
	public void backspace() {
		if (this.currentColumn > -1) { //only backspace if there's room
			wordleGrid[currentRow][currentColumn] = null;
			guess[currentColumn] = ' ';
			this.currentColumn--;
			this.currentColumn = Math.max(currentColumn, -1);
		}
	}
	
	public WordleResponse[] getCurrentRow() {
		return wordleGrid[getCurrentRowNumber()];
	}
	
	public int getCurrentRowNumber() {
		return currentRow - 1;
	}
	
	public boolean setCurrentRow() {		
		for (int column = 0; column < guess.length; column++) {
			Color backgroundColor = AppColors.GRAY;
			Color foregroundColor = Color.WHITE;
			if (guess[column] == currentWord[column]) {
				backgroundColor = AppColors.GREEN;
			} else if (contains(currentWord, guess, column)) {
				backgroundColor = AppColors.YELLOW;
			}
			
			wordleGrid[currentRow][column] = new WordleResponse(guess[column],
					backgroundColor, foregroundColor);
		}
		
		currentColumn = -1;
		currentRow++;
		guess = new char[columnCount];
		
		return currentRow < maximumRows;
	}
	
	private boolean contains(char[] currentWord, char[] guess, int column) {
		for (int index = 0; index < currentWord.length; index++) {
			if (index != column && guess[column] == currentWord[index]) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isHintAvailable() {
		if (getCurrentRowNumber() == -1) {
			return false;
		}
		previousGuess = new char [currentWord.length];
		for (int i = 0; i < currentWord.length; i++) {
			previousGuess[i] = wordleGrid[getCurrentRowNumber()][i].getChar();
		}
		for (int i = 0; i < currentWord.length; i++)
			if (previousGuess[i] != currentWord[i] && !contains(currentWord, previousGuess, i)) {
				return true;
			}
		return false;
	}
	
	public int getHint() {
		ArrayList<Integer> grayLetters = new ArrayList<Integer>();
		previousGuess = new char [currentWord.length];
		for (int i = 0; i < currentWord.length; i++) {
			previousGuess[i] = wordleGrid[getCurrentRowNumber()][i].getChar();
		}
		for (int i = 0; i < currentWord.length; i++)
			if (previousGuess[i] != currentWord[i] && !contains(currentWord, previousGuess, i)) {
				grayLetters.add(i);
			}
		Collections.shuffle(grayLetters);
		return grayLetters.get(0);
	}
	
	public void updateHint() {
		int index = getHint();
		char c = currentWord[index];
		wordleGrid[getCurrentRowNumber()][index] = new WordleResponse(c,
				AppColors.GREEN, Color.WHITE);
	}
	
	public void difficultyKids() {
		columnCount = 3;
		maximumRows = 4;
		mode = "kids";
		initialize();
		createWordList();

		
	}
	
	public void difficultyNormal() {
		columnCount = 5;
		maximumRows = 6;
		mode = "normal";
		initialize();
		createWordList();


	}
	
	public void difficultyHard() {
		columnCount = 7;
		maximumRows = 8;
		mode = "hard";
		initialize();
		createWordList();


	}

	public WordleResponse[][] getWordleGrid() {
		return wordleGrid;
	}
	
	public String getMode() {
		return mode;
	}
	
	public int getMaximumRows() {
		return maximumRows;
	}

	public int getColumnCount() {
		return columnCount;
	}
	
	public int getCurrentColumn() {
		return currentColumn;
	}

	public int getTotalWordCount() {
		return wordList.size();
	}

	public Statistics getStatistics() {
		return statistics;
	}

}
