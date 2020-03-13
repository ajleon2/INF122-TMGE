package application;

import javafx.scene.shape.Rectangle;

public class GameMesh {
	/**
	 * The number of rows in this mesh.
	 */
	private int numRows;
	/**
	 * The number of columns in this mesh.
	 */
	private int numColumns;
	/**
	 * Tracks each tile on the mesh.
	 */
	private Tile[][] mesh;
	
	public GameMesh(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		
		this.mesh = new Tile[numRows][numColumns];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++)
				this.mesh[row][col] = null;
		}
	}
	
	/**
	 * @param row The row.
	 * @param column The column.
	 * @return True if the given (row, column) cell of this game mesh is empty;
	 * False if otherwise.
	 * @throws IndexOutOfBoundsException Thrown if the given (row, column) cell is out
	 * of this game mesh's bounds.
	 */
	public boolean isEmpty(int row, int column) throws IndexOutOfBoundsException {
		if (!isInBounds(row, column))
			throw new IndexOutOfBoundsException("Given row & column are not on the game mesh.");
		
		return (mesh[row][column] == null);
	}
	
	/**
	 * 
	 * @return Number of rows in this game mesh.
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * 
	 * @return Number of columns in this game mesh.
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * Adds the given tile to the game mesh if the tile's (row, column)
	 * cell is within the mesh's bounds and if the space is currently
	 * unoccupied.
	 * 
	 * @param tile The tile.
	 * @throws IndexOutOfBoundsException Thrown if the tile's (row, column) cell is out
	 * of this game mesh's bounds.
	 */
	public void addTile(Tile tile) throws IndexOutOfBoundsException {
		final int ROW = tile.getRow();
		final int COLUMN = tile.getColumn();
		
		if (!isInBounds(ROW, COLUMN))
			throw new IndexOutOfBoundsException("Given row & column are not on the game mesh.");
		
		if (mesh[ROW][COLUMN] == null)
			mesh[ROW][COLUMN] = tile;
	}
	
	/**
	 * Removes any tile at the given (row, column) cell. Does nothing if the
	 * cell is currently empty.
	 * @param row The cell row number.
	 * @param column The cell column number.
	 * @throws IndexOutOfBoundsException Thrown if the given (row, column) cell is out
	 * of this game mesh's bounds.
	 */
	public void removeTile(int row, int column) throws IndexOutOfBoundsException {
		if (!isInBounds(row, column))
			throw new IndexOutOfBoundsException("Given row & column are not on the game mesh.");
		
		mesh[row][column] = null;
	}
	
	
	/**
	 * @param row The cell row number.
	 * @param column The cell column number.
	 * @return True if the given (row, column) cell is within this game mesh's
	 * bounds; False if otherwise.
	 */
	private boolean isInBounds(int row, int column) {
		return (row >= 0 && row < this.numRows && 
				column >=0 && column < this.numColumns);
	}
	
}
