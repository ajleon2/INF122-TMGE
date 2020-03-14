package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Manages the graphical user interface of the Tetris game.
 * Displays the game on the left and the player's stats on the
 * right.
 * @author Andrew Leon
 *
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
	 * Contains the overall layout (game on left, stats on right)
	 */
	private Pane pane;
	/**
	 * The Tetris scene
	 */
	private Scene scene;
	
	/**
	 * @param gameScreenWidth Width of the game screen (in pixels).
	 * @param gameScreenHeight Height of the game screen (in pixels).
	 */
	public GUI(int gameScreenWidth, int gameScreenHeight) {
		this.gameScreenWidth = gameScreenWidth;
		this.gameScreenHeight = gameScreenHeight;
		
		this.pane = new Pane();
		
		// Extra width for the player stats
		this.scene = new Scene(this.pane, gameScreenWidth + 150, gameScreenHeight); 
	}
	
	/**
	 * Displays the tiles in the game mesh and the player stats to the screen.
	 * If gameOver is True, will also display a game over message. If false, will not.
	 * @param stage The javafx stage to display to.
	 * @param gameMesh The game mesh which represents all tiles on the game screen.
	 * @param player Contains the player's name and stats.
	 * @param gameOver If True, displays a game over message. If false, doesn't.
	 */
	public void display(Stage stage, GameMesh gameMesh, Player player, Boolean gameOver) {
		this.pane.getChildren().clear();
		
		setPlayerStats(player);
		setGameMesh(gameMesh);
		
		if (gameOver)
			setGameOver();
		
		stage.setScene(this.scene);
		stage.setTitle("T E T R I S");
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
	 * each tile is placed on the screen.
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
	 * Puts the player's name and stats on the screen at the right side.
	 * @param player Contains the player's current stats.
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
		
		String linesCleared = String.format("Lines Cleared: %d", player.getRowsCleared());
		Text linesClearedText = new Text(linesCleared);
		linesClearedText.setX(this.gameScreenWidth + 5);
		linesClearedText.setY(150);
		
		this.pane.getChildren().addAll(line, playerNameText, scoreText, linesClearedText);
	}
	
	/**
	 * Set the javafx Scene object to listen for key presses.
	 * @param tetrisBlock The tetris block that should move or flip on key press.
	 * @param gameMesh The game mesh containing the tile placement information.
	 */
	public void setOnKeyPress(TetrisBlock tetrisBlock, GameMesh gameMesh) {
		this.scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.moveRight(tetrisBlock, gameMesh);
					break;
					
				case DOWN:
					Controller.moveDown(tetrisBlock, gameMesh);
					break;
					
				case LEFT:
					Controller.moveLeft(tetrisBlock, gameMesh);
					break;
					
				case UP:
					Controller.flip(tetrisBlock, gameMesh);
					break;
				}
			}
		});
	}
}
