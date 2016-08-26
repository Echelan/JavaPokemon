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
	// GENERAL
	private int dinero;
	private String name;
	
	// ITEMS
	private Item[] pocketItems;		// POCKET 1
	private Item[] pocketMeds;		// POCKET 2
	private Item[] pocketBalls;		// POCKET 3
	private Item[] pocketMachines;	// POCKET 4
	private Item[] pocketKeys;		// POCKET 8
	private Item[] pocketBattles;	// POCKET 7
	private int numItems;
	private int numMeds;
	private int numBalls;
	private int numMachines;
	private int numKeys;
	private int numBattles;
	
	// POKEMON
    private Pokemon[] equipo;
    private Pokemon[] PC;
	private int numPokemonTeam;
	private int numPokemonPC;
	
	// MOVEMENT
	private int x, y;
	private boolean left, right, up, down;
	private boolean isRunning;
	
	/**
	 * Create Player object.
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
	 * Create Player object.
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
	 * Create Player object.
	 * @param name Player name.
	 */
	public Player(String name) {
		setBasics();
		this.name = name;
		this.numPokemonTeam = 0;
	}

	/**
	 * Create Player object.
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
	 * Set Player's main default statistics.
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
	 * [MASTER] Returns amount of item carried by Player.
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
	
	/**
	 * 
	 * @param id
	 * @return 
	 */
	public int getAmountItem(int id){
		int amount;
		
		amount = getAmountItem(id,new Item(id).getPocket());
		
		return amount;
	}
	
	/**
	 * 
	 * @param internalName
	 * @return 
	 */
	public int getAmountItem(String internalName){
		int amount;
		
		amount = getAmountItem(new Item(internalName).getId());
		
		return amount;
	}
	
	/**
	 * [MASTER] Add item to Player inventory.
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
	
	/**
	 * Add item to Player inventory.
	 * (Sub-process, wraps to a MASTER.)
	 * @param id Item id to add.
	 * @return Success of process.
	 */
	public boolean addItem(int id){
		boolean success;
		
		success = addItem(id, 1);
		
		return success;
	}
	 
	/**
	 * Add item to Player inventory.
	 * (Sub-process, wraps to a MASTER.)
	 * @param internalName Item internal name to add.
	 * @return Success of process.
	 */
	public boolean addItem(String internalName){
		boolean success;
		
		success = addItem(new Item(internalName).getId());
		
		return success;
	}
	
	/**
	 * Add item to Player inventory.
	 * (Sub-process, wraps to a MASTER.)
	 * @param internalName Item internal name to add.
	 * @param amount Item amount to add.
	 * @return Success of process.
	 */
	public boolean addItem(String internalName, int amount){
		boolean success;
		
		success = addItem(new Item(internalName).getId(), amount);
		
		return success;
	}
	
	
	/**
	 * [MASTER] Remove item from Player inventory.
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
	
	/**
	 * Remove item from Player inventory.
	 * (Sub-process, wraps to a MASTER.)
	 * @param internalName Item internal name to remove.
	 * @return Success of process.
	 */
	public boolean subItem(String internalName){
		boolean success;
		
		success = subItem(new Item(internalName).getId());
		
		return success;
	}
	
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
