/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import static testamovilla.GameWindow.readFile;

/**
 *
 * @author Andres
 */
public class Pokemon {
	// 'GLOBAL'
    final int id;
	
	final String speciesName;
	//final TipoPokemon[] types;
	final int catchrate;
	//final int[] baseStats;
	//final int yieldXP;
	//final int[] yieldEV;
	//final boolean evolves;
	//final int evolvesInto;
	
	// 'LOCAL'
	//String gender;
	//String name;
	//int experience;
	//int[] stats;
	int level;
	
	
  public Pokemon(int id) {
      String pokeinfo = null;
    //  System.out.println(id);
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
     //     System.out.println(info[0]);
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
  }

}
