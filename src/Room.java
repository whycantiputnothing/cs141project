
public class Room extends BoardPiece {

	/**
	 * Subclass of BoardPiece. Contains unique attributes pertaining to only
	 * Rooms of the game.
	 */
	private static final long serialVersionUID = 842583909932733772L;

	private boolean isBriefcaseVisible = false;

	private boolean hasBriefcase = false;

	public Room(boolean b) {
		super("U");
		hasBriefcase = b;
		super.setIsVisible(true);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return true if room has briefcase, false otherwise
	 */
	public boolean getHasBriefcase() {
		return hasBriefcase;
	}

	/**
	 * @param vis
	 *            true if radar is found by the spy
	 */
	public void setIsBriefcaseVisible(boolean vis) {
		isBriefcaseVisible = vis;
	}

	/**
	 * @return boolean true if spy picks up radar, false otherwise
	 */
	public boolean getIsBriefcaseVisible() {
		return isBriefcaseVisible;
	}

	public String getPieceType() {
		if (hasBriefcase && isBriefcaseVisible)
			return "X";
		else
			return super.getPieceType();
	}

	public String toString() {
		return "Room";
	}
}
