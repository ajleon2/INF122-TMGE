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
//	public static class TetrisBlockCells {
//		
//		public Cell tileA;
//		public Cell tileB;
//		public Cell tileC;
//		public Cell tileD;
//		
//		public TetrisBlockCells(Cell tileA, Cell tileB, Cell tileC, Cell tileD) {
//			this.tileA = tileA;
//			this.tileB = tileB;
//			this.tileC = tileC;
//			this.tileD = tileD;
//		}
//	}
	
	/**
	 * Holds the cell data (row & column) of a single Tetris block
	 * tile.
	 *
	 */
//	public static class Cell {
//		public int row;
//		public int column;
//		
//		public Cell(int row, int col) {
//			this.row = row;
//			this.column = col;
//		}
//	}
	
	/**
	 * Get the cell data (row and column) of each Tetris block
	 * tile. 
	 * 
	 * @param tileLength The length of a tile (in pixels).
	 * @return The cell data (row and column) of each Tetris block
	 * tile.
	 */
//	public TetrisBlockCells getCells(int tileLength) {
//		Cell tileA = new Cell((int) this.tileA.getY()/tileLength, (int) this.tileA.getX()/tileLength);
//		Cell tileB = new Cell((int) this.tileB.getY()/tileLength, (int) this.tileB.getX()/tileLength);
//		Cell tileC = new Cell((int) this.tileC.getY()/tileLength, (int) this.tileC.getX()/tileLength);
//		Cell tileD = new Cell((int) this.tileD.getY()/tileLength, (int) this.tileD.getX()/tileLength);
//		
//		return new TetrisBlockCells(tileA, tileB, tileC, tileD);
//	}
	
	
	
	// The 4 tiles that make up a Tetris block.
	// *See documentation for mappings for each
	//  Tetris block type*
	private Tile tileA;
	private Tile tileB;
	private Tile tileC;
	private Tile tileD;
	
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
	private int orientation = 1;
	
	/**
	 * Create a Tetris block. See documentation for which tiles corresponding
	 * to which portion of each Tetris block type.
	 * @param tileA Tile A.
	 * @param tileB Tile B.
	 * @param tileC Tile C
	 * @param tileD Tile D.
	 * @param blockType The type of Tetris block (e.g. L shape).
	 */
	public TetrisBlock(Tile tileA, Tile tileB, Tile tileC, Tile tileD, 
			           BlockType blockType) {
		this.tileA = tileA;
		this.tileB = tileB;
		this.tileC = tileC;
		this.tileD = tileD;
		
		this.blockType = blockType;
		
		Color tileColor = blockTypeToColor(blockType);
		this.tileA.setColorFill(tileColor);
		this.tileB.setColorFill(tileColor);
		this.tileC.setColorFill(tileColor);
		this.tileD.setColorFill(tileColor);
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
	 * The sign determines the direction (positive = shift right; negative = shift left). In units
	 * of tiles (e.g. 1 = shift right 1 tile; -2 = shift left 2 tiles).
	 */
	public void shiftHorizontal(int horizontalChange) {
		tileA.setColumn(tileA.getColumn() + horizontalChange);
		tileB.setColumn(tileB.getColumn() + horizontalChange);
		tileC.setColumn(tileC.getColumn() + horizontalChange);
		tileD.setColumn(tileD.getColumn() + horizontalChange);
	}
	
	/**
	 * @param verticalChange The magnitude determines the number of pixels to shift this TetrisBlock.
	 * The sign determines the direction (positive = shift down; negative = shift up). In units of
	 * tiles (e.g. 1 = shift down 1 tile; -2 = shift up 2 tiles).
	 */
	public void shiftVertical(int verticalChange) {
		tileA.setRow(tileA.getRow() + verticalChange);
		tileB.setRow(tileB.getRow() + verticalChange);
		tileC.setRow(tileC.getRow() + verticalChange);
		tileD.setRow(tileD.getRow() + verticalChange);
	}
	
	
	/**
	 * @return The block type of this Tetris block.
	 */
	public BlockType getBlockType() {
		return this.blockType;
	}
	
	public Tile getTileA() {
		return this.tileA;
	}
	
	public Tile getTileB() {
		return this.tileB;
	}
	
	public Tile getTileC() {
		return this.tileC;
	}
	
	public Tile getTileD() {
		return this.tileD;
	}
	
	/**
	 * @return True if any of this Tetris block's tiles are at the
	 * top of the screen, False if otherwise.
	 */
	public boolean isAtTopOfScreen() {
		return (tileA.getRow() == 0 || tileB.getRow() == 0 
				|| tileC.getRow() == 0 || tileD.getRow() == 0);
	}	
}
