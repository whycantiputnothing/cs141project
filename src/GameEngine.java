
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

	public void lookAtSpaceU(int x, int y) {
		grid.getBoardPieceAt(x - 1, y).setIsVisible(true);
	}

	public void lookAtSpaceD(int x, int y) {
		grid.getBoardPieceAt(x + 1, y).setIsVisible(true);
	}

	public void lookAtSpaceL(int x, int y) {
		grid.getBoardPieceAt(x, y - 1).setIsVisible(true);
	}

	public void lookAtSpaceR(int x, int y) {
		grid.getBoardPieceAt(x, y + 1).setIsVisible(true);
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
