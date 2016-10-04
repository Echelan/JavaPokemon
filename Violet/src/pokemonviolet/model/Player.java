/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public final class Player {

	// <editor-fold defaultstate="collapsed" desc="Attributes">
		// <editor-fold defaultstate="collapsed" desc="General">
			/**
			 * Player funds.
			 */
			private int funds;
			/**
			 * Player name.
			 */
			private final String name;
			/**
			 * Player gender.
			 */
			private final String gender;
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Items">			
			private Item[][] bag;
			private int[] numItems;
			private static String[] namePockets = {"Items","Medicine","Pokéballs","TMs & HMs","Battle Items","Key Items"};
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Pokemon">
			/**
			 * Player Pokemon Team.
			 */
			private Pokemon[] team;
			/**
			 * Player Pokemon PC.
			 */
			private Pokemon[] PC;
			/**
			 * Number of Pokemon in Player Team.
			 */
			private int numPokemonTeam;
			/**
			 * Number of Pokemon in Player PC.
			 */
			private int numPokemonPC;
			/**
			 * Current active Pokemon.
			 */
			private int currentPokemon;
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Movement">
			/**
			 * Position change interval.
			 */
			public static int MOVE_POS = 9;
			/**
			 * Running multiplier.
			 */
			public static double RUN_MULT = 2.0;
			/**
			 * Player global tile coordinates.
			 */
			private int xTile, yTile;
			/**
			 * Player direction.
			 */
			private String direction;
			/**
			 * Player visual direction.
			 */
			private String vDirection;
			/**
			 * Player running boolean.
			 */
			private boolean running;
			/**
			 * Player steps missing to spawn.
			 */
			private int spawnSteps;
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Sprite">
			/**
			 * Current frame of Player animation.
			 */
			private int curFrame;
			/**
			 * Max frames of Player animation.
			 */
			private int maxFrames;
			/**
			 * Current animation of Player.
			 */
			private int curAnim;
			/**
			 * Player sprite X dimensions.
			 */
			public static int SPRITE_X = 20;
			/**
			 * Player sprite Y dimensions.
			 */
			public static int SPRITE_Y = 20;
			/**
			 * Player sprite multiplier.
			 */
			public static double SPRITE_RESIZE = 1.6;
			/**
			 * Player sprite sheet.
			 */
			public static BufferedImage SPRITE_SHEET;
		//</editor-fold>
	// </editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Constructors">
		/**
		 * Create Player with given name and given starter Pokemon.
		 *
		 * @param name Player name.
		 * @param gender Player gender.
		 * @param starterPokemon Starter Pokemon.
		 */
		public Player(String name, String gender, Pokemon starterPokemon) {
			this.gender = gender;
			setBasics();
			this.name = name;
			this.team[0] = starterPokemon;
			this.numPokemonTeam = 1;
		}

		/**
		 * Create Player with given name and starter Pokemon with given ID.
		 *
		 * @param name Player name.
		 * @param gender Player gender.
		 * @param starterID Starter Pokemon ID.
		 */
		public Player(String name, String gender, int starterID) {
			this.gender = gender;
			setBasics();
			this.name = name;
			this.team[0] = new Pokemon(starterID);
			this.numPokemonTeam = 1;
		}

		/**
		 * Create Player with given name and starter Pokemon with given internal
		 * name.
		 *
		 * @param name Player name.
		 * @param gender Player gender.
		 * @param internalName Starter Pokemon internal name.
		 */
		public Player(String name, String gender, String internalName) {
			this.gender = gender;
			setBasics();
			this.name = name;
			this.team[0] = new Pokemon(internalName);
			this.numPokemonTeam = 1;
		}

		/**
		 * Create Player with given name.
		 *
		 * @param name Player name.
		 * @param gender Player gender.
		 */
		public Player(String name, String gender) {
		this.gender = gender;
		setBasics();
		this.name = name;
		this.numPokemonTeam = 0;
	}
	//</editor-fold>

	/**
	 * Set Player's main default attributes.
	 */
	private void setBasics() {
		try {
			this.SPRITE_SHEET = ImageIO.read(new File("assets/" + this.gender.toLowerCase() + "Sprite.png"));
		} catch (IOException ex) {
		}
		this.team = new Pokemon[6];
		this.PC = new Pokemon[200];
		this.setFunds(500);
		this.bag = new Item[namePockets.length][100];
		this.numItems = new int[namePockets.length];
		for (int i = 0; i < numItems.length; i++) {
			this.numItems[i] = 0;
		}
		this.numPokemonPC = 0;
		this.setDirection("");
		this.setvDirection("");
		this.maxFrames = 3;
		this.setCurFrame(1);
		this.curAnim = 0;
		this.setxTile(30);
		this.setyTile(30);
		this.setCurrentPokemon(0);
		setSpawnSteps(roll(1, 2, 3));
	}

	/**
	 * Adds given Pokemon to Player.
	 *
	 * @param newPokemon Pokemon to add.
	 * @return Integer representing where Pokemon was sent to. (0 = Team, 1 =
	 * PC, 2 = Released)
	 */
	public int addPokemon(Pokemon newPokemon) {
		int response = 0;
		if (getNumPokemonTeam() == 6) {
			if (getNumPokemonPC() == 200) {
				response = 2;
			} else {
				response = 1;
				this.PC[getNumPokemonPC()] = newPokemon;
				numPokemonPC = getNumPokemonPC() + 1;
			}
		} else {
			response = 0;
			this.team[getNumPokemonTeam()] = newPokemon;
			numPokemonTeam = getNumPokemonTeam() + 1;
		}
		return response;
	}

	/**
	 * Returns amount of item with given ID carried by Player.
	 * <p>
	 * [MASTER] </p>
	 *
	 * @param item Item to search for.
	 * @return amount of Item present.
	 */
	public int getAmountItem(Item item) {
		int amount = 0;

		for (int i = 0; i < this.getNumItems()[item.getPocket()-1]; i++) {
			if (this.getBag()[item.getPocket()-1][i].getId() == item.getId()) {
				amount = this.getBag()[item.getPocket()-1][i].getAmount();
			}
		}

		return amount;
	}
	//<editor-fold defaultstate="collapsed" desc="getAmountItem Sub-Processes">
		/**
		 * Returns amount of item with given internal name carried by Player.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param internalName Item internal name to search for.
		 * @return amount of Item present.
		 */
		public int getAmountItem(String internalName) {
			int amount;

			amount = getAmountItem(new Item(internalName));

			return amount;
		}

		/**
		 * Returns amount of item with given ID in Player pocket with given ID.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param id Item ID to search for.
		 * @return amount of Item present.
		 */
		public int getAmountItem(int id) {
			int amount;
			
			amount = getAmountItem(new Item(id));

			return amount;
		}
	//</editor-fold>
	
	/**
	 * Add item to Player inventory.
	 * <p>
	 * [MASTER]</p>
	 *
	 * @param id Item ID to add.
	 * @param amount Amount of item to add.
	 * @return Success of process.
	 */
	public boolean addItem(int id, int amount) {
		boolean success = false;
		Item item = new Item(id, amount);

		for (int i = 0; i < this.getNumItems()[item.getPocket() - 1]; i++) {
			if (this.getBag()[item.getPocket() - 1][i].getId() == id) {
				this.getBag()[item.getPocket() - 1][i].setAmount(this.getBag()[item.getPocket() - 1][i].getAmount() + item.getAmount());
				success = true;
			}
		}

		if (!success) {
			this.getBag()[item.getPocket() - 1][this.getNumItems()[item.getPocket() - 1]] = item;
			this.getNumItems()[item.getPocket() - 1] = this.getNumItems()[item.getPocket() - 1] + 1;
			success = true;
 		}
		
		return success;
	}
	//<editor-fold defaultstate="collapsed" desc="addItem Sub-Processes">
		/**
		 * Add item to Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param id Item id to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(int id) {
			boolean success;

			success = addItem(id, 1);

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param internalName Item internal name to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(String internalName) {
			boolean success;

			success = addItem(new Item(internalName).getId());

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param internalName Item internal name to add.
		 * @param amount Item amount to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(String internalName, int amount) {
			boolean success;

			success = addItem(new Item(internalName).getId(), amount);

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param item Item to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(Item item) {
			boolean success;

			success = addItem(item.getId());

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param item Item to add.
		 * @param amount Item amount to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(Item item, int amount) {
		boolean success;

		success = addItem(item.getId(), amount);

		return success;
	}
	//</editor-fold>
	
	/**
	 * Remove item from Player inventory.
	 * <p>
	 * [MASTER]</p>
	 *
	 * @param id Item ID to remove.
	 * @param pocket Pocket of item.
	 * @return Success of process.
	 */
	public boolean subItem(int id, int pocket) {
		boolean success = false;
		Item item = new Item(id);

		for (int i = 0; i < this.getNumItems()[item.getPocket()-1]; i++) {
			if (this.getBag()[item.getPocket()-1][i].getId() == id) {
				success = true;
				this.getBag()[item.getPocket()-1][i].setAmount(this.getBag()[item.getPocket()-1][i].getAmount() - 1);
				if (this.getBag()[item.getPocket()-1][i].getAmount() < 0) {
					this.getBag()[item.getPocket()-1][i].setAmount(0);
					success = false;
				}
			}
		}

		return success;
	}
	//<editor-fold defaultstate="collapsed" desc="subItem Sub-Processes">
		/**
		 * Remove item from Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param internalName Item internal name to remove.
		 * @return Success of process.
		 * @see subItem(int, int)
		 */
		public boolean subItem(String internalName) {
			boolean success;

			success = subItem(new Item(internalName).getId(), new Item(internalName).getPocket());

			return success;
		}

		/**
		 * Remove item from Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param item Item to remove.
		 * @return Success of process.
		 * @see subItem(int, int)
		 */
		public boolean subItem(Item item) {
			boolean success;

			success = subItem(item.getId(), item.getPocket());

			return success;
		}

		/**
		 * Remove item from Player inventory.
		 * <p>
		 * (Sub-process, wraps to a MASTER.)</p>
		 *
		 * @param id Item id to remove.
		 * @return Success of process.
		 * @see subItem(int, int)
		 */
		public boolean subItem(int id) {
		boolean success;

		success = subItem(id, new Item(id).getPocket());

		return success;
	}
	//</editor-fold>

	/**
	 * Revives, heals to full health, clears status, 
	 * and recovers PP of all Pokemon in Player's team.
	 */
	public void pokemonCenter() {
		for (int i = 0; i < numPokemonTeam; i++) {
			team[i].revive(1);
			for (int j = 0; j < team[i].getNumMoves(); j++) {
				team[i].getMoveSet()[j].setPP(team[i].getMoveSet()[j].getPPMax());
			}
			team[i].setStatus("");
		}
	}
	
	public int blackOut() {
		pokemonCenter();
		this.funds = this.funds/2;
		
		return this.funds;
	}
	
	/**
	 * Calculates and returns the Player's Local X.
	 * @return Player's local X.
	 */
	public int calcXinMap() {
		int playerMapX, playerXinMap;
		playerMapX = ((int) Math.floor(xTile / Map.MAP_ROW_TILES)) + 1;
		playerXinMap = xTile - ((playerMapX - 1) * Map.MAP_ROW_TILES);

		return playerXinMap;
	}
	
	/**
	 * Calculates and returns the Player's Local Y.
	 * @return Player's local Y.
	 */
	public int calcYinMap() {
		int playerMapY, playerYinMap;
		playerMapY = ((int) Math.floor(yTile / Map.MAP_ROW_TILES)) + 1;
		playerYinMap = yTile - ((playerMapY - 1) * Map.MAP_ROW_TILES);

		return playerYinMap;
	}
	
	/**
	 * Calculates and returns the Player's Map X.
	 * @return Player's map X.
	 */
	public int calcMapX() {
		return ((int) Math.floor(getxTile() / Map.MAP_ROW_TILES));
	}
	
	/**
	 * Calculates and returns the Player's Map Y.
	 * @return Player's map Y.
	 */
	public int calcMapY() {
		return ((int) Math.floor(getyTile() / Map.MAP_ROW_TILES));
	}
	
	/**
	 * Returns Player's Current Frame.
	 * @return Current frame.
	 */
	public Image getCurFrameImage() {
		Image frameImg = null;

		int xPlayerIMG = 0;
		int yPlayerIMG = 0;
		if (getvDirection().compareTo("") != 0) {
			setCurFrame(getCurFrame() + 1);
			if (isRunning()) {
				xPlayerIMG = xPlayerIMG + 60;
			}
		}

		xPlayerIMG = xPlayerIMG + (getCurFrame() * 20);
		yPlayerIMG = yPlayerIMG + (getCurAnim() * 20);

		frameImg = SPRITE_SHEET.getSubimage(xPlayerIMG, yPlayerIMG, 20, 20);

		return frameImg;
	}

	/**
	 * Rolls -numDice- dice with -numSides- sides and adds a base -value-.
	 * @param value Base value to add.
	 * @param numDice Number of dice to roll.
	 * @param numSides Number of sides per die.
	 * @return Result of dice roll.
	 */
	public static int roll(int value, int numDice, int numSides) {
		Random rnd = new Random();

		for (int i = 0; i < numDice; i++) {
			value = value + (rnd.nextInt(numSides) + 1);
		}

		return value;
	}

	// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
		/**
		 * @return the funds
		 */
		public int getFunds() {
			return funds;
		}

		/**
		 * @param funds the funds to set
		 */
		public void setFunds(int funds) {
			this.funds = funds;
		}

		/**
		 * @return the PC
		 */
		public Pokemon[] getPC() {
			return PC;
		}

		/**
		 * @return the numPokemonPC
		 */
		public int getNumPokemonPC() {
			return numPokemonPC;
		}

		/**
		 * @return the running
		 */
		public boolean isRunning() {
			return running;
		}

		/**
		 * @param running the running to set
		 */
		public void setRunning(boolean running) {
			this.running = running;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the team
		 */
		public Pokemon[] getTeam() {
			return team;
		}

		/**
		 * @return the numPokemonTeam
		 */
		public int getNumPokemonTeam() {
			return numPokemonTeam;
		}

		/**
		 * @return the direction
		 */
		public String getDirection() {
			return direction;
		}

		/**
		 * @return the xTile
		 */
		public int getxTile() {
			return xTile;
		}

		/**
		 * @return the yTile
		 */
		public int getyTile() {
			return yTile;
		}

		/**
		 * @param direction the direction to set
		 */
		public void setDirection(String direction) {
			this.direction = direction;
		}

		/**
		 * @return the curFrame
		 */
		public int getCurFrame() {
			return curFrame;
		}

		/**
		 * @param curFrame the curFrame to set
		 */
		public void setCurFrame(int curFrame) {
			this.curFrame = curFrame;
			if (curFrame >= getMaxFrames()) {
				this.curFrame = 0;
			}
		}

		/**
		 * @return the maxFrames
		 */
		public int getMaxFrames() {
			return maxFrames;
		}

		/**
		 * @return the curAnim
		 */
		public int getCurAnim() {
			return curAnim;
		}

		/**
		 * @return the vDirection
		 */
		public String getvDirection() {
			return vDirection;
		}

		/**
		 * @param vDirection the vDirection to set
		 */
		public void setvDirection(String vDirection) {
			this.vDirection = vDirection;
			if (vDirection.compareTo("LEFT") == 0) {
				this.curAnim = 2;
			} else if (vDirection.compareTo("RIGHT") == 0) {
				this.curAnim = 3;
			} else if (vDirection.compareTo("UP") == 0) {
				this.curAnim = 1;
			} else if (vDirection.compareTo("DOWN") == 0) {
				this.curAnim = 0;
			} else {
				this.curFrame = 1;
			}
		}

		/**
		 * @return the spawnSteps
		 */
		public int getSpawnSteps() {
			return spawnSteps;
		}

		/**
		 * @param spawnSteps the spawnSteps to set
		 */
		public void setSpawnSteps(int spawnSteps) {
			this.spawnSteps = spawnSteps;
		}

		/**
		 * @param xTile the xTile to set
		 */
		public void setxTile(int xTile) {
			this.xTile = xTile;
		}

		/**
		 * @param yTile the yTile to set
		 */
		public void setyTile(int yTile) {
			this.yTile = yTile;
		}

		/**
		 * @return the currentPokemon
		 */
		public int getCurrentPokemon() {
			return currentPokemon;
		}

		/**
		 * @return the currentPokemon
		 */
		public int getFirstAvailablePokemon() {
			int value = -1;

			for (int i = 0; i < numPokemonTeam; i++) {
				if (!team[i].isFainted()) {
					value = i;
				}
			}

			return value;
		}

		/**
		 * @param currentPokemon the currentPokemon to set
		 */
		public void setCurrentPokemon(int currentPokemon) {
			this.currentPokemon = currentPokemon;
		}

		/**
		 * @return the bag
		 */
		public Item[][] getBag() {
			return bag;
		}

		/**
		 * @return the numItems
		 */
		public int[] getNumItems() {
			return numItems;
		}
	//</editor-fold>
		
		
}
