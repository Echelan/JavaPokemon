/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;


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
			private int dinero;
			/**
			 * Player name.
			 */
			private String name;
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
			private Pokemon[] equipo;
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
			 * Player direction booleans.
			 */
			private boolean left, right, up, down;
			/**
			 * Player running boolean.
			 */
			private boolean isRunning;
		// </editor-fold>
	// </editor-fold>
	
	/**
	 * Create Player with given name and given starter Pokemon.
	 * @param name Player name.
	 * @param starterPokemon Starter Pokemon.
	 */
	public Player(String name, Pokemon starterPokemon) {
		setBasics();
		this.name = name;
		this.equipo[0] = starterPokemon;
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
		this.equipo[0] = new Pokemon(starterID);
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
		this.equipo[0] = new Pokemon(internalName);
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
		this.equipo = new Pokemon[6];
		this.PC = new Pokemon[200];
		this.dinero = 0;
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
	}

	public void update(){
		move();
	}
	
	/**
	 * Move Player coordinates.
	 */
	public void move(){
		if (left){
			x = x - 1;
		}
		if (right){
			x = x + 1;
		}
		if (up){
			y = y - 1;
		}
		if (down){
			y = y + 1;
		}
	}
	
	/**
	 * Adds given Pokemon to Player.
	 * @param newPokemon Pokemon to add.
	 * @return Integer representing where Pokemon was sent to. (0 = Team, 1 = PC, 2 = Released)
	 */
	public int addPokemon(Pokemon newPokemon){
		int response = 0;
		if (numPokemonTeam == 6){
			if (numPokemonPC == 200){
				response = 2;
			}else{
				response = 1;
				this.PC[numPokemonPC] = newPokemon;
				numPokemonPC = numPokemonPC + 1;
			}
		}else{
			response = 0;
			this.equipo[numPokemonTeam] = newPokemon;
			numPokemonTeam = numPokemonTeam + 1;
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
		
		if (this.dinero >= amount){
			this.dinero = this.dinero - amount;
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
	//</editor-fold>
		
	// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
		public int getDinero() {
			return dinero;
		}

		public String getName() {
			return name;
		}

		public int getNumPokemonTeam() {
			return numPokemonTeam;
		}

		public void setDinero(int dinero) {
			this.dinero = dinero;
		}

		public Pokemon[] getEquipo() {
			return equipo;
		}

		public void setLeft(boolean left) {
			this.left = left;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		public void setUp(boolean up) {
			this.up = up;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		public boolean isIsRunning() {
			return isRunning;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	//</editor-fold>
}
