
public class GameEngine {
	
    private int numberOfMoves;
    	
    private Grid grid = new Grid();
    	
	
	public void printGrid(){
		System.out.println(grid.toString());
		
	}
	
	public void radar() {
		
	}
	
	public void makeGrid(){
		grid.instantiateGrid();
	}
	
	private boolean isAlive(int lives){	
		if(lives == 0){
			return false;
		}
		return true;
	}
	
	public void move(String s){
		if (s.toLowerCase().equals("w")){
			
			grid.swapSpace(grid.findSpy()[0], grid.findSpy()[1],grid.findSpy()[0] - 1, grid.findSpy()[1]);
		}
		else if (s.toLowerCase().equals("a")){
			
			grid.swapSpace(grid.findSpy()[0], grid.findSpy()[1],grid.findSpy()[0], grid.findSpy()[1] - 1);
		}
		else if (s.toLowerCase().equals("s")){
			
			grid.swapSpace(grid.findSpy()[0], grid.findSpy()[1],grid.findSpy()[0] + 1, grid.findSpy()[1]);
		}
		else if (s.toLowerCase().equals("d")){
			
			grid.swapSpace(grid.findSpy()[0], grid.findSpy()[1],grid.findSpy()[0], grid.findSpy()[1] + 1);
		}
	}
	
	public void lookAtSpaceU(int x, int y){
		grid.getBoardPieceAt(x - 1, y).setIsVisible(true);
	}
	
	public void lookAtSpaceD(int x, int y){
		grid.getBoardPieceAt(x + 1, y).setIsVisible(true);
	}
	
	public void lookAtSpaceL(int x, int y){
		grid.getBoardPieceAt(x, y - 1).setIsVisible(true);
	}
	
	public void lookAtSpaceR(int x, int y){
		grid.getBoardPieceAt(x, y + 1).setIsVisible(true);
	}
	
	public BoardPiece returnLocationOfSpy(int x, int y){
		return grid.getBoardPieceAt(x, y);
	}
	
	public void respawn(){
		
	}
	
	public void invincibility(){
	
	}
	
	public void swap(int w, int x, int y, int z){
			BoardPiece a = grid.getBoardPieceAt(w, x);
			BoardPiece b = grid.getBoardPieceAt(y, z);
			grid.setBoardPieceAt(w, x, a);
			grid.setBoardPieceAt(y, z, b);
	}

	public void quitGame(){
		
	}
	
	public void saveGame(){
		
	}
	
	public void loadGame(){
		
	}
	
	public void reset(){
		
	}

	public boolean gameWon() {
		// TODO Auto-generated method stub
		return false;
	}
}
