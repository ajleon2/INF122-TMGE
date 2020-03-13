package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {
	
	final private int TILE_LENGTH = 25; // pixels
	final private int NUM_COLS = 12; // # of tiles
	final private int NUM_ROWS = 24; // # of tiles
	final private int MAX_TOP_ITERATIONS = 2; // How many game 'ticks' the Tetris block can be at the top of the 
	                                          // screen before it's game over.
	
	private GameMesh gameMesh;
	private GUI gui;
	private Player player;
	
	private int topIterations; // Track how long the tetris block has been at the top of the screen
	private boolean gameOver; // If True, the game is over
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public Tetris() {
		this.gameMesh = new GameMesh(this.NUM_ROWS, this.NUM_COLS);
		this.gui = new GUI(this.NUM_COLS*TILE_LENGTH, this.NUM_ROWS*TILE_LENGTH);
		this.player = new Player("Player 1");
		this.topIterations = 0;
		this.gameOver = false;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		TetrisBlock currentBlock = TetrisBlockGenerator.createTetrisBlock(TILE_LENGTH, NUM_COLS);
		//TetrisBlock nextBlock = TetrisBlockGenerator.createTetrisBlock(TILE_LENGTH, NUM_COLS);
		
		Controller.addTetrisBlock(currentBlock, gameMesh);
		gui.display(mainStage, gameMesh, this.player, this.gameOver);		
		gui.setOnKeyPress(currentBlock, gameMesh);
		
		Timer fall = new Timer(); // When the Tetris block should fall down
		TimerTask task = new TimerTask() {
			public void run() { // New Java thread
				Platform.runLater(new Runnable() {
					public void run() {
						topIterations = (currentBlock.isAtTopOfScreen()) ? topIterations + 1 : 0;
						
						if (topIterations == MAX_TOP_ITERATIONS) { // Tetris block has been at the top of the screen for too long
							gameOver = true;
							gui.display(mainStage, gameMesh, player, gameOver);	
							
						}
						
						if (topIterations == MAX_TOP_ITERATIONS + 15) {
							System.exit(0);
						}
						
						if (!gameOver) {
							Controller.moveDown(currentBlock, gameMesh);
							gui.display(mainStage, gameMesh, player, gameOver);	
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 300); // 300 = length of a game 'tick'
	}
	
	
	private int getScreenPixelWidth() {
		return NUM_COLS * TILE_LENGTH;
	}
	
	private int getScreenPixelHeight() {
		return NUM_ROWS * TILE_LENGTH;
	}

}
