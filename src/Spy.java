public class Spy extends BoardPiece {
	
	private int ammoCount;
	
	private int numberOfLives;

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

	public void spyShoot(){
		
	}
	
	public void spyLook(){
		
	}
	
	public void getPowerup(){
		
	}

	
}
