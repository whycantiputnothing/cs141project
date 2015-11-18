
public class Main {

    public static void main(String[] args){
    	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	GameEngine ge = new GameEngine();
//    	g.instantiateGrid();
//    	g.debug(true);
//    	System.out.println(g.toString());
//    	g.swapSpace(8, 0, 7, 0);
    	ge.makeGrid();
    	ge.printGrid();
    	ge.move("w");
    	ge.printGrid();
	}
}
