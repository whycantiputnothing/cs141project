
public class GameEngine {

	private int numberOfMoves;
	
	private boolean isAlive = true;

	private Grid grid = new Grid();

	public void printGrid() {
		System.out.println(grid.toString());

	}

	public void radar() {

	}

	public void makeGrid() {
		grid.instantiateGrid();
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
	
	public void ninjaStab(){
		int[] ninjaPos = grid.findNinja();
		int[] spyPos = grid.findSpy();
		int c = spyPos[0];
		int d = spyPos[1];
		int count = 0;
		int count2 = 0;
		while (count < 6){
			int a = ninjaPos[count2];
			int b = ninjaPos[count2 + 1];
			if(((a + 1) == c) && b == d){
				isAlive = false;
			}
			else if(((a - 1) == c) && b == d){
				isAlive = false;
			}
			else if(((b - 1) == d) && c == a){
				isAlive = false;
			}
			else if(((b + 1) == d) && c == a){
				isAlive = false;
			}
		}
	}

	public void moveNinja() {
		int[] ninjaPos = grid.findNinja();
		boolean notAvailable = false;
		int[] randomNums = {9,9,9,9,9,9,9};
		
		for (int i = 0; i < ninjaPos.length -1; i += 2) {
			int count = 3;
			do {
				
				int moveDir = grid.randNinjaMove();
				//int moveDir = 0;
				if (count > 3){
					while(moveDir == randomNums[count] || moveDir == randomNums[count - 1] 
							|| moveDir == randomNums[count - 2] || moveDir == randomNums[count -3]){
						moveDir = grid.randNinjaMove();
					}
				}
				
				//up
				if (moveDir == 0) {
					if(ninjaPos[i] - 1 > 0){						
						if (grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] - 1, ninjaPos[i + 1]).getPieceType().equals("X")) {
							notAvailable = true;
						} else {
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] - 1, ninjaPos[i + 1]);
							
							notAvailable = false;
						}
					}else {
						notAvailable = true;
					}
					
				}
				
				//down
				else if (moveDir == 1) {
					if(ninjaPos[i] + 1 < 8){
						if (grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i] + 1, ninjaPos[i + 1]).getPieceType().equals("X")) {
							notAvailable = true;
						} else{
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i] + 1, ninjaPos[i + 1]);
							notAvailable = false;
						}
					}else{
						notAvailable = true;
					}
				}
				
				//left
				else if (moveDir == 2) {
					if (ninjaPos[i + 1] - 1 > 0){
						if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] - 1).getPieceType().equals("X")) {
							notAvailable = true;
						} else{
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] - 1);
							notAvailable = false;
						}
					}else {
						notAvailable = true;
					}
				}
				
				//right
				else if (moveDir == 3) {
					if (ninjaPos[i + 1] + 1 < 8){
						if (grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("U")
								|| grid.getBoardPieceAt(ninjaPos[i], ninjaPos[i + 1] + 1).getPieceType().equals("X")) {
							notAvailable = true;
						} else{
							swap(ninjaPos[i], ninjaPos[i + 1], ninjaPos[i], ninjaPos[i + 1] + 1);
							notAvailable = false;
						}
					}else{
						notAvailable = true;
					}
				}
				
				randomNums[count] = moveDir;
				
				count++;
				if (count == 7){
					notAvailable = false;
				}
			} while (notAvailable);
		}
	}

	public void respawn() {
		int[] spyPos = grid.findSpy();
		BoardPiece a = grid.getBoardPieceAt(spyPos[0], spyPos[1]);
		((Spy)(a)).death();
		grid.setBoardPieceAt(spyPos[0], spyPos[1], a);
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
	
	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
	
}
