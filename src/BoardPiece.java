
/**
 * @author Brandon Nguyen
 *
 */
public class BoardPiece {
	private boolean isVisible;
	
	private String pieceType;
	
	public BoardPiece (String s){
		pieceType = s;
	}
	
	public boolean getIsVisible(){
		return isVisible;
	}
	
	public String getPieceType(){
		return pieceType;
	}
	
	public void setIsVisible(boolean b){
		isVisible = b;
	}
	
}
