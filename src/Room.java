
public class Room extends BoardPiece{

	private boolean hasBriefcase;
	
	private boolean isBriefcaseVisible;
	
	public Room(boolean b) {
		super("U");
		hasBriefcase = b;
		super.setIsVisible(true);
		// TODO Auto-generated constructor stub
	}

	public boolean getHasBriefcase(){
		return hasBriefcase;
	}
	
	public String getPieceType(){
		if(hasBriefcase && isBriefcaseVisible)
			return "X";
		else
			return super.getPieceType();
	}
}
