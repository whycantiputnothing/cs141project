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
		System.out.println("New game started.\n");

		String[] choice = { "w", "a", "s", "d" };

		while (!GE.gameWon()) {
			if (!(GE.gotPowerup() == null)) {
				System.out.println("You got a: " + GE.gotPowerup());
				if (!GE.getIsAlive()) {
					System.out.println("A ninja has stabbed you");
					GE.respawn();
				}
			} else if (!GE.getIsAlive()) {
				System.out.println("A ninja has stabbed you");
				GE.respawn();
			}

			GE.printGrid();
			int option;
			int option2;
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
									GE.Shoot(choice[option]);
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
		}
	}

	private void welcomeMessage() {
		System.out.println("Welcome to the Spy Game!\n");
	}

	public static void deadMessage() {
		System.out.println("You died. You will be placed at the starting point.");
	}

}
