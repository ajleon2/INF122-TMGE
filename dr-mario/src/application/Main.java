package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * The launch point for this Dr. Mario game. Also holds the Pills currently
 * being controlled by each player's game instance and has static methods that
 * manages each player's control over their Pill.
 * @author Andrew Leon
 *
 */
public class Main extends Application {
	final static private String GAME_MODE_MENU = ("Select a game mode:\n" + 
                                                  "\t0. Exit Application\n" + 
			                                      "\t1. Single-player\n" +
                                                  "\t2. Multi-player\n");
	final static private int GAME_MODE_MENU_MIN = 0;
	final static private int GAME_MODE_MENU_MAX = 2;
	
	final static private String DIFFICULTY_SELECT_MENU = ("Select a game difficulty:\n" +
	                 									  "\t1. Easy\n" + "\t2. Normal\n" + "\t3. Hard\n");
	final static private int DIFFICULTY_SELECT_MIN = 1;
	final static private int DIFFICULTY_SELECT_MAX = 3;
	
	/**
	 * The length of a tile (in pixels). Tiles are squares.
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
	 * The width of each player's stats screen (in pixels).
	 */
	final private int STATS_SCREEN_WIDTH = 100;
	
	
	// These are static so each game can enable/disable
	// their own controls.
	
	/**
	 * Player 1's game instance.
	 */
	private static DrMario player1Game;
	/**
	 * Player 2's game instance.
	 */
	private static DrMario player2Game;
	
	/**
	 * The pill player 1 controls.
	 */
	private static Pill player1Pill;
	/**
	 * The pill player 2 controls.
	 */
	private static Pill player2Pill;
	
	/**
	 * If True, player 1's controls are currently disabled.
	 * If False, their controls are enabled.
	 */
	private static boolean player1Disabled;
	/**
	 * If True, player 2's controls are currently disabled.
	 * If False, their controls are enabled.
	 */
	private static boolean player2Disabled;
	
	/**
	 * The javafx scene that holds the entire GUI.
	 */
	private static Scene scene;
	

	/**
	 * Launches this Dr. Mario game.
	 * @param args Command line arguments (currently unused).
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Create a new main object. Initializes each player's pills
	 * and sets their controls to "disabled".
	 */
	public Main() {
		player1Pill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
		player2Pill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
		
		player1Disabled = true;
		player2Disabled = true;
	}

	/**
	 * Present a menu where the user selects an integer between 2
	 * bounds (inclusive). Repeatedly asks the user upon 
	 * invalid response(s) (e.g. non-integer value or integer out of range). 
	 * @param menu The menu to display.
	 * @param minResponse The lower bound.
	 * @param maxResponse The upper bound.
	 * @return The player's valid response.
	 */
	private int presentMenu(String menu, int minResponse, int maxResponse) {
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			try {
				System.out.print(menu);
				int response = scanner.nextInt();
				
				if (response <= maxResponse && response >= minResponse)
					return response;
				else
					System.err.println(String.format("Invalid response. Please pick an integer between %d and %d.", 
							           minResponse, maxResponse));
			}
			
			catch(InputMismatchException e) {
				System.err.println("Invalid response. Please enter an integer value.");
				scanner.next(); // clear the scanner
			}
			
		}
	}
	
	/**
	 * Ask the user for what game difficulty they want.
	 * Asks player1 or player2 depending on provided argument.
	 * @param playerNumber Which player to ask for their difficulty.
	 * @return The player's selected difficulty level.
	 */
	private GameLogic.Difficulty getGameDifficulty(Player.PlayerNumber playerNumber) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			System.out.println("Player 1...");
		else
			System.out.println("Player 2...");
		
		int response = presentMenu(DIFFICULTY_SELECT_MENU, DIFFICULTY_SELECT_MIN, DIFFICULTY_SELECT_MAX);
		
		if (response == 1)
			return GameLogic.Difficulty.EASY;
		else if (response == 2)
			return GameLogic.Difficulty.MEDIUM;
		else
			return GameLogic.Difficulty.HARD;
	}
	
	
	private String getPlayerName(Player.PlayerNumber playerNumber) {
		Scanner scanner = new Scanner(System.in);
		
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			System.out.println("Player 1...");
		else
			System.out.println("Player 2...");
		
		System.out.print("What is your name? ");
		
		return scanner.next();
	}

	@Override
	public void start(Stage mainStage) throws Exception {

		// Determine whether this is a single or 2 player game.
		boolean isMultiplayer = false;
		int response = presentMenu(GAME_MODE_MENU, GAME_MODE_MENU_MIN, GAME_MODE_MENU_MAX);
		if (response == 0)
			System.exit(0);
		if (response == 2)
			isMultiplayer = true;

		// The parent pane contains the panes that each
		// player game instance modifies.
		Pane parentPane = new Pane();
		Pane player1Pane = new Pane();
		Pane player2Pane = new Pane();

		// Start a single player game.
		if (!isMultiplayer) {
			parentPane.getChildren().add(player1Pane);
			scene = new Scene(parentPane, TILE_LENGTH*NUM_COLS + STATS_SCREEN_WIDTH, TILE_LENGTH*NUM_ROWS);
			setOnKeyPressed(Player.PlayerNumber.PLAYER_1);
			
			GameLogic.Difficulty player1Difficulty = getGameDifficulty(Player.PlayerNumber.PLAYER_1);
			String player1Name = getPlayerName(Player.PlayerNumber.PLAYER_1);
			
			player1Game = new DrMario(mainStage, scene, player1Pane, Player.PlayerNumber.PLAYER_1, player1Difficulty,
					                  player1Name, TILE_LENGTH, NUM_ROWS, NUM_COLS, STATS_SCREEN_WIDTH);
			player1Game.start();
		}
		
		// Start a multiplayer game
		else {
			player2Pane.setTranslateX(TILE_LENGTH*NUM_COLS + STATS_SCREEN_WIDTH);
			parentPane.getChildren().addAll(player1Pane, player2Pane);
			
			scene = new Scene(parentPane, TILE_LENGTH*NUM_COLS*2 + STATS_SCREEN_WIDTH*2, TILE_LENGTH*NUM_ROWS);
			
			
			GameLogic.Difficulty player1Difficulty = getGameDifficulty(Player.PlayerNumber.PLAYER_1);
			GameLogic.Difficulty player2Difficulty = getGameDifficulty(Player.PlayerNumber.PLAYER_2);
			String player1Name = getPlayerName(Player.PlayerNumber.PLAYER_1);
			String player2Name = getPlayerName(Player.PlayerNumber.PLAYER_2);
			player1Game = new DrMario(mainStage, scene, player1Pane, Player.PlayerNumber.PLAYER_1, player1Difficulty,
									  player1Name, TILE_LENGTH, NUM_ROWS, NUM_COLS, STATS_SCREEN_WIDTH);
			player2Game = new DrMario(mainStage, scene, player2Pane, Player.PlayerNumber.PLAYER_2, player2Difficulty,
									  player2Name, TILE_LENGTH, NUM_ROWS, NUM_COLS, STATS_SCREEN_WIDTH);
			player1Game.start();
			player2Game.start();
		}
	}
	
	/**
	 * Sets the specified player's Pill object to a new pill.
	 * @param playerNumber Determines which player's pill to set.
	 * @param newPill The new pill.
	 */
	public static void setPlayerPill(Player.PlayerNumber playerNumber, Pill newPill) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			player1Pill = newPill;
		else
			player2Pill = newPill;
	}
	
	/**
	 * Returns the specified player's Pill object.
	 * @param playerNumber Determines which player's pill to return.
	 * @return The specified player's Pill object.
	 */
	public static Pill getPlayerPill(Player.PlayerNumber playerNumber) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			return player1Pill;
		else
			return player2Pill;
	}
	
	
	/**
	 * Set the javafx Scene object to listen for key presses so the player can
	 * move the provided Pill object.
	 * @param playerNumber Which player to set the key presses to 
	 * (e.g. PlayerNumber.PLAYER_1 or PlayerNumber.Player_2). 
	 */
	public static void setOnKeyPressed(Player.PlayerNumber playerNumber) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			player1Disabled = false;
		else if (playerNumber == Player.PlayerNumber.PLAYER_2)
			player2Disabled = false;
		
		scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				
				KeyCode keyPressed = event.getCode();
				
				// Player 1's controls
				if (!player1Disabled) {
					if (keyPressed == KeyCode.W)
						Controller.flip(player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.D)
						Controller.moveRight(player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.S)
						Controller.moveDown(player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.A) 
						Controller.moveLeft(player1Pill, player1Game.getGameMesh());
				}
				
				
				// Player 2's controls
				if (!player2Disabled) {
					if (keyPressed == KeyCode.UP)
						Controller.flip(player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.RIGHT)
						Controller.moveRight(player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.DOWN)
						Controller.moveDown(player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.LEFT) 
						Controller.moveLeft(player2Pill, player2Game.getGameMesh());
				}
			}
		});
	}
	
	/**
	 * Remove the player's control over their pill.
	 * @param playerNumber Which player to disable the key presses from (e.g. Player.PLAYER_1).
	 */
	public static void disableOnKeyPress(Player.PlayerNumber playerNumber) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			player1Disabled = true;
		else if (playerNumber == Player.PlayerNumber.PLAYER_2)
			player2Disabled = true;
		
		setOnKeyPressed(null);
	}
}
