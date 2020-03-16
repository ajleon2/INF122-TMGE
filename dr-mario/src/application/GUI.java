package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Manages displaying the graphical user interface to the player.
 * Displays the game on the left and the player's stats on the
 * right. Also sets and disables key press events.
 * @author Andrew Leons
 */
public class GUI {
	/**
	 * The width of the game screen (in pixels).
	 */
	private int gameScreenWidth;
	/**
	 * The height of the game screen (in pixels).
	 */
	private int gameScreenHeight;
	
	/**
	 * Contains the overall layout (game on left, player stats on right).
	 */
	private Pane pane;
	/**
	 * The Dr. Mario scene
	 */
	private Scene scene;
	/**
	 * The game's title (e.g. "Dr. Mario").
	 */
	private String gameTitle;
	
	/**
	 * @param gameScreenWidth Width of the game screen (in pixels).
	 * @param gameScreenHeight Height of the game screen (in pixels).
	 * @param gameTitle The game's title.
	 */
	public GUI(int gameScreenWidth, int gameScreenHeight, String gameTitle) {
		this.gameScreenWidth = gameScreenWidth;
		this.gameScreenHeight = gameScreenHeight;
		this.gameTitle = gameTitle;
		
		this.pane = new Pane();
		
		// Extra width for the player's stats
		this.scene = new Scene(this.pane, gameScreenWidth + 150, gameScreenHeight); 
	}
	
	/**
	 * Displays the game mesh's tiles and the player's stats to the screen.
	 * If gameOver is True, will also display a game over message; if false, will not.
	 * @param stage The javafx stage to display to.
	 * @param gameMesh The game mesh which represents all tiles on the game screen.
	 * @param player Contains the player's name and stats.
	 * @param gameOver If True, displays a game over message. If false, doesn't.
	 */
	public void display(Stage stage, GameMesh gameMesh, Player player, Boolean gameOver) {
		this.pane.getChildren().clear(); // Reset the screen
		
		setPlayerStats(player);
		setGameMesh(gameMesh);
		
		if (gameOver)
			setGameOver();
		
		stage.setScene(this.scene);
		stage.setTitle(this.gameTitle);
		stage.show();
	}
	
	/**
	 * Put a "GAME OVER" overlay to the screen.
	 */
	private void setGameOver() {
		Text gameOverText = new Text("GAME OVER");
		gameOverText.setStyle("-fx-font: 70 arial;");
		gameOverText.setY(250);
		gameOverText.setX(10);
		this.pane.getChildren().add(gameOverText);
	}
	
	/**
	 * Puts the game mesh to the screen.
	 * @param gameMesh The game mesh, contains information about where
	 * each tile is to be placed on the screen.
	 */
	private void setGameMesh(GameMesh gameMesh) {
		for (int r = 0; r < gameMesh.getNumRows(); r++) {
			for (int c = 0; c < gameMesh.getNumColumns(); c++) {
				if (!gameMesh.isEmpty(r, c))
					this.pane.getChildren().add(gameMesh.getTile(r, c).getRectangle());
			}
		}
	}
	
	/**
	 * Puts the player's name and stats on the right side of the screen.
	 * @param player Contains the player's name and stats.
	 */
	private void setPlayerStats(Player player) {
		Line line = new Line(this.gameScreenWidth, 0, this.gameScreenWidth, this.gameScreenHeight);
		
		String playerName = String.format("Player: %s", player.getName());
		Text playerNameText = new Text(playerName);
		playerNameText.setX(this.gameScreenWidth + 5);
		playerNameText.setY(50);
		
		String score = String.format("Score: %d", player.getScore());
		Text scoreText = new Text(score);
		scoreText.setX(this.gameScreenWidth + 5);
		scoreText.setY(100);
		
		String tileMatches = String.format("Tiles matched: %d", player.getNumTileMatches());
		Text tileMatchesText = new Text(tileMatches);
		tileMatchesText.setX(this.gameScreenWidth + 5);
		tileMatchesText.setY(150);
		
		this.pane.getChildren().addAll(line, playerNameText, scoreText, tileMatchesText);
	}
	
	/**
	 * Set the javafx Scene object to listen for key presses so the player can
	 * move the provided Pill object.
	 * @param pill The pill that should move or flip on key press.
	 * @param gameMesh The game mesh containing the tile placement information.
	 */
	public void setOnKeyPress(Pill pill, GameMesh gameMesh) {
		this.scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.moveRight(pill, gameMesh);
					break;
					
				case DOWN:
					Controller.moveDown(pill, gameMesh);
					break;
					
				case LEFT:
					Controller.moveLeft(pill, gameMesh);
					break;
					
				case UP:
					Controller.flip(pill, gameMesh);
					break;
				}
			}
		});
	}
	
	/**
	 * Remove the player's control over the given Pill object.
	 * @param pill The pill object which the player should not be
	 * able to control.
	 */
	public void disableOnKeyPress(Pill pill) {
		this.scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
				case DOWN:
				case LEFT:
				case UP:
					break;
				}
			}
		});
	}
}
