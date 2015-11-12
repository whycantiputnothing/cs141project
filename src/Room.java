
public class Room extends BoardPiece{

	private boolean hasBriefcase;
	
	public Room(boolean b) {
		super("U");
		hasBriefcase = b;
		// TODO Auto-generated constructor stub
	}

	public boolean getHasBriefcase(){
		return hasBriefcase;
	}
	
	public String getPieceType(){
		if(hasBriefcase)
			return "X";
		else
			return super.getPieceType();
	}
}
