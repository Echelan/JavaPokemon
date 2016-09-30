/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;


/**
 *
 * @author Andres
 */
public abstract class PokemonType {
	
	/**
	 * Number of Attributes per Type.
	 */
	static final private int NUM_ATTRIB = 7;
	
	/**
	 * Get info from main data about Type with given ID.
	 * @param id Type ID to search for in main data.
	 * @return Information asked for.
	 */
	private static String readInfo(int id, String search){
		String returnValue = null;
		
		String[] typeInfo = Game.INFO_TYPES.get(id-1).split(";");
		for (int i = 0; i < NUM_ATTRIB; i++){
			String[] partes = typeInfo[i].split("=");
			if (partes[0].compareTo(search)==0){
				if (partes.length == 2){
				returnValue = partes[1];
				}else{
					returnValue="";
				}
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Returns ID of type.
	 * @param internalName
	 * @return ID of type with given internal name.
	 */
	private static int getId(String internalName){
		int id = 1;
		boolean found = false;
		while (!found){
			String[] typeInfo = Game.INFO_TYPES.get(id-1).split(";");
			for (int i = 0; i < NUM_ATTRIB; i++){
				String[] partes = typeInfo[i].split("=");
				if (partes[0].compareTo("InternalName")==0){
					if (partes[1].compareTo(internalName)==0){
						found=true;
					}else{
						id = id+1;
					}
				}
			}
		}
		
		return id;
	}
	
	/**
	 * @param id ID of type.
	 * @return the nameDisplay
	 */
	public static String getNameDisplay(int id) {
		return readInfo(id,"Name");
	}
	
	/**
	 * @param internalName Internal name of type.
	 * @return the nameDisplay
	 */
	public static String getNameDisplay(String internalName) {
		return readInfo(getId(internalName),"Name");
	}

	/**
	 * @param id ID of type.
	 * @return the nameInternal
	 */
	public static String getNameInternal(int id) {
		return readInfo(id,"InternalName");
	}

	/**
	 * @param id ID of type.
	 * @return the isPseudoType
	 */
	public static String getIsPseudoType(int id) {
		return readInfo(id,"IsPseudoType");
	}

	/**
	 * @param internalName Internal name of type.
	 * @return the isPseudoType
	 */
	public static String getIsPseudoType(String internalName) {
		return readInfo(getId(internalName),"IsPseudoType");
	}

	/**
	 * @param id ID of type.
	 * @return the isSpecialType
	 */
	public static String getIsSpecialType(int id) {
		return readInfo(id,"IsSpecialType");
	}

	/**
	 * @param internalName Internal name of type.
	 * @return the isSpecialType
	 */
	public static String getIsSpecialType(String internalName) {
		return readInfo(getId(internalName),"IsSpecialType");
	}

	/**
	 * @param id ID of type.
	 * @return the weakness
	 */
	public static String getWeakness(int id) {
		return readInfo(id,"Weaknesses");
	}

	/**
	 * @param internalName Internal name of type.
	 * @return the weakness
	 */
	public static String getWeakness(String internalName) {
		return readInfo(getId(internalName),"Weaknesses");
	}

	/**
	 * @param id ID of type.
	 * @return the resistance
	 */
	public static String getResistance(int id) {
		return readInfo(id,"Resistances");
	}

	/**
	 * @param internalName Internal name of type.
	 * @return the resistance
	 */
	public static String getResistance(String internalName) {
		return readInfo(getId(internalName),"Resistances");
	}

	/**
	 * @param id ID of type.
	 * @return the immunity
	 */
	public static String getImmunity(int id) {
		return readInfo(id,"Immunities");
	}

	/**
	 * @param internalName Internal name of type.
	 * @return the immunity
	 */
	public static String getImmunity(String internalName) {
		return readInfo(getId(internalName),"Immunities");
	}
	
	
		
}
