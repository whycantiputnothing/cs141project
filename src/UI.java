import java.util.*;

// test
public class UI {

	private GameEngine GE = null;
	private Scanner in = null;

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
		boolean quit = false;
		while (!quit) {
			int option = mainMenu();

			switch (option) {
			case 1:
				gameLoop();
				break;
			case 2:
				// GE.loadGame();
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
		GE.reset();
		GE.debug(true);
		System.out.println("New game started.\n");

		String[] choice = { "w", "a", "s", "d" };

		while (!GE.gameWon()) {
			powerUp();
			dead();
			
			GE.printGrid();
			int option;
			System.out.println("What would you like to do?:\n" + "(0)Look (1)Save Game (2)Quit");
			option = in.nextInt();
			in.nextLine();
			if (option == 1) {

			} else if (option == 2) {

			} else if (option == 0) {
				boolean turn = true;
				while (turn) {
					System.out.println("What direction would you like to look?:\n" + "(0)Up (1)Left (2)Down (3)Right");
					option = in.nextInt();
					in.nextLine();
					if (option < 0 || option > 3) {
						System.out.println("Please input a valid choice");
					} else {
						GE.look(choice[option]);
						GE.printGrid();
						while (turn) {
							System.out.println("What would you like to do?:\n" + "(0)Move (1)Shoot (2)Check Ammo");
							option = in.nextInt();
							in.nextLine();
							if (option == 1) {
								System.out.println("What direction would you like to shoot?:\n"
										+ "(0)Up (1)Left (2)Down (3)Right");
								option = in.nextInt();
								in.nextLine();
								if (option < 0 || option > 3) {
									System.out.println("Please input an integer between 0 and 3");
								} else {
									GE.shoot(choice[option]);
									GE.takeTurn();
									turn = false;
								}

							} else if (option == 2) {

							} else if (option == 0) {
								System.out.println(
										"What direction would you like to move?:\n" + "(0)Up (1)Left (2)Down (3)Right");
								option = in.nextInt();
								in.nextLine();
								if (option < 0 || option > 3) {
									System.out.println("Please input a integer between 0 and 3");
								} else {
									if (GE.canSpyMove(choice[option])) {
										GE.moveSpy(choice[option]);
										GE.takeTurn();
										turn = false;
									} else
										System.out.println("You cannot move there");
								}

							} else {
								System.out.println("Please input an integer between 0 and 2");
							}
						}
					}
				}

			} else {
				System.out.println("Please input an integer between 0 and 3");
			}
			GE.moveNinja();
		}
	}

	private void welcomeMessage() {
		System.out.println("Welcome to the Spy Game!\n");
	}

	private void dead() {
		if (!GE.getIsAlive()) {
			System.out.println("A ninja has stabbed you");
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
				System.out.println("You picked up the Power Up: " + s);
				int a = GE.briefcasePosition()[0];
				int b = GE.briefcasePosition()[1];
				String[] rows = {"top", "middle", "bottom"};
				String[] cols = {"left", "center","", "right"};
				System.out.println("The briefcase is in the " + rows[a/2] + " " + cols[b/2] + " room");
				GE.setGotPowerUp(false);
			}
			else {
				if(GE.getNumberOfMoves() == GE.getNumberOfMovesCounter()){
					System.out.println("You picked up the Power Up: " + s);
				}
			}
			
			if((GE.getNumberOfMoves()-GE.getNumberOfMovesCounter() > 0) && (GE.getNumberOfMoves() - GE.getNumberOfMovesCounter() < 5 ))
				System.out.println("You have " + (5 - (GE.getNumberOfMoves() - GE.getNumberOfMovesCounter())) + " turns of invinicibility left");
		}
	}

}
