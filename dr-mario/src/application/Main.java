package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Main extends Application {
	
	/**
	 * The length of a tile (in pixels).
	 */
	private int TILE_LENGTH = 25;
	/**
	 * The number of columns in the game mesh (in # of tiles).
	 */
	final private int NUM_COLS = 12;
	/**
	 * The number of rows in the game mesh (in # of tiles).
	 */
	final private int NUM_ROWS = 24;
	
	final private int STATS_LENGTH = 150;
	
	public static Pill player1Pill;
	public static Pill player2Pill;
	
	private DrMario player1Game;
	private DrMario player2Game;
	
	private static boolean player1Disabled;
	private static boolean player2Disabled;
	private static Scene scene;
	

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public Main() {
		player1Pill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
		player2Pill = ShapeGenerator.createRandomPill(TILE_LENGTH, NUM_COLS);
		
		player1Disabled = true;
		player2Disabled = true;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO: Ask for single or multiplayer
		//boolean isSinglePlayer = askForGameType();
		boolean isMultiplayer = true;
		
		Pane parentPane = new Pane();
		Pane player1Pane = new Pane();
		Pane player2Pane = new Pane();
		
		if (!isMultiplayer) {
			parentPane.getChildren().add(player1Pane);
			scene = new Scene(parentPane, TILE_LENGTH*NUM_COLS + STATS_LENGTH, TILE_LENGTH*NUM_ROWS);
			setOnKeyPressed(Player.PlayerNumber.PLAYER_1);
			
			player1Game = new DrMario(mainStage, scene, player1Pane, Player.PlayerNumber.PLAYER_1, NUM_ROWS, NUM_COLS);
			
			something();
			player1Game.start();
		}
		
		else {
			player2Pane.setTranslateX(TILE_LENGTH*NUM_COLS+STATS_LENGTH);
			parentPane.getChildren().addAll(player1Pane, player2Pane);
			
			scene = new Scene(parentPane, TILE_LENGTH*NUM_COLS*2 + STATS_LENGTH*2, TILE_LENGTH*NUM_ROWS);
			
			player1Game = new DrMario(mainStage, scene, player1Pane, Player.PlayerNumber.PLAYER_1, NUM_ROWS, NUM_COLS);
			player2Game = new DrMario(mainStage, scene, player2Pane, Player.PlayerNumber.PLAYER_2, NUM_ROWS, NUM_COLS);

			something();
			
			player1Game.start();
			player2Game.start();
		}
	}
	
	public static void setPlayerPill(Player.PlayerNumber playerNumber, Pill newPill) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1) {
			player1Pill = newPill;
		}
		
		else {
			player2Pill = newPill;
		}
	}
	
	public static Pill getPlayerPill(Player.PlayerNumber playerNumber) {
		if (playerNumber == Player.PlayerNumber.PLAYER_1)
			return player1Pill;
		else
			return player2Pill;
	}
	
	public void something() {
//		if (playerNumber == Player.PlayerNumber.PLAYER_1)
//			player1Disabled = false;
//		else if (playerNumber == Player.PlayerNumber.PLAYER_2)
//			player2Disabled = false;
		
		scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				
				KeyCode keyPressed = event.getCode();
				
				// Player 1's controls
				if (true) {
					if (keyPressed == KeyCode.W)
						Controller.flip(Main.player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.D)
						Controller.moveRight(Main.player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.S)
						Controller.moveDown(Main.player1Pill, player1Game.getGameMesh());
					if (keyPressed == KeyCode.A) 
						Controller.moveLeft(Main.player1Pill, player1Game.getGameMesh());
				}
				
				
				// Player 2's controls
				if (true) {
					if (keyPressed == KeyCode.UP)
						Controller.flip(Main.player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.RIGHT)
						Controller.moveRight(Main.player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.DOWN)
						Controller.moveDown(Main.player2Pill, player2Game.getGameMesh());
					if (keyPressed == KeyCode.LEFT) 
						Controller.moveLeft(Main.player2Pill, player2Game.getGameMesh());
				}
			}
		});
	}
	
	
	/**
	 * Set the javafx Scene object to listen for key presses so the player can
	 * move the provided Pill object.
	 * @param playerNumber Which player to set the key presses to 
	 * (e.g. PlayerNumber.PLAYER_1 or PlayerNumber.Player_2). 
	 */
	public static void setOnKeyPressed(Player.PlayerNumber playerNumber) {
//		if (playerNumber == Player.PlayerNumber.PLAYER_1)
//			player1Disabled = false;
//		else if (playerNumber == Player.PlayerNumber.PLAYER_2)
//			player2Disabled = false;
//		
//		scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
//			public void handle(KeyEvent event) {
//				
//				KeyCode keyPressed = event.getCode();
//				
//				// Player 1's controls
//				if (!player1Disabled) {
//					if (keyPressed == KeyCode.W)
//						Controller.flip(player1Pill, player1Game.getGameMesh());
//					if (keyPressed == KeyCode.D)
//						Controller.moveRight(player1Pill, player1Game.getGameMesh());
//					if (keyPressed == KeyCode.S)
//						Controller.moveDown(player1Pill, player1Game.getGameMesh());
//					if (keyPressed == KeyCode.A) 
//						Controller.moveLeft(player1Pill, player1Game.getGameMesh());
//				}
//				
//				
//				// Player 2's controls
//				if (!player2Disabled) {
//					if (keyPressed == KeyCode.UP)
//						Controller.flip(player2Pill, player2Game.getGameMesh());
//					if (keyPressed == KeyCode.RIGHT)
//						Controller.moveRight(player2Pill, player2Game.getGameMesh());
//					if (keyPressed == KeyCode.DOWN)
//						Controller.moveDown(player2Pill, player2Game.getGameMesh());
//					if (keyPressed == KeyCode.LEFT) 
//						Controller.moveLeft(player2Pill, player2Game.getGameMesh());
//				}
//			}
//		});
	}
	
	/**
	 * Remove the player's control over their pill.
	 * @param playerNumber Which player to disable the key presses from (e.g. Player.PLAYER_1).
	 */
	public static void disableOnKeyPress(Player.PlayerNumber playerNumber) {
		
		return;
		
//		if (playerNumber == Player.PlayerNumber.PLAYER_1)
//			player1Disabled = true;
//		else if (playerNumber == Player.PlayerNumber.PLAYER_2)
//			player2Disabled = true;
//		
//		setOnKeyPressed(null);
	}
}
