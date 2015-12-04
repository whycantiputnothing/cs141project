import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 *
 */
public class GameEngine implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8360099825502193420L;
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

	/**
	 * Instantiates the grid to be used in the game
	 */
	public void makeGrid() {
		grid.instantiateGrid();
	}

	/**
	 * Converts the grid into a printable string for the user to 
	 * see and interact with
	 * @return grid as string
	 */
	public String gridToString() {
		return grid.toString();

	}

	/**
	 * This method is called by the moveSpy method and is used to determine if 
	 * the extra bullet has been picked up. If the powerup has been picked up, it is deleted 
	 * from the game board. The powerup will increase the bullet count by 1, unless that bullet 
	 * count is already at 1, in which case the bullet count will remain the same.
	 * @param x row that the spy has moved into
	 * @param y column that the spy has moved into
	 */
	public void getBullet(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");
		int ammoCount = ((Spy) (grid.getBoardPieceAt(grid.findSpy()[0], grid.findSpy()[1]))).getAmmoCount();

		if (grid.getBoardPieceAt(x, y).getPieceType().equals("B")) {
			grid.setBoardPieceAt(x, y, delete);
			powerUpName = "Extra Bullet";
			gotPowerUp = true;

			if (ammoCount == 1) {
				hasExtraBullet = false;
			}

			else {
				hasExtraBullet = true;
				((Spy) (grid.getBoardPieceAt(grid.findSpy()[0], grid.findSpy()[1]))).setAmmoCount(1);
			}
		}
	}

	/**
	 * This method is called by the moveSpy method and is used to determine if 
	 * radar has been picked up. If the powerup has been picked up, it is deleted 
	 * from the game board. The powerup itself will show the location of the room
	 * that contains the briefcase by finding the briefcase and setting it to 
	 * visibile. A message is also displayed to the user in the UI that explains
	 * which room contains the briefcase
	 * 
	 * @param x row that the spy has moved into
	 * @param y column that the spy has moved into
	 */
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

	/**
	 * This method is called by the moveSpy method and is used to determine if 
	 * invincibility has been picked up. If the powerup has been picked up,
	 * it is deleted from the game board. The method takes the counter for the number
	 * of moves the spy has made and uses it to implement invincibility for the next 
	 * five moves.
	 * 
	 * @param x row that the spy has moved into
	 * @param y column that the spy has moved into
	 */
	public void getInvincibility(int x, int y) {
		BoardPiece delete = new BoardPiece(" ");

		if (grid.getBoardPieceAt(x, y).getPieceType().equals("I")) {
			grid.setBoardPieceAt(x, y, delete);

			powerUpName = "Invincibility";
			gotPowerUp = true;

			numberOfMovesCounter = numberOfMoves + 1;

			isInvincible = true;
		}

		if (numberOfMoves - numberOfMovesCounter > 4) {
			isInvincible = false;
		}
	}

	/**
	 * This method is the method instrumental in making board pieces move. It
	 * takes the coordinates of one piece and swaps it with the coordinates
	 * of an adjacent piece. 
	 * @param w row of the first board piece
	 * @param x column of the first board piece
	 * @param y	row of the second board piece
	 * @param z column of the second board piece
	 */
	public void swap(int w, int x, int y, int z) {
		BoardPiece a = grid.getBoardPieceAt(w, x);
		BoardPiece b = grid.getBoardPieceAt(y, z);
		grid.setBoardPieceAt(w, x, b);
		grid.setBoardPieceAt(y, z, a);
	}

	/**
	 * The moveSpy method takes a direction chosen by the user and moves the spy in that 
	 * direction. The method will first check if a power has been picked up, and then uses
	 * the swap method to swap places with whatever is next to the spy. If a powerup has
	 * been picked up, its function will be enabled on the next turn.
	 * 
	 * @param x direction in which the spy will move as chosen by the user
	 */
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

	/**
	 * The shoot method is used to shoot in the direction chosen by the user.
	 * If there is a ninja in the direction that the spy has chosen to shoot,
	 * the ninja will be removed from the grid and a message from the UI will inform the
	 * user that a ninja has been hit. The method only allows for the bullet to kill one
	 * ninja, regardless of how many ninjas may be in the row or column that the spy shot.
	 * 
	 * @param x determines which direction to shoot, as chosen by the user
	 */
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
		}

		else if (x == 0) {
			for (int i = row; i >= 0; i--) {
				if ((grid.getBoardPieceAt(i, col).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(i, col, a);
					ninjaGotShot = true;
					count++;
				}
			}
		}

		else if (x == 3) {
			for (int i = col; i < 9; i++) {
				if ((grid.getBoardPieceAt(row, i).getPieceType().equals("N")) && (count < 1)) {
					grid.setBoardPieceAt(row, i, a);
					ninjaGotShot = true;
					count++;
				}
			}
		}

		else if (x == 2) {
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

	/**
	 * The lookAround method will find the position of the spy on the grid and automatically
	 * set the pieces immediately around the spy to visible. However, if the spy is touching the 
	 * edge of the grid, nothing is changed on that side of the spy.
	 */
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

	
	/**
	 * The look method is the method that will make board pieces around the spy
	 * visible if the spy chooses to look. If there is a room blocking the direction in which
	 * the spy has chosen to look, the spy will not be able to look that way. Otherwise,
	 * the piece is set to visible temporarily.
	 * 
	 * @param x to determine which direction to show pieces
	 */
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
					|| grid.getBoardPieceAt(i - 2, j).toString().equals("Room")) {
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

		}

		else if (x == 1) {
			a = grid.getBoardPieceAt(i, j - 2);

			if (grid.getBoardPieceAt(i, j - 1).toString().equals("Room")
					|| grid.getBoardPieceAt(i, j - 2).toString().equals("Room")) {
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

		}

		else if (x == 2) {
			a = grid.getBoardPieceAt(i + 2, j);

			if (grid.getBoardPieceAt(i + 1, j).toString().equals("Room")
					|| grid.getBoardPieceAt(i + 2, j).toString().equals("Room")) {
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

		}

		else if (x == 3) {
			a = grid.getBoardPieceAt(i, j + 2);

			if (grid.getBoardPieceAt(i, j + 1).toString().equals("Room")
					|| grid.getBoardPieceAt(i, j + 2).toString().equals("Room")) {
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

	/**
	 * This method determines whether the spy is allowed to look in the chosen direction.
	 * It is not allowed to look if the spy is less than 2 spaces away from the boundaries
	 * of the grid. 
	 * @param i as the direction in which the user has chosen to look
	 * @return boolean that states if the spy is allowed to look
	 */
	public boolean canSpyLook(int i) {
		int[] spyPos = grid.findSpy();
		int a = spyPos[0];
		int b = spyPos[1];
		boolean canLook = true;

		if (i == 0) {
			if (a < 2)
				canLook = false;
		}

		else if (i == 1) {
			if (b < 2)
				canLook = false;
		}

		else if (i == 2) {
			if (a > 6)
				canLook = false;
		}

		else if (i == 3) {
			if (b > 6)
				canLook = false;
		}
		return canLook;
	}

	/**
	 * This method determines whether the spy can move in the chosen direction
	 * as determined by the boundaries of the grid and also stops the spy
	 * from moving if he tries to move into a room from any side other than the top.
	 * 
	 * @param direction as determined by user input from the UI
	 * @return a boolean that states if the spy is allowed to move in the chosen direction
	 */
	public boolean canSpyMove(int direction) {
		int[] spyPos = grid.findSpy();
		int a = spyPos[0];
		int b = spyPos[1];

		// up
		if (direction == 0) {
			if (a == 0) {
				cannotEnter = 0;
				return false;
			}

			else if (grid.getBoardPieceAt(spyPos[0] - 1, spyPos[1]).toString().equals("Room")) {
				cannotEnter = 1;
				return false;
			}

			else
				return true;

		}

		// left
		else if (direction == 1) {
			if (b == 0) {
				cannotEnter = 0;
				return false;
			}

			else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] - 1).toString().equals("Room")) {
				cannotEnter = 1;
				return false;
			}

			else
				return true;
		}

		// down
		else if (direction == 2) {
			if (a == 8) {
				return false;
			} else
				return true;
		}

		// right
		else if (direction == 3) {
			if (b == 8) {
				cannotEnter = 0;
				return false;
			}

			else if (grid.getBoardPieceAt(spyPos[0], spyPos[1] + 1).toString().equals("Room")) {
				cannotEnter = 1;
				return false;
			}

			else
				return true;
		} else
			return false;
	}

	/**
	 * This is a method used to show the occurrence of a ninja stabbing the spy.
	 * While the spy is not invincible and the ninja is next to the spy, boolean
	 * isAlive is set to false, removing one of the spy's lives.
	 */
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
				}

				else if (((a - 1) == c) && b == d) {
					isAlive = false;
				}

				else if (((b - 1) == d) && c == a) {
					isAlive = false;
				}

				else if (((b + 1) == d) && c == a) {
					isAlive = false;
				}
			}
		}
	}

	/**
	 * Gets a random number generated from 0 to 3. This number is used to set a
	 * random direction of movement applied to a ninja.
	 * 
	 * @return a random integer from 0 to 3.
	 */
	public int randNinjaMove() {
		Random rand = new Random();
		int moveDir = rand.nextInt(4);
		return moveDir;
	}

	/**
	 * This method moves each of the six ninjas in a random direction: up, down,
	 * left, or right. The ninja moves up if the number generated is 0, down if
	 * 1, left if 2, and right if 3. While the ninja is being told to move to a
	 * spot containing an array out of bounds, a room, a room with a briefcase,
	 * or the spy, the ninja will not move to that spot. It will instead go
	 * through the loop again until it finds an acceptable direction to move
	 * into.
	 */
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
					notAvailable = false;
				}
			} while (notAvailable);
		}
	}

	/**
	 * This method implements the hard version of the game. If this method is called,
	 * ninjas will follow the spy as long as there is a clear "line of sight" between the
	 * ninja and the spy. 
	 */
	public void moveNinjaHardMode() {
		int[] ninjaPos = new int[grid.findNinja().size()];
		int a = grid.findSpy()[0];
		int b = grid.findSpy()[1];
		
		for (int i = 0; i < grid.findNinja().size(); i++) {
			ninjaPos[i] = grid.findNinja().get(i);
		}
		
		boolean notAvailable = false;

		for (int i = 0; i < ninjaPos.length - 1; i += 2) {
			int count = 0;
			boolean follow = true;
			int moveDir = randNinjaMove();

			do {
				
				if (ninjaPos[i] == a){
					if (ninjaPos[i + 1] < b) {
						for (int j = ninjaPos[i + 1]; j < b; j++){
							if (grid.getBoardPieceAt(a, j).toString().equals("Room")) {
								follow = false;
							}
						}
						if (follow)
							moveDir = 3;
					}
					
					else if (ninjaPos[i + 1] > b) {
						for (int j = b; j < ninjaPos[i + 1]; j++){
							if (grid.getBoardPieceAt(a, j).toString().equals("Room")) {
								follow = false;
							}
						}
						if (follow)
							moveDir = 2;
					}
				}
				
				else if (ninjaPos[i + 1] == b){
					if (ninjaPos[i] < a) {
						for (int j = ninjaPos[i]; j < a; j++){
							if (grid.getBoardPieceAt(b, j).toString().equals("Room")) {
								follow = false;
							}
						}
						if (follow)
							moveDir = 1;
					}
					
					else if (ninjaPos[i] > a) {
						for (int j = a; j < ninjaPos[i]; j++){
							if (grid.getBoardPieceAt(b, j).toString().equals("Room")) {
								follow = false;
							}
						}
						if (follow)
							moveDir = 0;
					}
				}
				/*
				else {
					moveDir = randNinjaMove();					
				}
*/
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
	
	/**
	 * This method respawns the spy back to the initial position of the grid
	 * located at the bottom left corner. This occurs only when the spy loses a
	 * life. Boolean isAlive is set back to true.
	 */
	public void respawn() {
		int[] spyPos = grid.findSpy();
		BoardPiece a = grid.getBoardPieceAt(spyPos[0], spyPos[1]);
		((Spy) (a)).death();
		swap(8, 0, spyPos[0], spyPos[1]);
		isAlive = true;
		grid.checkNinjaPosition();
	}

	/**
	 * This method resets the game when the player wants to start a new game.
	 * The moves, power-ups, and lives are set back to default. A new grid is
	 * instantiated to play in.
	 */
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

	/**
	 * This is the debug method. The use for the debug method is to ease the
	 * testing process. This debug sets all the pieces in the game to visible.
	 */
	public void debug() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				BoardPiece a = grid.getBoardPieceAt(i, j);
				a.setIsVisible(!isDebug);
				if (a.getPieceType().equals("S")) {
					a.setIsVisible(true);
				}
				if (a.toString().equals("Room")) {
					a.setIsVisible(true);
					if (((Room) (a)).getHasBriefcase()) {
						if (isDebug)
							((Room) (a)).setIsBriefcaseVisible(false);
						else
							((Room) (a)).setIsBriefcaseVisible(true);

					}
				}
			}
		}
	}

	/**
	 * This method assists the debug method by setting the spy to be invincible
	 * whenever the debug is on/set to true.
	 */
	public void debugHelper() {
		if (isDebug) {
			numberOfMovesCounter = numberOfMoves - 5;
			isInvincible = true;
		}
	}

	/**
	 * This method checks to see if the spy has won the game by entering the
	 * room which contains the briefcase.
	 * 
	 * @return a boolean true or false, depending if the spy retrieved the
	 *         briefcase.
	 */
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

	/**
	 * This method shows whether the player has lost the game from losing all
	 * their lives.
	 * 
	 * @return a boolean true or false, depending if the spy has any lives left.
	 */
	public boolean gameLost() {
		if (getNumberOfLives() == 0)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the spy has entered the wrong room. The wrong room
	 * being the rooms that do not contain the briefcase.
	 * 
	 * @return a boolean isWrongRoom, true if room does not contain briefcase,
	 *         false if it does.
	 */
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

	/**
	 * This method sets all pieces on the board to not visible if the game is
	 * not in debug mode.
	 */
	public void hidePieces() {
		if (!isDebug) {
			BoardPiece temp;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					temp = grid.getBoardPieceAt(i, j);
					if (temp.toString().equals("Room")) {

					} else if (temp.getPieceType().equals("S")) {

					} else {
						temp.setIsVisible(false);
						grid.setBoardPieceAt(i, j, temp);
					}
				}
			}
		}
	}

	/**
	 * Retrieves an integer argument numberOfMoves. This number will be
	 * incremented as the player uses many turns.
	 * 
	 * @param numberOfMoves
	 *            The new numberOfMoves should increase as player's turns go by.
	 */
	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}

	/**
	 * This method increments the numberOfMoves whenever a turn is performed.
	 */
	public void takeTurn() {
		numberOfMoves++;
	}

	/**
	 * Gets whether the spy is alive or not.
	 * 
	 * @return whether the spy is alive or not.
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * Gets the number of moves performed.
	 * 
	 * @return the number of moves.
	 */
	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	/**
	 * Gets the number of moves counter used for invincibility.
	 * 
	 * @return the number of moves counter for invincibility.
	 */
	public int getNumberOfMovesCounter() {
		return numberOfMovesCounter;
	}

	/**
	 * Gets whether or not the spy picks up a power-up.
	 * 
	 * @return if the spy picked up a power-up.
	 */
	public boolean getGotPowerUp() {
		return gotPowerUp;
	}

	/**
	 * Changes if the spy received a power-up.
	 * 
	 * @param b
	 *            This is whether or not the spy received a power-up.
	 */
	public void setGotPowerUp(boolean b) {
		gotPowerUp = b;
	}

	/**
	 * Gets the name of the power-up picked up.
	 * 
	 * @return the name of the power-up.
	 */
	public String getPowerUpName() {
		return powerUpName;
	}

	/**
	 * This method resets the name of the power-up name that the spy picked up.
	 */
	public void resetPowerUpName() {
		powerUpName = "";
	}

	/**
	 * Gets if the player has an extra bullet.
	 * 
	 * @return whether or not the spy has an extra bullet.
	 */
	public boolean getHasExtraBullet() {
		return hasExtraBullet;
	}

	/**
	 * Gets the location of the briefcase by calling the method from the Grid
	 * class.
	 * 
	 * @return the position of briefcase.
	 */
	public int[] briefcasePosition() {
		return grid.findBriefCase();
	}

	/**
	 * Gets whether the spy is invincible or not from picking up the power-up.
	 * 
	 * @return true if spy is invincible, false if not.
	 */
	public boolean getIsInvincible() {
		return isInvincible;
	}

	/**
	 * Gets number of bullets the spy is holding.
	 * 
	 * @return number of bullets spy has at the moment.
	 */
	public int getNumberOfBullets() {
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		return ((Spy) (grid.getBoardPieceAt(c, d))).getAmmoCount();
	}

	/**
	 * Gets whether or not the spy shot a ninja.
	 * 
	 * @return true if a ninja was shot, false if not.
	 */
	public boolean getNinjaGotShot() {
		return ninjaGotShot;
	}

	/**
	 * Gets whether or not the spy sees a ninja from looking.
	 * 
	 * @return true if a ninja was spotted, false if not.
	 */
	public boolean getNinjaSpotted() {
		return ninjaSpotted;
	}

	/**
	 * Gets if the spy cannot enter the room which is from the left, bottom, and
	 * right.
	 * 
	 * @return true if spy is underneath or next to room, false if on top.
	 */
	public int getCannotEnter() {
		return cannotEnter;
	}

	/**
	 * Gets the number of lives the spy has remaining.
	 * 
	 * @return number of remaining lives of the spy.
	 */
	public int getNumberOfLives() {
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		return ((Spy) (grid.getBoardPieceAt(c, d))).getNumberOfLives();
	}

	/**
	 * Gets whether or not a room is blocking the spy's vision from looking.
	 * 
	 * @return true if a room is blocking the spy, false if not.
	 */
	public boolean getRoomBlock() {
		return roomBlock;
	}

	/**
	 * Changes the debug from either true to false, or false to true when
	 * called.
	 */
	public void setIsDebug() {
		isDebug = !isDebug;
	}

	/**
	 * Gets if the debug is on or off.
	 * 
	 * @return true if debug is on, false if off.
	 */
	public boolean getIsDebug() {
		return isDebug;
	}

}
