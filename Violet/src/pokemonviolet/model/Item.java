/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Item {

	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Number of attributes per Item.
		 */
		private static int NUM_ATTRIB = 11;
		private static int SPRITE_WIDTH = 60;
		private static int SPRITE_HEIGHT = 60;
		
		/**
		 * ID of Item.
		 */
		private final int id;
		/**
		 * Name of Item, singular form.
		 */
		private String nameSingular;
		/**
		 * Name of Item, plural form.
		 */
		private String namePlural;
		/**
		 * Internal name of Item.
		 */
		private String nameInternal;
		/**
		 * Pocket where Item is found.
		 */
		private int pocket;
		/**
		 * Price for Item in a store.
		 */
		private int price;
		/**
		 * Description of Item.
		 */
		private String description;
		/**
		 * Usage out of battle of Item.
		 */
		private int useOutBattle;
		/**
		 * Usage in battle of Item.
		 */
		private int useInBattle;
		/**
		 * Pokemon catch rate of Item, only applicable for Pokeballs.
		 */
		private double pokeRate;
		/**
		 * Value for items.
		 */
		private double value;
		/**
		 * Amount of Item.
		 */
		private int amount;
		/**
		 * Name of move to teach.
		 */
		private String moveName;
	// </editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Constructors">
		/**
		 * Create Item based on given ID.
		 *
		 * @param id Item ID to create.
		 */
		public Item(int id) {
			this.id = id;
			this.amount = 1;

			boolean couldCreate = readInfo(this.id);

			if (!couldCreate) {
				System.err.println("Could not find item with id " + id + ".");
			}
		}

		/**
		 * Create Item based on given ID and given amount.
		 *
		 * @param id Item ID to create.
		 * @param amount Item amount to create.
		 */
		public Item(int id, int amount) {
			this.id = id;
			this.amount = amount;

			boolean couldCreate = readInfo(this.id);

			if (!couldCreate) {
				System.err.println("Could not find item with id " + id + ".");
			}
		}

		/**
		 * Create Item based on given internal name.
		 *
		 * @param internalName Item internal name to create.
		 */
		public Item(String internalName) {
			this.id = getItemID(internalName);
			this.amount = 1;

			boolean couldCreate = readInfo(this.id);

			if (!couldCreate) {
				System.err.println("Could not find item with id " + id + ".");
			}
		}

		/**
		 * Create Item based on given internal and given amount.
		 *
		 * @param internalName Item internal name to create.
		 * @param amount Item amount to create.
		 */
		public Item(String internalName, int amount) {
		this.id = getItemID(internalName);
		this.amount = amount;

		boolean couldCreate = readInfo(this.id);

		if (!couldCreate) {
			System.err.println("Could not find item with id " + id + ".");
		}
	}
	//</editor-fold>
	
	/**
	 * Acquire information from main data about Item with given ID.
	 *
	 * @param id Item to search for in main data.
	 * @return Success of process.
	 */
	private boolean readInfo(int id) {
		boolean success = false;

		String[] iteminfo = pokemonviolet.data.NIC.INFO_ITEMS.get(id - 1).split(";");
		for (int i = 0; i < NUM_ATTRIB; i++) {
			String[] partes = iteminfo[i].split("=");
			if (partes[0].compareTo("internalName") == 0) {
				this.nameInternal = partes[1];
			} else if (partes[0].compareTo("nameSingular") == 0) {
				this.nameSingular = partes[1];
			} else if (partes[0].compareTo("namePlural") == 0) {
				this.namePlural = partes[1];
			} else if (partes[0].compareTo("pocket") == 0) {
				this.pocket = Integer.parseInt(partes[1]);
			} else if (partes[0].compareTo("price") == 0) {
				this.price = Integer.parseInt(partes[1]);
			} else if (partes[0].compareTo("description") == 0) {
				this.description = partes[1];
			} else if (partes[0].compareTo("useOutBattle") == 0) {
				this.useOutBattle = Integer.parseInt(partes[1]);
			} else if (partes[0].compareTo("useInBattle") == 0) {
				this.useInBattle = Integer.parseInt(partes[1]);
			} else if (partes[0].compareTo("pokeRate") == 0) {
				this.pokeRate = Double.parseDouble(partes[1]);
			} else if (partes[0].compareTo("moveName") == 0 && partes.length > 1) {
				this.moveName = partes[1];
			}
		}
		switch (id){
			case 1:
				this.value = 100;
				break;
			case 2:
				this.value = 200;
				break;
			case 3:
				this.value = 250;
				break;
			case 19:
				this.value = 20;
				break;
			case 20:
				this.value = 50;
				break;
			case 21:
				this.value = 200;
				break;
			case 34:
				this.value = 20;
				break;
			case 35:
				this.value = 50;
				break;
			case 36:
				this.value = 60;
				break;
			case 37:
				this.value = 80;
				break;
			case 38:
				this.value = 100;
				break;
			case 39:
				this.value = 50;
				break;
			case 40:
				this.value = 200;
				break;
			case 43:
				this.value = 10;
				break;
			case 47:
				this.value = 1;
				break;
			case 48:
				this.value = 10;
				break;
			default:
				this.value = 0;
				break;
		}
		
		success = true;

		return success;
	}

	/**
	 * Searches in main data for Item with given internal name, and returns its
	 * ID.
	 *
	 * @param internalName Item internal name to search for.
	 * @return Item ID.
	 */
	public static int getItemID(String internalName) {
		int id = 0;

		boolean foundItem = false;

		while (foundItem == false && id < pokemonviolet.data.NIC.INFO_ITEMS.size()) {
			String[] iteminfo = pokemonviolet.data.NIC.INFO_ITEMS.get(id).split(";");
			int attribComp = 0;
			while (attribComp < NUM_ATTRIB && foundItem == false) {
				String[] partes = iteminfo[attribComp].split("=");
				if (partes[0].compareTo("internalName") == 0) {
					if (partes[1].compareTo(internalName) == 0) {
						foundItem = true;
					} else {
						attribComp = attribComp + 100;
					}
				} else {
					attribComp = attribComp + 1;
				}
			}
			id = id + 1;
		}
		if (!foundItem) {
			System.err.println("Could not find item " + internalName + ".");
		}

		return id;
	}

	public BufferedImage getImage() throws IOException {
		BufferedImage image = new BufferedImage(SPRITE_WIDTH, SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		int x = ( (this.id - 1) % 24 ) * (SPRITE_WIDTH / 2), y = ((int) Math.floor( (double) (this.id-1) / 24 )) * (SPRITE_HEIGHT / 2);
		
		g.drawImage((ImageIO.read(new File("assets/itemsIcons.png"))).getSubimage(x, y, SPRITE_WIDTH/2, SPRITE_HEIGHT/2),0,0,SPRITE_WIDTH,SPRITE_HEIGHT,null);
		
		return image;
	}

	// <editor-fold defaultstate="collapsed" desc="Getters & Setters">
		/**
		 * @return the nameSingular
		 */
		public String getNameSingular() {
			return nameSingular;
		}

		/**
		 * @return the namePlural
		 */
		public String getNamePlural() {
			return namePlural;
		}

		/**
		 * @return the pocket
		 */
		public int getPocket() {
			return pocket;
		}

		/**
		 * @return the price
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @return the pokeRate
		 */
		public double getPokeRate() {
			return pokeRate;
		}

		/**
		 * @return the amount
		 */
		public int getAmount() {
			return amount;
		}

		/**
		 * @param amount the amount to set
		 */
		public void setAmount(int amount) {
			this.amount = amount;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the nameInternal
		 */
		public String getNameInternal() {
			return nameInternal;
		}
		
		/**
		 * @return the useOutBattle
		 */
		public int getUseOutBattle() {
			return useOutBattle;
		}

		/**
		 * @return the useInBattle
		 */
		public int getUseInBattle() {
			return useInBattle;
		}

		/**
		 * @return the moveName
		 */
		public String getMoveName() {
			return moveName;
		}
		
		/**
		 * @return the value
		 */
		public double getValue() {
			return value;
		}
	//</editor-fold>



}
