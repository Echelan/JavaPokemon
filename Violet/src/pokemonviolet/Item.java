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
	private int special; //Pokeball catch rate, TM move
	private int amount;

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
		
		boolean couldCreate = readInfo( id );
		
		if (!couldCreate){
			System.err.println("Could not find item with id " + id + ".");
		}
	}

	private boolean readInfo(int id){
		boolean success = false;
		
		int limite = 0;
		String[] iteminfo = new String[NUMATTRIB];
	  
        File archivo = new File("listItem.txt");
		try {
			boolean foundItem = false;
			Scanner eyes = new Scanner(archivo);
			while (eyes.hasNextLine() && foundItem == false) {
				String thisline = eyes.nextLine();
				if ( thisline.matches("\\[\\d*\\]") ){
					if (Integer.parseInt(thisline.substring(1, thisline.length()-1)) == id ){
						foundItem = true;
					}
				}
			}
			if (foundItem == true){
				boolean endInfo = false;
				while (eyes.hasNextLine() && endInfo == false) {
					String thisline = eyes.nextLine();
					if ( thisline.matches("\\[\\d*\\]") ){

						endInfo = true;
					}else{
						iteminfo[ limite ] = thisline;
						limite++;
					}
				}
			}
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
				}else if (partes[0].compareTo("special")==0){
					this.special = Integer.parseInt(partes[1]);
				}
			}
			success = true;
		}catch (FileNotFoundException ex) {
		}
		
		return success;
	}
}
