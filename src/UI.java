import java.util.*;
// test
public class UI {

	private static GameEngine GE = null;
	private static Scanner in = null;	
	
	public UI(GameEngine game){
		UI.GE = game;
		in = new Scanner(System.in);
	}
	
	private int mainMenu(){
		int option;
		System.out.println("Please choose an option:\n" + "\t1. New Game\n" + "\t2. Load Game\n" + "\t3. Quit");
		option = in.nextInt();
		in.nextLine();
		return option;
	}
	
	public void startGame(){
		welcomeMessage();
		boolean quit = false;
		while(!quit){
			int option = mainMenu();
			
			switch (option){
			case 1:
				gameLoop();
				break;
			case 2:
				GE.loadGame();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}
	
	private void gameLoop(){
		GE.reset();
		System.out.println("New game started.\n");
		GE.printGrid();
		
		while(!GE.gameWon()){
			int option;
			int option2;
			System.out.println("What would you like do to?:\n" + "(1)Look Up (2)Look Down (3)Look Right (4)Look Left (5)Save Game (6)Quit");
			option = in.nextInt();
			switch (option){
			case 1:
				System.out.println("Choose to:\n" + "(1)Move up (2)Move Down (3)Move Right (4)Move Left\n" + "(5)Shoot Up (6)Shoot Down (7)Shoot Right (8)Shoot Left");
				option2 = in.nextInt();
				switch (option2){
				case 1:
					
				}
			}
		}
	}
	
	private void welcomeMessage() {
		System.out.println("Welcome to the Spy Game!\n");
	}

	
	
	public static void deadMessage(){
		System.out.println("You died. You will be placed at the starting point.");
	}
	

	


}
