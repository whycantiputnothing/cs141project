import java.io.Serializable;

/**
 * BoardPiece is a super class that takes in a String s, that represents the
 * type of board piece (a power up, a ninja, etc). It also takes in a boolean to
 * set whether or not the String s board piece should be visible to the user.
 */

public class BoardPiece implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8409883465218665536L;
	private boolean isVisible;
	private String pieceType;

	public BoardPiece(String s) {
		pieceType = s;
	}

	/**
	 * @return boolean true if board piece is visible, otherwise false
	 */
	public boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @return String of the piece type
	 */
	public String getPieceType() {
		return pieceType;
	}

	/**
	 * @param b
	 *            boolean true if visible, false if not
	 */
	public void setIsVisible(boolean b) {
		isVisible = b;
	}

	/**
	 * sets a piece that is visible to not visible
	 */
	public void setIsVisible() {
		isVisible = !isVisible;
	}

}
