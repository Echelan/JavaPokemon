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
 *
 * @author Andres
 */
public class PokemonMove {
	
	//<editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Number of attributes per Move.
		 */
		static final private int NUM_ATTRIB = 13;
		
		/**
		 * ID of Move
		 */
		private final int id;
		/**
		 * Name of Move.
		 */
		private String nameDisplay;
		/**
		 * Internal name of Move.
		 */
		private String nameInternal;
		/**
		 * Type of Move.
		 */
		private PokemonType type;
		/**
		 * Description of Move.
		 */
		private String description;
		/**
		 * Category of Move.
		 */
		private String category;
		/**
		 * PowerPoints of Move.
		 */
		private int PP;
		/**
		 * Max PowerPoints of Move.
		 */
		private int PPMax;
		/**
		 * Damage Calculation (Made-Up) Values.
		 */
		private int dmgBase,numDice,numSides;
		/**
		 * Accuracy of Move.
		 */
		private int accuracy;
		/**
		 * Flags of Move.
		 */
		private String flags;
	//</editor-fold>
	
	/**
	 * Create PokemonMove based on given ID.
	 * @param id PokemonMove ID to create.
	 */
	public PokemonMove(int id){
		this.id = id;

		boolean couldCreate = readInfo( this.id );

		if (!couldCreate){
			System.err.println("Could not find Move with id " + id + ".");
		}
	}

	/**
	 * Create PokemonMove based on given ID.
	 * @param nameInternal PokemonMove nameInternal to create.
	 */
	public PokemonMove(String nameInternal){
		this.nameInternal = nameInternal;
		this.id = getMoveID(nameInternal);

		boolean couldCreate = readInfo( this.id );

		if (!couldCreate){
			System.err.println("Could not find Move with id " + id + ".");
		}
	}

	/**
	 * Acquire information from main data about Pokemon moves with given ID.
	 * @param id PokemonMove to search for in main data.
	 * @return Success of process.
	 */
	private boolean readInfo(int id){
			boolean success = false;

			String[] iteminfo = Game.INFO_MOVES.get(id-1).split(";");
			for (int i = 0; i < NUM_ATTRIB; i++){
					String[] partes = iteminfo[i].split("=");
					if (partes[0].compareTo("internalName")==0){
							this.nameInternal = partes[1];
					}else if (partes[0].compareTo("displayName")==0){
							this.nameDisplay = partes[1];
					}else if (partes[0].compareTo("basePower")==0){
							this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("code")==0){
			//		this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("type")==0){
			//		this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("category")==0){
							this.category = partes[1];
					}else if (partes[0].compareTo("description")==0){
							this.description = partes[1];
					}else if (partes[0].compareTo("accuracy")==0){
							this.accuracy = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("totalPP")==0){
						this.setPPMax(Integer.parseInt(partes[1]));
					}else if (partes[0].compareTo("effectChance")==0){
			//		this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("target")==0){
			//		this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("priority")==0){
			//		this.dmgBase = Integer.parseInt(partes[1]);
					}else if (partes[0].compareTo("flags")==0){
							this.flags = partes[1];
					}
			}
			success = true;

			return success;
	}
	
	/**
	 * Searches in main data for PokemonMove with given internal name, and returns its ID.
	 * @param internalName PokemonMove internal name to search for.
	 * @return Move ID.
	 */
	private int getMoveID(String internalName){
		int id = 0;
		boolean foundMove = false;
	//	System.out.println("Searching for " + internalName);
		while (foundMove == false && id < Game.INFO_MOVES.size()){
			String[] moveinfo = Game.INFO_MOVES.get(id).split(";");
			int attribComp = 0;
			while (attribComp < NUM_ATTRIB && foundMove == false){
					String[] partes = moveinfo[attribComp].split("=");
					if (partes[0].compareTo("internalName")==0){
						if (partes[1].compareTo(internalName)==0){
							foundMove = true;
						}else{
							attribComp = attribComp + 100;
						}
					}else{
						attribComp = attribComp + 1;
					}
			}
			id = id + 1;
		}
	//	if (!foundMove){
	//		System.err.println("Could not find move " + internalName + ".");
	//	}


		return id;
	}
	
	/**
	 * Calculate damage caused of move.
	 * @return Damage caused.
	 */
	public int calcDaño(){
	  int daño = dmgBase;
	  //random rnd = new random();
	  Random rnd = new Random();

	  for (int i = 0; i < numDice; i++) {
		daño = daño + (rnd.nextInt(numSides)+1);
	  }

	  return daño;
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
		
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the nameDisplay
		 */
		public String getNameDisplay() {
			return nameDisplay;
		}

		/**
		 * @return the nameInternal
		 */
		public String getNameInternal() {
			return nameInternal;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @return the PP
		 */
		public int getPP() {
			return PP;
		}

		/**
		 * @param PP the PP to set
		 */
		public void setPP(int PP) {
			this.PP = PP;
		}

		/**
		 * @return the PPMax
		 */
		public int getPPMax() {
			return PPMax;
		}

		/**
		 * @param PPMax the PPMax to set
		 */
		public void setPPMax(int PPMax) {
			this.PPMax = PPMax;
		}
	//</editor-fold>
	
}
