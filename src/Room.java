
public class Room extends BoardPiece{

	private boolean hasBriefcase;
	
	public Room(boolean b) {
		super("R");
		hasBriefcase = b;
		// TODO Auto-generated constructor stub
	}

	public boolean getHasBriefcase(){
		return hasBriefcase;
	}
}
