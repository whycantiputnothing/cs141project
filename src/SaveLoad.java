import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 */

public class SaveLoad {
	
	public static void Save(GameEngine ge){
		 try { 
	
			  FileOutputStream fos = new FileOutputStream("GameEngine.dat");
		      ObjectOutputStream oos = new ObjectOutputStream(fos);

		      oos.writeObject(ge);
		      oos.close();
		    } catch (IOException e) {
		      System.out.println("Unable to save the game");
		    }
				
	}		

	public static GameEngine Load(){
		GameEngine save = null;
		try{
		 
			FileInputStream fis = new FileInputStream("GameEngine.dat");
		 	ObjectInputStream ois = new ObjectInputStream(fis);
		 	
		 	save = (GameEngine) ois.readObject();
		 	fis.close();
		 	
		 	
		 }catch(IOException | ClassNotFoundException e){
		 	System.out.println("Unable to load previously saved game.");
		 }
		 return save;
	}
}
