/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
			/**
			 * Canvas size in X dimension.
			 */
			public static int SCREEN_SIZE_X;
			/**
			 * Canvas size in Y dimension.
			 */
			public static int SCREEN_SIZE_Y;
			/**
			 * All maps dimensions.
			 */
			public static int ALL_MAPS_WIDTH, ALL_MAPS_HEIGHT;
			/**
			 * Current map position.
			 */
			public static int ALL_MAPS_X, ALL_MAPS_Y;
		//</editor-fold>
		
		/**
		 * The Player in game.
		 */
		public static Player player;
		/**
		 * Maps displayed.
		 */
		public static Map[][] displayedMaps;
		/**
		 * All maps in one Buffered Image!
		 * <p>(What a time to be alive.)</p>
		 */
		public static BufferedImage ALL_MAPS;
		/**
		 * Current battle handler.
		 */
		public static Combat currentBattle;
	// </editor-fold>
		
	/**
	 * Build game.
	 */
	public Game() {
		Map.loadImages();
		
		NUM_MAPS_X = 4;
		NUM_MAPS_Y = 4;
		
		SCREEN_SIZE_X = 480;
		SCREEN_SIZE_Y = 320;
		
		ALL_MAPS_WIDTH = Map.MAP_TOTAL_SIZE_X*3;
		ALL_MAPS_HEIGHT = Map.MAP_TOTAL_SIZE_Y*3;
		
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
		
		player = new Player ("Red",new Pokemon("SQUIRTLE",10));
		player.addItem("POKEBALL",15);
		player.addItem("MASTERBALL",1);
		
	//	Thread playerThread = new Thread(player);
	//	playerThread.start();
		
	//	windowClassTest = new ClassTestWindow(javax.swing.JFrame.HIDE_ON_CLOSE,false);
	//	windowMapBuilder = new MapBuilder(javax.swing.JFrame.HIDE_ON_CLOSE,false);
	//	windowGame = new GameWindow(javax.swing.JFrame.HIDE_ON_CLOSE,false);
		
		
			setMaps();
			
			new GameWindow();
			
		//	new ClassTestWindow();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		
		splash.dispose();
	}
	
	private void setMaps(){
		int playerMapX, playerMapY;

		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES));
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES));
		
		
		for (int j = playerMapY-1; j < playerMapY+2; j++) {
			for (int i = playerMapX-1; i < playerMapX+2; i++) {
				List<String> thisMapInfo;

				if (i<1 || i>NUM_MAPS_X || j<1 || j>NUM_MAPS_Y){
					thisMapInfo = new ArrayList<String>(INFO_BLANK_MAP);
				}else{
					int mapID = ((j)*NUM_MAPS_X)+i;
					thisMapInfo = new ArrayList<String>(INFO_MAPS.get(mapID));
				}
				if (thisMapInfo.get(0).split(";")[0].compareTo("#")==0){
					thisMapInfo.remove(0);
					thisMapInfo.add(0,i+";"+j);
				}

				int mapIDx, mapIDy;
				mapIDy = j-(playerMapY-2)-1;
				mapIDx = i-(playerMapX-2)-1;
				
				displayedMaps[mapIDx][mapIDy] = new Map(thisMapInfo,player.getxTile(),player.getyTile(),mapIDx,mapIDy);
			}
		}
		
		BufferedImage tempStitched = new BufferedImage( ALL_MAPS_WIDTH, ALL_MAPS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) tempStitched.getGraphics();
		
		for (int i = 0; i < displayedMaps.length; i++) {
			for (int j = 0; j < displayedMaps[i].length; j++) {
				g.drawImage( displayedMaps[i][j].getImage(), (int)(i*Map.MAP_TOTAL_SIZE_X), (int)(j*Map.MAP_TOTAL_SIZE_Y),(int)(Map.MAP_TOTAL_SIZE_X), (int)(Map.MAP_TOTAL_SIZE_Y), null);
			}
		}
		
		ALL_MAPS = tempStitched;
		
		ALL_MAPS_X = calcX();
		ALL_MAPS_Y = calcY();
	}
		
	@Override
	public void run(){
		while(true){
			if (!player.isInCombat()){
				if (player.getvDirection().compareTo("")!=0){
					boolean finished = moveMap();
					
					if (finished){
						player.setvDirection("");
					}
				}
			}else{
			// 	System.out.println("Resistance is futile!");
			}
			try {
				Thread.sleep(70);
			} catch (InterruptedException ex) {
			}
		}
	}
	
	private int calcX(){
		
		int xTile, xDisplace, xTotal;
		
		xTile = player.getxTile();
		
		xDisplace = xTile * (Map.MAP_TOTAL_SIZE_X/Map.MAP_ROW_TILES);
		
		xTotal = (xDisplace*-1)+(SCREEN_SIZE_X/2);
		
		return xTotal;
	}
	
	private int calcY(){
		
		int yTile, yTotal, yDisplace;
		
		yTile = player.getyTile();
		
		yDisplace = yTile * (Map.MAP_TOTAL_SIZE_Y/Map.MAP_ROW_TILES);
		
		yTotal = (yDisplace*-1)+(SCREEN_SIZE_Y/2);
		
		return yTotal;
	}
			
	public void mapCheck(){
		int playerMapX, playerMapY;
		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES));
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES));
		
		if (playerMapX != displayedMaps[1][1].getxMap() || playerMapY != displayedMaps[1][1].getyMap()){
			cleanMaps();
			setMaps();
		}
	}
	
	public void tileCheck(){
		int xTile, yTile;
		xTile=player.getxTile();
		yTile=player.getyTile();
		while (xTile>20){
			xTile=xTile-20;
		}
		while (xTile<0){
			xTile=xTile+20;
		}
		while (yTile>20){
			yTile=yTile-20;
		}
		while (yTile<0){
			yTile=yTile+20;
		}
		
		String[] tileInfo = displayedMaps[1][1].getTileInformation(yTile, xTile);
		if (Integer.parseInt(tileInfo[2]) == 0 && Integer.parseInt(tileInfo[3]) == 1){
			steppedGrass();
		}
	}
	
	public void steppedGrass(){
		player.setSpawnSteps(player.getSpawnSteps()-1);
		if (player.getSpawnSteps() == 0){
			int maxNum = 0;
			int[][] enemyTeam = new int[6][2];
			for (int i = 0; i < player.getNumPokemonTeam(); i++) {
				enemyTeam[i] = getWildPokemon();
				maxNum = maxNum+1;
				if (i>1){
					if (Math.abs(enemyTeam[i][1]-player.getTeam()[i].getLevel())>5){
						Random rnd = new Random();
						enemyTeam[i][0] = rnd.nextInt(151)+1;
						enemyTeam[i][1] = player.getTeam()[i].getLevel();
						maxNum = maxNum+1;
					}
				}
			}
			
			currentBattle = new Combat(player,new Trainer("", "", enemyTeam,maxNum),true);
		}
	}
	
	private int[] getWildPokemon(){
		int xTile, yTile;
		xTile=player.getxTile();
		yTile=player.getyTile();
		while (xTile>20){
			xTile=xTile-20;
		}
		while (xTile<0){
			xTile=xTile+20;
		}
		while (yTile>20){
			yTile=yTile-20;
		}
		while (yTile<0){
			yTile=yTile+20;
		}
		return displayedMaps[1][1].getWildPokemon(xTile,yTile);
	}
	
	public void cleanMaps(){
		for (int i = 0; i < displayedMaps.length; i++) {
			for (int j = 0; j < displayedMaps[i].length; j++) {
				displayedMaps[i][j] = null;
			}
		}
	}
	
	public boolean getCanMove(String direction){
		boolean canMove = false;
		
		int posX, posY;
		int pTotalx, pTotaly;
		
		pTotalx = -1;
		pTotaly = -1;
		posX = -1;
		posY = -1;
		
		switch (direction){
			case "LEFT":
				pTotalx = player.getxTile()-1;
				pTotaly = player.getyTile();
				
				posX = player.calcXinMap()-1;
				posY = player.calcYinMap();
			break;
			case "RIGHT":
				pTotalx = player.getxTile()+1;
				pTotaly = player.getyTile();
				
				posX = player.calcXinMap()+1;
				posY = player.calcYinMap();
			break;
			case "UP":
				pTotalx = player.getxTile();
				pTotaly = player.getyTile()-1;
				
				posX = player.calcXinMap();
				posY = player.calcYinMap()-1;
			break;
			case "DOWN":
				pTotalx = player.getxTile();
				pTotaly = player.getyTile()+1;
				
				posX = player.calcXinMap();
				posY = player.calcYinMap()+1;
			break;
		}
				
		int newPlayerMapX, newPlayerMapY;
		newPlayerMapX = ((int)Math.floor(pTotalx/Map.MAP_ROW_TILES))+1;
		newPlayerMapY = ((int)Math.floor(pTotaly/Map.MAP_ROW_TILES))+1;

		int playerMapX, playerMapY;
		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES))+1;
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES))+1;

		int diffX, diffY;

		diffX = newPlayerMapX - playerMapX;
		diffY = newPlayerMapY - playerMapY;

		posX = posX - (diffX*Map.MAP_ROW_TILES);
		posY = posY - (diffY*Map.MAP_ROW_TILES);
		
		if (displayedMaps[diffX+1][diffY+1].getBounds()[posX][posY].substring(0,1).compareTo("0")==0){
			canMove = true;
		}
		
		return canMove;
	}
		
	public boolean moveMap(){		
		int baseX, baseY;
		boolean isDone = true;
		
		baseX = calcX();
		baseY = calcY();
		
		if (ALL_MAPS_X == baseX && ALL_MAPS_Y == baseY){
			switch (player.getDirection()){
				case "LEFT":
					if (getCanMove(player.getDirection())){
						player.setxTile(player.getxTile()-1);
					}
				break;
				case "RIGHT":
					if (getCanMove(player.getDirection())){
						player.setxTile(player.getxTile()+1);
					}
				break;
				case "UP":
					if (getCanMove(player.getDirection())){
						player.setyTile(player.getyTile()-1);
					}
				break;
				case "DOWN":
					if (getCanMove(player.getDirection())){
						player.setyTile(player.getyTile()+1);
					}
				break;
			}
			mapCheck();
		}
		
		baseX = calcX();
		baseY = calcY();
		
		if (ALL_MAPS_X != baseX || ALL_MAPS_Y != baseY){
			int amount = player.MOVE_POS;
			if (player.isRunning()){
				amount = (int)(amount * player.RUN_MULT);
			}
			
			
			switch (player.getvDirection()){
				case "LEFT":
					if (Math.abs(baseX-ALL_MAPS_X) >= amount){
						ALL_MAPS_X = ALL_MAPS_X + amount;
						isDone=false;
					}else{
						ALL_MAPS_X = baseX;
					}
					break;
				case "RIGHT":
					if (Math.abs(baseX-ALL_MAPS_X) >= amount){
						ALL_MAPS_X = ALL_MAPS_X - amount;
						isDone=false;
					}else{
						ALL_MAPS_X = baseX;
					}
					break;
				case "UP":
					if (Math.abs(baseY-ALL_MAPS_Y) >= amount){
						ALL_MAPS_Y = ALL_MAPS_Y + amount;
						isDone=false;
					}else{
						ALL_MAPS_Y = baseY;
					}
					break;
				case "DOWN":
					if (Math.abs(baseY-ALL_MAPS_Y) >= amount){
						ALL_MAPS_Y = ALL_MAPS_Y - amount;
						isDone=false;
					}else{
						ALL_MAPS_Y = baseY;
					}
					break;
			}
			if (isDone){
				tileCheck();
			}
		}
		
		return isDone;
	}
	
}
