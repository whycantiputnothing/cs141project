
public class Grid {

	private BoardPiece[][] grid = new BoardPiece[9][9];
	
	 
		
	public void instantiateGrid(){
		int x = 0;
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				if (x < 68){
					grid[i][j] = new BoardPiece("E");
				}
			}
		}
		grid[7][5] = new Ninja();
		grid[7][6] = new Ninja();
		grid[7][7] = new Ninja();
		grid[7][8] = new Spy();
		grid[8][0] = new Room(false);
		grid[8][1] = new Room(false);
		grid[8][2] = new Room(false);
		grid[8][3] = new Room(false);
		grid[8][4] = new Room(false);
		grid[8][5] = new Room(false);
		grid[8][6] = new PowerUps("B");
		grid[8][7] = new PowerUps("I");
		grid[8][8] = new PowerUps("R");
	
		
	}
	
	public void shuffle() {
		
	}
	
	public void getBoardPieceAt(){
		
	}

	
	public String toString() {
		String printPosition = "";
		 for (BoardPiece[] b : grid){
			 for (BoardPiece p : b){
				 printPosition += "[" + p.getPieceType() + "]";
			 }
			 printPosition += "\n";
			 
		 }
		return printPosition;
		
	}
}	
