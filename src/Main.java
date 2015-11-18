
public class Main {

    public static void main(String[] args){
    	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	GameEngine ge = new GameEngine();
<<<<<<< HEAD
    	g.moveNinja();
=======
    	//g.instantiateGrid();
    	//g.debug(true);
    	
    	
    	//g.shufflePieces();
    	//System.out.println(g.toString());
    	//g.placeRooms();
    	//g.checkNinjaPosition();
    	//System.out.println(g.toString());
//    	g.swapSpace(8, 0, 7, 0);
    	
    	ge.makeGrid();
    	ge.printGrid();
    	ge.move("w");
    	ge.printGrid();
    	ge.move("d");
    	ge.printGrid();
    	ge.move("s");
    	ge.printGrid();
    	ge.move("a");
    	ge.printGrid();
	
>>>>>>> 36b3acd6780dc6294d06bbf22aad7e4b03017af6
	}
}
