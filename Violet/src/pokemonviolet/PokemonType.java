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
public class PokemonType {
	
	//<editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Number of Attributes per Type.
		 */
		static final private int NUMATTRIB = 7;
		
		/**
		 * ID of Type.
		 */
		private final int id;
		/**
		 * Name of Type.
		 */
		private String nameDisplay;
		/**
		 * Internal name of Type.
		 */
		private String nameInternal;
		/**
		 * isPseudoType boolean. (TODO: FIX THIS)
		 */
		private String isPseudoType;
		/**
		 * isSpecialType boolean. (TODO: FIX THIS, ALSO WHAT IS THIS?)
		 */
		private String isSpecialType;
		/**
		 * Weaknesses of Type.
		 */
		private String weakness;
		/**
		 * Resistances of Type.
		 */
		private String resistance;
		/**
		 * Immunities of Type.
		 */
		private String immunity;	
	//</editor-fold>
	
	/**
	 * Create Type based on ID.
	 * @param id ID of Type to create.
	 */
    public PokemonType(int id) {
		this.id = id;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
    }
	
	/**
	 * Create Type based on internal name.
	 * @param internalName Internal name of Type to create.
	 */
    public PokemonType(String internalName) {
		this.nameInternal = internalName;
		this.id = getTypeID(internalName);
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
    }
	
	/**
	 * Get info from main data about Type with given ID.
	 * @param id Type ID to search for in main data.
	 * @return Success of process.
	 */
	private boolean readInfo(int id){
		boolean success = false;
		
		String[] iteminfo = Game.	INFOTYPES.get(id-1).split(";");
		for (int i = 0; i < NUMATTRIB; i++){
			String[] partes = iteminfo[i].split("=");
			if (partes[0].compareTo("InternalName")==0){
				this.nameInternal = partes[1];
			}else if (partes[0].compareTo("Name")==0){
				this.nameDisplay = partes[1];
			}else if (partes[0].compareTo("IsPseudoType")==0){
				this.isPseudoType = partes[1];
			}else if (partes[0].compareTo("IsSpecialType")==0){
				this.isSpecialType = partes[1];
			}else if (partes[0].compareTo("Weaknesses")==0){
				this.weakness = partes[1];
			}else if (partes[0].compareTo("Resistances")==0){
				this.resistance = partes[1];
			}else if (partes[0].compareTo("Immunities")==0){
				this.immunity = partes[1];
			}
		}
		success = true;
		
		return success;
	}
	
	/**
	 * Get ID of Type based on internal name.
	 * @param internalName Internal name of Type.
	 * @return Type ID.
	 */
	private int getTypeID(String internalName){
		int id = 0;
		
		boolean foundItem = false;

		while (foundItem == false){
			String[] iteminfo = Game.INFOITEMS.get(id).split(";");
			int attribComp = 0;
			while (attribComp < NUMATTRIB && foundItem == false){
				String[] partes = iteminfo[attribComp].split("=");
				if (partes[0].compareTo("internalName")==0){
					if (partes[1].compareTo(internalName)==0){
						foundItem = true;
					}else{
						attribComp = attribComp + 100;
					}
				}else{
					attribComp = attribComp + 1;
				}
			}
			id = id + 1;
		}
		if (!foundItem){
			System.err.println("Could not find item " + internalName + ".");
		}
		
		
		return id;
	}

	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

		/**
		 * @return the nameDisplay
		 */
		public String getNameDisplay() {
			return nameDisplay;
		}

		/**
		 * @return the weakness
		 */
		public String getWeakness() {
			return weakness;
		}

		/**
		 * @return the resistance
		 */
		public String getResistance() {
			return resistance;
		}

		/**
		 * @return the immunity
		 */
		public String getImmunity() {
			return immunity;
		}
	//</editor-fold>
		
}
