package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * A Tile is the smallest unit in a tile matching game.
 * Tetris blocks are comprised of tiles. Tiles manage
 * their own position on the screen based on their position
 * on the game mesh.
 * @author Andrew Leon
 *
 */
public class Tile {
	
	/**
	 * Some tile matching games have different types of tiles w/
	 * different properties. B/c Tetris isn't such a game,
	 * there is only 1 tile type.
	 * @author Andrew Leon
	 *
	 */
	public enum TileType {
		TETRIS
	}
	
	/**
	 * A javafx rectangle represents this Tile.
	 */
	private Rectangle tile;
	/**
	 * The row this tile resides on in the game mesh.
	 */
	private int row;
	/**
	 * The column this tile resides on in the game mesh.
	 */
	private int column;
	/**
	 * The length of this tile (in pixels). All tiles are squares.
	 */
	private int tileLength;
	/**
	 * A formality. Some tile matching games use different tiles
	 * with different properties; this member determinse said tile type.
	 */
	private TileType tileType;
	
	/**
	 * Create a new Tile object with the provided color. Tiles are squares. 
	 * Assumes each cell on the game board is equivalent to 1 tile.
	 * @param color The tile's color.
	 * @param tileLength Length of the Tile (in pixels).
	 * @param row The row on the game mesh this tile will
	 * reside in. Assumes 0 indexing.
	 * @param column The column on the game mesh this tile
	 * will reside in. Assumes 0 indexing.
	 */
	public Tile(Color color, int tileLength, int row, int column) {
		this.row = row;
		this.column = column;
		this.tileLength = tileLength;
		
		this.tile = new Rectangle(tileLength, tileLength);
		this.tile.setX(column*tileLength);
		this.tile.setY(row*tileLength);
		this.tile.setFill(color);
		
		this.tileType = TileType.TETRIS;
	}
	
	/**
	 * Create a new, dark gray Tile object. Tiles are squares. Assumes each
	 * cell on the game board is equivalent to 1 tile.
	 * @param tileLength Length of the Tile (in pixels).
	 * @param row The row on the game mesh this tile will
	 * reside in. Assumes 0 indexing.
	 * @param column The column on the game mesh this tile
	 * will reside in. Assumes 0 indexing.
	 */
	public Tile(int tileLength, int row, int column) {
		this.row = row;
		this.column = column;
		this.tileLength = tileLength;
		
		this.tile = new Rectangle(tileLength, tileLength);
		this.tile.setX(column*tileLength);
		this.tile.setY(row*tileLength);
		this.tile.setFill(Color.DARKGRAY);
		
		this.tileType = TileType.TETRIS;
	}
	
	/**
	 * @return The length (in pixels) of this tile.
	 */
	public int getLength() {
		return this.tileLength;
	}
	
	/**
	 * @return The row number of the game mesh this Tile 
	 * is currently in.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return The column number of the game mesh this Tile is
	 * currently in.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Changes this Tile's row position on the game mesh and
	 * its corresponding position on the screen.
	 * @param row The row number of the game mesh.
	 */
	public void setRow(int row) {
		this.row = row;
		this.tile.setY(row * this.tileLength);
	}
	
	/**
	 * Changes this Tile's column position on the game mesh and
	 * its corresponding position on the screen.
	 * @param column The column number of the game mesh.
	 */
	public void setColumn(int column) {
		this.column = column;
		this.tile.setX(column * this.tileLength);
	}
	
	/**
	 * Changes this Tile's row and column positions on the game mesh
	 * and its corresponding position on the screen.
	 * @param row The row number of the game mesh.
	 * @param column The column number of the game mesh.
	 */
	public void setCell(int row, int column) {
		setRow(row);
		setColumn(column);
	}
	
	/**
	 * Set this Tile's color fill.
	 * @param color This Tile's new color fill.
	 */
	public void setColorFill(Color color) {
		this.tile.setFill(color);
	}
	
	/**
	 * @return This tile's javafx Rectangle object.
	 */
	public Rectangle getRectangle() {
		return this.tile;
	}
	
	/**
	 * @return This tile's color.
	 */
	public Color getColor() {
		return (Color) this.tile.getFill();
	}
	
}
