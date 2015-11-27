import java.util.Scanner;

public class Main {

    public static void main(String[] args){
    //	Grid g = new Grid();
    	UI ui = new UI(new GameEngine());
    	//GameEngine ge = new GameEngine();
/*
    	ge.makeGrid();
    	ge.debug(true);
    	ge.lookAround();
    	ge.printGrid();
    	ge.moveSpy("W");
    	ge.printGrid();
    	ge.moveSpy("W");
    	ge.printGrid();
    	ge.moveSpy("W");
    	ge.printGrid();
    	ge.moveSpy("W");
    	ge.printGrid();
    	ge.Shoot("W");
    	ge.printGrid();
    	ge.moveSpy("s");
    	ge.moveSpy("s");
    	ge.moveSpy("s");
    	ge.printGrid();
*/
    //	ge.debug(true);
    	ui.startGame();

	}
}
