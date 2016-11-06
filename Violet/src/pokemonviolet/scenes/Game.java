/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pokemonviolet.model.Handler;
import pokemonviolet.control.KeyHandler;
import pokemonviolet.model.Map;
import pokemonviolet.model.Player;
import pokemonviolet.model.Trainer;

/**
 *
 * @author Andres
 */
public class Game extends Scene {

		/**
		 * The Player in game.
		 */
		private Player player;
		/**
		 * Maps displayed.
		 */
		private Map[][] displayedMaps;
		private int curMapX, curMapY;
		private boolean randomPokemon = false;
		private boolean fillTeam = false;
		
	public Game(Handler main, Player player) {
		super(main, "GAME", true);
		
		displayedMaps = new Map[3][3];

		this.player = player;
		player.addItem("POKEBALL", 15);
		player.addItem("MASTERBALL", 1);
		player.addItem("POTION",1);
		
		refreshDisplayedMaps();
	}

	private void refreshDisplayedMaps() {
		int maxMapsX = pokemonviolet.data.NIC.NUM_MAPS_X, maxMapsY = pokemonviolet.data.NIC.NUM_MAPS_Y;
		for (int j = player.calcMapY() - 1; j < player.calcMapY() + 2; j++) {
			for (int i = player.calcMapX() - 1; i < player.calcMapX() + 2; i++) {
				List<String> thisMapInfo;

				if (i < 1 || i >= maxMapsX || j < 1 || j >= maxMapsY) {
					thisMapInfo = new ArrayList<String>(pokemonviolet.data.NIC.INFO_BLANK_MAP);
				} else {
					int mapID = ((j) * maxMapsX) + i;
					thisMapInfo = new ArrayList<String>(pokemonviolet.data.NIC.INFO_MAPS.get(mapID));
				}
				if (thisMapInfo.get(0).split(";")[0].compareTo("#") == 0) {
					thisMapInfo.remove(0);
					thisMapInfo.add(0, i + ";" + j);
				}

				int mapIDx, mapIDy;
				mapIDy = j - (player.calcMapY() - 2) - 1;
				mapIDx = i - (player.calcMapX() - 2) - 1;

				displayedMaps[mapIDx][mapIDy] = new Map(thisMapInfo, player.getxTile(), player.getyTile(), mapIDx, mapIDy);
			}
		}

		curMapX = calcX();
		curMapY = calcY();
	}
	
	private void cleanMaps() {
		for (int i = 0; i < displayedMaps.length; i++) {
			for (int j = 0; j < displayedMaps[i].length; j++) {
				displayedMaps[i][j] = null;
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private boolean getCanMove(String direction) {
		boolean canMove = false;
		
		int posX, posY;
		int pTotalx, pTotaly;
		
		pTotalx = -1;
		pTotaly = -1;
		posX = -1;
		posY = -1;
		
		switch (direction) {
			case "LEFT":
				pTotalx = player.getxTile() - 1;
				pTotaly = player.getyTile();
				
				posX = player.calcXinMap() - 1;
				posY = player.calcYinMap();
				break;
			case "RIGHT":
				pTotalx = player.getxTile() + 1;
				pTotaly = player.getyTile();
				
				posX = player.calcXinMap() + 1;
				posY = player.calcYinMap();
				break;
			case "UP":
				pTotalx = player.getxTile();
				pTotaly = player.getyTile() - 1;
				
				posX = player.calcXinMap();
				posY = player.calcYinMap() - 1;
				break;
			case "DOWN":
				pTotalx = player.getxTile();
				pTotaly = player.getyTile() + 1;
				
				posX = player.calcXinMap();
				posY = player.calcYinMap() + 1;
				break;
		}
		
		int newPlayerMapX, newPlayerMapY;
		newPlayerMapX = ((int) Math.floor(pTotalx / Map.MAP_ROW_TILES)) + 1;
		newPlayerMapY = ((int) Math.floor(pTotaly / Map.MAP_ROW_TILES)) + 1;
		
		int diffX, diffY;
		
		diffX = newPlayerMapX - (player.calcMapX()+1);
		diffY = newPlayerMapY - (player.calcMapY()+1);
		
		posX = posX - (diffX * Map.MAP_ROW_TILES);
		posY = posY - (diffY * Map.MAP_ROW_TILES);
		
		if (displayedMaps[diffX + 1][diffY + 1].getBounds()[posX][posY].substring(0, 1).compareTo("0") == 0) {
			canMove = true;
		}
		
		return canMove;
	}
	
	private boolean moveMap() {
		int baseX, baseY;
		boolean isDone = true;
		
		baseX = calcX();
		baseY = calcY();
		
		if (curMapX == baseX && curMapY == baseY) {
			switch (player.getDirection()) {
				case "LEFT":
					if (getCanMove(player.getDirection())) {
						player.setxTile(player.getxTile() - 1);
					}
					break;
				case "RIGHT":
					if (getCanMove(player.getDirection())) {
						player.setxTile(player.getxTile() + 1);
					}
					break;
				case "UP":
					if (getCanMove(player.getDirection())) {
						player.setyTile(player.getyTile() - 1);
					}
					break;
				case "DOWN":
					if (getCanMove(player.getDirection())) {
						player.setyTile(player.getyTile() + 1);
					}
					break;
			}
			mapCheck();
		}
		
		baseX = calcX();
		baseY = calcY();
		
		if (curMapX != baseX || curMapY != baseY) {
			int amount = player.MOVE_POS;
			if (player.isRunning()) {
				amount = (int) (amount * player.RUN_MULT);
			}
			
			switch (player.getvDirection()) {
				case "LEFT":
					if (Math.abs(baseX - curMapX) >= amount) {
						curMapX = curMapX + amount;
						isDone = false;
					} else {
						curMapX = baseX;
					}
					break;
				case "RIGHT":
					if (Math.abs(baseX - curMapX) >= amount) {
						curMapX = curMapX - amount;
						isDone = false;
					} else {
						curMapX = baseX;
					}
					break;
				case "UP":
					if (Math.abs(baseY - curMapY) >= amount) {
						curMapY = curMapY + amount;
						isDone = false;
					} else {
						curMapY = baseY;
					}
					break;
				case "DOWN":
					if (Math.abs(baseY - curMapY) >= amount) {
						curMapY = curMapY - amount;
						isDone = false;
					} else {
						curMapY = baseY;
					}
					break;
			}
		}
		if (isDone) {
			tileCheck();
		}
		
		return isDone;
	}
	
	private int calcX() {
		
		int xTile, xDisplace, xTotal;
		
		xTile = player.getxTile();
		
		while (xTile > 39) {
			xTile = xTile - 20;
		}
		
		while (xTile < 20) {
			xTile = xTile + 20;
		}
		
		xDisplace = xTile * (Map.MAP_TOTAL_SIZE_X / Map.MAP_ROW_TILES);
		
		xTotal = (xDisplace * -1) + (main.BASE_SCREEN_SIZE_X / 2);
		
		return xTotal;
	}
	
	private int calcY() {
		
		int yTile, yTotal, yDisplace;
		
		yTile = player.getyTile();
		
		while (yTile > 39) {
			yTile = yTile - 20;
		}
		
		while (yTile < 20) {
			yTile = yTile + 20;
		}
		
		yDisplace = yTile * (Map.MAP_TOTAL_SIZE_Y / Map.MAP_ROW_TILES);
		
		yTotal = (yDisplace * -1) + (main.BASE_SCREEN_SIZE_Y / 2);
		
		return yTotal;
	}
	
	private void mapCheck() {
		if (player.calcMapX() != displayedMaps[1][1].getxMap() || player.calcMapY() != displayedMaps[1][1].getyMap()) {
			cleanMaps();
			refreshDisplayedMaps();
		}
	}

	private void tileCheck() {
		int xTile, yTile;
		xTile = player.calcXinMap();
		yTile = player.calcYinMap();
		
		String[] tileInfo = displayedMaps[1][1].getTileInformation(yTile, xTile);
		if (Integer.parseInt(tileInfo[2]) == 0 && Integer.parseInt(tileInfo[3]) == 1) {
			steppedGrass();
		}
	}

	private void steppedGrass() {
		player.setSpawnSteps(player.getSpawnSteps() - 1);
		if (player.getSpawnSteps() == 0) {
			player.setRunning(false);
			int maxNum = 0;
			int[][] enemyTeam = new int[6][2];

			if (fillTeam) {
				for (int i = 0; i < player.getNumPokemonTeam(); i++) {
					if (randomPokemon) {
						Random rnd = new Random();
						enemyTeam[i][0] = rnd.nextInt(151) + 1;
						enemyTeam[i][1] = player.getTeam()[i].getLevel() - 3;
						maxNum = maxNum + 1;
					} else {
						enemyTeam[i] = getWildPokemon();
						maxNum = maxNum + 1;
					}
				}
			} else {
				int i = 0;
				if (randomPokemon) {
					Random rnd = new Random();
					enemyTeam[i][0] = rnd.nextInt(151) + 1;
					enemyTeam[i][1] = player.getTeam()[i].getLevel() - 3;
					maxNum = maxNum + 1;
				} else {
					enemyTeam[i] = getWildPokemon();
					maxNum = maxNum + 1;
				}
			}

			main.gameState.add(new Combat(main, player, new Trainer("", "", enemyTeam, maxNum), true));
		}
	}

	private int[] getWildPokemon() {
		int xTile, yTile;
		xTile = player.getxTile();
		yTile = player.getyTile();
		while (xTile > 20) {
			xTile = xTile - 20;
		}
		while (xTile < 0) {
			xTile = xTile + 20;
		}
		while (yTile > 20) {
			yTile = yTile - 20;
		}
		while (yTile < 0) {
			yTile = yTile + 20;
		}
		return displayedMaps[1][1].getWildPokemon(xTile, yTile);
	}
	
	@Override
	public void receiveKeyAction(int action, int state) {
		if (action == KeyHandler.ACTION_A) {
		} else if (action == KeyHandler.ACTION_B) {
			player.setRunning(state == KeyHandler.STATE_PRESS);
		} else if (action == KeyHandler.ACTION_START) {
			if (state == KeyHandler.STATE_RELEASE) {
				start();
			}
		} else {
			String actionStr = "";
				switch (action) {
					case KeyHandler.ACTION_LEFT:
						actionStr = "LEFT";
						break;
					case KeyHandler.ACTION_UP:
						actionStr = "UP";
						break;
					case KeyHandler.ACTION_RIGHT:
						actionStr = "RIGHT";
						break;
					case KeyHandler.ACTION_DOWN:
						actionStr = "DOWN";
						break;
				}
				
			if (state == KeyHandler.STATE_PRESS) {
				if (player.getvDirection().compareTo("") == 0) {
					player.setvDirection(actionStr);
					player.setDirection(actionStr);
				}
			} else if (state == KeyHandler.STATE_RELEASE) {
				if (player.getDirection().compareTo(actionStr) == 0) {
					player.setDirection("");
				}
			}
		}
	}

	@Override
	protected void accept() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void cancel() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void move(int dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void start() {
		main.gameState.add(new Pause(main));
	}

	@Override
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		if (main.gameState.get(main.gameState.size() - 1).getName().compareTo("GAME") == 0) {
			if (player.getvDirection().compareTo("") != 0) {
				boolean finished = moveMap();

				if (finished) {
					player.setvDirection("");
				}
			}
		}

		for (int i = 0; i < displayedMaps.length; i++) {
			for (int j = 0; j < displayedMaps[i].length; j++) {
				if (displayedMaps[i][j] != null) {
					g.drawImage(displayedMaps[i][j].getImage(), resizedValue(curMapX + i * pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), resizedValue(curMapY + (j * pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y)), resizedValue(pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), resizedValue(pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y), null);
				}
			}
		}
		g.drawImage(player.getCurFrameImage(), resizedValue(ssX / 2), resizedValue(ssY / 2), (int) (player.SPRITE_X * player.SPRITE_RESIZE), (int) (player.SPRITE_Y * player.SPRITE_RESIZE), null);

		return display;
	}

}
