
/**
 * @author Brandon Nguyen
 *
 */
public class BoardPiece {
	private boolean isVisible;
	
	protected boolean hasBriefcase;
	
	private String pieceType;
	
	public BoardPiece (String s){
		pieceType = s;
	}
	
	public boolean getHasBriefcase(){
		return hasBriefcase;
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
