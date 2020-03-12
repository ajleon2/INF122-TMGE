package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrisBlock {
	
	/**
	 * The 7 Tetris block types.
	 */
	public static enum BlockType {
		S, Z, L, J, O, I, T;
	}
	
	/**
	 * @param blockType The block type.
	 * @return The block type's associated color.
	 */
	private static Color blockTypeToColor(BlockType blockType) {
		switch(blockType) {
		case J:
			return Color.SLATEGRAY;
		case L:
			return Color.DARKGOLDENROD;
		case O:
			return Color.INDIANRED;
		case S:
			return Color.FORESTGREEN;
		case T:
			return Color.CADETBLUE;
		case Z:
			return Color.HOTPINK;
		case I:
			return Color.SANDYBROWN;
		default:
			return Color.DARKGRAY;
		}
	}
	
	/**
	 * Holds the cell data (row and column) of each Tetris block
	 * tile.
	 */
	public static class TetrisBlockCells {
		
		public Cell tileA;
		public Cell tileB;
		public Cell tileC;
		public Cell tileD;
		
		public TetrisBlockCells(Cell tileA, Cell tileB, Cell tileC, Cell tileD) {
			this.tileA = tileA;
			this.tileB = tileB;
			this.tileC = tileC;
			this.tileD = tileD;
		}
	}
	
	/**
	 * Holds the cell data (row & column) of a single Tetris block
	 * tile.
	 *
	 */
	public static class Cell {
		public int row;
		public int column;
		
		public Cell(int row, int col) {
			this.row = row;
			this.column = col;
		}
	}
	
	/**
	 * Get the cell data (row and column) of each Tetris block
	 * tile. 
	 * 
	 * @param tileLength The length of a tile (in pixels).
	 * @return The cell data (row and column) of each Tetris block
	 * tile.
	 */
	public TetrisBlockCells getCells(int tileLength) {
		Cell tileA = new Cell((int) this.tileA.getY()/tileLength, (int) this.tileA.getX()/tileLength);
		Cell tileB = new Cell((int) this.tileB.getY()/tileLength, (int) this.tileB.getX()/tileLength);
		Cell tileC = new Cell((int) this.tileC.getY()/tileLength, (int) this.tileC.getX()/tileLength);
		Cell tileD = new Cell((int) this.tileD.getY()/tileLength, (int) this.tileD.getX()/tileLength);
		
		return new TetrisBlockCells(tileA, tileB, tileC, tileD);
	}
	
	
	
	// The 4 tiles that make up a Tetris block.
	// *See documentation for mappings for each
	//  Tetris block type*
	private Rectangle tileA;
	private Rectangle tileB;
	private Rectangle tileC;
	private Rectangle tileD;
	
	/**
	 * The block type of this Tetris block
	 */
	private BlockType blockType;
	
	/**
	 * Ranges from 1-4 for the 4 orientations the Tetris
	 * block can be in.
	 * - 1 = initial position
	 * - 2 = rotated 90 degrees to the right from 1
	 * - 3 = rotated 90 degrees to the right from 2
	 * - 4 = rotated 90 degrees to the right from 3
	 */
	public int orientation = 1;
	
	/**
	 * 
	 * @param tileA The top tile.
	 * @param tileB The right tile.
	 * @param tileC The bottom tile.
	 * @param tileD The left tile.
	 * @param blockType The type of Tetris block (e.g. L shape).
	 */
	public TetrisBlock(Rectangle tileA, Rectangle tileB, Rectangle tileC, Rectangle tileD, 
			           BlockType blockType) {
		this.tileA = tileA;
		this.tileB = tileB;
		this.tileC = tileC;
		this.tileD = tileD;
		
		this.blockType = blockType;
		
		Color blockColor = blockTypeToColor(blockType);
		this.tileA.setFill(blockColor);
		this.tileB.setFill(blockColor);
		this.tileC.setFill(blockColor);
		this.tileD.setFill(blockColor);
	}
	
	/**
	 * Change orientation of this Tetris block to the next one.
	 */
	public void flip() {
		if (this.orientation != 4)
			this.orientation++;
		else
			this.orientation = 1;
	}
	
	/**
	 * @param horizontalChange The magnitude determines the number of pixels to shift this TetrisBlock.
	 * The sign determines the direction (positive = shift right; negative = shift left).
	 */
	public void shiftHorizontal(int horizontalChange) {
		tileA.setX(tileA.getX() + horizontalChange);
		tileB.setX(tileB.getX() + horizontalChange);
		tileC.setX(tileC.getX() + horizontalChange);
		tileD.setX(tileD.getX() + horizontalChange);
	}
	
	/**
	 * @param verticalChange The magnitude determines the number of pixels to shift this TetrisBlock.
	 * The sign determines the direction (positive = shift down; negative = shift up).
	 */
	public void shiftVertical(int verticalChange) {
		tileA.setY(tileA.getY() + verticalChange);
		tileB.setY(tileB.getY() + verticalChange);
		tileC.setY(tileC.getY() + verticalChange);
		tileD.setY(tileD.getY() + verticalChange);
	}
	
	
	/**
	 * 
	 * @return The block type of this Tetris block.
	 */
	public BlockType getBlockType() {
		return this.blockType;
	}
	
	public Rectangle getTileA() {
		return this.tileA;
	}
	
	public Rectangle getTileB() {
		return this.tileB;
	}
	
	public Rectangle getTileC() {
		return this.tileC;
	}
	
	public Rectangle getTileD() {
		return this.tileD;
	}
	
	/**
	 * @return True if any of this Tetris block's tiles are at the
	 * top of the screen, False if otherwise.
	 */
	public boolean isAtTopOfScreen() {
		return (tileA.getY() == 0.0 || tileB.getY() == 0.0 
				|| tileC.getY() == 0.0 || tileD.getY() == 0.0);
	}	
}
