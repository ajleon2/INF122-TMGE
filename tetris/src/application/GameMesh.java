package application;

import java.util.Arrays;

import javafx.scene.paint.Color;

/**
 * Manages the positions of tiles on the game mesh.
 * @author Andrew Leon
 *
 */
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
	
	/**
	 * Creates a new game mesh.
	 * @param numRows The number of rows in the mesh.
	 * @param numColumns The number of columns in the mesh.
	 */
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
	 * @param row The row number.
	 * @param column The column number.
	 * @return The tile at the given (row, column) cell; null if there
	 * is no tile there.
	 * @throws IndexOutOfBoundsException Thrown if the given (row, column) cell is out
	 * of this game mesh's bounds.
	 */
	public Tile getTile(int row, int column) throws IndexOutOfBoundsException {
		if (!isInBounds(row, column))
			throw new IndexOutOfBoundsException("Given row & column are not on the game mesh.");
		return mesh[row][column];
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
	
	/**
	 * Clears all matching tiles on the game mesh, then returns the number
	 * of rows cleared.
	 * @return Number of rows cleared.
	 */
	public int clearMatchingTiles() {
		int numRowsDeleted = 0;
		boolean[] rowsToDelete = getCompletedRows();
	
		if (contains(rowsToDelete, true)) {
			int topmostDeletedRow = -1;
			
			// Delete marked tiles (from bottom to the top)
			for (int r = rowsToDelete.length - 1; r >= 0; r--) {
				if (rowsToDelete[r] == true) {
					topmostDeletedRow = r;
					for (int c = 0; c < this.numColumns; c++)
						mesh[r][c] = null;
					numRowsDeleted += 1;
				}
			}
			
			// Shift all tiles above the highest deleted row down by the
			// number of rows that were deleted.
			for (int r = topmostDeletedRow - 1; r >= 0; r--) {
				for (int c = 0; c < this.numColumns; c++) {
					if (mesh[r][c] instanceof Tile) {
						Color tileColor = mesh[r][c].getColor();
						int tileLength = mesh[r][c].getLength();
						int newTileRow = r + numRowsDeleted;
						
						mesh[r][c] = null;
						mesh[newTileRow][c] = new Tile(tileColor, tileLength, newTileRow, c);
					}
				}
			}
		}
		
		return numRowsDeleted;
	}
	
	/**
	 * @return An array of boolean values whose indexes represent
	 * rows in this game mesh. Rows that are complete are marked as
	 * True; rows that aren't complete are marked as False.
	 */
	private boolean[] getCompletedRows() {
		boolean[] rowsToDelete = new boolean[this.numRows];
		Arrays.fill(rowsToDelete, false);
		
		for (int r = this.numRows - 1; r >= 0; r--) {
			for (int c = 0; c < this.numColumns; c++) {
				if (mesh[r][c] == null)
					break;
				
				if (c == this.numColumns - 1)
					rowsToDelete[r] = true;
			}
		}
		
		return rowsToDelete;
	}
	
	/**
	 * @param array An array of boolean values.
	 * @param value The value to match against.
	 * @return True if "array" contains "value". False if otherwise.
	 */
	private boolean contains(boolean[] array, boolean value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value)
				return true;
		}
		return false;
	}
}
