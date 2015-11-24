
public class Room extends BoardPiece{
	
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
	
	public void setIsBriefcaseVisible(boolean vis){
		isBriefcaseVisible = vis;
	}
	
	public String getPieceType(){
		if(hasBriefcase && isBriefcaseVisible)
			return "X";
		else
			return super.getPieceType();
	}
}
