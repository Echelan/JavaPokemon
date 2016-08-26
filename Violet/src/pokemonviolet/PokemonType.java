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
public class PokemonType {
	static final private int NUMATTRIB = 7;
	
    private int id;
    private String nameDisplay;
	private String nameInternal;
	private String isPseudoType;
	private String isSpecialType;
	private String weakness;
	private String resistance;
	private String immunity;	
	
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

	// GETTERS AND SETTERS
	public String getNameDisplay() {
		return nameDisplay;
	}

	public String getWeakness() {
		return weakness;
	}

	public String getResistance() {
		return resistance;
	}

	public String getImmunity() {
		return immunity;
	}
	
}
