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
public class Jugador {
	
    private Pokemon[] equipo;
    private Pokemon[] PC;
	private int dinero;
	private String name;
	private Item[] pocketItems;		// POCKET 1
	private Item[] pocketMeds;		// POCKET 2
	private Item[] pocketBalls;		// POCKET 3
	private Item[] pocketMachines;	// POCKET 4
	private Item[] pocketKeys;		// POCKET 8
	private Item[] pocketBattles;	// POCKET 7
	private int numPokemonTeam;
	private int numPokemonPC;
	private int numItems;
	private int numMeds;
	private int numBalls;
	private int numMachines;
	private int numKeys;
	private int numBattles;

	public Jugador(String name, Pokemon starter) {
		setBasics();
		this.name = name;
		this.equipo[0] = starter;
		this.numPokemonTeam = 1;
	}

	public Jugador(String name, int starterID) {
		setBasics();
		this.name = name;
		this.equipo[0] = new Pokemon(starterID);
		this.numPokemonTeam = 1;
	}

	public Jugador(String name) {
		setBasics();
		this.name = name;
		this.numPokemonTeam = 0;
	}

	public Jugador(String name, String internalName) {
		setBasics();
		this.name = name;
		this.equipo[0] = new Pokemon(internalName);
		this.numPokemonTeam = 1;
	}
	
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
	
	public int getAmountItem(String internalName, int pocketID){
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
			if (pocket[i].getNameInternal().compareTo(internalName) == 0){
				amount = pocket[i].getAmount();
			}
		}
		
		return amount;
	}
	
	public int getAmountItem(int id){
		int amount = 0;
		Item[] pocket;
		int pocketCap;
		
		for (int i = 0; i < 8; i++) {
			pocket = null;
			pocketCap = 0;
			switch(i){
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
			for (int j = 0; i < pocketCap; i++) {
				if (pocket[j].getId() == id){
					amount = pocket[j].getAmount();
				}
			}
		}
		
		return amount;
	}
	
	public int getAmountItem(String internalName){
		int amount = 0;
		Item[] pocket;
		int pocketCap;
		
		for (int i = 0; i < 8; i++) {
			pocket = null;
			pocketCap = 0;
			switch(i){
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
			for (int j = 0; i < pocketCap; i++) {
				if (pocket[i].getNameInternal().compareTo(internalName) == 0){
					amount = pocket[i].getAmount();
				}
			}
		}
		
		return amount;
	}
	
	public boolean addItem(int id){
		boolean success = false;
		Item[] pocket = null;
		int pocketCap = 0;
		Item item = new Item(id);
		
		switch(item.getPocket()){
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
		
		for (int i = 0; i < pocket.length; i++) {
			if (pocket[i].getId() == id){
				pocket[i].setAmount(pocket[i].getAmount()+1);
				success = true;
			}
		}
		
		if (!success){
			pocket[pocketCap] = item;
			pocketCap = pocketCap + 1;
		}
		
		return success;
	}
	
	public boolean addItem(String internalName){
		boolean success = false;
		Item[] pocket = null;
		int pocketCap = 0;
		Item item = new Item(internalName);
		
		switch(item.getPocket()){
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
		
		for (int i = 0; i < pocket.length; i++) {
			if (pocket[i].getId() == item.getId()){
				pocket[i].setAmount(pocket[i].getAmount()+1);
				success = true;
			}
		}
		
		if (!success){
			pocket[pocketCap] = item;
			pocketCap = pocketCap + 1;
		}
		
		return success;
	}
	
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

	public Pokemon[] getEquipo() {
		return equipo;
	}

}
