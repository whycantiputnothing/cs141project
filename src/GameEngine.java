import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Serializable {

	private int numberOfMoves;

	private int numberOfMovesCounter;

	private boolean isAlive = true;

	private boolean gotPowerUp = false;

	private String powerUpName = "";

	private int cannotEnter;

	private boolean hasExtraBullet = false;

	private boolean isInvincible;

	private boolean ninjaGotShot;

	private boolean ninjaSpotted;

	private Grid grid = new Grid();

	public void makeGrid() {
		grid.instantiateGrid();
	}

	public String gridToString() {
		return grid.toString();

	}

	public void getBullet(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");
		int ammoCount = ((Spy) (grid.getBoardPieceAt(grid.findSpy()[0], grid.findSpy()[1]))).getAmmoCount();
		if (grid.getBoardPieceAt(x, y).getPieceType().equals("B")) {
			grid.setBoardPieceAt(x, y, delete);
			powerUpName = "Extra Bullet";
			gotPowerUp = true;
			if (ammoCount == 1) {
				hasExtraBullet = false;
			} else {
				hasExtraBullet = true;
				((Spy) (grid.getBoardPieceAt(grid.findSpy()[0], grid.findSpy()[1]))).setAmmoCount(1);
			}
		}
	}

	public void getRadar(int x, int y) {
		int briefRow = grid.findBriefCase()[0];
		int briefCol = grid.findBriefCase()[1];
		BoardPiece delete = new BoardPiece(" ");

		if (grid.getBoardPieceAt(x, y).getPieceType().equals("R")) {
			grid.setBoardPieceAt(x, y, delete);
			powerUpName = "Radar";
			gotPowerUp = true;
			((Room) (grid.getBoardPieceAt(briefRow, briefCol))).setIsBriefcaseVisible(true);

		}
	}

	public void getInvincibility(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");

		if (grid.getBoardPieceAt(x, y).getPieceType().equals("I")) {
			grid.setBoardPieceAt(x, y, delete);

			powerUpName = "Invincibility";
			gotPowerUp = true;

			// int a = numberOfMoves;
			numberOfMovesCounter = numberOfMoves + 1;

			isInvincible = true;
		}
		if (numberOfMoves - numberOfMovesCounter == 5) {
			isInvincible = false;
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
		ninjaGotShot = false;
		int row = grid.findSpy()[0];
		int col = grid.findSpy()[1];
		int count = 0;

		if (s.toLowerCase().equals("a")) {
			for (int i = col; i >= 0; i--) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (s.toLowerCase().equals("w")) {
			for (int i = row; i >= 0; i--) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (s.toLowerCase().equals("d")) {
			for (int i = col; i < 9; i++) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (s.toLowerCase().equals("s")) {
			for (int i = row; i < 9; i++) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					ninjaGotShot = true;
					count++;
				}
			}
		}

		((Spy) (grid.getBoardPieceAt(row, col))).setAmmoCount(0);

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

	public void look(int x) {
		int i = grid.findSpy()[0];
		int j = grid.findSpy()[1];
		BoardPiece a;
		BoardPiece b;
		ninjaSpotted = false;

		if (x == 0) {
			a = grid.getBoardPieceAt(i - 2, j);
			a.setIsVisible(true);
			if (grid.getBoardPieceAt(i - 1, j).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i - 2, j).getPieceType().equals("N")) {
				ninjaSpotted = true;
			}

			if (i > 2) {
				b = grid.getBoardPieceAt(i - 3, j);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i - 3, j).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 1) {
			a = grid.getBoardPieceAt(i, j - 2);
			a.setIsVisible(true);
			if (grid.getBoardPieceAt(i, j - 1).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i, j - 2).getPieceType().equals("N")) {
				ninjaSpotted = true;
			}

			if (j > 2) {
				b = grid.getBoardPieceAt(i, j - 3);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i, j - 3).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 2) {
			a = grid.getBoardPieceAt(i + 2, j);
			a.setIsVisible(true);
			if (grid.getBoardPieceAt(i + 1, j).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i + 2, j).getPieceType().equals("N")) {
				ninjaSpotted = true;
			}

			if (i < 7) {
				b = grid.getBoardPieceAt(i + 3, j);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i + 3, j).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 3) {
			a = grid.getBoardPieceAt(i, j + 2);
			a.setIsVisible(true);
			if (grid.getBoardPieceAt(i, j + 1).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i, j + 2).getPieceType().equals("N")) {
				ninjaSpotted = true;
			}

			if (j < 7) {
				b = grid.getBoardPieceAt(i, j + 3);
				b.setIsVisible(true);
				;
				if (grid.getBoardPieceAt(i, j + 3).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}
		}

	}

	public boolean canSpyLook(int i) {
		int[] spyPos = grid.findSpy();
		int a = spyPos[0];
		int b = spyPos[1];
		boolean canLook = true;

		if (i == 0) {
			if (a < 2)
				canLook = false;
		} else if (i == 1) {
			if (b < 2)
				canLook = false;
		} else if (i == 2) {
			if (a > 6)
				canLook = false;
		} else if (i == 3) {
			if (b > 6)
				canLook = false;
		}

		return canLook;
	}

	public boolean canSpyMove(int direction) {
		int[] spyPos = grid.findSpy();
		int a = spyPos[0];
		int b = spyPos[1];
		// up
		if (direction == 0) {
			if (a == 0) {
				cannotEnter = 1;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0] - 1, spyPos[1]).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0] - 1, spyPos[1]).getPieceType().equals("X")) {
				cannotEnter = 0;
				return false;
			} else
				return true;
			// left
		} else if (direction == 1) {
			if (b == 0) {
				cannotEnter = 1;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] - 1).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0], spyPos[1] - 1).getPieceType().equals("X")) {
				cannotEnter = 0;
				return false;
			} else
				return true;
			// down
		} else if (direction == 2) {
			if (a == 8) {
				return false;
			} else
				return true;
			// right
		} else if (direction == 3) {
			if (b == 8) {
				cannotEnter = 1;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] + 1).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0], spyPos[1] + 1).getPieceType().equals("X")) {
				cannotEnter = 0;
				return false;
			} else
				return true;
		} else
			return false;
	}

	public void ninjaStab() {
		if (!isInvincible) {
			int[] ninjaPos = new int[grid.findNinja().size()];
			for (int i = 0; i < grid.findNinja().size(); i++) {
				ninjaPos[i] = grid.findNinja().get(i);
			}
			int[] spyPos = grid.findSpy();
			int c = spyPos[0];
			int d = spyPos[1];
			for (int i = 0; i < grid.findNinja().size(); i += 2) {
				int a = ninjaPos[i];
				int b = ninjaPos[i + 1];
				if (((a + 1) == c) && b == d) {
					isAlive = false;
				} else if (((a - 1) == c) && b == d) {
					isAlive = false;
				} else if (((b - 1) == d) && c == a) {
					isAlive = false;
				} else if (((b + 1) == d) && c == a) {
					isAlive = false;
				}
			}
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
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("S")) {
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
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("S")) {
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
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("S")) {
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
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i + 1], ninjaPos[i + 1] + 1).getPieceType()
										.equals("S")) {
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
		swap(8, 0, spyPos[0], spyPos[1]);
		isAlive = true;
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
		grid.setBoardPieceAt(7, 0, r);
		grid.setBoardPieceAt(5, 0, b);
		grid.setBoardPieceAt(1, 0, n);
		grid.setBoardPieceAt(6, 0, m);

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
	
	public boolean gameLost(){
		if (getNumberOfLives() == 0)
			return true;
		else
			return false;
	}

	public boolean wrongRoom() {
		int[] findSpy = grid.findSpy();
		int[] roomRows = { 1, 1, 1, 4, 4, 4, 7, 7, 7 };
		int[] roomCols = { 1, 4, 7, 1, 4, 7, 1, 4, 7 };

		// if wrong room is true, it swaps the spy back to its position
		boolean isWrongRoom = false;
		for (int i = 0; i < 9; i++) {
			if (!((Room) (grid.getBoardPieceAt(roomRows[i], roomCols[i]))).getHasBriefcase()) {
				isWrongRoom = true;
				swap(findSpy[0], findSpy[1], findSpy[0] - 1, findSpy[1]);
			} else {
				isWrongRoom = false;
			}
		}
		return isWrongRoom;
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

	public void setGotPowerUp(boolean b) {
		gotPowerUp = b;
	}

	public String getPowerUpName() {
		return powerUpName;
	}

	public void resetPowerUpName() {
		powerUpName = "";
	}

	public boolean getHasExtraBullet() {
		return hasExtraBullet;
	}

	public int[] briefcasePosition() {
		return grid.findBriefCase();
	}

	public boolean getIsInvincible() {
		return isInvincible;
	}

	public int getNumberOfBullets() {
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		return ((Spy) (grid.getBoardPieceAt(c, d))).getAmmoCount();
	}

	public boolean getNinjaGotShot() {
		return ninjaGotShot;
	}

	public boolean getNinjaSpotted() {
		return ninjaSpotted;
	}

	public int getCannotEnter() {
		return cannotEnter;
	}
	
	public int getNumberOfLives(){
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		return ((Spy) (grid.getBoardPieceAt(c, d))).getNumberOfLives();
	}

}
