/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.util.Random;

/**
 * @author Andres
 */
public class Pokemon {
	
	//<editor-fold defaultstate="collapsed" desc="Attributes">
		// 'GLOBAL'
		static final private int NUM_ATTRIB = 40;
		static final private int MAX_TOTAL_EV = 510;
		static final private int MAX_SINGLE_EV = 252;

		private int id;
		private final boolean shiny;
		private String nameSpecies;
		private String nameInternal;
		private String kind;
		private String pokeEntry;
		private PokemonType[] types;

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
		private String[] allMoves;

		private String height;
		private String weight;

		private int numEvols;
		private String[] evolvesInto;
		private String[] evolveMethod;
		private int[] evolveLevel;
		private String[] evolveItem;


		// 'LOCAL'
		private String gender;
		private String nameNick;
		private String ballType;
		private PokemonMove[] moves;
		private int curHP;
		private int curEXP;
		private int maxEXP;
		private int level;
		private boolean wild;
		private boolean fainted;
		private String status;
	//</editor-fold>
	
	/**
	 * Create Pokemon based on given ID.
	 * @param id Pokemon ID to create.
	 */
	public Pokemon(int id) {
		this.id = id;
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
		this.ballType = "POKEBALL";
		this.wild = true;
		this.status="";
		
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
		this.ballType = "POKEBALL";
		this.wild = true;
		this.status="";
		
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
		if (ballType.compareTo("WILD") != 0){
			this.ballType = ballType;
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
	 */
	public Pokemon(String nameInternal, int ballType) {
		this.nameInternal = nameInternal;
		this.id = getPokemonID(nameInternal);
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = 0;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
		if (ballType.compareTo("WILD") != 0){
			this.ballType = ballType;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
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
		this.shiny = false;
		this.gender = "TBD";
		this.level = level;
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
				this.allMoves = new String[101];
				String[] readMoves = partes[1].split(",");
				for (int j = 0; j < readMoves.length; j = j+2) {
					int position = Integer.parseInt(readMoves[j]);
					if (this.allMoves[position] != null){
						this.allMoves[position] = this.allMoves[position] + "," + readMoves[j+1];
					}else{
						this.allMoves[position] = readMoves[j+1];
					}
				}
				if (this.getLevel() == 0){
					Random rnd = new Random();
					this.level = rnd.nextInt(99)+1;
					int moveCount = 0;
					int allMovesCounter = this.getLevel() + 1;
					this.moves = new PokemonMove[4];
					boolean finished = false;
					while (!finished){
						allMovesCounter = allMovesCounter - 1;
						if (this.allMoves[allMovesCounter] != null){
							String[] thisLevelMoves = this.allMoves[allMovesCounter].split(",");
							for (int j = 0; j < thisLevelMoves.length; j++) {
								if (moveCount < 4){
									this.moves[moveCount] = new PokemonMove(thisLevelMoves[j]);
									moveCount = moveCount + 1;
								}
							}
						}
						if (moveCount == 4 || allMovesCounter == 1){
							finished = true;
						}
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
		moves[place] = newMove;
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
			for (int i = 0; i < moves.length; i++) {
				if (moves[i].getId() == oldMove){
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
			for (int i = 0; i < moves.length; i++) {
				if (moves[i].getNameInternal().compareTo(oldMove)== 0){
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
	
	// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
		
		public boolean getCanEvolve(){
			return (this.numEvols > 0);
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
		
}
