import java.io.File;
import java.util.*;

// test
public class UI {

	private GameEngine GE = null;
	private Scanner in = null;
	private int option;
	private boolean turn;

	public UI(GameEngine game) {
		GE = game;
		in = new Scanner(System.in);
	}

	private int mainMenu() {
		int option;
		System.out.println("Please choose an option:\n" + "\t1. New Game\n" + "\t2. Load Game\n" + "\t3. Quit");
		option = in.nextInt();
		in.nextLine();
		return option;
	}

	public void startGame() {
		welcomeMessage();
		GE.reset();
		boolean quit = false;
		while (!quit) {
			int option = mainMenu();

			switch (option) {
			case 1:
				System.out.println("New game started.\n");
				gameLoop();
				break;
			case 2:
				GE = SaveLoad.Load(load());
				gameLoop();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}

	private void gameLoop() {

		while (!GE.gameWon()&&!GE.gameLost()) {
			GE.hidePieces();
			
			boolean turn1 = true;
			while(turn1){
				powerUp();
				GE.lookAround();
				System.out.println(GE.gridToString());
				System.out.println("What would you like to do?:\n" + "(0)Look (1)Other Actions (2)Save Game (3)Quit (4)Debug");
				option = in.nextInt();
				in.nextLine();
				
				if (option == 2) {
					SaveLoad.Save(GE , save());
				} 
				
				else if (option == 3) {
					System.exit(0);
				} 
				
				else if (option == 0) {
					choice1();
					System.out.println(GE.gridToString());
					choice2();
					turn1 = false;
				} 
				
				else if(option == 1){
					choice2();
					turn1 = false;
				}
				
				else if(option == 4){
					GE.debug();
					GE.setIsDebug();
				}
				
				else {
					System.out.println("Please input an integer between 0 and 3");
				}
			}
			GE.debugHelper();
			GE.moveNinja();
			GE.ninjaStab();
			dead();
		}
		gameOver();
	}
	
	private void choice1() {
		turn = true;
		while (turn) {
			System.out.println("What direction would you like to look?:\n" + "(0)Up (1)Left (2)Down (3)Right");
			option = in.nextInt();
			in.nextLine();
			
			if (option < 0 || option > 3) {
				System.out.println("Please input an integer between 0 and 3");
			} 
			
			else if (option == 4){
				GE.debug();
				GE.setIsDebug();
				System.out.println(GE.gridToString());
			}
			
			else if(GE.canSpyLook(option)){
					GE.look(option);
					
					if(GE.getNinjaSpotted()){
						System.out.println("Ninja ahead!");
					}
					
					else if (GE.getRoomBlock()){
						System.out.println("There is a room blocking your view");
					}
					
					else{
						System.out.println("The coast is clear!");
					}
					turn = false;
				}
			
			else {
				System.out.println("There is nothing to see there");
			}
		}
	}
	
	private void choice2(){
		turn = true;
		while (turn) {
			System.out.println("What would you like to do?:\n" + "(0)Move (1)Shoot (2)Check Ammo");
			option = in.nextInt();
			in.nextLine();
			
			if (option == 1) {
				shoot();
			} 
			
			else if (option == 2) {
				System.out.println("You have " + GE.getNumberOfBullets() + " bullets remaining");
			} 
			
			else if (option == 0) {
				move();
			} 
			
			else if (option == 4) {
				GE.debug();
				GE.setIsDebug();
				System.out.println(GE.gridToString());
			}
			
			else {
				System.out.println("Please input an integer between 0 and 2");
			}
		}
	}

	private void welcomeMessage() {
		System.out.println("Welcome to the Spy Game!\n");
	}
	
	private void move(){
		System.out.println(
				"What direction would you like to move?:\n" + "(0)Up (1)Left (2)Down (3)Right");
		option = in.nextInt();
		in.nextLine();
		
		if (option < 0 || option > 3) {
			System.out.println("Please input a integer between 0 and 3");
		} 
		
		else {
			if (GE.canSpyMove(option)) {
				GE.moveSpy(option);
				if(GE.wrongRoom()){
					System.out.println("The room is empty");
				}
				GE.takeTurn();
				turn = false;
			} 
			
			else {
					if (GE.getCannotEnter() == 1){
						System.out.println("You can only enter a room from the top");
					}
					else {
						System.out.println("You have hit a wall. You cannot move there");
					}
				}
			}
	}

	private void dead() {
		if (GE.getIsAlive() == false) {
			System.out.println("A ninja has stabbed you");
			System.out.println("You have " + (GE.getNumberOfLives() - 1) + " lives remaining");
			GE.respawn();
		}
	}
	
	private void powerUp(){
		if(GE.getGotPowerUp()){
			String s = GE.getPowerUpName();
			if (s.equals("Extra Bullet")){
				System.out.println("You picked up the Power Up: " + s);
				
				if(GE.getHasExtraBullet())
					System.out.println("Here is one more bullet");
				
				else
					System.out.println("Your gun already has a bullet in it. You cannot carry anymore bullets");
				
			GE.setGotPowerUp(false);
			}
			else if(s.equals("Radar")){
				if(!GE.getIsDebug()){
					System.out.println("You picked up the Power Up: " + s);	
				}
				
				int a = GE.briefcasePosition()[0];
				int b = GE.briefcasePosition()[1];
				String[] rows = {"top","", "middle", "bottom"};
				String[] cols = {"left","", "center", "right"};
				System.out.println("The briefcase is in the " + rows[a/2] + " " + cols[b/2] + " room");
				GE.setGotPowerUp(false);
			}
			else {
				if(s.equals("Invincibility")){
					System.out.println("You picked up the Power Up: " + s);
					GE.setGotPowerUp(false);
				}
			}
		}
		
		if(GE.getIsInvincible() && GE.getIsDebug() == false)
			System.out.println("You have " + (5 - (GE.getNumberOfMoves() - GE.getNumberOfMovesCounter())) + " moves of invinicibility remaining");
		GE.resetPowerUpName();
	}
	
	private void shoot(){
		if(GE.getNumberOfBullets() == 1){
			System.out.println("What direction would you like to shoot?:\n"
					+ "(0)Up (1)Left (2)Down (3)Right");
			option = in.nextInt();
			in.nextLine();
			
			if (option < 0 || option > 3) {
				System.out.println("Please input an integer between 0 and 3");
			} 
			
			else {
				GE.shoot(option);
				ninjaShot();
				GE.takeTurn();
				turn = false;
			}
		}
		else
			System.out.println("Your gun has no bullets in it");
		
	}
	
	private void ninjaShot(){
		if(GE.getNinjaGotShot()){
			System.out.println("You have killed a ninja!");
		}
		
		else
			System.out.println("Your bullet hit nothing");
	}
	
	private void gameOver(){
		if(GE.gameLost()){
			System.out.println("Game Over");
			System.out.println("Please choose an option:\n" + "\t1. Main Menu\n" + "\t2. Quit\n");
			option = in.nextInt();
			in.nextLine();
			
			if (option == 1){
				startGame();
			}
			
			else
				System.exit(0);
		}
		
		else if(GE.gameWon()){
			System.out.println("Congradulations! You have found the briefcase!");
			System.out.println("It only took you " + GE.getNumberOfMoves() + " moves");
			System.out.println("Please choose an option:\n" + "\t1. Main Menu\n" + "\t2. Quit\n");
			option = in.nextInt();
			in.nextLine();
		
			if (option == 1){
				startGame();
			}
			else
				System.exit(0);
			
		}
	}
	
	private String save(){
		System.out.println("What would you like to name your savefile?");
		String s = in.nextLine();
		
		if (s.endsWith(".dat"))
			return s;
		
		else {
			s += ".dat";
			return s;
		}
	}
	
	private String load(){
		System.out.println("Which save would you like to load?");
		File directory = new File("C:\\Users\\calvin\\workspace\\cs141project");
		
		// get all the files from a directory
		File[] fList = directory.listFiles();
		List<File> files = new ArrayList<File>();
		for (File file : fList) {
			if (file.getName().toLowerCase().endsWith(".dat")) {
				files.add(file);
			}
		}
		
		for (File file : files) {
			System.out.println(file.getName());
		}
		
		String s = in.nextLine();
		
		if (s.endsWith(".dat"))
			return s;
		
		else {
			s += ".dat";
			return s;
		}
	}

}
