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
		
		NUM_MAPS_X = 4;
		NUM_MAPS_Y = 4;
		
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
					temp = readInfoB;
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
		
	//	Thread playerThread = new Thread(player);
	//	playerThread.start();
		
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

		for (int j = playerMapY-1; j < playerMapY+2; j++) {
			for (int i = playerMapX-1; i < playerMapX+2; i++) {
				List<String> thisMapInfo;

				if (i<1 || i>NUM_MAPS_X || j<1 || j>NUM_MAPS_Y){
					thisMapInfo = new ArrayList<String>(INFO_BLANK_MAP);
				}else{
					int mapID = ((j)*NUM_MAPS_X)+i;
					thisMapInfo = new ArrayList<String>(INFO_MAPS.get(mapID));
					System.out.println(i+","+j);
				}
				if (thisMapInfo.get(0).split(";")[0].compareTo("#")==0){
					thisMapInfo.remove(0);
					thisMapInfo.add(0,i+";"+j);
				}

				int mapIDx, mapIDy;
				mapIDy = j-(playerMapY-2)-1;
				mapIDx = i-(playerMapX-2)-1;
				
				displayedMaps[mapIDx][mapIDy] = new Map(thisMapInfo,player.getxTile(),player.getyTile());
			}
		}
	}
		
	@Override
	public void run(){
		while(true){
		//	//System.out.println(player.getxTile()+","+player.getyTile());
			
			for (int i = 0; i < displayedMaps.length; i++) {
				for (int j = 0; j < displayedMaps[i].length; j++) {
					move(displayedMaps[i][j]);
				}
			}
		
			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
			}
		}
	}
	
	public boolean getCanMove(String direction){
		boolean canMove = false;
		
		switch (direction){
			case "LEFT":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.calcXinMap()-1][Game.player.calcYinMap()].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "RIGHT":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.calcXinMap()+1][Game.player.calcYinMap()].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "UP":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.calcXinMap()][Game.player.calcYinMap()-1].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
			case "DOWN":
				if (Game.displayedMaps[1][1].getBounds()[Game.player.calcXinMap()][Game.player.calcYinMap()+1].substring(0, 1).compareTo("0")==0){
					canMove = true;
				}
			break;
		}
		/*
		if (!canMove){
			System.out.println("Player is in map coordinates:");
			System.out.println(Game.player.calcXinMap()+","+Game.player.calcYinMap());
			System.out.println("Moving "+direction);
			System.out.println("But he's blocked.");
		}
		*/
		return canMove;
	}
	
	public void move(Map map){		
		int baseX, baseY;
		
		baseX = map.calcX(player.getxTile());
		baseY = map.calcY(player.getxTile());
		
		if (map.getX() == baseX && map.getY() == baseY){
			switch (player.getDirection()){
				case "LEFT":
					if (getCanMove(player.getDirection())){
						player.setxTile(player.getxTile()-1);
					}else{
						player.setDirection("");
					}
				break;
				case "RIGHT":
					if (getCanMove(player.getDirection())){
						player.setxTile(player.getxTile()+1);
					}else{
						player.setDirection("");
					}
				break;
				case "UP":
					if (getCanMove(player.getDirection())){
						player.setyTile(player.getyTile()-1);
					}else{
						player.setDirection("");
					}
				break;
				case "DOWN":
					if (getCanMove(player.getDirection())){
						player.setyTile(player.getyTile()+1);
					}else{
						player.setDirection("");
					}
				break;
			}
		}else{
			int amount = player.MOVE_POS;
			if (player.isRunning()){
				amount = (int)(amount * player.RUN_MULT);
			}
						
			switch (player.getvDirection()){
				case "LEFT":
					if (Math.abs(baseX-map.getX()) >= amount){
						map.setX(map.getX() + amount);
					}else{
						map.setX(baseX);
						player.setvDirection("");
					}
				break;
				case "RIGHT":
					if (Math.abs(baseX-map.getX()) >= amount){
						map.setX(map.getX() - amount);
					}else{
						map.setX(baseX);
						player.setvDirection("");
					}
				break;
				case "UP":
					if (Math.abs(baseY-map.getY()) >= amount){
						map.setY(map.getY() + amount);
					}else{
						map.setY(baseY);
						player.setvDirection("");
					}
				break;
				case "DOWN":
					if (Math.abs(baseY-map.getY()) >= amount){
						map.setY(map.getY() - amount);
					}else{
						map.setY(baseY);
						player.setvDirection("");
					}
				break;
			}
			
		}
		
	}
	
	
}
