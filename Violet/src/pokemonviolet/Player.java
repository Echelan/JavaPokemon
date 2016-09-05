/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;


/**
 *
 * @author Andres
 */
public final class Player implements Runnable {
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		//<editor-fold defaultstate="collapsed" desc="Statics">
			/**
			 * Position change interval.
			 */
			private static int MOVE_POS = 4;
			/**
			 * Running multiplier.
			 */
			private static double RUN_MULT = 2.0;
		//</editor-fold>
		// <editor-fold defaultstate="collapsed" desc="General">
			/**
			 * Player funds.
			 */
			private int funds;
			/**
			 * Player name.
			 */
			private final String name;
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Items">
			/**
			 * Player Bag Pocket for Items [1].
			 */
			private Item[] pocketItems;		// POCKET 1
			/**
			 * Player Bag Pocket for Medicine [2].
			 */
			private Item[] pocketMeds;		// POCKET 2
			/**
			 * Player Bag Pocket for Pokeballs [3].
			 */
			private Item[] pocketBalls;		// POCKET 3
			/**
			 * Player Bag Pocket for Machines (TM/HM) [4].
			 */
			private Item[] pocketMachines;	// POCKET 4
			/**
			 * Player Bag Pocket for Battle-Related Items [7].
			 */
			private Item[] pocketBattles;	// POCKET 7
			/**
			 * Player Bag Pocket for Key Items [8].
			 */
			private Item[] pocketKeys;		// POCKET 8
			/**
			 * Number of Items in Player Bag Pocket for Items [1].
			 */
			private int numItems;			// POCKET 1
			/**
			 * Number of Items in Player Bag Pocket for Medicine [2].
			 */
			private int numMeds;			// POCKET 2
			/**
			 * Number of Items in Player Bag Pocket for Pokeballs [3].
			 */
			private int numBalls;			// POCKET 3
			/**
			 * Number of Items in Player Bag Pocket for Machines [4].
			 */
			private int numMachines;		// POCKET 4
			/**
			 * Number of Items in Player Bag Pocket for Battle-related Items [7].
			 */
			private int numBattles;			// POCKET 7
			/**
			 * Number of Items in Player Bag Pocket for Key Items [8].
			 */
			private int numKeys;			// POCKET 8
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
		// </editor-fold>
		// <editor-fold defaultstate="collapsed" desc="Movement">
			/**
			 * Player coordinates.
			 */
			private int x, y;
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
			 * Player tile coordinates.
			 */
			private int xTile, yTile;
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
		//</editor-fold>
	// </editor-fold>
	
	/**
	 * Create Player with given name and given starter Pokemon.
	 * @param name Player name.
	 * @param starterPokemon Starter Pokemon.
	 */
	public Player(String name, Pokemon starterPokemon) {
		setBasics();
		this.name = name;
		this.team[0] = starterPokemon;
		this.numPokemonTeam = 1;
	}

	/**
	 * Create Player with given name and starter Pokemon with given ID.
	 * @param name Player name.
	 * @param starterID Starter Pokemon ID.
	 */
	public Player(String name, int starterID) {
		setBasics();
		this.name = name;
		this.team[0] = new Pokemon(starterID);
		this.numPokemonTeam = 1;
	}

	/**
	 * Create Player with given name and starter Pokemon with given internal name.
	 * @param name Player name.
	 * @param internalName Starter Pokemon internal name.
	 */
	public Player(String name, String internalName) {
		setBasics();
		this.name = name;
		this.team[0] = new Pokemon(internalName);
		this.numPokemonTeam = 1;
	}
	
	/**
	 * Create Player with given name.
	 * @param name Player name.
	 */
	public Player(String name) {
		setBasics();
		this.name = name;
		this.numPokemonTeam = 0;
	}

	/**
	 * Set Player's main default attributes.
	 */
	private void setBasics(){
		this.team = new Pokemon[6];
		this.PC = new Pokemon[200];
		this.setFunds(0);
		this.pocketBalls = new Item[100];
		this.pocketBattles = new Item[100];
		this.pocketItems = new Item[100];
		this.pocketKeys = new Item[100];
		this.pocketMachines = new Item[100];
		this.pocketMeds = new Item[100];
		this.numPokemonPC = 0;
		this.numItems = 0;
		this.numMeds = 0;
		this.numBalls = 0;
		this.numMachines = 0;
		this.numKeys = 0;
		this.numBattles = 0;
		this.setDirection("");
		this.setvDirection("");
		this.maxFrames = 3;
		this.setCurFrame(1);
		this.curAnim = 0;
		this.xTile=10;
		this.yTile=10;
		this.x =(int)(getxTile()*(SPRITE_X*SPRITE_RESIZE));
		this.y =(int)(getyTile()*(SPRITE_Y*SPRITE_RESIZE));
	}
	
	/**
	 * Adds given Pokemon to Player.
	 * @param newPokemon Pokemon to add.
	 * @return Integer representing where Pokemon was sent to. (0 = Team, 1 = PC, 2 = Released)
	 */
	public int addPokemon(Pokemon newPokemon){
		int response = 0;
		if (getNumPokemonTeam() == 6){
			if (getNumPokemonPC() == 200){
				response = 2;
			}else{
				response = 1;
				this.PC[getNumPokemonPC()] = newPokemon;
				numPokemonPC = getNumPokemonPC() + 1;
			}
		}else{
			response = 0;
			this.team[getNumPokemonTeam()] = newPokemon;
			numPokemonTeam = getNumPokemonTeam() + 1;
		}
		return response;
	}

	/**
	 * Reduce Player funds by given amount if possible, returns false if Player has insufficient funds.
	 * @param amount Amount to reduce Player funds by.
	 * @return Success of process.
	 */
	public boolean reduceMoney(int amount){
		boolean success = false;
		
		if (this.getFunds() >= amount){
			this.setFunds(this.getFunds() - amount);
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Returns amount of item with given ID in Player pocket with given ID.
	 * <p> [MASTER] </p>
	 * @param id Item ID to search for.
	 * @param pocketID Pocket ID to search in.
	 * @return amount of Item present.
	 */
	public int getAmountItem(int id, int pocketID){
		int amount = 0;
		Item[] pocket = null;
		int pocketCap = 0;
		switch(pocketID){
			case 1:
				pocket = this.pocketItems;
				pocketCap = this.numItems;
			break;
			case 2:
				pocket = this.pocketMeds;
				pocketCap = this.numMeds;
			break;
			case 3:
				pocket = this.pocketBalls;
				pocketCap = this.numBalls;
			break;
			case 4:
				pocket = this.pocketMachines;
				pocketCap = this.numMachines;
			break;
			case 7:
				pocket = this.pocketBattles;
				pocketCap = this.numBattles;
			break;
			case 8:
				pocket = this.pocketKeys;
				pocketCap = this.numKeys;
			break;
		}
		for (int i = 0; i < pocketCap; i++) {
			if (pocket[i].getId() == id){
				amount = pocket[i].getAmount();
			}
		}
		
		return amount;
	}
	
	//<editor-fold defaultstate="collapsed" desc="getAmountItem Sub-Processes">
		/**
		 * Returns amount of item with given ID carried by Player.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param id Item ID to search for.
		 * @return amount of Item present.
		 * @see getAmountItem(int, int)
		 */
		public int getAmountItem(int id){
			int amount;

			amount = getAmountItem(id,new Item(id).getPocket());

			return amount;
		}

		/**
		 * Returns amount of item with given internal name carried by Player.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param internalName Item internal name to search for.
		 * @return amount of Item present.
		 * @see getAmountItem(int, int)
		 */
		public int getAmountItem(String internalName){
			int amount;

			amount = getAmountItem(new Item(internalName).getId());

			return amount;
		}
		
		/**
		 * Returns amount of item with given ID carried by Player.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param id Item ID to search for.
		 * @return amount of Item present.
		 * @see getAmountItem(int, int)
		 */
		public int getAmountItem(Item item){
			int amount;

			amount = getAmountItem(item.getId(),item.getPocket());

			return amount;
		}

	//</editor-fold>
	
	/**
	 * Add item to Player inventory.
	 * <p>[MASTER]</p>
	 * @param id Item ID to add.
	 * @param amount Amount of item to add.
	 * @return Success of process.
	 */
	public boolean addItem(int id, int amount){
		boolean success = false;
		Item item = new Item(id, amount);
		
		switch(item.getPocket()){
			case 1:
				for (int i = 0; i < this.numItems; i++) {
					if (this.pocketItems[i].getId() == id){
						this.pocketItems[i].setAmount(this.pocketItems[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketItems[this.numItems] = item;
					this.numItems = this.numItems + 1;
				}
			break;
			case 2:
				for (int i = 0; i < this.numMeds; i++) {
					if (this.pocketMeds[i].getId() == id){
						this.pocketMeds[i].setAmount(this.pocketMeds[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketMeds[this.numMeds] = item;
					this.numMeds = this.numMeds + 1;
				}
			break;
			case 3:
				for (int i = 0; i < this.numBalls; i++) {
					if (this.pocketBalls[i].getId() == id){
						this.pocketBalls[i].setAmount(this.pocketBalls[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketBalls[this.numBalls] = item;
					this.numBalls = this.numBalls + 1;
				}
			break;
			case 4:
				for (int i = 0; i < this.numMachines; i++) {
					if (this.pocketMachines[i].getId() == id){
						this.pocketMachines[i].setAmount(this.pocketMachines[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketMachines[this.numMachines] = item;
					this.numMachines = this.numMachines + 1;
				}
			break;
			case 7:
				for (int i = 0; i < this.numBattles; i++) {
					if (this.pocketBattles[i].getId() == id){
						this.pocketBattles[i].setAmount(this.pocketBattles[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketBattles[this.numBattles] = item;
					this.numBattles = this.numBattles + 1;
				}
			break;
			case 8:
				for (int i = 0; i < this.numKeys; i++) {
					if (this.pocketKeys[i].getId() == id){
						this.pocketKeys[i].setAmount(this.pocketKeys[i].getAmount() + item.getAmount());
						success = true;
					}
				}

				if (!success){
					this.pocketKeys[this.numKeys] = item;
					this.numKeys = this.numKeys + 1;
				}
			break;
		}
		return success;
	}
	
	//<editor-fold defaultstate="collapsed" desc="addItem Sub-Processes">
		/**
		 * Add item to Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param id Item id to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(int id){
			boolean success;

			success = addItem(id, 1);

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param internalName Item internal name to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(String internalName){
			boolean success;

			success = addItem(new Item(internalName).getId());

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param internalName Item internal name to add.
		 * @param amount Item amount to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(String internalName, int amount){
		boolean success;
		
		success = addItem(new Item(internalName).getId(), amount);
		
		return success;
	}
		
		/**
		 * Add item to Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param item Item to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(Item item){
			boolean success;

			success = addItem(item.getId());

			return success;
		}

		/**
		 * Add item to Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param item Item to add.
		 * @param amount Item amount to add.
		 * @return Success of process.
		 * @see addItem(int, int)
		 */
		public boolean addItem(Item item, int amount){
		boolean success;
		
		success = addItem(item.getId(), amount);
		
		return success;
	}
	
	//</editor-fold>
	
	/**
	 * Remove item from Player inventory.
	 * <p>[MASTER]</p>
	 * @param id Item ID to remove.
	 * @return Success of process.
	 */
	public boolean subItem(int id){
		boolean success = false;
		Item item = new Item(id);
		
		switch(item.getPocket()){
			case 1:
				for (int i = 0; i < this.numItems; i++) {
					if (this.pocketItems[i].getId() == id){
						success = true;
						this.pocketItems[i].setAmount(this.pocketItems[i].getAmount()-1);
						if (this.pocketItems[i].getAmount() < 0){
							this.pocketItems[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
			case 2:
				for (int i = 0; i < this.numMeds; i++) {
					if (this.pocketMeds[i].getId() == id){
						success = true;
						this.pocketMeds[i].setAmount(this.pocketMeds[i].getAmount()-1);
						if (this.pocketMeds[i].getAmount() < 0){
							this.pocketMeds[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
			case 3:
				for (int i = 0; i < this.numBalls; i++) {
					if (this.pocketBalls[i].getId() == id){
						success = true;
						this.pocketBalls[i].setAmount(this.pocketBalls[i].getAmount()-1);
						if (this.pocketBalls[i].getAmount() < 0){
							this.pocketBalls[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
			case 4:
				for (int i = 0; i < this.numMachines; i++) {
					if (this.pocketMachines[i].getId() == id){
						success = true;
						this.pocketMachines[i].setAmount(this.pocketMachines[i].getAmount()-1);
						if (this.pocketMachines[i].getAmount() < 0){
							this.pocketMachines[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
			case 7:
				for (int i = 0; i < this.numBattles; i++) {
					if (this.pocketBattles[i].getId() == id){
						success = true;
						this.pocketBattles[i].setAmount(this.pocketBattles[i].getAmount()-1);
						if (this.pocketBattles[i].getAmount() < 0){
							this.pocketBattles[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
			case 8:
				for (int i = 0; i < this.numKeys; i++) {
					if (this.pocketKeys[i].getId() == id){
						success = true;
						this.pocketKeys[i].setAmount(this.pocketKeys[i].getAmount()-1);
						if (this.pocketKeys[i].getAmount() < 0){
							this.pocketKeys[i].setAmount(0);
							success = false;
						}
					}
				}
			break;
		}
		
		return success;
	}
	
	//<editor-fold defaultstate="collapsed" desc="subItem Sub-Processes">
		/**
		 * Remove item from Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param internalName Item internal name to remove.
		 * @return Success of process.
		 * @see subItem(int)
		 */
		public boolean subItem(String internalName){
			boolean success;

			success = subItem(new Item(internalName).getId());

			return success;
		}
		
		/**
		 * Remove item from Player inventory.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param item Item to remove.
		 * @return Success of process.
		 * @see subItem(int)
		 */
		public boolean subItem(Item item){
			boolean success;

			success = subItem(item.getId());

			return success;
		}
	//</editor-fold>
		
	@Override
	public void run() {
		while(true){
			move();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
			}
		}
	}
	
	/**
	 * Move Player coordinates.
	 */
	public void move(){
		int baseX, baseY, diff;
		baseX =(int)(getxTile()*(SPRITE_X*SPRITE_RESIZE));
		baseY =(int)(getyTile()*(SPRITE_Y*SPRITE_RESIZE));
		diff = 2;
		
		boolean passX, passY;
		passX = (x<baseX+diff && x>baseX-diff);
		passY = (y<baseY+diff && y>baseY-diff);
		
		if (passX && passY){
			switch (getDirection()){
				case "LEFT":
					setvDirection(getDirection());
					xTile = (getxTile()-1);
				break;
				case "RIGHT":
					setvDirection(getDirection());
					xTile = (getxTile()+1);
				break;
				case "UP":
					setvDirection(getDirection());
					yTile = (getyTile()-1);
				break;
				case "DOWN":
					setvDirection(getDirection());
					yTile = (getyTile()+1);
				break;
				default:
					setvDirection(getDirection());
				break;
			}
		}else{
			int amount = MOVE_POS;
			if (isRunning()){
				amount = (int)(amount * RUN_MULT);
			}
			
			switch (getvDirection()){
				case "LEFT":
					x = getX() - amount;
				break;
				case "RIGHT":
					x = getX() + amount;
				break;
				case "UP":
					y = getY() - amount;
				break;
				case "DOWN":
					y = getY() + amount;
				break;
			}
			
		}
		
		//refreshTileZ();
		
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
		 * @return the x
		 */
		public int getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return y;
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
			if (curFrame >= getMaxFrames()){
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
			if (vDirection.compareTo("LEFT") ==0){
				this.curAnim=2;
			}else if (vDirection.compareTo("RIGHT") ==0){
				this.curAnim=3;
			}else if (vDirection.compareTo("UP") ==0){
				this.curAnim=1;
			}else if (vDirection.compareTo("DOWN") ==0){
				this.curAnim=0;
			}else{
				this.curFrame=1;
			}
		}
	//</editor-fold>
}
