package application;

import javafx.scene.paint.Color;

/**
 * A Tetris block is comprised of 4 tiles: TileA, TileB,
 * TileC, & TileD. Each block also has a type that denotes
 * its shape (e.g. L type, O type, I type, etc.).
 * *See external documentation for mappings between each
 *  tile an their position on each Tetris block type*
 * @author Andrew Leon
 *
 */
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
	
	
	// The 4 tiles that make up a Tetris block.
	// *See documentation for tile mappings to each
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
	 * Specifies the orientation of a Tetris
	 * block. Units are in degrees and direction is with
	 * respect to the default orientation.
	 */
	public enum Orientation {
		DEFAULT,
		RIGHT90,
		RIGHT180,
		RIGHT270
	}
	
	/**
	 * The current orientation of this Tetris block.
	 */
	private Orientation orientation;
	
	/**
	 * Create a Tetris block. 
	 * *See documentation for which tiles corresponding
	 * to which portion of each Tetris block type.*
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
		this.orientation = Orientation.DEFAULT;
		
		Color tileColor = blockTypeToColor(blockType);
		this.tileA.setColorFill(tileColor);
		this.tileB.setColorFill(tileColor);
		this.tileC.setColorFill(tileColor);
		this.tileD.setColorFill(tileColor);
	}
	
	/**
	 * Change orientation of this Tetris block to the next one.
	 * @param reverse If True, flips to the previous orientation.
	 * If False, flips to the next orientation.
	 */
	public void flip(boolean reverse) {		
		// Flip 3 times to get to the previous orientation
		final int NUMBER_OF_FLIPS = (reverse) ? 3 : 1;
		
		for (int i = 0; i < NUMBER_OF_FLIPS; i++) {
			switch(this.blockType) {
			case I:
				flipI();
				break;
			case O:
				break;
			case T:
				flipT();
				break;
			case J:
				flipJ();
				break;
			case L:
				flipL();
				break;
			case S:
				flipS();
				break;
			case Z:
				flipZ();
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Flips a Z Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipZ() {
		int tileANewRow, tileBNewRow, tileDNewRow;
		int tileANewCol, tileBNewCol, tileDNewCol;
		
		final int PIVOT_ROW = tileC.getRow();
		final int PIVOT_COL = tileC.getColumn();
		
		if (this.orientation == Orientation.DEFAULT) {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL + 1;
			
			tileBNewRow = PIVOT_ROW;
			tileBNewCol = PIVOT_COL + 1;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL;
		}
		
		else if (this.orientation == Orientation.RIGHT90) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL + 1;
			
			tileBNewRow = PIVOT_ROW + 1;
			tileBNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else if (this.orientation == Orientation.RIGHT180) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL - 1;
			
			tileBNewRow = PIVOT_ROW;
			tileBNewCol = PIVOT_COL - 1;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL;
		}
		
		else {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL - 1;
			
			tileBNewRow = PIVOT_ROW - 1;
			tileBNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileB.setCell(tileBNewRow, tileBNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * Flips a S Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipS() {
		int tileANewRow, tileCNewRow, tileDNewRow;
		int tileANewCol, tileCNewCol, tileDNewCol;
		
		final int PIVOT_ROW = tileB.getRow();
		final int PIVOT_COL = tileB.getColumn();
		
		if (this.orientation == Orientation.DEFAULT) {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL + 1;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		else if (this.orientation == Orientation.RIGHT90) {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL + 1;
			
			tileCNewRow = PIVOT_ROW + 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else if (this.orientation == Orientation.RIGHT180) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL - 1;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL - 1;
			
			tileCNewRow = PIVOT_ROW - 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileC.setCell(tileCNewRow, tileCNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * Flips a L Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipL() {
		int tileANewRow, tileCNewRow, tileDNewRow;
		int tileANewCol, tileCNewCol, tileDNewCol;
		
		final int PIVOT_ROW = tileB.getRow();
		final int PIVOT_COL = tileB.getColumn();
		
		if (this.orientation == Orientation.DEFAULT) {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW + 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		else if (this.orientation == Orientation.RIGHT90) {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL + 1;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL - 1;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else if (this.orientation == Orientation.RIGHT180) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW - 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL - 1;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL + 1;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileC.setCell(tileCNewRow, tileCNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * Flips a J Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipJ() {
		int tileANewRow, tileBNewRow, tileDNewRow;
		int tileANewCol, tileBNewCol, tileDNewCol;
		
		final int PIVOT_ROW = tileC.getRow();
		final int PIVOT_COL = tileC.getColumn();
		
		if (this.orientation == Orientation.DEFAULT) {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL + 1;
			
			tileBNewRow = PIVOT_ROW - 1;
			tileBNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL;
		}
		
		else if (this.orientation == Orientation.RIGHT90) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL + 1;
			
			tileBNewRow = PIVOT_ROW;
			tileBNewCol = PIVOT_COL + 1;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL - 1;
		}
		
		else if (this.orientation == Orientation.RIGHT180) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL - 1;
			
			tileBNewRow = PIVOT_ROW + 1;
			tileBNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL;
		}
		
		else {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL - 1;
			
			tileBNewRow = PIVOT_ROW;
			tileBNewCol = PIVOT_COL - 1;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL + 1;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileB.setCell(tileBNewRow, tileBNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * Flips a T Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipT() {
		int tileANewRow, tileCNewRow, tileDNewRow;
		int tileANewCol, tileCNewCol, tileDNewCol;
		
		final int PIVOT_ROW = this.tileB.getRow();
		final int PIVOT_COL = this.tileB.getColumn();

		if (this.orientation == Orientation.DEFAULT) {
			tileANewRow = PIVOT_ROW - 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW + 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL - 1;
		}
		else if (this.orientation == Orientation.RIGHT90) {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL + 1;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL - 1;
			
			tileDNewRow = PIVOT_ROW - 1;
			tileDNewCol = PIVOT_COL;
		}
		else if (this.orientation == Orientation.RIGHT180) {
			tileANewRow = PIVOT_ROW + 1;
			tileANewCol = PIVOT_COL;
			
			tileCNewRow = PIVOT_ROW - 1;
			tileCNewCol = PIVOT_COL;
			
			tileDNewRow = PIVOT_ROW;
			tileDNewCol = PIVOT_COL + 1;
		}
		else {
			tileANewRow = PIVOT_ROW;
			tileANewCol = PIVOT_COL - 1;
			
			tileCNewRow = PIVOT_ROW;
			tileCNewCol = PIVOT_COL + 1;
			
			tileDNewRow = PIVOT_ROW + 1;
			tileDNewCol = PIVOT_COL;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileC.setCell(tileCNewRow, tileCNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * Flips an I Tetris block to the next orientation. Also
	 * changes this.orientation.
	 */
	private void flipI() {
		// Currently, only cycles between 2 positions
		int tileANewRow, tileBNewRow, tileCNewRow, tileDNewRow;
		int tileANewCol, tileBNewCol, tileCNewCol, tileDNewCol;
		if (this.orientation == Orientation.DEFAULT || this.orientation == Orientation.RIGHT180) {
			tileANewRow = this.tileA.getRow() - 2;
			tileANewCol = this.tileA.getColumn() + 2;
			
			tileBNewRow = this.tileB.getRow() - 1;
			tileBNewCol = this.tileB.getColumn() + 1;
			
			tileCNewRow = this.tileC.getRow();
			tileCNewCol = this.tileC.getColumn();
			
			tileDNewRow = this.tileD.getRow() + 1;
			tileDNewCol = this.tileD.getColumn() - 1;
		}
		
		else {
			tileANewRow = this.tileA.getRow() + 2;
			tileANewCol = this.tileA.getColumn() - 2;
			
			tileBNewRow = this.tileB.getRow() + 1;
			tileBNewCol = this.tileB.getColumn() - 1;
			
			tileCNewRow = this.tileC.getRow();
			tileCNewCol = this.tileC.getColumn();
			
			tileDNewRow = this.tileD.getRow() - 1;
			tileDNewCol = this.tileD.getColumn() + 1;
		}
		
		tileA.setCell(tileANewRow, tileANewCol);
		tileB.setCell(tileBNewRow, tileBNewCol);
		tileC.setCell(tileCNewRow, tileCNewCol);
		tileD.setCell(tileDNewRow, tileDNewCol);
		this.orientation = getNextOrientation();
	}
	
	/**
	 * @return This Tetris block's orientation after flipping once.
	 */
	private Orientation getNextOrientation() {
		if (this.orientation == Orientation.DEFAULT)
			return Orientation.RIGHT90;
		else if (this.orientation == Orientation.RIGHT90)
			return Orientation.RIGHT180;
		else if (this.orientation == Orientation.RIGHT180)
			return Orientation.RIGHT270;
		else
			return Orientation.DEFAULT;
	}

	
	/**
	 * @param horizontalChange The magnitude determines the number of tiles to shift this TetrisBlock.
	 * The sign determines the direction (positive = shift right; negative = shift left).
	 * (e.g. 1 = shift right 1 tile; -2 = shift left 2 tiles).
	 */
	public void shiftHorizontal(int horizontalChange) {
		tileA.setColumn(tileA.getColumn() + horizontalChange);
		tileB.setColumn(tileB.getColumn() + horizontalChange);
		tileC.setColumn(tileC.getColumn() + horizontalChange);
		tileD.setColumn(tileD.getColumn() + horizontalChange);
	}
	
	/**
	 * @param verticalChange The magnitude determines the number of tiles to shift this TetrisBlock.
	 * The sign determines the direction (positive = shift down; negative = shift up).
	 * (e.g. 1 = shift down 1 tile; -2 = shift up 2 tiles).
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
