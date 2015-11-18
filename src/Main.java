
public class Main {

    public static void main(String[] args){
    	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	GameEngine ge = new GameEngine();
    	g.moveNinja();
	}
}
