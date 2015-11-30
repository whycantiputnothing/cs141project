public class Spy extends BoardPiece {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ammoCount = 1;
	
	private int numberOfLives = 3;

	public Spy() {
		super("S");
		super.setIsVisible(true);
		// TODO Auto-generated constructor stub
	}
	
	public void setAmmoCount(int i){
		ammoCount = i;
	}
	
	public int getAmmoCount() {
		return ammoCount;
	}
	
	public void death(){
		numberOfLives--;
	}
	
	public int getNumberOfLives(){
		return numberOfLives;
	}
	
	public void setNumberOfLives(int i){
		numberOfLives = i;
	}
}
