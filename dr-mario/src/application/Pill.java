package application;

/**
 * A Pill is comprised of 2 tiles: TileA and TileB. Each 
 * Pill also has a type that denotes.
 * *See external documentation for mappings between each
 *  tile an their position on each Pill*
 * @author Andrew Leon
 *
 */
public class Pill {
	
	/**
	 * All Pills have the same shape, so they are the
	 * same BlockType.
	 */
	public static enum BlockType { // Rename to "ShapeType"
		PILL
	}
	
	
	// The 2 tiles that make up a Pill.
	// *See documentation for tile mappings to each
	//  Pill*
	private Tile tileA;
	private Tile tileB;
	
	/**
	 * The block type of this Pill. All Pills are of
	 * type "BlockType.PILL"
	 */
	private BlockType blockType;
	
	/**
	 * Specifies the orientation of a Pill. Units are in 
	 * degrees and direction is with respect to the default 
	 * orientation.
	 */
	public enum Orientation {
		DEFAULT,
		RIGHT90,
		RIGHT180,
		RIGHT270
	}
	
	/**
	 * The current orientation of this Pill.
	 */
	private Orientation orientation;
	
	/**
	 * Create a Pill of default orientation.
	 * @param tileA Tile A.
	 * @param tileB Tile B.
	 */
	public Pill(Tile tileA, Tile tileB) {
		this.tileA = tileA;
		this.tileB = tileB;
		
		this.blockType = BlockType.PILL;
		this.orientation = Orientation.DEFAULT;
	}
	
	/**
	 * Change orientation of this Pill to the next one.
	 * @param reverse If True, flips to the previous orientation.
	 * If False, flips to the next orientation.
	 */
	public void flip(boolean reverse) {		
		// Flip 3 times to get to the previous orientation
		final int NUMBER_OF_FLIPS = (reverse) ? 3 : 1;
		
		final int PIVOT_ROW = tileB.getRow();
		final int PIVOT_COL = tileB.getColumn();
		
		int tileANewRow = 0, tileANewCol = 0;
		
		for (int i = 0; i < NUMBER_OF_FLIPS; i++) {
			if (this.orientation == Orientation.DEFAULT) {
				tileANewRow = PIVOT_ROW - 1;
				tileANewCol = PIVOT_COL;
				tileA.setCompanionDirection(Tile.Direction.DOWN);
				tileB.setCompanionDirection(Tile.Direction.UP);
			}
			else if (this.orientation == Orientation.RIGHT90) {
				tileANewRow = PIVOT_ROW;
				tileANewCol = PIVOT_COL + 1;
				tileA.setCompanionDirection(Tile.Direction.LEFT);
				tileB.setCompanionDirection(Tile.Direction.RIGHT);
			}
			else if (this.orientation == Orientation.RIGHT180) {
				tileANewRow = PIVOT_ROW + 1;
				tileANewCol = PIVOT_COL;
				tileA.setCompanionDirection(Tile.Direction.UP);
				tileB.setCompanionDirection(Tile.Direction.DOWN);
			}
			else {
				tileANewRow = PIVOT_ROW;
				tileANewCol = PIVOT_COL - 1;
				tileA.setCompanionDirection(Tile.Direction.RIGHT);
				tileB.setCompanionDirection(Tile.Direction.LEFT);
			}
			this.orientation = getNextOrientation();
			tileA.setCell(tileANewRow, tileANewCol);
		}
	}
	
	/**
	 * @return This Pill's orientation after one flip.
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
	 * @param horizontalChange The magnitude determines the number of tiles to shift this Pill.
	 * The sign determines the direction (positive = shift right; negative = shift left).
	 * (e.g. 1 = shift right 1 tile; -2 = shift left 2 tiles).
	 */
	public void shiftHorizontal(int horizontalChange) {
		tileA.setColumn(tileA.getColumn() + horizontalChange);
		tileB.setColumn(tileB.getColumn() + horizontalChange);
	}
	
	/**
	 * @param verticalChange The magnitude determines the number of tiles to shift this Pill.
	 * The sign determines the direction (positive = shift down; negative = shift up).
	 * (e.g. 1 = shift down 1 tile; -2 = shift up 2 tiles).
	 */
	public void shiftVertical(int verticalChange) {
		tileA.setRow(tileA.getRow() + verticalChange);
		tileB.setRow(tileB.getRow() + verticalChange);
	}
	
	
	/**
	 * @return The block type of this Pill.
	 */
	public BlockType getBlockType() { // change to getShapeType
		return this.blockType;
	}
	
	public Tile getTileA() {
		return this.tileA;
	}
	
	public Tile getTileB() {
		return this.tileB;
	}
	
	/**
	 * @param row A row on the game mesh.
	 * @return True if any part of this Pill is on the 
	 * provided game mesh row; False if otherwise.
	 */
	public boolean isOnRow(int row) {
		return (tileA.getRow() == row || tileB.getRow() == row);
	}	
}
