/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.util.Random;


/**
 *
 * @author Andres
 */
public class Game {
	
	public static Player player;
	public static int stepsToSpawn = calcSteps();;
    public static Pokemon currentPokemon;
	private static GameWindow gameWindow;
/*
	public int getStepsToSpawn() {
		return stepsToSpawn;
	}

	public void setStepsToSpawn(int stepsToSpawn) {
		this.stepsToSpawn = stepsToSpawn;
	}

	public void setCurrentPokemon(Pokemon currentPokemon) {
		this.currentPokemon = currentPokemon;
	}

	public Pokemon getCurrentPokemon() {
		return currentPokemon;
	}
*/
	public Game() {
		
		SplashWindow splash = new SplashWindow();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
		//	Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	//	splash.setVisible(false);
		splash = null;
		
		player = new Player ("Red",1);
		player.addItem("POKEBALL",15);
		
		gameWindow=new GameWindow();
		
	}
	/*
	public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		Thread.sleep(100);
		
		Game game = new Game();
	//	splash.setVisible(false);
		splash = null;
		
		player = new Player ("Red",1);
		player.addItem("POKEBALL",15);
		
		gameWindow=new GameWindow();
		
    }
	*/
	public static int calcSteps(){
		int steps = 1;
		int numDados = 2;
		int numLados = 3;
		
		Random rnd = new Random();

		for (int i = 0; i < numDados; i++) {
		  steps = steps + (rnd.nextInt(numLados)+1);
		}
	//	System.out.println(steps);
		return steps;
	}
}
