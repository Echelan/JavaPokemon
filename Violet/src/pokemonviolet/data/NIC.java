/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import pokemonviolet.model.Map;

/**
 *
 * @author Andres
 */
public abstract class NIC {

	//<editor-fold defaultstate="collapsed" desc="Information Lists">
		/**
		 * Main data for Pokemon.
		 */
		public static List<String> INFO_POKEMON;
		/**
		 * Main data for Items.
		 */
		public static List<String> INFO_ITEMS;
		/**
		 * Main data for Moves.
		 */
		public static List<String> INFO_MOVES;
		/**
		 * Main data for Types.
		 */
		public static List<String> INFO_TYPES;
		/**
		 * Main data for Maps.
		 */
		public static ArrayList<List<String>> INFO_MAPS;
		/**
		 * Blank map data.
		 */
		public static List<String> INFO_BLANK_MAP;
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Static Attributes">
		/**
		 * Amount of Maps in X.
		 */
		public static int NUM_MAPS_X;
		/**
		 * Amount of Maps in Y.
		 */
		public static int NUM_MAPS_Y;
		public static BufferedImage pokemonIcons;
	//</editor-fold>

	public static void loadData() {

		Map.loadImages();

		NUM_MAPS_X = 4;
		NUM_MAPS_Y = 4;

		List<String> readInfoP = null;
		List<String> readInfoI = null;
		List<String> readInfoM = null;
		List<String> readInfoT = null;
		List<String> readInfoB = null;
		ArrayList<List<String>> readMap = new ArrayList();
		
		try {
			pokemonIcons = ImageIO.read(new File("assets/pokemon/pokemonIconsSmall.png"));
		} catch (IOException ex) {
		}

		File archivo;

		try {

			archivo = new File("listPokemon.txt");
			readInfoP = Files.readAllLines(archivo.toPath());

		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listItems.txt");
			readInfoI = Files.readAllLines(archivo.toPath());

		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listMoves.txt");
			readInfoM = Files.readAllLines(archivo.toPath());

		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("listTypes.txt");
			readInfoT = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		try {
			archivo = new File("mapBLANK.txt");
			readInfoB = Files.readAllLines(archivo.toPath());
		} catch (IOException ex) {
			System.err.println("Couldn't load files!");
			System.exit(0);
		}

		for (int y = 0; y < NUM_MAPS_Y; y++) {
			for (int x = 0; x < NUM_MAPS_X; x++) {
				List<String> temp = null;
				try {
					archivo = new File("mapX" + x + "Y" + y + ".txt");
					temp = Files.readAllLines(archivo.toPath());
				} catch (IOException ex1) {
					temp = readInfoB;
				}
				readMap.add(temp);
			}
		}

		INFO_ITEMS = readInfoI;
		INFO_POKEMON = readInfoP;
		INFO_MOVES = readInfoM;
		INFO_TYPES = readInfoT;
		INFO_MAPS = readMap;
		INFO_BLANK_MAP = readInfoB;
	}
}
