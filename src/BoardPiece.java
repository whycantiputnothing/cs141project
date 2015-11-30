import java.io.Serializable;

public class BoardPiece implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
