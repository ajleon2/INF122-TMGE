package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * The entry point for this Dr. Mario application.
 * @author Andrew Leon
 */
public class DrMario extends Application {
	/**
	 * The game difficulty. Higher difficulties means more viruses on
	 * the screen.
	 */
	final private GameLogic.Difficulty GAME_DIFFICULTY = GameLogic.Difficulty.EASY;
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
	 * How many game 'ticks' the Pill can be at the top of
	 * the screen before it's game over.
	 */
	final private int MAX_TOP_ITERATIONS = 5;
	/**
	 * How many game 'ticks' the Pill can be stationary
	 * until a new one is spawned.
	 */
	final private int MAX_STATIONARY_ITERATIONS = 1;
	
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
	 * Tracks how many game 'ticks' the player's Pill has been on
	 * top of the screen. If it reaches MAX_TOP_ITERATIONS, it's
	 * game over.
	 */
	private int topIterations;
	/**
	 * Tracks how many game 'ticks' the player's Pill has been stationary. If 
	 * it reaches MAX_STATIONARY_ITERATIONS, a new player Pill is spawned.
	 */
	private int stationaryIterations;
	/**
	 * If True, it's game over. If False, the game is in progress.
	 */
	private boolean gameOver;
	
	/**
	 * The current Pill being controlled by the player.
	 */
	private Pill currentPill;
	/**
	 * True if there are "floating"  pills that must move down. 
	 * Every game tick these  pills move down 1 tile; the user 
	 * is not allowed to move until all  pills have fallen down.
	 */
	private Boolean floatingPillsMustFall;
	/**
	 * When the  pills are falling down, the player's next
	 * pill is not on the screen. This member keeps track of
	 * when the player's pill is on the screen (True) and when 
	 * the game is  waiting for all  pills to fall down before
	 * spawning the player's next pill (False).
	 */
	private Boolean playerPillOnMesh;
	
	/**
	 * Launches this Dr. Mario application with command line
	 * arguments (currently, command line arguments aren't used).
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Create a new DrMario game.
	 */
	public DrMario() {
		final String GAME_NAME = "Dr. Mario";
		
		this.gameMesh = new GameMesh(this.NUM_ROWS, this.NUM_COLS);
		this.gui = new GUI(this.NUM_COLS*TILE_LENGTH, this.NUM_ROWS*TILE_LENGTH, GAME_NAME);
		this.player = new Player("Player 1");
		this.topIterations = 0;
		this.gameOver = false;
		this.floatingPillsMustFall = false;
		this.playerPillOnMesh = false;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// Place the viruses on the game mesh
		GameLogic.spawnViruses(gameMesh, GAME_DIFFICULTY, TILE_LENGTH);
		// Create the player's pill
		this.currentPill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
		// Add the player's pill to the game mesh
		Controller.addPill(currentPill, gameMesh);
		// Display the game mesh & player stats
		gui.display(mainStage, gameMesh, this.player, this.gameOver);	
		// Gives the player control of their pill (best done AFTER
		// displaying the game mesh).
		gui.setOnKeyPress(currentPill, gameMesh);
		this.playerPillOnMesh = true;
		
		Timer fall = new Timer(); // Manages when the Pill should fall down
		TimerTask task = new TimerTask() {
			public void run() { // New Java thread
				Platform.runLater(new Runnable() {
					public void run() {
						
						// The player cleared all the viruses. Start a new game.
						if (GameLogic.allVirusesKilled(gameMesh)) {
							gameMesh.clearMesh();
							GameLogic.spawnViruses(gameMesh, GAME_DIFFICULTY, TILE_LENGTH);
							gui.display(mainStage, gameMesh, player, gameOver);
						}
						
						// There are "floating"  pills that need to move down.
						// Move these  pills down 1 tile. The player's next pill
						// should not be controllable nor on the game mesh.
						else if (floatingPillsMustFall) {
							floatingPillsMustFall = Controller.moveFloatingPillsDown(gameMesh);
							gui.display(mainStage, gameMesh, player, gameOver);	
							
							// After all  pills are down falling, check if a match was made;
							// a match may result in more  pills needing to fall.
							if (floatingPillsMustFall == false) { 
								int numVirusesKilled = GameLogic.clearMatchingTiles(gameMesh);
								player.changeTileMatches(numVirusesKilled);
								player.changeScore(GameLogic.getPoints(numVirusesKilled));
								floatingPillsMustFall = Controller.moveFloatingPillsDown(gameMesh);
								gui.display(mainStage, gameMesh, player, gameOver);
							}
						}
						
						// Half pills are finished falling, but the player's pill isn't
						// on the screen nor does the player have control of their pill.
						// Add the player's pill to the screen and give them control over
						// it.
						else if (!playerPillOnMesh) {
							Controller.addPill(currentPill, gameMesh);
							playerPillOnMesh = true;
							gui.display(mainStage, gameMesh, player, gameOver);	
							gui.setOnKeyPress(currentPill, gameMesh);
						}
						
						// The player is currently controlling their pill or the game
						// is over.
						else {
							// Either increment the number of game ticks the player's pill
							// has been at the top of the screen or reset it.
							topIterations = (currentPill.isOnRow(0)) ? topIterations + 1 : 0;
							
							// The player's pill has been at the top of the screen for too long;
							// the game is over.
							if (topIterations == MAX_TOP_ITERATIONS || gameOver) { 
								gameOver = true;
								gui.display(mainStage, gameMesh, player, gameOver);	
								
								// Exit the game after 
								if (topIterations == MAX_TOP_ITERATIONS + 15)
									System.exit(0);
							}
							
							else {		
								// Get row and column of TileA in the player's pill.
								// We'll compare these values to those of TileA after
								// the pill is forced to move down 1 tile.
								int previousRow = currentPill.getTileA().getRow();
								int previousColumn = currentPill.getTileA().getColumn();

								Controller.moveDown(currentPill, gameMesh);
								gui.display(mainStage, gameMesh, player, gameOver);
								
								// Check if the pill has moved. If it hasn't, increment how 
								// many game ticks the pill is stationary; else, reset this count.
								int currentRow = currentPill.getTileA().getRow();
								int currentCol = currentPill.getTileA().getColumn();
								if (previousRow == currentRow && previousColumn == currentCol)
									stationaryIterations += 1;
								else
									stationaryIterations = 0;
								
								// The player's pill has been stationary for too long; it's
								// time for the player to get a new pill. 
								if (stationaryIterations >= MAX_STATIONARY_ITERATIONS) {
									// Remove the player's ability to move this pill while the system
									// creates a new one for them.
									gui.disableOnKeyPress(currentPill);
									stationaryIterations = 0;
									currentPill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
									
									// Check if the player has made any tile matches
									int numVirusesKilled = GameLogic.clearMatchingTiles(gameMesh);
									player.changeTileMatches(numVirusesKilled);
									player.changeScore(GameLogic.getPoints(numVirusesKilled));
									
									// Make any pills that are "floating" fall down 1 tile
									floatingPillsMustFall = Controller.moveFloatingPillsDown(gameMesh);
									
									// There are tiles obstructing the pill's spawn point. The game
									// is now over.
									if (!GameLogic.canSpawnPill(currentPill, gameMesh))
										gameOver = true;
									
									// There may be "floating"  pills that need to fall. The player
									// is NOT given control of their new pill nor is the new pill added
									// to the game mesh. The game will refrain from giving the player control
									// until after the game state is in the proper state.
									else if (floatingPillsMustFall) {
										gui.display(mainStage, gameMesh, player, gameOver);	
										playerPillOnMesh = false;
									}
									
									// The player's new pill can spawn and there are no "floating"  pills.
									// Add the player's new pill to the game mesh and give the player control
									// over it.
									else {
										Controller.addPill(currentPill, gameMesh);
										playerPillOnMesh = true;
										gui.display(mainStage, gameMesh, player, gameOver);	
										gui.setOnKeyPress(currentPill, gameMesh);
									}
								}
							}
						}						
					}
				});
			}
		};
		fall.schedule(task, 0, 300); // 300 = length of a game 'tick'
	}
}
