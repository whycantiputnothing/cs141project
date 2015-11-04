
public class Room extends BoardPiece{

	private boolean hasBriefcase;
	
	public Room(String s, boolean b) {
		super(s);
		hasBriefcase = b;
		// TODO Auto-generated constructor stub
	}

	public boolean getHasBriefcase(){
		return hasBriefcase;
	}
}
