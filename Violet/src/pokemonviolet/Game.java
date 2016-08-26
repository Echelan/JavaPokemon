/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;


/**
 *
 * @author Andres
 */
public class Game {
	
	public static List<String> INFOPOKEMON;
	public static List<String> INFOITEMS;
	public static List<String> INFOMOVES;
	public static List<String> INFOTYPES;
	
	public static Player player;
	public static int stepsToSpawn = roll(1,2,3);;
	public static Pokemon currentPokemon;
	private static GameWindow gameWindow;
	private static ClassTestWindow classTestWindow;
	
	/**
	 * Build game.
	 */
	public Game() {
		
		SplashWindow splash = new SplashWindow();

		List<String> readInfoP = null;
		List<String> readInfoI = null;
		List<String> readInfoM = null;
		List<String> readInfoT = null;
		try {
				File archivo = new File("listPokemon.txt");
				readInfoP = Files.readAllLines(archivo.toPath());

				archivo = new File("listItems.txt");
				readInfoI = Files.readAllLines(archivo.toPath());

				archivo = new File("listMoves.txt");
				readInfoM = Files.readAllLines(archivo.toPath());

				archivo = new File("listTypes.txt");
				readInfoT = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
				System.err.println("Couldn't load files!");
				System.exit(0);
		}

		INFOITEMS = readInfoI;
		INFOPOKEMON = readInfoP;
		INFOMOVES = readInfoM;
		INFOTYPES = readInfoT;


		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}

		splash.dispose();

		player = new Player ("Red",1);
		player.addItem("POKEBALL",15);

	    classTestWindow=new ClassTestWindow();
		gameWindow=new GameWindow();
			
	}
	
	/**
	 * Roll given amount of dice with given amount of sides with a starting base value.
	 * @param value Starting base value.
	 * @param numDice Number of dice to roll.
	 * @param numSides Number of sides per die.
	 * @return Resulting value.
	 */
	public static int roll(int value, int numDice, int numSides){
		Random rnd = new Random();

		for (int i = 0; i < numDice; i++) {
		  value = value + (rnd.nextInt(numSides)+1);
		}
//		System.out.println(steps);
		return value;
	}
}
