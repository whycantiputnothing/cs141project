
public class Main {
//test
    public static void main(String[] args){
    	Grid g = new Grid();
    	g.instantiateGrid();
    	g.shufflePieces();
    	System.out.println(g.toString());
    	g.putSpyAtStart();
    	g.placeRooms();
    	g.checkNinja();
    	System.out.println(g.toString());
	}
}
