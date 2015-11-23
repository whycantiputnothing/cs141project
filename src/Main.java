import java.util.Scanner;

public class Main {

    public static void main(String[] args){
    	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	GameEngine ge = new GameEngine();
    	//g.instantiateGrid();
    	//g.debug(true);
    	ge.makeGrid();
    	ge.debug(true);
    	ge.lookAround();
    	ge.printGrid();
    	Scanner sc = new Scanner(System.in);
    	ge.moveNinja();
    	ge.printGrid();
    	ge.moveNinja();
    	ge.printGrid();
    	ge.moveNinja();
    	ge.printGrid();
    	ge.moveNinja();
    	ge.printGrid();
    	
    	//g.shufflePieces();
    	//System.out.println(g.toString());
    	//g.placeRooms();
    	//g.checkNinjaPosition();
    	//System.out.println(g.toString());
//    	g.swapSpace(8, 0, 7, 0);
    	

	}
}
