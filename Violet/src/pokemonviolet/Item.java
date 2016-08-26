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
public class Item {
	static int NUMATTRIB = 10;
	
    private int id;
	private String nameSingular;
	private String namePlural;
	private String nameInternal;
	private int pocket;
	private int price;
	private String description;
	private int useOutBattle;
	private int useInBattle;
	private double pokeRate; 
	private int amount;

	/**
	 * Create Item based on ID.
	 * @param id ID to create Item with.
	 */
	public Item(int id) {
		this.id = id;
		this.amount = 1;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find item with id " + id + ".");
		}
	}
	
	public Item(int id, int amount){
		this.id = id;
		this.amount = amount;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find item with id " + id + ".");
		}
	}

	public Item(String internalName) {
		this.id = getItemID(internalName);
		this.amount = 1;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find item with id " + id + ".");
		}
	}
	
	public Item(String internalName, int amount){
		this.id = getItemID(internalName);
		this.amount = amount;
		
		boolean couldCreate = readInfo( this.id );
		
		if (!couldCreate){
			System.err.println("Could not find item with id " + id + ".");
		}
	}

	private boolean readInfo(int id){
		boolean success = false;
		
		String[] iteminfo = Game.INFOITEMS.get(id-1).split(";");
		for (int i = 0; i < NUMATTRIB; i++){
			String[] partes = iteminfo[i].split("=");
			if (partes[0].compareTo("internalName")==0){
				this.nameInternal = partes[1];
			}else if (partes[0].compareTo( "nameSingular" )==0){
				this.nameSingular = partes[1];
			}else if (partes[0].compareTo("namePlural")==0){
				this.namePlural = partes[1];
			}else if (partes[0].compareTo("pocket")==0){
				this.pocket = Integer.parseInt(partes[1]);
			}else if (partes[0].compareTo("price")==0){
				this.price = Integer.parseInt(partes[1]);
			}else if (partes[0].compareTo("description")==0){
				this.description = partes[1];
			}else if (partes[0].compareTo("useOutBattle")==0){
				this.useOutBattle = Integer.parseInt(partes[1]);
			}else if (partes[0].compareTo("useInBattle")==0){
				this.useInBattle = Integer.parseInt(partes[1]);
			}else if (partes[0].compareTo("pokeRate")==0){
				this.pokeRate = Double.parseDouble(partes[1]);
			}
		}
		success = true;
		
		return success;
	}

	public String getNameInternal() {
		return nameInternal;
	}

	private int getItemID(String internalName){
		int id = 0;
		
		boolean foundItem = false;

		while (foundItem == false && id < Game.INFOITEMS.size()){
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
	public int getId() {
		return id;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getNameSingular() {
		return nameSingular;
	}

	public String getNamePlural() {
		return namePlural;
	}

	public int getPocket() {
		return pocket;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getUseOutBattle() {
		return useOutBattle;
	}

	public int getUseInBattle() {
		return useInBattle;
	}

	public double getPokeRate() {
		return pokeRate;
	}

	public int getAmount() {
		return amount;
	}

}
