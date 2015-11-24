import java.util.ArrayList;
import java.util.List;

public class GameEngine {

	private int numberOfMoves;

	private int numberOfMovesCounter;

	private boolean isAlive = true;
	
	private boolean gotPowerUp = false;
	
	private String powerUpName = "";

	private boolean hasExtraBullet = false;
	
	private Grid grid = new Grid();

	public void makeGrid() {
		grid.instantiateGrid();
	}

	public void printGrid() {
		System.out.println(grid.toString());

	}

	public void getBullet(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");
		powerUpName = "Extra Bullet";
		int ammoCount = ((Spy) (grid.getBoardPieceAt(grid.findSpy()[0], grid.findSpy()[1]))).getAmmoCount();
		if (ammoCount == 0) {
			if (grid.getBoardPieceAt(x, y).getPieceType().equals("B")) {
				grid.setBoardPieceAt(x, y, delete);
				hasExtraBullet = true;
				ammoCount++;
			}
		} else if (ammoCount == 1) {
			if (grid.getBoardPieceAt(x, y).getPieceType().equals("B")) {
				grid.setBoardPieceAt(x, y, delete);
				hasExtraBullet = false;
			}
		}
	}

	public void getRadar(int x, int y) {
		int briefRow = grid.findBriefCase()[0];
		int briefCol = grid.findBriefCase()[1];
		BoardPiece delete = new BoardPiece(" ");
		powerUpName = "Radar";
		
		if (grid.getBoardPieceAt(x, y).getPieceType().equals("R")) {
			grid.setBoardPieceAt(x, y, delete);
			gotPowerUp = true;
			((Room)(grid.getBoardPieceAt(briefRow, briefCol))).setIsBriefcaseVisible(true);;
		}
	}
	
	public void swap(int w, int x, int y, int z) {
		BoardPiece a = grid.getBoardPieceAt(w, x);
		BoardPiece b = grid.getBoardPieceAt(y, z);
		grid.setBoardPieceAt(w, x, b);
		grid.setBoardPieceAt(y, z, a);
	}

	public void moveSpy(String s) {

		if (s.toLowerCase().equals("w")) {
			getRadar(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			getBullet(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			getInvincibility(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] - 1, grid.findSpy()[1]);
		} else if (s.toLowerCase().equals("a")) {
			getRadar(grid.findSpy()[0], grid.findSpy()[1] - 1);
			getBullet(grid.findSpy()[0], grid.findSpy()[1] - 1);
			getInvincibility(grid.findSpy()[0], grid.findSpy()[1] - 1);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] - 1);
		} else if (s.toLowerCase().equals("s")) {
			getRadar(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			getBullet(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			getInvincibility(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] + 1, grid.findSpy()[1]);
		} else if (s.toLowerCase().equals("d")) {
			getRadar(grid.findSpy()[0], grid.findSpy()[1] + 1);
			getBullet(grid.findSpy()[0], grid.findSpy()[1] + 1);
			getInvincibility(grid.findSpy()[0], grid.findSpy()[1] + 1);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] + 1);
		}
	}

	public void shoot(String s) {
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
		} else if (s.toLowerCase().equals("w")) {
			for (int i = row; i >= 0; i--) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					count++;
				}
			}
		} else if (s.toLowerCase().equals("d")) {
			for (int i = col; i < 9; i++) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					count++;
				}
			}
		} else if (s.toLowerCase().equals("s")) {
			for (int i = row; i < 9; i++) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					count++;
				}
			}
		}
		int ammo = ((Spy) (grid.getBoardPieceAt(row, col))).getAmmoCount();
		((Spy) (grid.getBoardPieceAt(row, col))).setAmmoCount(ammo - 1);
		System.out.println(((Spy) (grid.getBoardPieceAt(row, col))).getAmmoCount());
	}

	public void lookAround() {
		int row = grid.findSpy()[0];
		int col = grid.findSpy()[1];

		if (row > 0) {
			grid.getBoardPieceAt(row - 1, col).setIsVisible(true);
		}
		if (row < 8) {
			grid.getBoardPieceAt(row + 1, col).setIsVisible(true);
		}
		if (col > 0) {
			grid.getBoardPieceAt(row, col - 1).setIsVisible(true);
		}
		if (col < 8) {
			grid.getBoardPieceAt(row, col + 1).setIsVisible(true);
		}

	}

	public void look(String direction) {
		int i = grid.findSpy()[0];
		int j = grid.findSpy()[1];
		BoardPiece a;

		if (direction.toLowerCase().equals("w")) {
			a = grid.getBoardPieceAt(i - 2, j);
			a = grid.getBoardPieceAt(i - 3, j);
			a.setIsVisible(true);
		} else if (direction.toLowerCase().equals("a")) {
			a = grid.getBoardPieceAt(i, j - 2);
			a = grid.getBoardPieceAt(i, j - 3);
			a.setIsVisible(true);
		} else if (direction.toLowerCase().equals("s")) {
			a = grid.getBoardPieceAt(i + 2, j);
			a = grid.getBoardPieceAt(i + 3, j);
			a.setIsVisible(true);
		} else if (direction.toLowerCase().equals("d")) {
			a = grid.getBoardPieceAt(i, j + 2);
			a = grid.getBoardPieceAt(i, j + 3);
			a.setIsVisible(true);
		}
	}

	public boolean canSpyMove(String s) {
		int[] spyPos = grid.findSpy();
		int a = spyPos[0];
		int b = spyPos[1];
		boolean c = false;
		if (s.equals("w")) {
			if (a == 0)
				c = false;
			else
				c = true;
		} else if (s.equals("a")) {
			if (b == 0)
				c = false;
			else
				c = true;
		} else if (s.equals("s")) {
			if (a == 8)
				c = false;
			else
				c = true;
		} else if (s.equals("d")) {
			if (b == 8)
				c = false;
			else
				c = true;
		}
		return c;
	}

	public void ninjaStab() {
		int[] ninjaPos = new int[grid.findNinja().size()];
		for (int i = 0; i < grid.findNinja().size(); i++) {
			ninjaPos[i] = grid.findNinja().get(i);
		}
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		int count = 0;
		int count2 = 0;
		while (count < grid.findNinja().size()) {
			int a = ninjaPos[count2];
			int b = ninjaPos[count2 + 1];
			if (((a + 1) == c) && b == d) {
				isAlive = false;
			} else if (((a - 1) == c) && b == d) {
				isAlive = false;
			} else if (((b - 1) == d) && c == a) {
				isAlive = false;
			} else if (((b + 1) == d) && c == a) {
				isAlive = false;
			}
			count++;
		}
	}

	public void moveNinja() {
		int[] ninjaPos = new int[grid.findNinja().size()];
		for (int i = 0; i < grid.findNinja().size(); i++) {
			ninjaPos[i] = grid.findNinja().get(i);
		}
		boolean notAvailable = false;
		int[] randomNums = { 9, 9, 9, 9, 9, 9, 9 };

		for (int i = 0; i < ninjaPos.length - 1; i += 2) {
			int count = 3;
			do {

				int moveDir = grid.randNinjaMove();
				// int moveDir = 0;
				if (count > 3) {
					while (moveDir == randomNums[count] || moveDir == randomNums[count - 1]
							|| moveDir == randomNums[count - 2] || moveDir == randomNums[count - 3]) {
						moveDir = grid.randNinjaMove();
					}
				}

				// up
				if (moveDir == 0) {
					if (ninjaPos[i] - 1 > 0) {
						if (grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("X")) {
							notAvailable = true;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] - 1, ninjaPos[i + 1]);

							notAvailable = false;
						}
					} else {
						notAvailable = true;
					}

				}

				// down
				else if (moveDir == 1) {
					if (ninjaPos[i] + 1 < 8) {
						if (grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("X")) {
							notAvailable = true;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] + 1, ninjaPos[i + 1]);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
					}
				}

				// left
				else if (moveDir == 2) {
					if (ninjaPos[i + 1] - 1 > 0) {
						if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("X")) {
							notAvailable = true;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] - 1);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
					}
				}

				// right
				else if (moveDir == 3) {
					if (ninjaPos[i + 1] + 1 < 8) {
						if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("X")) {
							notAvailable = true;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] + 1);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
					}
				}

				randomNums[count] = moveDir;

				count++;
				if (count == 7) {
					notAvailable = false;
				}
			} while (notAvailable);
		}
	}

	public void respawn() {
		int[] spyPos = grid.findSpy();
		BoardPiece a = grid.getBoardPieceAt(spyPos[0], spyPos[1]);
		((Spy) (a)).death();
		grid.setBoardPieceAt(spyPos[0], spyPos[1], a);
		isAlive = true;
	}

	public void getInvincibility(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");

		numberOfMovesCounter = numberOfMoves + 1;

		if (grid.getBoardPieceAt(x, y).getPieceType().equals("I")) {
			grid.setBoardPieceAt(x, y, delete);

			while (numberOfMovesCounter < numberOfMoves + 5) {
				isAlive = true;
			}
		}

	}

	public void reset() {
		grid.instantiateGrid();
		numberOfMoves = 0;
		isAlive = true;
	}

	public void debug(boolean a) {
		BoardPiece n = new BoardPiece("N");
		BoardPiece b = new BoardPiece("B");
		BoardPiece r = new PowerUps("R");
		BoardPiece m = new PowerUps("I");
		grid.setBoardPieceAt(6, 0, r);
		grid.setBoardPieceAt(5, 0, b);
		grid.setBoardPieceAt(1, 0, n);
	//	grid.setBoardPieceAt(7, 0, m);
		grid.debug(a);
	}

	public boolean gameWon() {
		int[] spyPos = grid.findSpy();
		int[] briefcasePos = grid.findBriefCase();

		if (spyPos[0] == (briefcasePos[0] + 1) && spyPos[1] == briefcasePos[1])
			return true;

		else
			return false;
	}

	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}

	public void takeTurn() {
		numberOfMoves++;
	}

	public boolean getIsAlive() {
		return isAlive;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	public int getNumberOfMovesCounter() {
		return numberOfMovesCounter;
	}

	public boolean getGotPowerUp() {
		return gotPowerUp;
	}
	
	public void setGotPowerUp(boolean b){
		gotPowerUp = b;
	}

	public String getPowerUpName() {
		return powerUpName;
	}

	public boolean getHasExtraBullet() {
		return hasExtraBullet;
	}
	
	public int[] briefcasePosition(){
		return grid.findBriefCase();
	}
	
	
	
}
