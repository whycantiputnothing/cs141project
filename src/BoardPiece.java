import java.io.Serializable;

/**
 * @author Brandon Nguyen
 *
 */
public class BoardPiece implements Serializable{
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
	
	public void setIsVisible(){
		isVisible = !isVisible;
	}
	
}
