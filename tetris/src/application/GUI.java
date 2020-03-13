package application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
	
	/**
	 * Length of a tile (in pixels).
	 */
	private int tileLength;

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
	 * The Tetris scene (additional width for stats on right)
	 */
	private Scene scene;
	
	/**
	 * @param tileLength Length of a tile (in pixels). Tiles are assumed to be squares.
	 * @param gameScreenWidth Width of the game screen (in pixels).
	 * @param gameScreenHeight Height of the game screen (in pixels).
	 */
	public GUI(int tileLength, int gameScreenWidth, int gameScreenHeight) {
		this.tileLength = tileLength;
		this.gameScreenWidth = gameScreenWidth;
		this.gameScreenHeight = gameScreenHeight;
		
		this.pane = new Pane();
		this.scene = new Scene(this.pane, gameScreenWidth + 150, gameScreenHeight); // Extra width for stats sidebar
	}
	
	public void display(Stage stage, GameMesh gameMesh, TetrisBlock tetrisBlock, Player player) {
		this.pane.getChildren().clear();
		
		setPlayerStats(player);
		setTetrisBlock(tetrisBlock);
		
		stage.setScene(this.scene);
		stage.setTitle("T E T R I S");
		stage.show();
	}
	
	public void displayGameOver(Stage stage, GameMesh gameMesh, TetrisBlock tetrisBlock, Player player) {
		this.pane.getChildren().clear();
		
		setPlayerStats(player);
		setTetrisBlock(tetrisBlock);
		
		stage.setScene(this.scene);
		stage.setTitle("T E T R I S");
		
		
		Text gameOverText = new Text("GAME OVER");
		gameOverText.setStyle("-fx-font: 70 arial;");
		gameOverText.setY(250);
		gameOverText.setX(10);
		this.pane.getChildren().add(gameOverText);
		
		stage.show();
	}
	
	private void setPlayerStats(Player player) {
		Line line = new Line(this.gameScreenWidth, 0, this.gameScreenWidth, this.gameScreenHeight);
		
		String score = String.format("Score: %d", player.getScore());
		Text scoreText = new Text(score);
		scoreText.setX(this.gameScreenWidth + 5);
		scoreText.setY(50);
		
		String linesCleared = String.format("Lines Cleared: %d", player.getRowsCleared());
		Text linesClearedText = new Text(linesCleared);
		linesClearedText.setX(this.gameScreenWidth + 5);
		linesClearedText.setY(100);
		
		this.pane.getChildren().addAll(line, scoreText, linesClearedText);
	}
	
	private void setTetrisBlock(TetrisBlock tetrisBlock) {
		this.pane.getChildren().addAll(tetrisBlock.getTileA().getRectangle(),
				                       tetrisBlock.getTileB().getRectangle(),
				                       tetrisBlock.getTileC().getRectangle(),
				                       tetrisBlock.getTileD().getRectangle());
	}
	
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
					tetrisBlock.flip();
					break;
				}
			}
		});
	}
}
