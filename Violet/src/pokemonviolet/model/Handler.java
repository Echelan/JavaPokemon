/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;

import pokemonviolet.scenes.Combat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pokemonviolet.scenes.Scene;

/**
 *
 * @author Andres
 */
public class Handler implements Runnable{
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		// <editor-fold defaultstate="collapsed" desc="Statics">
			/**
			 * Canvas size in X dimension.
			 */
			public static int SCREEN_SIZE_X;
			/**
			 * Canvas size in Y dimension.
			 */
			public static int SCREEN_SIZE_Y;
		//</editor-fold>
		
		/**
		 * The Player in game.
		 */
		public Player player;
		/**
		 * Maps displayed.
		 */
		public Map[][] displayedMaps;
		/**
		 * Current battle handler.
		 */
//		public Combat currentBattle;
		/**
		 * List of states.
		 */
		public static ArrayList<Scene> gameState;
		public int curMapX, curMapY;
	// </editor-fold>
		
	/**
	 * Build game.
	 */
	public Handler() {
		gameState = new ArrayList<Scene>();
		
		SCREEN_SIZE_X = 480;
		SCREEN_SIZE_Y = 320;
		new pokemonviolet.view.GameWindow();
		
		gameState.add(new pokemonviolet.scenes.Title(this));
	}
	
	public void startGame(String name, String gender){
		
		displayedMaps = new Map[3][3];
		
		player = new Player (name, gender, new Pokemon("SQUIRTLE",15));
		player.addItem("POKEBALL",15);
		player.addItem("MASTERBALL",1);
		
		refreshDisplayedMaps();
	}
	
	private void refreshDisplayedMaps(){
		int playerMapX, playerMapY;
	
		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES));
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES));
		
		int maxMapsX=pokemonviolet.data.NIC.NUM_MAPS_X, maxMapsY=pokemonviolet.data.NIC.NUM_MAPS_Y;
		for (int j = playerMapY-1; j < playerMapY+2; j++) {
			for (int i = playerMapX-1; i < playerMapX+2; i++) {
				List<String> thisMapInfo;

				if (i<1 || i>maxMapsX || j<1 || j>maxMapsY){
					thisMapInfo = new ArrayList<String>(pokemonviolet.data.NIC.INFO_BLANK_MAP);
				}else{
					int mapID = ((j)*maxMapsX)+i;
					thisMapInfo = new ArrayList<String>(pokemonviolet.data.NIC.INFO_MAPS.get(mapID));
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
		
		curMapX = calcX();
		curMapY = calcY();
	}
	
	@Override
	public void run(){
		while(true){
			if (!gameState.isEmpty()){
				if (gameState.get(gameState.size()-1).getName().compareTo("GAME")==0){
					if (player.getvDirection().compareTo("")!=0){
						boolean finished = moveMap();

						if (finished){
							player.setvDirection("");
						}
					}
				}else{
				}

				try {
					Thread.sleep(70);
				} catch (InterruptedException ex) {
				}
			}
		}
	}
			
	private void mapCheck(){
		int playerMapX, playerMapY;
		playerMapX = ((int)Math.floor(player.getxTile()/Map.MAP_ROW_TILES));
		playerMapY = ((int)Math.floor(player.getyTile()/Map.MAP_ROW_TILES));
		
		if (playerMapX != displayedMaps[1][1].getxMap() || playerMapY != displayedMaps[1][1].getyMap()){
			cleanMaps();
			refreshDisplayedMaps();
		}
	}
	
	private void tileCheck(){
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
	
	private void steppedGrass(){
		player.setSpawnSteps(player.getSpawnSteps()-1);
		if (player.getSpawnSteps() == 0){
			int maxNum = 0;
			int[][] enemyTeam = new int[6][2];
			for (int i = 0; i < player.getNumPokemonTeam(); i++) {
			//	enemyTeam[i] = getWildPokemon();
			//	maxNum = maxNum+1;
			//	if (i>1){
			//		if (Math.abs(enemyTeam[i][1]-player.getTeam()[i].getLevel())>5){
						Random rnd = new Random();
						enemyTeam[i][0] = rnd.nextInt(151)+1;
						enemyTeam[i][1] = player.getTeam()[i].getLevel()-3;
						maxNum = maxNum+1;
			//		}
			//	}
			}
			
			gameState.add(new Combat(player,new Trainer("", "", enemyTeam,maxNum),true,this));
		}
	}
	
	/*
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
	*/
	
	private void cleanMaps(){
		for (int i = 0; i < displayedMaps.length; i++) {
			for (int j = 0; j < displayedMaps[i].length; j++) {
				displayedMaps[i][j] = null;
			}
		}
	}
	
	private boolean getCanMove(String direction){
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
	
	private boolean moveMap(){		
		int baseX, baseY;
		boolean isDone = true;
		
		baseX = calcX();
		baseY = calcY();
		
		if (curMapX == baseX && curMapY == baseY){
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
		
		if (curMapX != baseX || curMapY != baseY){
			int amount = player.MOVE_POS;
			if (player.isRunning()){
				amount = (int)(amount * player.RUN_MULT);
			}
			
			switch (player.getvDirection()){
				case "LEFT":
					if (Math.abs(baseX-curMapX) >= amount){
						curMapX = curMapX + amount;
						isDone=false;
					}else{
						curMapX = baseX;
					}
					break;
				case "RIGHT":
					if (Math.abs(baseX-curMapX) >= amount){
						curMapX = curMapX - amount;
						isDone=false;
					}else{
						curMapX = baseX;
					}
					break;
				case "UP":
					if (Math.abs(baseY-curMapY) >= amount){
						curMapY = curMapY + amount;
						isDone=false;
					}else{
						curMapY = baseY;
					}
					break;
				case "DOWN":
					if (Math.abs(baseY-curMapY) >= amount){
						curMapY = curMapY - amount;
						isDone=false;
					}else{
						curMapY = baseY;
					}
					break;
			}
			if (isDone){
				tileCheck();
			}
		}
		
		return isDone;
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
	
}
