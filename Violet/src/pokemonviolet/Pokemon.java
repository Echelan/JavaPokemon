/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Andres
 */
public class Pokemon {
	// 'GLOBAL'
	static int NUMATTRIB = 40;
	
    private int id;
	private boolean isShiny;
	private String nameSpecies;
	private String nameInternal;
	private String kind;
	private String pokeEntry;
	private TipoPokemon[] types;
	
	private int baseHP;
	private int baseAttack;
	private int baseDefense;
	private int baseSpeed;
	private int baseSpAtk;
	private int baseSpDef;
	
	private int catchRate;
	private String growthRate;
	private int hatchSteps;
	private String color;
	private String habitat;
	
	private int yieldEXP;
	private int yieldHP;
	private int yieldAttack;
	private int yieldDefense;
	private int yieldSpeed;
	private int yieldSpAtk;
	private int yieldSpDef;
	
	private String height;
	private String weight;
//	private String genderRate;
	
	private int numEvols;
	private String[] evolvesInto;
	private String[] evolveMethod;
	private int[] evolveLevel;
	private String[] evolveItem;
	
	
	// 'LOCAL'
	private String gender;
	private String nameNick;
//	int experience;
//	int[] stats;
//	int level;

	public Pokemon(String nameInternal) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.isShiny = false;
		this.gender = "TBD";
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	
	public Pokemon(int id) {
		this.id = id;
		this.isShiny = false;
		this.gender = "TBD";
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	private boolean readInfo(int id){
		boolean success = false;
		
		int limite = 0;
		String[] pokeinfo = new String[NUMATTRIB];
	  
        File archivo = new File("listPokemon.txt");
		try {
			Scanner eyes = new Scanner(archivo);
			boolean foundPokemon = false;
			while (eyes.hasNextLine() && foundPokemon == false) {
				String thisline = eyes.nextLine();
				if ( thisline.matches("\\[\\d*\\]") ){
					if (Integer.parseInt(thisline.substring(1, thisline.length()-1)) == id ){
						foundPokemon = true;
					}
				}
			}
			if (foundPokemon == true){
				boolean endInfo = false;
				while (eyes.hasNextLine() && endInfo == false) {
					String thisline = eyes.nextLine();
					if ( thisline.matches("\\[\\d*\\]") ){

						endInfo = true;
					}else{
						pokeinfo[ limite ] = thisline;
						limite++;
					}
				}
				for (int i = 0; i < NUMATTRIB; i++){
					String[] partes = pokeinfo[i].split("=");
					if (partes[0].compareTo( "Name" )==0){
						this.nameSpecies = partes[1];
						this.nameNick = partes[1];
					}else if (partes[0].compareTo("InternalName")==0){
						this.nameInternal = partes[1];
					 }else if (partes[0].compareTo("Pokedex")==0){
						this.pokeEntry = partes[1];
					}else if (partes[0].compareTo("Kind")==0){
						this.kind = partes[1];
					}else if (partes[0].compareTo("BaseHP")==0){
						this.baseHP = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("BaseAttack")==0){
						this.baseAttack = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("BaseDefense")==0){
						this.baseDefense = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("BaseSpeed")==0){
						this.baseSpeed = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("BaseSpAtk")==0){
						this.baseSpAtk = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("BaseSpDef")==0){
						this.baseSpDef = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("CatchRate")==0){
						this.catchRate = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("GrowthRate")==0){
						this.growthRate = partes[1];
					}else if (partes[0].compareTo("StepsToHatch")==0){
						this.hatchSteps = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("Color")==0){
						this.color = partes[1];
					}else if (partes[0].compareTo("Habitat")==0){
						this.habitat = partes[1];
					}else if (partes[0].compareTo("YieldHP")==0){
						this.yieldHP = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldAttack")==0){
						this.yieldAttack = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldDefense")==0){
						this.yieldDefense = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldSpeed")==0){
						this.yieldSpeed = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldSpAtk")==0){
						this.yieldSpAtk = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldSpDef")==0){
						this.yieldSpDef = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("YieldEXP")==0){
						this.yieldEXP = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("Weight")==0){
						this.weight = partes[1];
					}else if (partes[0].compareTo("Height")==0){
						this.height = partes[1];
					}else if (partes[0].compareTo("GenderRate")==0 && this.gender.compareTo("TBD") == 0){
						/*
							AlwaysMale
							FemaleOneEighth
							Female25Percent
							Female50Percent
							Female75Percent
							FemaleSevenEighths
							AlwaysFemale
							Genderless
						*/
						String genderRate = partes[1];
						if (genderRate.compareTo("AlwaysMale") == 0){
							this.gender = "Male";
						}else if (genderRate.compareTo("AlwaysFemale") == 0){
							this.gender = "Female";
						}else if (genderRate.compareTo("Genderless") == 0){
							this.gender = "Genderless";
						}else{
							Random rnd = new Random();
							double roll = (rnd.nextInt(99)+1)/100;
							if (genderRate.compareTo("FemaleOneEighth") == 0 && (roll < 1/8)){
								this.gender = "Female";
							}else if (genderRate.compareTo("Female25Percent") == 0 && (roll < 1/4)){
								this.gender = "Female";
							}else if (genderRate.compareTo("Female50Percent") == 0 && (roll < 1/2)){
								this.gender = "Female";
							}else if (genderRate.compareTo("Female75Percent") == 0 && (roll < 3/4)){
								this.gender = "Female";
							}else if (genderRate.compareTo("FemaleSevenEighths") == 0 && (roll < 7/8)){
								this.gender = "Female";
							}else{
								this.gender = "Male";
							}
						}
					}else if (partes[0].compareTo("numEvols")==0){
						this.numEvols = Integer.parseInt(partes[1]);
						this.evolvesInto = new String[this.numEvols];
						this.evolveMethod = new String[this.numEvols];
						this.evolveLevel = new int[this.numEvols];
						this.evolveItem = new String[this.numEvols];
					}else if (partes[0].compareTo("Evolutions")==0){
						if (this.numEvols > 0){
							String[] allEvolutionInfo = partes[1].split(",");
							for (int j = 0; j < allEvolutionInfo.length; j++) {
								switch (j%3) {
									case 0:
										this.evolvesInto[(int)Math.floor(j/3)] = allEvolutionInfo[j];
									break;
									case 1:
										this.evolveMethod[(int)Math.floor(j/3)] = allEvolutionInfo[j];
									break;
									case 2:
										try { 
											this.evolveLevel[(int)Math.floor(j/3)] = Integer.parseInt(allEvolutionInfo[j]); 
											this.evolveItem[(int)Math.floor(j/3)] = "";
										} catch(NumberFormatException e) { 
											this.evolveLevel[(int)Math.floor(j/3)] = 1;
											this.evolveItem[(int)Math.floor(j/3)] = allEvolutionInfo[j];
										}
									break;
								}
							}
						}
					}
				}
				success = true;
			}
		}catch (FileNotFoundException ex) {
		}
		
		return success;
	}
	
	private double calcAlpha() {
		// MADE UP VALUES
		Random rnd = new Random();
		int HPpercent = rnd.nextInt(99)+1;
		int HPmax = this.baseHP;
		double HPcurrent = this.baseHP * (HPpercent/100);
		int rate = this.catchRate;
		int bonusball = 1;
		double bonusstatus= (rnd.nextInt(2)+2)/2;
		
		double alpha = ((((3 * HPmax) - (2 * HPcurrent)) * rate * bonusball)/(3*HPmax))*bonusstatus;
		
		return alpha;
	}
	
	public boolean tryCatch(){
		boolean shakeCheck = false;
		Random rnd = new Random();
		
		double alpha = calcAlpha();
		
		double beta = 1048560/Math.sqrt(Math.sqrt(16711680/alpha));
		if (beta > rnd.nextInt(65535)) {
			shakeCheck = true;
		}
		
		return shakeCheck;
	}
	
	private int getPokemonID(String internalName){
		int id = 0;
	//	System.out.println("Searching for " + internalName + "...");
		File archivo = new File("listPokemon.txt");
		try {
			Scanner eyes = new Scanner(archivo);
			boolean foundPokemon = false;
			while (eyes.hasNextLine() && foundPokemon == false) {
				String thisline = eyes.nextLine();
				if ( thisline.matches("\\[\\d*\\]") ){
					id = Integer.parseInt(thisline.substring(1, thisline.length()-1));
				}else if (thisline.split("=")[0].compareTo("InternalName") == 0){
					if (thisline.split("=")[1].compareTo(internalName) == 0){
						foundPokemon = true;
					}
				}
			}
			if (!foundPokemon){
				id = 0;
			}
		}catch (FileNotFoundException ex) {
		}
		
		return id;
	}
	
	public boolean evolve(){
		boolean success = false;
		if (this.numEvols > 0){
			Random rnd = new Random();
			int roll = rnd.nextInt(this.evolvesInto.length);
			this.id = getPokemonID(this.evolvesInto[roll]);
			
			boolean couldCreate = readInfo( this.id );
			
			success = couldCreate;
			
			if (!couldCreate){
				System.err.println("Error evolving to id " + id + ".");
			}
		}
		return success;
	}
	
	public String getNameSpecies() {
		return nameSpecies;
	}

	public String getGender() {
		return gender;
	}

	public String getNameNick() {
		return nameNick;
	}

	public void setNameNick(String nickname) {
		this.nameNick = nickname;
	}

	public String getPokeEntry() {
		return pokeEntry;
	}

	public String getHeight() {
		return height;
	}

	public String getWeight() {
		return weight;
	}

	public int getId() {
		return id;
	}
	
	public boolean getCanEvolve(){
		return (this.numEvols > 0);
	}

}
