
public class Main {
//test
    public static void main(String[] args){
    	Grid g = new Grid();
    	g.instantiateGrid();
    	g.shufflePieces();
    	String str = g.toString();
    	System.out.println(str);
	}
}
