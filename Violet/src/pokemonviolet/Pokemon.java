/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static pokemonviolet.GameWindow.readFile;

/**
 *
 * @author Andres
 */
public class Pokemon {
	// 'GLOBAL'
  //  final int id;
	
	final String speciesName;
//	final TipoPokemon[] types;
	final int catchRate;
        final String pokeEntry;
	//final int[] baseStats;
	//final int yieldXP;
	//final int[] yieldEV;
	//final boolean evolves;
	//final int evolvesInto;
	
	// 'LOCAL'
//	String gender;
	String name;
	//int experience;
	//int[] stats;
//	int level;
	
	
  public Pokemon(int id) {
	  this.name = "";
	  this.pokeEntry = "";
	  this.speciesName = "";
	  this.catchRate = 1;
      int limite = 0;
	  
      String[] pokeinfo = new String[30];
      System.out.println("ID: " + id);
	  
        File archivo = new File("pokemonlist.txt");
            try {
                Scanner eyes = new Scanner(archivo);
                boolean foundPokemon = false;
                while (eyes.hasNextLine() && foundPokemon == false) {
                    String thisline = eyes.nextLine();
					if ( thisline.matches("\\[\\d*\\]") ){
						if (Integer.parseInt(thisline.substring(1, thisline.length()-1)) == id ){
							foundPokemon = true;
						}
					}
                }
                if (foundPokemon == true){
                       boolean endInfo = false;
                    while (eyes.hasNextLine() && endInfo == false) {
                        String thisline = eyes.nextLine();
                        if ( thisline.matches("\\[\\d*\\]") ){
                            
                            endInfo = true;
                        }else{
                            pokeinfo[ limite ] = thisline;
                            limite++;
                        }
                    }
                }
            }catch (FileNotFoundException ex) {
            }
          
            
            
     for (int i = 0; i < limite; i++){
    
         System.out.println( pokeinfo[i] );
         
         String[] partes = pokeinfo[i].split("=");
         
         if (partes[0].compareTo( "Name" )==0){
    //         this.name = partes[1];
         }else if (partes[0].compareTo("InternalName")==0){
    //         this.speciesName = partes[1];
         }else if (partes[0].compareTo("Pokedex")==0){
    //         this.pokeEntry = partes[1];
         }else if (partes[0].compareTo("Rareness")==0){
    //         this.catchRate = partes[1];
   //      }else if (partes[0].compareTo("Pokedex")==0){
   //          this.pokeEntry = partes[1];
         }else{
             
         }
     }
  }


}
