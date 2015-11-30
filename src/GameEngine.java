import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	private boolean roomBlock;
	
	private boolean isDebug;

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
		if (numberOfMoves - numberOfMovesCounter > 4) {
			isInvincible = false;
		}
	}

	public void swap(int w, int x, int y, int z) {
		BoardPiece a = grid.getBoardPieceAt(w, x);
		BoardPiece b = grid.getBoardPieceAt(y, z);
		grid.setBoardPieceAt(w, x, b);
		grid.setBoardPieceAt(y, z, a);
	}

	public void moveSpy(int x) {

		if (x == 0) {
			getRadar(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			getBullet(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			getInvincibility(grid.findSpy()[0] - 1, grid.findSpy()[1]);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] - 1, grid.findSpy()[1]);
		} else if (x == 1) {
			getRadar(grid.findSpy()[0], grid.findSpy()[1] - 1);
			getBullet(grid.findSpy()[0], grid.findSpy()[1] - 1);
			getInvincibility(grid.findSpy()[0], grid.findSpy()[1] - 1);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] - 1);
		} else if (x == 2) {
			getRadar(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			getBullet(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			getInvincibility(grid.findSpy()[0] + 1, grid.findSpy()[1]);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0] + 1, grid.findSpy()[1]);
		} else if (x == 3) {
			getRadar(grid.findSpy()[0], grid.findSpy()[1] + 1);
			getBullet(grid.findSpy()[0], grid.findSpy()[1] + 1);
			getInvincibility(grid.findSpy()[0], grid.findSpy()[1] + 1);
			swap(grid.findSpy()[0], grid.findSpy()[1], grid.findSpy()[0], grid.findSpy()[1] + 1);
		}
	}

	public void shoot(int x) {
		BoardPiece a = new BoardPiece(" ");
		ninjaGotShot = false;
		int row = grid.findSpy()[0];
		int col = grid.findSpy()[1];
		int count = 0;

		if (x == 1) {
			for (int i = col; i >= 0; i--) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (x == 0) {
			for (int i = row; i >= 0; i--) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (x == 3) {
			for (int i = col; i < 9; i++) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					ninjaGotShot = true;
					count++;
				}
			}
		} else if (x == 2) {
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
		roomBlock = false;

		if (x == 0) {
			a = grid.getBoardPieceAt(i - 2, j);
			
			if (grid.getBoardPieceAt(i - 1, j).toString().equals("Room")
					||grid.getBoardPieceAt(i - 2, j).toString().equals("Room")){
				roomBlock = true;
			}
			
			else if (grid.getBoardPieceAt(i - 1, j).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i - 2, j).getPieceType().equals("N")) {
				ninjaSpotted = true;
				a.setIsVisible(true);
			}
			
			else
				a.setIsVisible(true);

			if (i > 2 && !roomBlock) {
				b = grid.getBoardPieceAt(i - 3, j);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i - 3, j).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 1) {
			a = grid.getBoardPieceAt(i, j - 2);
		
			if (grid.getBoardPieceAt(i, j - 1).toString().equals("Room")
					||grid.getBoardPieceAt(i, j - 2).toString().equals("Room")){
				roomBlock = true;
			}
			
			else if (grid.getBoardPieceAt(i, j - 1).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i, j - 2).getPieceType().equals("N")) {
				ninjaSpotted = true;
				a.setIsVisible(true);
			}
			
			else
				a.setIsVisible(true);

			if (j > 2 && !roomBlock) {
				b = grid.getBoardPieceAt(i, j - 3);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i, j - 3).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 2) {
			a = grid.getBoardPieceAt(i + 2, j);
		
			if (grid.getBoardPieceAt(i + 1, j).toString().equals("Room")
					||grid.getBoardPieceAt(i + 2, j).toString().equals("Room")){
				roomBlock = true;
			}
			
			else if (grid.getBoardPieceAt(i + 1, j).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i + 2, j).getPieceType().equals("N")) {
				ninjaSpotted = true;
				a.setIsVisible(true);
			}
			
			else
				a.setIsVisible(true);

			if (i < 6 && !roomBlock) {
				b = grid.getBoardPieceAt(i + 3, j);
				b.setIsVisible(true);
				if (grid.getBoardPieceAt(i + 3, j).getPieceType().equals("N")) {
					ninjaSpotted = true;
				}
			}

		} else if (x == 3) {
			a = grid.getBoardPieceAt(i, j + 2);
		
			if (grid.getBoardPieceAt(i, j + 1).toString().equals("Room")
					||grid.getBoardPieceAt(i, j + 2).toString().equals("Room")){
				roomBlock = true;
			}
			
			else if (grid.getBoardPieceAt(i, j + 1).getPieceType().equals("N")
					|| grid.getBoardPieceAt(i, j + 2).getPieceType().equals("N")) {
				ninjaSpotted = true;
				a.setIsVisible(true);
			}
			
			else
				a.setIsVisible(true);

			if (j < 6 && !roomBlock) {
				b = grid.getBoardPieceAt(i, j + 3);
				b.setIsVisible(true);

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
				cannotEnter = 0;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0] - 1, spyPos[1]).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0] - 1, spyPos[1]).getPieceType().equals("X")) {
				cannotEnter = 1;
				return false;
			} else
				return true;
			// left
		} else if (direction == 1) {
			if (b == 0) {
				cannotEnter = 0;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] - 1).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0], spyPos[1] - 1).getPieceType().equals("X")) {
				cannotEnter = 1;
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
				cannotEnter = 0;
				return false;
			} else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] + 1).getPieceType().equals("U")
					|| grid.getBoardPieceAt(spyPos[0], spyPos[1] + 1).getPieceType().equals("X")) {
				cannotEnter = 1;
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
	
	public int randNinjaMove(){
		Random rand = new Random();
		int moveDir = rand.nextInt(3);
		return moveDir;
	}

	public void moveNinja() {
		int[] ninjaPos = new int[grid.findNinja().size()];
		for (int i = 0; i < grid.findNinja().size(); i++) {
			ninjaPos[i] = grid.findNinja().get(i);
		}
		boolean notAvailable = false;

		for (int i = 0; i < ninjaPos.length - 1; i += 2) {
			int count = 0;

			do {
				int moveDir = randNinjaMove();

				// up
				if (moveDir == 0) {
					if (ninjaPos[i] - 1 > 0) {
						if (grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("S")) {
							notAvailable = true;
							count++;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] - 1, ninjaPos[i + 1]);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
						count++;
					}
				}

				// down
				else if (moveDir == 1) {
					if (ninjaPos[i] + 1 < 8) {
						if (grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("S")) {
							notAvailable = true;
							count++;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] + 1, ninjaPos[i + 1]);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
						count++;
					}
				}

				// left
				else if (moveDir == 2) {
					if (ninjaPos[i + 1] - 1 > 0) {
						if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("X")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("S")) {
							notAvailable = true;
							count++;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] - 1);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
						count++;
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
							count++;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] + 1);
							notAvailable = false;
						}
					} else {
						notAvailable = true;
						count++;
					}
				}

				if (count == 4) {
					// swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i],
					// ninjaPos[i + 1]);
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
		numberOfMovesCounter = 0;
		gotPowerUp = false;
		powerUpName = "";
		cannotEnter = 0;
		hasExtraBullet = false;
		isInvincible = false;
		ninjaGotShot = false;
		ninjaSpotted = false;	
		numberOfMoves = 0;
		isAlive = true;
	}

	public void debug() {
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				BoardPiece a = grid.getBoardPieceAt(i, j);
				a.setIsVisible();
				if (a.getPieceType().equals("S")){
					a.setIsVisible(true);
				}
				if (a.toString().equals("Room")){
					a.setIsVisible(true);
					if (((Room)(a)).getHasBriefcase()){
						if(isDebug)
							((Room)(a)).setIsBriefcaseVisible(false);
						else
							((Room)(a)).setIsBriefcaseVisible(true);
							
					}
				}
			}
		}
	}
	
	public void debugHelper(){
		if (isDebug){
			numberOfMovesCounter = numberOfMoves - 5;
			isInvincible = true;	
		}			
	}
	

	public boolean gameWon() {
		int[] spyPos = grid.findSpy();
		int[] briefcasePos = grid.findBriefCase();
		int[] roomPosRows = { 1, 1, 1, 4, 4, 4, 7, 7, 7 };
		int[] roomPosCols = { 1, 4, 7, 1, 4, 7, 1, 4, 7 };

		for (int i = 0; i < roomPosRows.length; i++) {
			if (spyPos[0] == roomPosRows[i] && spyPos[1] == roomPosCols[i]) {
				if (spyPos[0] == (briefcasePos[0] + 1) && spyPos[1] == briefcasePos[1])
					return true;

			}
		}
		return false;
	}

	public boolean gameLost() {
		if (getNumberOfLives() == 0)
			return true;
		else
			return false;
	}

	public boolean wrongRoom() {
		int[] findSpy = grid.findSpy();
		int[] findBriefcase = grid.findBriefCase();
		int[] roomRows = { 1, 1, 1, 4, 4, 4, 7, 7, 7 };
		int[] roomCols = { 1, 4, 7, 1, 4, 7, 1, 4, 7 };

		// if wrong room is true, it swaps the spy back to its position
		boolean isWrongRoom = false;
		for (int i = 0; i < 9; i++) {
			if (findSpy[0] == roomRows[i] && findSpy[1] == roomCols[i]) {
				if (findSpy[0] == findBriefcase[0] + 1 && findSpy[1] == findBriefcase[1]) {
					isWrongRoom = false;
					
				} else {
					isWrongRoom = true;
					swap(findSpy[0], findSpy[1], findSpy[0] - 1, findSpy[1]);
					
				}
			}
		}
		return isWrongRoom;
	}

	public void hidePieces() {
		if (!isDebug){
			BoardPiece temp;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					temp = grid.getBoardPieceAt(i, j);
					if (temp.toString().equals("Room")) {
						
					} 
					else if (temp.getPieceType().equals("S")) {
						
					} else {
						temp.setIsVisible(false);
						grid.setBoardPieceAt(i, j, temp);
					}
				}
			}			
		}
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

	public int getNumberOfLives() {
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		return ((Spy) (grid.getBoardPieceAt(c, d))).getNumberOfLives();
	}
	
	public boolean getRoomBlock(){
		return roomBlock;
	}
	
	public void setIsDebug(){
		isDebug = !isDebug;
	}
	
	public boolean getIsDebug(){
		return isDebug;
	}

}
