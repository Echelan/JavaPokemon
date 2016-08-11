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
import static testamovilla.TestAMovilla.readFile;

/**
 *
 * @author Andres
 */
public class Pokemon {
	// 'GLOBAL'
    final int id;
	/*
	final String speciesName;
	final TipoPokemon[] types;
	final int catchrate;
	final int[] baseStats;
	final int yieldXP;
	final int[] yieldEV;
	final boolean evolves;
	final int evolvesInto;
	
	// 'LOCAL'
	String gender;
	String name;
	int experience;
	int[] stats;
	int level;
	*/
	
  public Pokemon(int id) {
      String pokeinfo = null;
	this.id = id;
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
         pokeinfo = parts[this.id];
         parts=null;
         
        } catch (IOException ex) {
                Logger.getLogger(TestAMovilla.class.getName()).log(Level.SEVERE, null, ex);
        }
        //system.
        System.out.println(pokeinfo);
  }

}
