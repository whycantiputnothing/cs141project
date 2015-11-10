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
		BoardPiece[] pieces = { grid[6][6], grid[6][7], grid[6][8], grid[7][0], grid[7][1], grid[7][2], grid[7][3],
				grid[7][4], grid[7][5], grid[7][7], grid[7][8], grid[8][0], grid[8][1], grid[8][2], grid[8][3],
				grid[8][4], grid[8][5], grid[8][6] };
		List<BoardPiece> somePieces = new ArrayList<BoardPiece>();
		somePieces = Arrays.asList(pieces);
		Collections.shuffle(somePieces);
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (count < somePieces.size()){
					grid[i][j] = somePieces.get(count);
					count++;
				}
			}
		}
	}
	
	public void putSpyAtStart(){
		BoardPiece a = grid[8][0];
		int x = 0;
		int y = 0;
		for(int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
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
