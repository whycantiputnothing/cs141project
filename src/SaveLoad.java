import java.io.*;

/**
 * The SaveLoad class saves and loads states of the game engine.
 */

public class SaveLoad {

	/**
	 * Saves the current state of the game by saving the entire game engine.
	 * 
	 * @param ge
	 *            game engine to be saved
	 * @param s
	 *            name of the save state created by the player
	 */
	public static void Save(GameEngine ge, String s) {
		try {

			FileOutputStream fos = new FileOutputStream(s);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(ge);
			oos.close();
		} catch (IOException e) {
			System.out.println("Unable to save the game");
		}

	}

	/**
	 * Loads a saved state of the game.
	 * 
	 * @param s
	 *            name of the file to be loaded
	 * @return game engine of the loaded save
	 */
	public static GameEngine Load(String s) {
		GameEngine save = null;
		try {

			FileInputStream fis = new FileInputStream(s);
			ObjectInputStream ois = new ObjectInputStream(fis);

			save = (GameEngine) ois.readObject();
			fis.close();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Unable to load previously saved game.");
		}
		return save;
	}
}
