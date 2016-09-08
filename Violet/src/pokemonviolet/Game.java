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
			public static List<String> INFO_POKEMON;
			/**
			 * Main data for Items.
			 */
			public static List<String> INFO_ITEMS;
			/**
			 * Main data for Moves.
			 */
			public static List<String> INFO_MOVES;
			/**
			 * Main data for Types.
			 */
			public static List<String> INFO_TYPES;
			/**
			 * Main data for Maps.
			 */
			public static ArrayList<List<String>> INFO_MAPS;
			/**
			 * Blank map data.
			 */
			public static List<String> INFO_BLANK_MAP;
			/**
			 * Amount of Maps in X.
			 */
			public static int NUM_MAPS_X;
			/**
			 * Amount of Maps in Y.
			 */
			public static int NUM_MAPS_Y;
		//</editor-fold>
		
		/**
		 * The Player in game.
		 */
		public static Player player;
		/**
		 * Steps needed to spawn a Pokemon.
		 */
	//	public static int stepsToSpawn = roll(1,2,3);
		/**
		 * Current Pokemon in game.
		 */
		public static Pokemon enemyPokemon;
		/**
		 * Game Window. (The one with the canvas.)
		 */
	//	private static GameWindow windowGame;
		/**
		 * Class Test Window. (The one with the buttons and pictures.)
		 */
	//	private static ClassTestWindow windowClassTest;
		/**
		 * Map Builder Window. (The one with the map.)
		 */
	//	private static MapBuilder windowMapBuilder;
		/**
		 * Maps displayed.
		 */
		public static Map[][] displayedMaps;
		/**
		 * Canvas size in X dimension.
		 */
		public static int SCREEN_SIZE_X;
		/**
		 * Canvas size in Y dimension.
		 */
		public static int SCREEN_SIZE_Y;
	// </editor-fold>
		
	/**
	 * Build game.
	 * @param openID ID of window to open.
	 */
	public Game(String openID) {
		
		NUM_MAPS_X = 3;
		NUM_MAPS_Y = 3;
		
		SCREEN_SIZE_X = 592;
		SCREEN_SIZE_Y = 469;
		
		displayedMaps = new Map[3][3];
		
		SplashWindow splash = new SplashWindow();

		List<String> readInfoP = null;
		List<String> readInfoI = null;
		List<String> readInfoM = null;
		List<String> readInfoT = null;
		List<String> readInfoB = null;
		ArrayList<List<String>> readMap = new ArrayList();
		
		File archivo;
		
		try {
			
			archivo = new File("listPokemon.txt");
			readInfoP = Files.readAllLines(archivo.toPath());
			
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listItems.txt");
			readInfoI = Files.readAllLines(archivo.toPath());
			
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listMoves.txt");
			readInfoM = Files.readAllLines(archivo.toPath());
			
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listTypes.txt");
			readInfoT = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("mapBLANK.txt");
			readInfoB = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}
			
		
		for (int y = 0; y < NUM_MAPS_Y; y++) {
			for (int x = 0; x < NUM_MAPS_X; x++) {
				List<String> temp = null;
				try {
					archivo = new File("mapX"+x+"Y"+y+".txt");
					temp = Files.readAllLines(archivo.toPath());
				} catch (IOException ex1) {
				//	System.err.println("Couldn't load files!");
					try {
						archivo = new File("mapBLANK.txt");
						temp = Files.readAllLines(archivo.toPath());
					} catch (IOException ex2) {

					}
				}
				readMap.add(temp);
			}
		}
		

		INFO_ITEMS = readInfoI;
		INFO_POKEMON = readInfoP;
		INFO_MOVES = readInfoM;
		INFO_TYPES = readInfoT;
		INFO_MAPS = readMap;
		INFO_BLANK_MAP = readInfoB;
		
		player = new Player ("Red","EEVEE");
		player.addItem("POKEBALL",15);
		
		Thread playerThread = new Thread(player);
		playerThread.start();
		
	//	windowClassTest = new ClassTestWindow(javax.swing.JFrame.HIDE_ON_CLOSE,false);
	//	windowMapBuilder = new MapBuilder(javax.swing.JFrame.HIDE_ON_CLOSE,false);
	//	windowGame = new GameWindow(javax.swing.JFrame.HIDE_ON_CLOSE,false);
		
	
		if (openID.compareTo("0") == 0){
			new ClassTestWindow(javax.swing.JFrame.EXIT_ON_CLOSE,true);
		}else{
			Map.loadImages();
		
			setMaps();
			
			new GameWindow(javax.swing.JFrame.EXIT_ON_CLOSE,true);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		
		splash.dispose();
	}
	
	private void setMaps(){
		int playerMapX, playerMapY;

		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES))+1;
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES))+1;

		int playerXinMap, playerYinMap;

		playerXinMap = player.getxTile()-((playerMapX-1)*Map.MAP_ROW_TILES);
		playerYinMap = player.getyTile()-((playerMapY-1)*Map.MAP_ROW_TILES);

		for (int j = playerMapY-1; j < playerMapY+2; j++) {
			for (int i = playerMapX-1; i < playerMapX+2; i++) {
				List<String> thisMapInfo;

				if (i<1 || i>NUM_MAPS_X || j<1 || j>NUM_MAPS_Y){
					thisMapInfo = INFO_BLANK_MAP;
				}else{
					int mapID = ((j-1)*NUM_MAPS_X)+i-1;
					thisMapInfo = INFO_MAPS.get(mapID);
				}

				int mapIDx, mapIDy;
				mapIDy = j-(playerMapY-2)-1;
				mapIDx = i-(playerMapX-2)-1;

				int baseX, baseY;
				baseX = (int)((i-1)*Map.MAP_TOTAL_SIZE_X);
				baseY = (int)((j-1)*Map.MAP_TOTAL_SIZE_Y);
				
				double percentX, percentY;
				
				percentX = (double)((double)playerXinMap/(double)Map.MAP_ROW_TILES);
				percentY = (double)((double)playerYinMap/(double)Map.MAP_ROW_TILES);
				
				int displacementX, displacementY;
				
				displacementX = (int)(Map.MAP_TOTAL_SIZE_X*(percentX))+Map.MAP_TOTAL_SIZE_X-(int)(SCREEN_SIZE_X/2);
				displacementY = (int)(Map.MAP_TOTAL_SIZE_Y*(percentY))+Map.MAP_TOTAL_SIZE_Y-(int)(SCREEN_SIZE_Y/2);
				
				displayedMaps[mapIDx][mapIDy] = new Map(thisMapInfo, baseX-displacementX, baseY-displacementY);
			}
		}
	}
	
	/**
	 * Roll given amount of dice with given amount of sides with a starting base value.
	 * @param value Starting base value.
	 * @param numDice Number of dice to roll.
	 * @param numSides Number of sides per die.
	 * @return Resulting value.
	 */
	/*
	public static int roll(int value, int numDice, int numSides){
		Random rnd = new Random();

		for (int i = 0; i < numDice; i++) {
		  value = value + (rnd.nextInt(numSides)+1);
		}
		
		return value;
	}
	*/
	
	@Override
	public void run(){
		
	}
		
	public void movePlayer(){
		
	}
	
	/*
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
						new ClassTestWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowClassTest.setVisible(true);
					break;
					case 2:
					//	gameWindow=new GameWindow();
					//	new GameWindow();
						new GameWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowGame.setVisible(true);
					break;
					case 3:
					//	classTestWindow=new ClassTestWindow();
					//	new ClassTestWindow();
						new ClassTestWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowClassTest.setVisible(true);
					//	gameWindow=new GameWindow();
					//	new GameWindow();
						new GameWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowGame.setVisible(true);
					break;
					case 4:
					//	new MapBuilder();
					//	windowMapBuilder.setVisible(true);
						new MapBuilder(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					break;
					case 5:
					//	new MapBuilder();
					//	windowMapBuilder.setVisible(true);
						new MapBuilder(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	classTestWindow=new ClassTestWindow();
					//	new ClassTestWindow();
						new ClassTestWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowClassTest.setVisible(true);
					break;
					case 6:
					//	new MapBuilder();
					//	windowMapBuilder.setVisible(true);
						new MapBuilder(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	gameWindow=new GameWindow();
					//	new GameWindow();
						new GameWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowGame.setVisible(true);
					break;
					case 7:
					//	classTestWindow=new ClassTestWindow();
					//	gameWindow=new GameWindow();
					//	new ClassTestWindow();
						new ClassTestWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowClassTest.setVisible(true);
					//	new GameWindow();
						new GameWindow(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowGame.setVisible(true);
					//	new MapBuilder();
						new MapBuilder(javax.swing.JFrame.DISPOSE_ON_CLOSE,true);
					//	windowMapBuilder.setVisible(true);
					break;
					default:
						System.exit(0);
					break;
				}
			}catch (NumberFormatException ex){
				
			}
			
		}
	}
	*/
	
}
