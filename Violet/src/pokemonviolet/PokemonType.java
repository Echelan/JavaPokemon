/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.util.List;

/**
 *
 * @author Andres
 */
public class PokemonType {
	static final private int NUMATTRIB = 7;
	
    private static int id;
    private static String nameDisplay;
	private static String nameInternal;
	private static String isPseudoType;
	private static String isSpecialType;
	private static String weakness;
	private static String resistance;
	private static String immunity;	
	
	
    public PokemonType(int id) {
		this.id = id;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
    }
	
    public PokemonType(String internalName) {
		this.nameInternal = internalName;
		this.id = getTypeID(internalName);
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find Pokemon with id " + id + ".");
		}
    }
	
	private boolean readInfo(int id){
		boolean success = false;
		
		String[] iteminfo = Game.INFOTYPES.get(id-1).split(";");
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
	
	private int getTypeID(String internalName){
		int id = 0;
		
		boolean foundItem = false;
		List<String> lines = Game.INFOITEMS;

		while (foundItem == false){
			String[] iteminfo = lines.get(id).split(";");
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
}
