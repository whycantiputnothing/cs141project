
public class Main {

    public static void main(String[] args){
    	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	g.instantiateGrid();
    	g.shufflePieces();
    	System.out.println(g.toString());
    	g.putSpyAtStart();
    	g.placeRooms();
    	g.checkNinjaPosition();
    	System.out.println(g.toString());
    	g.debug(true);
    	System.out.println(g.toString());
	}
}
