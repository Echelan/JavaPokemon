/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Andres
 */
public class Game implements Runnable{
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		// <editor-fold defaultstate="collapsed" desc="Statics">
			/**
			 * Main data for Pokemon.
			 */
			public static List<String> INFOPOKEMON;
			/**
			 * Main data for Items.
			 */
			public static List<String> INFOITEMS;
			/**
			 * Main data for Moves.
			 */
			public static List<String> INFOMOVES;
			/**
			 * Main data for Types.
			 */
			public static List<String> INFOTYPES;
			/**
			 * Main data for Maps.
			 */
			public static ArrayList<List<String>> INFOMAPS;
			/**
			 * Amount of Maps.
			 */
			public static int NUMMAPS;
		//</editor-fold>
		
		/**
		 * The Player in game.
		 */
		public static Player player;
		/**
		 * Steps needed to spawn a Pokemon.
		 */
		public static int stepsToSpawn = roll(1,2,3);;
		/**
		 * Current Pokemon in game.
		 */
		public static Pokemon currentPokemon;
		/**
		 * Game Window. (The one with the canvas.)
		 */
		private static GameWindow windowGame;
		/**
		 * Class Test Window. (The one with the buttons and pictures.)
		 */
		private static ClassTestWindow windowClassTest;
		/**
		 * Map Builder Window. (The one with the map.)
		 */
		private static MapBuilder windowMapBuilder;
	// </editor-fold>
		
	/**
	 * Build game.
	 */
	public Game() {
		
		NUMMAPS = 2;
		
		SplashWindow splash = new SplashWindow();

		List<String> readInfoP = null;
		List<String> readInfoI = null;
		List<String> readInfoM = null;
		List<String> readInfoT = null;
		ArrayList<List<String>> readMap = new ArrayList();
		
		try {
			File archivo;
			
			archivo = new File("listPokemon.txt");
			readInfoP = Files.readAllLines(archivo.toPath());

			archivo = new File("listItems.txt");
			readInfoI = Files.readAllLines(archivo.toPath());

			archivo = new File("listMoves.txt");
			readInfoM = Files.readAllLines(archivo.toPath());

			archivo = new File("listTypes.txt");
			readInfoT = Files.readAllLines(archivo.toPath());
			
			for (int i = 0; i < NUMMAPS; i++) {
				archivo = new File("Map"+i+".txt");
				List<String> temp = Files.readAllLines(archivo.toPath());
				readMap.add(temp);
			}
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		INFOITEMS = readInfoI;
		INFOPOKEMON = readInfoP;
		INFOMOVES = readInfoM;
		INFOTYPES = readInfoT;
		INFOMAPS = readMap;
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
		}
		
		splash.dispose();
		
		player = new Player ("Red","EEVEE");
		player.addItem("POKEBALL",15);
		
		Thread playerThread = new Thread(player);
		playerThread.start();
			
		windowClassTest = new ClassTestWindow();
		windowGame = new GameWindow();
		windowMapBuilder = new MapBuilder();
		
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
		
		return value;
	}

	@Override
	public void run() {
		Scanner s = new Scanner(System.in);
		System.out.println("1-ClassTest; 2-Game; 4-MapBuilder");
		while(true){
			String input = s.next();
			try{
				int convertedInput = Integer.parseInt(input);
				switch(convertedInput){
					case 1:
					//	classTestWindow=new ClassTestWindow();
					//	new ClassTestWindow();
						windowClassTest.setVisible(true);
					break;
					case 2:
					//	gameWindow=new GameWindow();
					//	new GameWindow();
						windowGame.setVisible(true);
					break;
					case 3:
					//	classTestWindow=new ClassTestWindow();
					//	new ClassTestWindow();
						windowClassTest.setVisible(true);
					//	gameWindow=new GameWindow();
					//	new GameWindow();
						windowGame.setVisible(true);
					break;
					case 4:
					//	new MapBuilder();
						windowMapBuilder.setVisible(true);
					break;
					case 5:
					//	new MapBuilder();
						windowMapBuilder.setVisible(true);
					//	classTestWindow=new ClassTestWindow();
					//	new ClassTestWindow();
						windowClassTest.setVisible(true);
					break;
					case 6:
					//	new MapBuilder();
						windowMapBuilder.setVisible(true);
						//gameWindow=new GameWindow();
					//	new GameWindow();
						windowGame.setVisible(true);
					break;
					case 7:
					//	classTestWindow=new ClassTestWindow();
					//	gameWindow=new GameWindow();
					//	new ClassTestWindow();
						windowClassTest.setVisible(true);
					//	new GameWindow();
						windowGame.setVisible(true);
					//	new MapBuilder();
						windowMapBuilder.setVisible(true);
					break;
					default:
						System.exit(0);
					break;
				}
			}catch (NumberFormatException ex){
				
			}
			
		}
	}
	
}
