
public class GameEngine {

	private int numberOfMoves;

	private Grid grid = new Grid();

	public void printGrid() {
		System.out.println(grid.toString());

	}

	public void radar() {

	}

	public void makeGrid() {
		grid.instantiateGrid();
	}

	private boolean isAlive(int lives) {
		if (lives == 0) {
			return false;
		}
		return true;
	}

	public void moveSpy(String s) {
		if (s.toLowerCase().equals("w")) {

			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] - 1, grid.findSpy()[1]);
		} else if (s.toLowerCase().equals("a")) {

			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] - 1);
		} else if (s.toLowerCase().equals("s")) {

			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] + 1, grid.findSpy()[1]);
		} else if (s.toLowerCase().equals("d")) {

			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] + 1);
		}
	}
	
	public void Shoot(String s) {
		BoardPiece a = new BoardPiece(" ");
		int row = grid.findSpy()[0];
		int col = grid.findSpy()[1];
		int count = 0;
		
		if (s.toLowerCase().equals("a")) {
			for (int i = col; i >= 0; i--) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					count++;
				}
			}
		}
		else if (s.toLowerCase().equals("w") ) {
			for (int i = row; i >= 0; i--) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					count++;
				}
			}	
		}
		else if (s.toLowerCase().equals("d") ) {
			for (int i = col; i < 9 ; i++) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					count++;
				}
			}	
		}
		else if (s.toLowerCase().equals("s") ) {
			for (int i = row; i < 9; i++) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					count++;
				}
			}	
		}
		
		int ammo = ((Spy)(grid.getBoardPieceAt(row, col))).getAmmoCount();
		((Spy)(grid.getBoardPieceAt(row, col))).setAmmoCount(ammo - 1);
	}
		
	public void look(String direction){
		int i = grid.findSpy()[0];
		int j = grid.findSpy()[1];
		BoardPiece a;
		
		if (direction.toLowerCase().equals("w")){
			a = grid.getBoardPieceAt(i - 2, j); 
			a.setIsVisible(true);
		}
		else if (direction.toLowerCase().equals("a")){
			a = grid.getBoardPieceAt(i, j - 2); 
			a.setIsVisible(true);
		}
		else if (direction.toLowerCase().equals("s")){
			a = grid.getBoardPieceAt(i + 2, j); 
			a.setIsVisible(true);
		}
		else if (direction.toLowerCase().equals("d")){
			a = grid.getBoardPieceAt(i, j + 2); 
			a.setIsVisible(true);
		}
	}

	public BoardPiece returnLocationOfSpy(int x, int y) {
		return grid.getBoardPieceAt(x, y);
	}

	public void moveNinja() {
		int[] ninjaPos = grid.findNinja();
		boolean notAvailable = false;

		for (int i = 0; i < ninjaPos.length; i += 2) {
			do {
				
				int moveDir = grid.randNinjaMove();
				if (moveDir == 0) {
					if (grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("U")
							|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("X")
							|| ninjaPos[i] - 1 < 0) {
						notAvailable = true;
					} else {
						swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] - 1, ninjaPos[i + 1]);
					}
				}

				else if (moveDir == 1) {
					if (grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("U")
							|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("X")
							|| ninjaPos[i] + 1 > 8) {
						notAvailable = true;
					} else{
					swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] + 1, ninjaPos[i + 1]);
					}
				}
				
				else if (moveDir == 2) {
					if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("U")
							|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("X")
							|| ninjaPos[i + 1] - 1 < 0) {
						notAvailable = true;
					} else{
					swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] - 1);
					}
				}
				else if (moveDir == 3) {
					if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("U")
							|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("X")
							|| ninjaPos[i + 1] + 1 > 8) {
						notAvailable = true;
					} else{
					swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] + 1);
					}
				}
			} while (notAvailable);
		}
	}

	public void respawn() {

	}

	public void invincibility() {

	}

	public void swap(int w, int x, int y, int z) {
		BoardPiece a = grid.getBoardPieceAt(w, x);
		BoardPiece b = grid.getBoardPieceAt(y, z);
		grid.setBoardPieceAt(w, x, b);
		grid.setBoardPieceAt(y, z, a);
	}

	public void quitGame() {

	}

	public void saveGame() {

	}

	public void loadGame() {

	}

	public void reset() {

	}
	
	public void debug(boolean b){
		grid.debug(b);
	}

	public boolean gameWon() {
		// TODO Auto-generated method stub
		return false;
	}
}
