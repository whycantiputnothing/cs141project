import java.io.Serializable;
import java.util.*;

public class Grid implements Serializable{

	private static final long serialVersionUID = 4899020493699487965L;
	private BoardPiece[][] grid = new BoardPiece[9][9];
	
	/**
	 The instantiateGrid will place all of the board pieces to their appropriate 
	 positions for any new game. This method will instantiate, then place all of the 
	 Ninja and Power up board pieces to random locations in the grid, while placing 
	 the Spy and Room board pieces to their correct location.  Any Ninjas that are 
	 too close to the spy will also be moved further away from the Spy's location. 
	 */
	public void instantiateGrid() {

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new BoardPiece(" ");
			}
		}
		grid[6][6] = new PowerUps("B");
		grid[6][7] = new PowerUps("I");
		grid[6][8] = new PowerUps("R");
		grid[7][0] = new Ninja();
		grid[7][1] = new Ninja();
		grid[7][2] = new Ninja();
		grid[7][3] = new Ninja();
		grid[7][4] = new Ninja();
		grid[7][5] = new Ninja();
		grid[7][6] = new Room(true);
		grid[7][7] = new Room(false);
		grid[7][8] = new Room(false);
		grid[8][0] = new Spy();
		grid[8][1] = new Room(false);
		grid[8][2] = new Room(false);
		grid[8][3] = new Room(false);
		grid[8][4] = new Room(false);
		grid[8][5] = new Room(false);
		grid[8][6] = new Room(false);
		
		shufflePieces();
		placeSpyAtStart();
		placeRooms();
		checkNinjaPosition();
	}
	/**
	 The placeRooms method will place all of the Room board pieces
	 to their correct locations on the grid. This method primarily uses 
	 Int Array Lists to accomplish this, because we can change the length 
	 of an Array List after it has been instantiated. We have two array lists 
	 for the rows and the columns the Rooms were randomly shuffled to, and 
	 another two array lists for the rows and the columns of the correct
	 location for the rooms. If any of the shuffled Rooms happen to also be in a
	 correct location, the row and column value of that Room will be deleted 
	 from all Array Lists. 
	 */
	public void placeRooms() {
		int count = 0;
		List<Integer> rows = new ArrayList<Integer>();
		List<Integer> cols = new ArrayList<Integer>();
		List<Integer> roomPositionRows = new ArrayList<Integer>();
		List<Integer> roomPositionCols = new ArrayList<Integer>();
		
		int[] roomPostitionRows1 = { 1, 1, 1, 4, 4, 4, 7, 7, 7 };
		while (count < 9) {
			roomPositionRows.add(roomPostitionRows1[count]);
			count++;
		}
		
		count = 0;
		int[] roomPostitionCols1 = { 1, 4, 7, 1, 4, 7, 1, 4, 7 };
		while (count < 9) {
			roomPositionCols.add(roomPostitionCols1[count]);
			count++;
		}
	
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getPieceType().equals("U") || grid[i][j].getPieceType().equals("X")) {
					rows.add(i);
					cols.add(j);
				
				}
			}
		}
	
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(j < rows.size() && i < rows.size()){					
					int a = rows.get(i);
					int b = cols.get(i);
					int c = roomPositionRows.get(j);
					int d = roomPositionCols.get(j);
					if (a == c && b == d) {
						rows.remove(i);
						cols.remove(i);
						roomPositionRows.remove(j);
						roomPositionCols.remove(j);
					}
				}
			}
		}
	
		for (int i = 0; i < rows.size(); i++) {
			BoardPiece x = grid[roomPositionRows.get(i)][roomPositionCols.get(i)];
			BoardPiece z = grid[rows.get(i)][cols.get(i)];
			grid[rows.get(i)][cols.get(i)] = x;
			grid[roomPositionRows.get(i)][roomPositionCols.get(i)] = z;
		}
	}
	
	/**
	 The shufflePieces method takes the instantiated 2D array, grid, and places
	 all of its board pieces in an Array List. This is so collections.shuffle 
	 can be used to shuffle all of the board pieces. Once the board pieces have 
	 been shuffled, the pieces will be reassigned to their new positions, back
	 to the grid, 2D array. 
	 */
	public void shufflePieces() {
		List<BoardPiece> somePieces = new ArrayList<BoardPiece>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				somePieces.add(grid[i][j]);
			}
		}
	
		Collections.shuffle(somePieces);
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (count < somePieces.size()) {
					grid[i][j] = somePieces.get(count);
					count++;
				}
			}
		}
	}
	
	/**
	 The placeSpyAtStart method finds the position on the grid where the Spy 
	 board piece is located, and swaps the Spy board piece with whatever 
	 board piece is located in the lower left hand corner of the grid. 
	 */
	public void placeSpyAtStart() {
		BoardPiece a = grid[8][0];
		int c = findSpy()[0];
		int d = findSpy()[1];
		BoardPiece b = grid[c][d];
		grid[8][0] = b;
		grid[c][d] = a;
	}
	
	/**
	 The findSpy method returns the position the Spy board piece is at,
	 as the int array, spyAt. The first position of spyAt, or spyAt[0] 
	 will be the row value of the position of the spy. The second position,
	 spyAt[1], equals the column value of the position of the spy. 
	 @return an int array spyAt, with findSpy[0] = row value for spy, 
	 	and findSpy[1] = column value for spy.
	 */
	public int[] findSpy() {
		int[] spyAt = { 0, 0 };
	
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getPieceType().equals("S")) {
					spyAt[0] = i;
					spyAt[1] = j;
				}
			}
		}
		return spyAt;
	}

	public int[] findBriefCase() {
		int[] briefcaseAt = { 0, 0 };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if ((getBoardPieceAt(i, j).getPieceType().equals("U")||getBoardPieceAt(i, j).getPieceType().equals("X"))) {
					if(((Room)(getBoardPieceAt(i,j))).getHasBriefcase()){
						briefcaseAt[0] = i;
						briefcaseAt[1] = j;
					}
				}
			}
		}
		return briefcaseAt;
	}

	public void checkNinjaPosition() {
		for (int i = 5; i < grid.length; i++) {
			for (int j = 0; j < (i - 4); j++) {
				if (grid[i][j].getPieceType().equals("N")) {
					for (int k = j; k < grid.length; k++) {
						if (grid[i][k].getPieceType().equals(" ")) {
							BoardPiece a = grid[i][j];
							BoardPiece b = grid[i][k];
							grid[i][j] = b;
							grid[i][k] = a;
						}
					}
				}
			}
		}
	}

	public List<Integer> findNinja(){
		List<Integer> ninjaPos = new ArrayList<Integer>();
	
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j <grid[i].length; j++){
				if (grid[i][j].getPieceType().equals("N")){
					ninjaPos.add(i);
					ninjaPos.add(j);
					
				}
			}
		}
		return ninjaPos;	
	}
	
	/**
	 This object, is an Int Array List with all of the row and column values
	 of all of the power ups.
	 @return Int Array List of row and column values, respectively, for all 
	 	powerups by order of closest to the top of the grid, to the furthest.
	 */
	public List<Integer> findPowerUp() {
		List<Integer> powerUpAt = new ArrayList<Integer>();
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getClass().getName().equals("PowerUps")) {
					powerUpAt.add(i);
					powerUpAt.add(j);
				}
			}
		}
		
		return powerUpAt;
		
	}
	
	/**
	 This method is the getter for any BoardPiece on the board, given the location
	 of the board piece. It will return the BoardPiece type.
	 @param x is the int value for the row the board piece is in
	 @param y is the int value for the column the board piece is in
	 @return the board piece at the location, grid[x][y]
	 */
	public BoardPiece getBoardPieceAt(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 This method is the setter for any BoardPiece on the grid, given the location
	 of the board piece. 
	 @param x is the int value for the row the board piece is in
	 @param y is the int value for the column the board piece is in
	 @param a is the BoardPiece value located on the grid at grid[x][y]
	 */
	public void setBoardPieceAt(int x, int y, BoardPiece a) {
		grid[x][y] = a;

	}

	public String toString() {
		String printPosition = "";
		for (BoardPiece[] b : grid) {
			for (BoardPiece p : b) {
				if (p.getIsVisible()) {
					printPosition += "[" + p.getPieceType() + "]";
				} else
					printPosition += "[-]";
			}
			printPosition += "\n";
		}
		return printPosition;
	}
}
