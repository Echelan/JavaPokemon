/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Andres
 */
public class Pokemon {
	
	//<editor-fold defaultstate="collapsed" desc="Attributes">
		//<editor-fold defaultstate="collapsed" desc="Species Wide">
			static final private int NUM_ATTRIB = 40;
			static final private int MAX_TOTAL_EV = 510;
			static final private int MAX_SINGLE_EV = 252;

			private int id;
			private String nameSpecies;
			private String nameInternal;
			private String kind;
			private String pokeEntry;
			private String[] types;

			private int baseHP;
			private int baseAttack;
			private int baseDefense;
			private int baseSpeed;
			private int baseSpAtk;
			private int baseSpDef;

			private int yieldEXP;
			private int yieldHP;
			private int yieldAttack;
			private int yieldDefense;
			private int yieldSpeed;
			private int yieldSpAtk;
			private int yieldSpDef;

			private int statHP;
			private int statAttack;
			private int statDefense;
			private int statSpeed;
			private int statSpAtk;
			private int statSpDef;

			private int EVHP;
			private int EVAttack;
			private int EVDefense;
			private int EVSpeed;
			private int EVSpAtk;
			private int EVSpDef;

			private int IVHP;
			private int IVAttack;
			private int IVDefense;
			private int IVSpeed;
			private int IVSpAtk;
			private int IVSpDef;

			private int catchRate;
			private String growthRate;
			private int hatchSteps;
			private String color;
			private String habitat;
			private ArrayList<String> allMoves;

			private String height;
			private String weight;

			private int numEvols;
			private String[] evolvesInto;
			private String[] evolveMethod;
			private int[] evolveLevel;
			private String[] evolveItem;
		//</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="Pokemon Specific">
			private boolean shiny;
			private String gender;
			private String nameNick;
			private String ballType;
			private PokemonMove[] moveSet;
			private int curHP;
			private int curEXP;
			private int maxEXP;
			private int level;
			private boolean wild;
			private boolean fainted;
			private String status;
			private int numMoves;
		//</editor-fold>
	//</editor-fold>
	
	/**
	 * Create Pokemon based on given ID.
	 * @param id Pokemon ID to create.
	 */
	public Pokemon(int id) {
		this.id = id;
		this.gender = "TBD";
		this.level = 3;
		this.wild = true;
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}

	/**
	 * Create Pokemon based on given internal name.
	 * @param nameInternal Pokemon internal name to create.
	 */
	public Pokemon(String nameInternal) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = 3;
		this.wild = true;
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball type internal name.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ballType Pokemon Pokeball internal name.
	 */
	public Pokemon(String nameInternal, String ballType) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = ballType;
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball type internal name.
	 * @param id Pokemon ID to create.
	 * @param ballType Pokemon Pokeball internal name.
	 */
	public Pokemon(int id, String ballType) {
		this.id = id;
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = ballType;
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball type ID.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ballType Pokemon Pokeball ID.
	 */
	public Pokemon(String nameInternal, int ballType) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = new Item(ballType).getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball type ID.
	 * @param id Pokemon ID to create.
	 * @param ballType Pokemon Pokeball ID.
	 */
	public Pokemon(int id, int ballType) {
		this.id = id;
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = new Item(ballType).getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball item.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ball Pokemon Pokeball Item.
	 */
	public Pokemon(String nameInternal, Item ball) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = ball.getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball item.
	 * @param id Pokemon ID to create.
	 * @param ball Pokemon Pokeball Item
	 */
	public Pokemon(int id, Item ball) {
		this.id = id;
		this.gender = "TBD";
		this.level = 3;
		this.wild = false;
		this.ballType = ball.getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball type internal name.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ballType Pokemon Pokeball internal name.
	 * @param level Pokemon level to set.
	 */
	public Pokemon(String nameInternal, String ballType, int level) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = level;
		this.wild = false;
		this.ballType = ballType;
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball type internal name.
	 * @param id Pokemon ID to create.
	 * @param ballType Pokemon Pokeball internal name.
	 * @param level Pokemon level to set.
	 */
	public Pokemon(int id, String ballType, int level) {
		this.id = id;
		this.gender = "TBD";
		this.level = level;
		if (ballType.compareTo("WILD") != 0){
			this.ballType = ballType;
			this.wild = true;
		}else{
			this.wild = false;
		}
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball type ID.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ballType Pokemon Pokeball ID.
	 * @param level Pokemon level to set.
	 */
	public Pokemon(String nameInternal, int ballType, int level) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = level;
		this.wild = false;
		this.ballType = new Item(ballType).getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball type ID.
	 * @param id Pokemon ID to create.
	 * @param ballType Pokemon Pokeball ID.
	 * @param level Pokemon level to set.
	 */
	public Pokemon(int id, int ballType, int level) {
		this.id = id;
		this.gender = "TBD";
		this.level = level;
		this.wild = false;
		this.ballType = new Item(ballType).getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given internal name and given ball item.
	 * @param nameInternal Pokemon internal name to create.
	 * @param ball Pokemon Pokeball Item.
	 * @param level Pokemon level to set.
	 */
	public Pokemon(String nameInternal, Item ball, int level) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.gender = "TBD";
		this.level = level;
		this.wild = false;
		this.ballType = ball.getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Create Pokemon based on given id and given ball item.
	 * @param id Pokemon ID to create.
	 * @param ball Pokemon Pokeball Item
	 * @param level Pokemon level to set.
	 */
	public Pokemon(int id, Item ball, int level) {
		this.id = id;
		this.gender = "TBD";
		this.level = level;
		this.wild = false;
		this.ballType = ball.getNameInternal();
		
		genIVs();
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
	}
	
	/**
	 * Generate Pokemon IVs.
	 */
	private void genIVs(){
		Random rnd = new Random();
		
		this.IVHP = rnd.nextInt(32);
		this.IVAttack = rnd.nextInt(32);
		this.IVDefense = rnd.nextInt(32);
		this.IVSpeed = rnd.nextInt(32);
		this.IVSpAtk = rnd.nextInt(32);
		this.IVSpDef = rnd.nextInt(32);
		
		if (this.IVSpeed==10 && this.IVDefense==10 && this.IVSpAtk==10 && this.IVSpDef==10 && this.IVAttack==10){
			this.shiny = true;
		}else{
			this.shiny = false;
		}
		
		wipeEVs();
	}
	
	/**
	 * Wipe Pokemon EVs.
	 */
	public void wipeEVs(){
		this.EVHP = 0;
		this.EVAttack = 0;
		this.EVDefense = 0;
		this.EVSpeed = 0;
		this.EVSpAtk = 0;
		this.EVSpDef = 0;
	}
	
	/**
	 * Acquire information from main data about Pokemon with given ID.
	 * @param id Pokemon to search for in main data.
	 * @return Success of process.
	 */
	private boolean readInfo(int id){
		boolean success = false;
		
		String[] pokeinfo = Game.INFO_POKEMON.get(id-1).split(";");
		
		for (int i = 0; i < NUM_ATTRIB; i++){
			String[] partes = pokeinfo[i].split("=");
			if (partes[0].compareTo( "Name" )==0){
				this.nameSpecies = partes[1];
				this.setNameNick(partes[1]);
			}else if (partes[0].compareTo("InternalName")==0){
				this.nameInternal = partes[1];
			 }else if (partes[0].compareTo("Pokedex")==0){
				this.pokeEntry = partes[1];
			 }else if (partes[0].compareTo("Type1")==0){
				this.types = new String[2];
				this.types[0] = partes[1];
			 }else if (partes[0].compareTo("Type2")==0){
				if (partes.length==2){
					this.types[1] = partes[1];
				}
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
				/*
					Fast
					Medium or MediumFast
					Slow
					Parabolic or MediumSlow
					Erratic
					Fluctuating
				*/
				this.growthRate = partes[1];
			}else if (partes[0].compareTo("StepsToHatch")==0){
				this.hatchSteps = Integer.parseInt(partes[1]);
			}else if (partes[0].compareTo("Moves")==0){
				this.allMoves = new ArrayList<String>();
				this.moveSet = new PokemonMove[4];
					
				String[] readMoves = partes[1].split(",");
				
				for (int j = 0; j < readMoves.length; j = j+2) {
					int moveLevel = Integer.parseInt(readMoves[j]);
					this.allMoves.add(moveLevel+"-"+readMoves[j+1]);
				}
				
				if (this.numMoves == 0){
					
					int cycle = 0;
					while (numMoves < 4 && cycle < this.allMoves.size()){
						String thisMove = this.allMoves.get(cycle);
						if (Integer.parseInt(thisMove.split("-")[0])==1){
							addMove(thisMove.split("-")[1]);
						}
						cycle=cycle+1;
					}
				}
			}else if (partes[0].compareTo("Color")==0){
				/*
					HP
					Attack
					Defense
					Speed
					Special Attack
					Special Defense
				*/		
				this.color = partes[1];
			}else if (partes[0].compareTo("Habitat")==0){
				/*
					Cave
					Forest
					Grassland
					Mountain
					Rare
					RoughTerrain
					Sea
					Urban
					WatersEdge
				*/
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
			}else if (partes[0].compareTo("GenderRate")==0 && this.getGender().compareTo("TBD") == 0){
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
		updateStats();
		this.setCurHP(this.statHP);
		success = true;
		
		return success;
	}
	
	/**
	 * Calculate the 'Alpha' of the Catch Rate formula.
	 * @param bonusball Pokeball multiplier.
	 * @return 'Alpha' value for the formula
	 */
	private double calcAlpha(double bonusball) {
		
		double bonusstatus;
		switch (this.status){
			case "SLEEP":
				bonusstatus = 2;
			break;
			case "FREEZE":
				bonusstatus = 2;
			break;
			case "PARALYZE":
				bonusstatus = 1.5;
			break;
			case "POISON":
				bonusstatus = 1.5;
			break;
			case "BURN":
				bonusstatus = 1.5;
			break;
			default:
				bonusstatus = 1;
			break;
		}
		
		double alpha = ((((3 * this.statHP) - (2 * this.getCurHP())) * this.getCatchRate() * bonusball)/(3*this.statHP))*bonusstatus;
		
		return alpha;
	}
	
	/**
	 * Do a shake check.
	 * <p>[MASTER]</p>
	 * @param bonusball Pokeball multiplier.
	 * @return Success of shake check.
	 */
	public boolean tryCatch(double bonusball){
		boolean shakeCheck = false;
		Random rnd = new Random();
		
		double alpha = calcAlpha(bonusball);
		
		double beta = 1048560/Math.sqrt(Math.sqrt(16711680/alpha));
		if (beta > rnd.nextInt(65535)) {
			shakeCheck = true;
		}
		
		return shakeCheck;
	}
	
	//<editor-fold defaultstate="collapsed" desc="tryCatch Sub-Processes">
		/**
		 * Do a shake check.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param ball Item Pokeball being used.
		 * @return Success of shake check.
		 * @see tryCatch(double)
		 */
		public boolean tryCatch(Item ball){
			boolean shakeCheck;

			shakeCheck = tryCatch(ball.getPokeRate());

			return shakeCheck;
		}

		/**
		 * Do a shake check.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param ballName Internal name of Pokeball being used.
		 * @return Success of shake check.
		 * @see tryCatch(double)
		 */
		public boolean tryCatch(String ballName){
			boolean shakeCheck;

			shakeCheck = tryCatch(new Item(ballName).getPokeRate());

			return shakeCheck;
		}

		/**
		 * Do a shake check.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param ballID ID of Pokeball being used.
		 * @return Success of shake check.
		 * @see tryCatch(double)
		 */
		public boolean tryCatch(int ballID){
			boolean shakeCheck;

			shakeCheck = tryCatch(new Item(ballID).getPokeRate());

			return shakeCheck;
		}
	//</editor-fold>
	
	/**
	 * Searches in main data for Pokemon with given internal name, and returns its ID.
	 * @param internalName Pokemon internal name to search for.
	 * @return Pokemon ID.
	 */
	private int getPokemonID(String internalName){
		int id = 0;
		boolean foundPokemon = false;

		while (foundPokemon == false && id < Game.INFO_POKEMON.size()){
			String[] pokeinfo = Game.INFO_POKEMON.get(id).split(";");
			int attribComp = 0;
			while (attribComp < NUM_ATTRIB && foundPokemon == false){
				String[] partes = pokeinfo[attribComp].split("=");
				if (partes[0].compareTo("InternalName")==0){
			//		System.out.println("Comparing " + partes[1] + "...");
					if (partes[1].compareTo(internalName)==0){
						foundPokemon = true;
					}else{
						attribComp = attribComp + 100;
					}
				}else{
					attribComp = attribComp + 1;
				}
			}
			id = id + 1;
		}
		if (!foundPokemon){
			System.err.println("Could not find pokemon " + internalName + ".");
		}
			
		return id;
	}
	
	/**
	 * Evolve this Pokemon.
	 * @return Success of process.
	 */
	public boolean evolve(){
		boolean success = false;
		if (this.numEvols > 0){
			Random rnd = new Random();
			int roll = rnd.nextInt(this.evolvesInto.length);
			this.id = getPokemonID(this.evolvesInto[roll]);
			
			boolean couldCreate = readInfo(this.getId());
			
			success = couldCreate;
			
			if (!couldCreate){
				System.err.println("Error evolving to id " + getId() + ".");
			}
		}
		return success;
	}
	
	/**
	 * Sets a given move in a given place.
	 * <p>[MASTER]</p>
	 * @param place Place to set given move in.
	 * @param newMove New move to set.
	 */
	public void setMove(int place, PokemonMove newMove){
		moveSet[place] = newMove;
	}
	
	//<editor-fold defaultstate="collapsed" desc="setMove Sub-Processes">
		/**
		 * Sets a given move in a given place.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param place Place to set given move in.
		 * @param newMove Internal name of new move to set.
		 */
		public void setMove(int place, String newMove){
			setMove(place,new PokemonMove(newMove));
		}

		/**
		 * Sets a given move in a given place.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param place Place to set given move in.
		 * @param newMove ID of new move to set.
		 */
		public void setMove(int place, int newMove){
			setMove(place,new PokemonMove(newMove));
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove ID of old move.
		 * @param newMove New move to set.
		 */
		public void replaceMove(int oldMove, PokemonMove newMove){
			int id = 0;
			for (int i = 0; i < getMoveSet().length; i++) {
				if (getMoveSet()[i].getId() == oldMove){
					id = i;
				}
			}
			setMove(id,newMove);
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove ID of old move.
		 * @param newMove Internal name of new move to set.
		 */
		public void replaceMove(int oldMove, String newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove ID of old move.
		 * @param newMove ID of new move to set.
		 */
		public void replaceMove(int oldMove, int newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}
	
		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Internal name of old move.
		 * @param newMove New move to set.
		 */
		public void replaceMove(String oldMove, PokemonMove newMove){
			int place = 0;
			for (int i = 0; i < getMoveSet().length; i++) {
				if (getMoveSet()[i].getNameInternal().compareTo(oldMove)== 0){
					place = i;
				}
			}
			setMove(place,newMove);
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Internal name of old move.
		 * @param newMove Internal name of new move to set.
		 */
		public void replaceMove(String oldMove, String newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Internal name of old move.
		 * @param newMove ID of new move to set.
		 */
		public void replaceMove(String oldMove, int newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}
		
		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Old move.
		 * @param newMove New move to set.
		 */
		public void replaceMove(PokemonMove oldMove, PokemonMove newMove){
			replaceMove(oldMove.getId(),newMove);
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Old move.
		 * @param newMove Internal name of new move to set.
		 */
		public void replaceMove(PokemonMove oldMove, String newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}

		/**
		 * Replaces a given move with another given move.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param oldMove Old move.
		 * @param newMove ID of new move to set.
		 */
		public void replaceMove(PokemonMove oldMove, int newMove){
			replaceMove(oldMove,new PokemonMove(newMove));
		}
		
		/**
		 * Adds a given move to moveset.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param newMove New move to set.
		 * @return Success of process.
		 */
		public boolean addMove(PokemonMove newMove){
			boolean success = true;
			if (numMoves<4){
				setMove(numMoves,newMove);
				numMoves = numMoves+1;
			}else{
				success = false;
			}
			
			return success;
		}
		
		/**
		 * Adds a given move to moveset.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param newMove Internal name of new move to set.
		 * @return Success of process.
		 */
		public boolean addMove(String newMove){
			boolean success = true;
			if (numMoves<4){
				setMove(numMoves,new PokemonMove(newMove));
				numMoves = numMoves+1;
			}else{
				success = false;
			}
			
			return success;
		}
		
		/**
		 * Adds a given move to moveset.
		 * <p>(Sub-process, wraps to a MASTER.)</p>
		 * @param newMove ID of new move to set.
		 * @return Success of process.
		 */
		public boolean addMove(int newMove){
			boolean success = true;
			if (numMoves<4){
				setMove(numMoves,new PokemonMove(newMove));
				numMoves = numMoves+1;
			}else{
				success = false;
			}
			
			return success;
		}
	//</editor-fold>
	
	/**
	 * Update Pokemon stats.
	 */
	public void updateStats(){
		this.statHP = (((2 * this.baseHP + this.IVHP + (this.EVHP/4))*this.getLevel())/100)+this.getLevel()+10;
		this.statAttack = (((2 * this.baseAttack + this.IVAttack + (this.EVAttack/4))*this.getLevel())/100)+5;
		this.statDefense = (((2 * this.baseDefense + this.IVDefense + (this.EVDefense/4))*this.getLevel())/100)+5;
		this.statSpAtk = (((2 * this.baseSpAtk + this.IVSpAtk + (this.EVSpAtk/4))*this.getLevel())/100)+5;
		this.statSpDef = (((2 * this.baseSpDef + this.IVSpDef + (this.EVSpDef/4))*this.getLevel())/100)+5;
		this.statSpeed = (((2 * this.baseSpeed + this.IVSpeed + (this.EVSpeed/4))*this.getLevel())/100)+5;
		
		switch(this.getGrowthRate()){
			case "Parabolic":
				this.maxEXP = (6/5*(this.getLevel()^3)) - (15*(this.getLevel()^2)) + (100*this.getLevel()) - 140;
			break;
			case "Fast":
				this.maxEXP = (4*(this.getLevel()^3))/5;
			break;
			case "Medium":
				this.maxEXP = this.getLevel()^3;
			break;
			case "Slow":
				this.maxEXP = (5*(this.getLevel()^3))/4;
			break;
		}
	}
	
	/**
	 * Level up Pokemon.
	 */
	public void levelUp(){
		this.setCurEXP(this.getCurEXP() - this.maxEXP);
		this.level = this.getLevel() + 1;
	}
	
	/**
	 * Set 'faint' values.
	 */
	public void faint(){
		this.status="";
		this.setFainted(true);
		this.setCurHP(0);
	}
	
	/**
	 * Gets Experience value result for winning against this Pokemon.
	 * @return Experience value to gain.
	 */
	public int getExpGain(){
		double gain = 0;
		double a = 1;
		if (this.isWild()){
			a = 1.5;
		}
		gain = (a * this.yieldEXP * this.getLevel())/7;
		return (int)gain;
	}
	
	public int[] getDamage(int moveNum, String[] enemyTypes){
		int[] damageInfo = new int[3];
		
		//<editor-fold defaultstate="collapsed" desc="Base damage calculation">
			damageInfo[0] = (2*this.level+10)/250;
			damageInfo[0] = damageInfo[0]*(this.statAttack/this.statDefense);
		//	System.out.println(moveNum);
		//	System.out.println(this.getMoveSet()[moveNum].getNameDisplay());
			damageInfo[0] = damageInfo[0]*this.getMoveSet()[moveNum].getDmgBase()+2;
		//</editor-fold>
		
		double modifier=1;
		//<editor-fold defaultstate="collapsed" desc="Damage modifier calculation">
			//<editor-fold defaultstate="collapsed" desc="Critical hit calculation">
				int T, P;
				
				Random rnd = new Random();
				P = (rnd.nextInt(256));
				
				T = this.baseSpeed/2;
				if (this.getMoveSet()[moveNum].isHighCrit()){
					T = T * 8;
				}
				
				if (P<T){
					modifier = modifier * 2;
					damageInfo[1] = 1;
				}else{
					damageInfo[1] = 0;
				}
			//</editor-fold>
			//<editor-fold defaultstate="collapsed" desc="STAB modifier calculation">
				if (this.getMoveSet()[moveNum].getType().compareTo(this.getTypes()[0])==0 || this.getMoveSet()[moveNum].getType().compareTo(this.getTypes()[1])==0){
					modifier = modifier*1.5;
				}
			//</editor-fold>
			//<editor-fold defaultstate="collapsed" desc="Type effectiveness calculation">
				double type=1;
				
				if( PokemonType.getWeakness(enemyTypes[0]).contains(this.getMoveSet()[moveNum].getType()) ){
					type=type*2;
				}
				if( PokemonType.getImmunity(enemyTypes[0]).contains(this.getMoveSet()[moveNum].getType()) ){
					type=type*0;
				}
				if( PokemonType.getResistance(enemyTypes[0]).contains(this.getMoveSet()[moveNum].getType()) ){
					type=type*0.5;
				}
				if (enemyTypes[1] != null){
					if( PokemonType.getWeakness(enemyTypes[1]).contains(this.getMoveSet()[moveNum].getType()) ){
						type=type*2;
					}
					if( PokemonType.getImmunity(enemyTypes[1]).contains(this.getMoveSet()[moveNum].getType()) ){
						type=type*0;
					}
					if( PokemonType.getResistance(enemyTypes[1]).contains(this.getMoveSet()[moveNum].getType()) ){
						type=type*0.5;
					}
				}
				
				damageInfo[2] = (int)type*10;
				modifier = modifier*type;
			//</editor-fold>
			//<editor-fold defaultstate="collapsed" desc="Random calculation">
				modifier = modifier * ((rnd.nextInt(25)+85)/100);
			//</editor-fold>
		//</editor-fold>
		
		damageInfo[0] = (int)(damageInfo[0]*modifier);	
		
		return damageInfo;
	}
	
	// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
		
		public boolean getCanEvolve(){
			return (this.numEvols > 0);
		}

		/**
		 * @return the numMoves
		 */
		public int getNumMoves() {
			return numMoves;
		}
		
		/**
		 * @return the types
		 */
		public String[] getTypes() {
			return types;
		}

		/**
		 * @return the moveSet
		 */
		public PokemonMove[] getMoveSet() {
			return moveSet;
		}
		
		/**
		 * @return the nameInternal
		 */
		public String getNameInternal() {
			return nameInternal;
		};
		
		/**
		 * @return the shiny
		 */
		public boolean isShiny() {
			return shiny;
		}

		/**
		 * @return the nameSpecies
		 */
		public String getNameSpecies() {
			return nameSpecies;
		}

		/**
		 * @return the kind
		 */
		public String getKind() {
			return kind;
		}

		/**
		 * @return the pokeEntry
		 */
		public String getPokeEntry() {
			return pokeEntry;
		}

		/**
		 * @return the catchRate
		 */
		public int getCatchRate() {
			return catchRate;
		}

		/**
		 * @return the growthRate
		 */
		public String getGrowthRate() {
			return growthRate;
		}

		/**
		 * @return the hatchSteps
		 */
		public int getHatchSteps() {
			return hatchSteps;
		}

		/**
		 * @return the color
		 */
		public String getColor() {
			return color;
		}

		/**
		 * @return the habitat
		 */
		public String getHabitat() {
			return habitat;
		}

		/**
		 * @return the height
		 */
		public String getHeight() {
			return height;
		}

		/**
		 * @return the weight
		 */
		public String getWeight() {
			return weight;
		}

		/**
		 * @return the gender
		 */
		public String getGender() {
			return gender;
		}

		/**
		 * @return the nameNick
		 */
		public String getNameNick() {
			return nameNick;
		}

		/**
		 * @param nameNick the nameNick to set
		 */
		public void setNameNick(String nameNick) {
			this.nameNick = nameNick;
		}

		/**
		 * @return the ballType
		 */
		public String getBallType() {
			return ballType;
		}

		/**
		 * @param ballType the ballType to set
		 */
		public void setBallType(String ballType) {
			this.ballType = ballType;
		}

		/**
		 * @return the curHP
		 */
		public int getCurHP() {
			return curHP;
		}

		/**
		 * @return the curEXP
		 */
		public int getCurEXP() {
			return curEXP;
		}

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * @return the wild
		 */
		public boolean isWild() {
			return wild;
		}

		/**
		 * @param wild the wild to set
		 */
		public void setWild(boolean wild) {
			this.wild = wild;
		}

		/**
		 * @return the fainted
		 */
		public boolean isFainted() {
			return fainted;
		}

		/**
		 * @param fainted the fainted to set
		 */
		public void setFainted(boolean fainted) {
			this.fainted = fainted;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @param curHP the curHP to set
		 */
		public void setCurHP(int curHP) {
			this.curHP = curHP;
			if (this.getCurHP() <= 0){
				faint();
			}
		}

		/**
		 * @param curEXP the curEXP to set
		 */
		public void setCurEXP(int curEXP) {
			this.curEXP = curEXP;
			if (this.getCurEXP() >= this.maxEXP){
				levelUp();
			}
		}
	// </editor-fold>

	/**
	 * @return the statSpeed
	 */
	public int getStatSpeed() {
		return statSpeed;
	}



}
