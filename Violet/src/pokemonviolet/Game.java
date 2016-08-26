/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;


/**
 *
 * @author Andres
 */
public class Game {
	
	public static List<String> INFOPOKEMON;
	public static List<String> INFOITEMS;
	public static List<String> INFOMOVES;
	public static List<String> INFOTYPES;
	
	public static Player player;
	public static int stepsToSpawn = calcSteps();;
        public static Pokemon currentPokemon;
	private static GameWindow gameWindow;
	private static ClassTestWindow classTestWindow;
	
	public Game() {
		
<<<<<<< Updated upstream
            SplashWindow splash = new SplashWindow();

            List<String> readInfoP = null;
            List<String> readInfoI = null;
            List<String> readInfoM = null;
            List<String> readInfoT = null;
            try {
                    File archivo = new File("listPokemon.txt");
                    readInfoP = Files.readAllLines(archivo.toPath());

                    archivo = new File("listItems.txt");
                    readInfoI = Files.readAllLines(archivo.toPath());

                    archivo = new File("listMoves.txt");
                    readInfoM = Files.readAllLines(archivo.toPath());

                    archivo = new File("listTypes.txt");
                    readInfoT = Files.readAllLines(archivo.toPath());
            } catch (IOException ex) {
                    System.err.println("Couldn't load files!");
                    System.exit(0);
            }

            INFOITEMS = readInfoI;
            INFOPOKEMON = readInfoP;
            INFOMOVES = readInfoM;
            INFOTYPES = readInfoT;


            try {
                    Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

            splash.setVisible(false);
            splash = null;

            player = new Player ("Red",1);
            player.addItem("POKEBALL",15);

        //    classTestWindow=new ClassTestWindow();
            gameWindow=new GameWindow();
=======
		SplashWindow splash = new SplashWindow();
		
		List<String> readInfoP = null;
		List<String> readInfoI = null;
		List<String> readInfoM = null;
		List<String> readInfoT = null;
		try {
			File archivo = new File("listPokemon.txt");
			readInfoP = Files.readAllLines(archivo.toPath());
			
			archivo = new File("listItems.txt");
			readInfoI = Files.readAllLines(archivo.toPath());
			
			archivo = new File("listMoves.txt");
			readInfoM = Files.readAllLines(archivo.toPath());
			
			archivo = new File("listTypes.txt");
			readInfoT = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}
		
		INFOITEMS = readInfoI;
		INFOPOKEMON = readInfoP;
		INFOMOVES = readInfoM;
		INFOTYPES = readInfoT;
				
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
		}
		splash.dispose();
		
		player = new Player ("Red",1);
		player.addItem("POKEBALL",15);
		
		classTestWindow=new ClassTestWindow();
		gameWindow=new GameWindow();
>>>>>>> Stashed changes
		
		splash.dispose();
	}
	
	public static int calcSteps(){
            int steps = 1;
            int numDados = 2;
            int numLados = 3;

            Random rnd = new Random();

            for (int i = 0; i < numDados; i++) {
              steps = steps + (rnd.nextInt(numLados)+1);
            }
    //	System.out.println(steps);
            return steps;
	}
}
