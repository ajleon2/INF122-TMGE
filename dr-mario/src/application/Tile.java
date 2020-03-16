package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


/**
 * A Tile is the smallest unit in a tile matching game.
 * Pills are comprised of 2 tiles. Viruses are 1 Tile. Tiles manage
 * their own position on the screen based on their position
 * on the game mesh. Pill tiles know in which direction their "companion"
 * pill tile is and whether they still have a companion tile.
 * @author Andrew Leon
 *
 */
public class Tile {
	
	/**
	 * Some tile matching games have different types of tiles w/
	 * different properties. A Dr. Mario Tile is either half a 
	 * pill or a virus with 1 of 3 different colors.
	 * @author Andrew Leon
	 *
	 */
	public enum TileType {
		RED_PILL, BLUE_PILL, YELLOW_PILL,
		RED_VIRUS, BLUE_VIRUS, YELLOW_VIRUS
	}
	/**
	 * 1 of 4 cardinal directions. NONE represents
	 * no direction.
	 * @author Andrew Leon
	 *
	 */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT, NONE
	}
	
	/**
	 * A javafx rectangle is used to display this tile to the screen.
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
	 * Some tile matching games use different tiles
	 * with different properties. In Dr. Mario, this member determines
	 * whether the tile is a pill tile or a virus.
	 */
	private TileType tileType;
	/**
	 * Each pill tile knows which direction its second half
	 * is at. If its second half doesn't exits (either b/c this tile
	 * is a virus or its second half was destroyed), this member will 
	 * be Direction.NONE.
	 */
	private Direction companionDirection;
	
	/**
	 * Create a new Tile object with the provided color, tile type, and companion
	 * direction. Tiles are squares. Assumes each cell on the game 
	 * board is equivalent to 1 tile.
	 * @param color The tile's color fill.
	 * @param tileType Whether this tile is a pill tile or a virus.
	 * @param companionDirection The direction of a pill tile's second half.
	 * Set to "Direction.NONE" if this is a virus tile or the pill tile's
	 * second half was destroyed.
	 * @param tileLength Length of the Tile (in pixels).
	 * @param row The row on the game mesh this tile will
	 * reside in. Assumes 0 indexing.
	 * @param column The column on the game mesh this tile
	 * will reside in. Assumes 0 indexing.
	 */
	public Tile(Color color, TileType tileType, Direction companionDirection, int tileLength, 
			    int row, int column) {
		this.tileType = tileType;
		this.companionDirection = companionDirection;
		this.row = row;
		this.column = column;
		this.tileLength = tileLength;
		
		this.tile = new Rectangle(tileLength, tileLength);
		this.tile.setX(column*tileLength);
		this.tile.setY(row*tileLength);
		this.tile.setFill(color);
	}
	
	/**
	 * Create a new Tile object with the provided image and tile type. 
	 * Tiles are squares. Assumes each cell on the game board is equivalent 
	 * to 1 tile.
	 * @param image The tile's image fill.
	 * @param tileType Whether this tile is a pill tile or a virus.
	 * @param companionDirection The direction of a pill tile's second half.
	 * Set to "Direction.NONE" if this is a virus tile or the pill tile's
	 * second half was destroyed.
	 * @param tileLength Length of the Tile (in pixels).
	 * @param row The row on the game mesh this tile will
	 * reside in. Assumes 0 indexing.
	 * @param column The column on the game mesh this tile
	 * will reside in. Assumes 0 indexing.
	 */
	public Tile(ImagePattern image, TileType tileType, Direction companionDirection, int tileLength, 
			    int row, int column) {
		this.tileType = tileType;
		this.companionDirection = companionDirection;
		this.row = row;
		this.column = column;
		this.tileLength = tileLength;
		
		this.tile = new Rectangle(tileLength, tileLength);
		this.tile.setX(column*tileLength);
		this.tile.setY(row*tileLength);
		this.tile.setFill(image);
	}
	
	/**
	 * @return True if this tile has a companion; False if otherwise.
	 */
	public boolean hasCompanion() {
		return (this.companionDirection != Direction.NONE);
	}
	
	/**
	 * @return The row of this tile's companion tile.
	 * @throws IllegalStateException Thrown if this tile doesn't have a companion tile.
	 */
	public int getCompanionRow() throws IllegalStateException {
		if (this.companionDirection == Direction.NONE)
			throw new IllegalStateException("This tile doesn't have a companion pill tile.");
		
		if (this.companionDirection == Direction.UP)
			return this.row - 1;
		else if (this.companionDirection == Direction.DOWN)
			return this.row + 1;
		else
			return this.row;
	}
	
	/**
	 * @return The column of this tile's companion tile.
	 * @throws IllegalStateException Thrown if this tile doesn't have a companion tile.
	 */
	public int getCompanionColumn() throws IllegalStateException {
		if (this.companionDirection == Direction.NONE)
			throw new IllegalStateException("This tile doesn't have a companion pill tile.");
		
		if (this.companionDirection == Direction.RIGHT)
			return this.column + 1;
		else if (this.companionDirection == Direction.LEFT)
			return this.column - 1;
		else
			return this.column;
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
	 * Set this Tile's color fill. Replaces any current fill.
	 * @param color This Tile's new color fill.
	 */
	public void setColorFill(Color color) {
		this.tile.setFill(color);
	}
	
	/**
	 * Sets tjos Tile's image fill. Replaces any current fill.
	 * @param image This Tile's new image fill.
	 */
	public void setImageFill(ImagePattern image) {
		this.tile.setFill(image);
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
	
	/**
	 * @return This tile's TileType (i.e. whether it's
	 * a pill or a virus).
	 */
	public TileType getTileType() {
		return this.tileType;
	}
	
	/**
	 * @return The direction of the other half of a pill.
	 * Returns Direction.NONE if the other pill half is 
	 * gone or if this tile is a virus.
	 */
	public Direction getCompanionDirection() {
		return this.companionDirection;
	}
	
	/**
	 * @param companionDirection The direction of this tile's companion
	 * tile.
	 */
	public void setCompanionDirection(Direction companionDirection) {
		this.companionDirection = companionDirection;
	}
	
	/**
	 * @return True if this tile is a virus; False if otherwise.
	 */
	public boolean isVirus() {
		return (tileType == TileType.BLUE_VIRUS || tileType == TileType.RED_VIRUS 
				|| tileType == TileType.YELLOW_VIRUS);
	}
	/**
	 * @return True if this tile is a pill; False if otherwise.
	 */
	public boolean isPill() {
		return (tileType == TileType.BLUE_PILL || tileType == TileType.RED_PILL 
				|| tileType == TileType.YELLOW_PILL);
	}
}
