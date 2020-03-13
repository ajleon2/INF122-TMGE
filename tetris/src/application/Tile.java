package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {

	private Rectangle tile;
	private int row;
	private int column;
	private int tileLength;
	
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
	 * Set this Tile's color fill.
	 * @param color This Tile's new color fill.
	 */
	public void setColorFill(Color color) {
		this.tile.setFill(color);
	}
	
	public Rectangle getRectangle() {
		return this.tile;
	}
	
}
