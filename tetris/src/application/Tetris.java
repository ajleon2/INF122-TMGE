package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * The entry point for this Tetris application.
 * @author Andrew Leon
 */
public class Tetris extends Application {
	/**
	 * The length of a tile (in pixels).
	 */
	final private int TILE_LENGTH = 25;
	/**
	 * The number of columns in the game mesh (in # of tiles).
	 */
	final private int NUM_COLS = 12;
	/**
	 * The number of rows in the game mesh (in # of tiles).
	 */
	final private int NUM_ROWS = 24;
	/**
	 * How many game 'ticks' the Tetris block can be at the top of
	 * the screen before it's game over.
	 */
	final private int MAX_TOP_ITERATIONS = 5; 
	/**
	 * How many game 'ticks' the Tetris block can be stationary
	 * until a new one is spawned.
	 */
	final private int MAX_STATIONARY_ITERATIONS = 3;
	
	/**
	 * Manages the positions of all tiles.
	 */
	private GameMesh gameMesh;
	/**
	 * Manages displaying the game mesh and player stats.
	 */
	private GUI gui;
	/**
	 * Manages the player's stats.
	 */
	private Player player;
	
	/**
	 * Tracks how many 'ticks' the Tetris block has been on
	 * top of the screen. If it reaches MAX_TOP_ITERATIONS, it's
	 * game over.
	 */
	private int topIterations;
	/**
	 * Tracks how many 'ticks' the Tetris block has been stationary. If it
	 * reaches MAX_STATIONARY_ITERATIONS, a new Tetris block is spawned.
	 */
	private int stationaryIterations;
	
	/**
	 * If True, it's game over. If False, the game is in progress.
	 */
	private boolean gameOver;
	
	/**
	 * The current Tetris block being controlled by the player.
	 */
	private TetrisBlock currentBlock;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Create a new Tetris game.
	 */
	public Tetris() {
		this.gameMesh = new GameMesh(this.NUM_ROWS, this.NUM_COLS);
		this.gui = new GUI(this.NUM_COLS*TILE_LENGTH, this.NUM_ROWS*TILE_LENGTH);
		this.player = new Player("Player 1");
		this.topIterations = 0;
		this.gameOver = false;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		// Generate a new block
		this.currentBlock = TetrisBlockGenerator.createTetrisBlock(TILE_LENGTH, NUM_COLS);
		// Put currentBlock on the game mesh.
		Controller.addTetrisBlock(currentBlock, gameMesh);
		// Display the screen
		gui.display(mainStage, gameMesh, this.player, this.gameOver);	
		// Enable player to move the Tetris block
		gui.setOnKeyPress(currentBlock, gameMesh);
		
		Timer fall = new Timer(); // Manages when the Tetris block should fall down
		TimerTask task = new TimerTask() {
			public void run() { // New Java thread
				Platform.runLater(new Runnable() {
					public void run() {
						// Used to know if the Tetris block has remained stationary
						int tetrisBlockRow = currentBlock.getTileA().getRow();
						int tetrisBlockColumn = currentBlock.getTileA().getColumn();
						
						topIterations = (currentBlock.isOnRow(0)) ? topIterations + 1 : 0;
						
						if (topIterations == MAX_TOP_ITERATIONS || gameOver) { // Tetris block has been at the top of the screen for too long
							gameOver = true;
							gui.display(mainStage, gameMesh, player, gameOver);	
						}
						
						if (topIterations == MAX_TOP_ITERATIONS + 15) { // Exit after the game over screen is seen for 15 game ticks
							System.exit(0);
						}
						
						if (!gameOver) {
							Controller.moveDown(currentBlock, gameMesh);
							gui.display(mainStage, gameMesh, player, gameOver);
							
							// Check if the block hasn't moved
							int currentRow = currentBlock.getTileA().getRow();
							int currentCol = currentBlock.getTileA().getColumn();
							if (tetrisBlockRow == currentRow && tetrisBlockColumn == currentCol)
								stationaryIterations += 1;
							else
								stationaryIterations = 0;
							
							// Spawn a new block, clear any completed rows, and update player score. 
							// If spawning a block results in a collision with a 
							// tile, the game is over.
							if (stationaryIterations >= MAX_STATIONARY_ITERATIONS) {
								stationaryIterations = 0;
								currentBlock = TetrisBlockGenerator.createTetrisBlock(TILE_LENGTH, NUM_COLS);
								int numRowsCleared = gameMesh.clearMatchingTiles();
								player.changeRowsCleared(numRowsCleared);
								player.changeScore(getPoints(numRowsCleared));
								
								if (!canSpawnBlock(currentBlock, gameMesh))
									gameOver = true;
								
								else {
									gui.setOnKeyPress(currentBlock, gameMesh);
									Controller.addTetrisBlock(currentBlock, gameMesh);
									gui.display(mainStage, gameMesh, player, gameOver);	
								}
							}
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 300); // 300 = length of a game 'tick'
	}
	
	/**
	 * Converts the number of rows cleared to number of points earned.
	 * @param numRowsCleared The number of rows cleared.
	 * @return The corresponding points earned.
	 */
	private static int getPoints(int numRowsCleared) {
		if (numRowsCleared == 0)
			return 0;
		else if (numRowsCleared == 1)
			return 50;
		else if (numRowsCleared == 2)
			return 100;
		else if (numRowsCleared == 3)
			return 200;
		else
			return 400;
	}
	
	/**
	 * @param tetrisBlock The Tetris block.
	 * @param gameMesh Manages the positions of tiles on the game mesh.
	 * @return True if the given Tetris block doesn't collide with a tile on the game mesh.
	 * False if otherwise.
	 */
	private static boolean canSpawnBlock(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		Tile tileA = tetrisBlock.getTileA();
		Tile tileB = tetrisBlock.getTileB();
		Tile tileC = tetrisBlock.getTileC();
		Tile tileD = tetrisBlock.getTileD();
		
		try {
			return (gameMesh.isEmpty(tileA.getRow(), tileA.getColumn()) && 
					gameMesh.isEmpty(tileB.getRow(), tileB.getColumn()) && 
					gameMesh.isEmpty(tileC.getRow(), tileC.getColumn()) && 
					gameMesh.isEmpty(tileD.getRow(), tileD.getColumn()));
		}
		
		catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
}
