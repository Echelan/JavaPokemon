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
      String[] pokeinfo = new String[100];
      int limite = 0;
      System.out.println("ID: " + id);
     // pokeinfo[0] = "" + id;
        File archivo = new File("pokeDB2.txt");
            
            //archivoRuta.setText(archivo.getAbsolutePath());
            //DefaultTableModel model = (DefaultTableModel) tablaDatos.getModel();
            try {
                Scanner eyes = new Scanner(archivo);
                boolean foundPokemon = false;
              //  int counter=0;
               //    System.out.println( eyes.findInLine("\\[[\\d*]\\]") );
              // eyes.
                while (eyes.hasNextLine() && foundPokemon == false) {
                    String thisline = eyes.nextLine();
                //    counter++;
                //    System.out.println("LINE: " + counter);
                    //  System.out.println(thisline);
                    if ( thisline.matches("\\[\\d*\\]") ){
                    //String[] datos = thisline;
                    /*
                   System.out.println( thisline.substring(0, thisline.length()) );
                   System.out.println( thisline.substring(0, thisline.length()-1) );
                   System.out.println( thisline.substring(0, thisline.length()-2) );
                   System.out.println( thisline.substring(1, thisline.length()) );
                    */
                //   System.out.println( thisline.substring(1, thisline.length()-1) );
                   if (Integer.parseInt(thisline.substring(1, thisline.length()-1)) == id ){
                //    thisline.substring
                      foundPokemon = true;
             //         System.out.println( "Found it!" );
              //     System.out.println( thisline.substring(1, thisline.length()-1) );
                   //   System.out.println(thisline);
                   }
                  }
              //      String cedula = datos[0];
               //     String nombre = datos[1];
                //    int saldo = Integer.parseInt(datos[2]);
                    
                 //   model.addRow(new Object[]{cedula, nombre, saldo});
                }
                if (foundPokemon == true){
                       boolean endInfo = false;
                 //      int limite = 0;
                    while (eyes.hasNextLine() && endInfo == false) {
                        String thisline = eyes.nextLine();
                    //    counter++;
                    //    System.out.println("LINE: " + counter);
                        //  System.out.println(thisline);
                        if ( thisline.matches("\\[\\d*\\]") ){
                            
                            endInfo = true;
                        }else{
                            pokeinfo[ limite ] = thisline;
                            limite++;
                        }
                        
                        //String[] datos = thisline;
                        /*
                       System.out.println( thisline.substring(0, thisline.length()) );
                       System.out.println( thisline.substring(0, thisline.length()-1) );
                       System.out.println( thisline.substring(0, thisline.length()-2) );
                       System.out.println( thisline.substring(1, thisline.length()) );
                        */
                    //   System.out.println( thisline.substring(1, thisline.length()-1) );
                    //   if (Integer.parseInt(thisline.substring(1, thisline.length()-1)) == id ){
                    //    thisline.substring
                    //      foundPokemon = true;
                   //       System.out.println( "Found it!" );
                   //    System.out.println( thisline.substring(1, thisline.length()-1) );
                       //   System.out.println(thisline);
                    //   }
                  //    }
                  //      String cedula = datos[0];
                   //     String nombre = datos[1];
                    //    int saldo = Integer.parseInt(datos[2]);

                     //   model.addRow(new Object[]{cedula, nombre, saldo});
                    }
                    //System.out.println( "TXT: " + counter );
                }
                
            } catch (FileNotFoundException ex) {
                // TODO: No sé encontró el archivo
            }
            /*
        try {
                String content = readFile("pokeDB.txt", StandardCharsets.UTF_8);
        //	System.out.println(content);
                String[] parts = content.split("(?<=},)");
         //       String part1 = parts[0]; // 004
        //        String part2 = parts[1]; // 034556
         //   System.out.println(parts[0]);
        //    System.out.println("--");                    
         //   System.out.println(parts[1]);
         content=null;
         pokeinfo = parts[id];
         parts=null;
         
        } catch (IOException ex) {
                Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  System.out.println(pokeinfo);
        String[] info = pokeinfo.split(",");
        
    //      System.out.println(info[0]);
        info[0] = info[0].substring(1);
    //      System.out.println(info[0]);
        info[0] = info[0].substring(1);
     //     System.out.println(info[0]);
          
        info[info.length-1] = info[info.length-1].substring(0,info[info.length-1].length()-1);
    //    info[info.length] = info[info.length].substring(0,info[info.length-1].length()-1);
        for (int i = 0; i < info.length; i++) {
          info[i] = info[i].substring(1,info[i].length()-1);
        //  System.out.println(info[i]);
      }
          //System.out.println(info[0]);
		this.id = Integer.parseInt(info[0]);
        this.speciesName = info[1];
    //    this.types = types;
        this.catchrate = Integer.parseInt(info[4]);
    //    this.baseStats = baseStats;
    //    this.yieldXP = yieldXP;
    //    this.yieldEV = yieldEV;
    //    this.evolves = evolves;
    //    this.evolvesInto = evolvesInto;
    //    this.gender = gender;
    //    this.name = name;
    //    this.experience = experience;
    //    this.stats = stats;
        this.level = 1;
        */
          
            
            
     for (int i = 0; i < limite; i++){
    
         System.out.println( pokeinfo[i] );
         
         String[] partes = pokeinfo[i].split("=");
         
         if (partes[0].compareTo( "Name" )==0){
             this.name = partes[1];
         }else if (partes[0].compareTo("InternalName")==0){
             this.speciesName = partes[1];
         }else if (partes[0].compareTo("Pokedex")==0){
             this.pokeEntry = partes[1];
         }else if (partes[0].compareTo("Rareness")==0){
             this.catchRate = partes[1];
   //      }else if (partes[0].compareTo("Pokedex")==0){
   //          this.pokeEntry = partes[1];
         }else{
             
         }
     }
  }


}
