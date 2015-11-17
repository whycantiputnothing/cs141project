
public class GameEngine {
	
    private int numberOfMoves;
    	
    private Grid grid = new Grid();
    	
	
	public void printGrid(){
		grid.instantiateGrid();
		grid.shufflePieces();
		grid.findSpy();
		grid.placeRooms();
		grid.checkNinjaPosition();
		grid.debug(true);
		System.out.println(grid.toString());
		
	}
	
	private boolean isAlive(int lives){	
		if(lives == 0){
			return false;
		}
		return true;
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
		grid.swapSpace(w, x, y, z);
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
