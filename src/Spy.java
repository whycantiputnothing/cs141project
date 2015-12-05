public class Spy extends BoardPiece {

	/**
	 * Subclass of BoardPiece which holds own unique attributes to its objects
	 * when instantiated.
	 */
	private static final long serialVersionUID = -829564556790736540L;

	private int ammoCount = 1;

	private int numberOfLives = 3;

	public Spy() {
		super("S");
		super.setIsVisible(true);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param i
	 *            ammo left in gun, either 0 or 1.
	 */
	public void setAmmoCount(int i) {
		ammoCount = i;
	}

	/**
	 * @return gets remaining number of ammo, 0 or 1.
	 */
	public int getAmmoCount() {
		return ammoCount;
	}

	/**
	 * Method that depletes number of lives by 1 when stabbed.
	 */
	public void death() {
		numberOfLives--;
	}

	/**
	 * @return returns the number of lives of the spy.
	 */
	public int getNumberOfLives() {
		return numberOfLives;
	}

	/**
	 * @param i
	 *            sets the number of lives of the spy.
	 */
	public void setNumberOfLives(int i) {
		numberOfLives = i;
	}
}
