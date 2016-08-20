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
	private Item[] pocketItems;
	private Item[] pocketMeds;
	private Item[] pocketBalls;
	private Item[] pocketMachines;
	private Item[] pocketKeys;
	private Item[] pocketBattles;
	private int numPokemonTeam;
	private int numPokemonPC;

	public Jugador(String name, Pokemon starter) {
		this.equipo = new Pokemon[6];
		this.equipo[0] = starter;
		this.name = name;
		this.PC = new Pokemon[200];
		this.dinero = 0;
		this.pocketBalls = new Item[100];
		this.pocketBattles = new Item[100];
		this.pocketItems = new Item[100];
		this.pocketKeys = new Item[100];
		this.pocketMachines = new Item[100];
		this.pocketMeds = new Item[100];
		this.numPokemonTeam = 1;
		this.numPokemonPC = 0;
	}

	public Jugador(String name, int starterID) {
		this.equipo = new Pokemon[6];
		this.equipo[0] = new Pokemon(starterID);
		this.name = name;
		this.PC = new Pokemon[200];
		this.dinero = 0;
		this.pocketBalls = new Item[100];
		this.pocketBattles = new Item[100];
		this.pocketItems = new Item[100];
		this.pocketKeys = new Item[100];
		this.pocketMachines = new Item[100];
		this.pocketMeds = new Item[100];
		this.numPokemonTeam = 1;
		this.numPokemonPC = 0;
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
