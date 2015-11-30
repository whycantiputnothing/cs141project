import java.io.*;
import java.util.*;

/**
 * 
 */

public class SaveLoad {
	
	public static void Save(GameEngine ge, String s){
		 try { 
	
			  FileOutputStream fos = new FileOutputStream(s);
		      ObjectOutputStream oos = new ObjectOutputStream(fos);

		      oos.writeObject(ge);
		      oos.close();
		    } catch (IOException e) {
		      System.out.println("Unable to save the game");
		    }
				
	}		

	public static GameEngine Load(String s){
		GameEngine save = null;
		try{
		 
			FileInputStream fis = new FileInputStream(s);
		 	ObjectInputStream ois = new ObjectInputStream(fis);
		 	
		 	save = (GameEngine) ois.readObject();
		 	fis.close();
		 	
		 	
		 }catch(IOException | ClassNotFoundException e){
		 	System.out.println("Unable to load previously saved game.");
		 }
		 return save;
	}
}

	
