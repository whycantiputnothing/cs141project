import java.util.*;

public class Grid {

	private BoardPiece[][] grid = new BoardPiece[9][9];

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

	}

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

	public void putSpyAtStart() {
		BoardPiece a = grid[8][0];
		int x = 0;
		int y = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getPieceType().equals("S")) {
					x = i;
					y = j;
				}
			}
		}
		BoardPiece b = grid[x][y];
		grid[8][0] = b;
		grid[x][y] = a;
	}

	public void placeRooms() {
		int count = 0;
		List<Integer> rows = new ArrayList<Integer>();
		List<Integer> cols = new ArrayList<Integer>();
		List<Integer> roomPositionRows = new ArrayList<Integer>();
		int[] roomPostitionRows1 = { 2, 2, 2, 4, 4, 4, 6, 6, 6 };
		while (count < 9) {
			roomPositionRows.add(roomPostitionRows1[count]);
			count++;
		}
		count = 0;
		List<Integer> roomPositionCols = new ArrayList<Integer>();
		int[] roomPostitionCols1 = { 2, 4, 6, 2, 4, 6, 2, 4, 6 };
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

		for (int i = 0; i < rows.size(); i++) {
			for (int j = 0; j < cols.size(); j++) {
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

		for (int i = 0; i < rows.size(); i++) {
			BoardPiece x = grid[roomPositionRows.get(i)][roomPositionCols.get(i)];
			BoardPiece z = grid[rows.get(i)][cols.get(i)];
			grid[rows.get(i)][cols.get(i)] = x;
			grid[roomPositionRows.get(i)][roomPositionCols.get(i)] = z;
		}
	}

	public void checkNinjaPosition() {
		for (int i = 5; i < grid.length; i++) {
			for (int j = 0; j < (i - 4); j++) {
				if (grid[i][j].getPieceType().equals("N")) {
					for (int k = 0; k < grid.length; k++) {
						if (grid[i][k].getPieceType().equals(" ") && (k > j)) {
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

	public BoardPiece getBoardPieceAt(int x, int y) {
		return grid[x][y];
	}

	public String toString() {
		String printPosition = "";
		for (BoardPiece[] b : grid) {
			for (BoardPiece p : b) {
				printPosition += "[" + p.getPieceType() + "]";
			}
			printPosition += "\n";

		}
		return printPosition;

	}
}
