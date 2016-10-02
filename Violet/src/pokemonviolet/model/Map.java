/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;


/**
 *
 * @author Andres
 */
public class Map {
	
	//<editor-fold defaultstate="collapsed" desc="Static Images">
		private static BufferedImage tileset;
		private static BufferedImage gym;
		private static BufferedImage center;
		private static BufferedImage house;
		private static BufferedImage objects;
		private static BufferedImage shop;
		private static BufferedImage house2;
		private static BufferedImage house3;
		private static BufferedImage wstone;
		private static BufferedImage wstone2;
		private static BufferedImage tree;
		private static BufferedImage tree2;
		private static BufferedImage[] objSets;
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Statics">
		private static int TILE_WIDTH = 16;
		private static int TILE_HEIGHT = 16;

		private static int MAX_TILE_IN_TILESET = 120;
		private static int MAX_TILE_IN_TILESET_ROW = 20;
		private static int MAX_SET_IN_TILESET = 8;
		private static int MAX_TILE_IN_SET = 15;
		private static int MAX_TILE_IN_SET_ROW = 5;
		private static int MAX_TILE_IN_SET_COL = 3;
		private static int MAX_SET_IN_TILE_SET_ROW = 4;
		
		public static int MAP_TOTAL_SIZE_X;
		public static int MAP_TOTAL_SIZE_Y;

		public static int MAP_ROW_TILES = 20;
		
		private static double MAP_MULT = 2.0;
	//</editor-fold>
		
	//<editor-fold defaultstate="collapsed" desc="Vars">
		private final int xMap, yMap;
	//	private int x,y;
		private final BufferedImage image;
		private final String[][] bounds;
		private int[][] pokemonInMap;
		private List<String> tileInformation;
	//</editor-fold>
	
	public static void loadImages(){
		try{
			tileset = ImageIO.read(new File("assets/map/tileset.png"));
			
			shop = ImageIO.read(new File("assets/map/shop.png"));
			
			gym = ImageIO.read(new File("assets/map/gym.png"));
			
			center = ImageIO.read(new File("assets/map/center.png"));
			
			objects = ImageIO.read(new File("assets/map/objects.png"));
			
			house = ImageIO.read(new File("assets/map/house.png"));
			
			tree = ImageIO.read(new File("assets/map/tree.png"));
			
			tree2 = ImageIO.read(new File("assets/map/tree2.png"));
			
			house2 = ImageIO.read(new File("assets/map/house2.png"));
			
			house3 = ImageIO.read(new File("assets/map/house3.png"));
			
			wstone = ImageIO.read(new File("assets/map/wstone.png"));
			
			wstone2 = ImageIO.read(new File("assets/map/wstone2.png"));
			
			objSets = new BufferedImage[] {objects,house,house2,house3,center,shop,gym,tree,tree2,wstone,wstone2};
		}catch (IOException ex){
		}
		
		MAP_TOTAL_SIZE_X = (int)(MAP_ROW_TILES*TILE_WIDTH*MAP_MULT);
		MAP_TOTAL_SIZE_Y = (int)(MAP_ROW_TILES*TILE_HEIGHT*MAP_MULT);
	}
		
	public Map(List<String> info, int x, int y, int posX, int posY) {
		pokemonInMap = new int[151][7];
		
		this.xMap = Integer.parseInt(info.get(0).split(";")[0]);
		this.yMap = Integer.parseInt(info.get(0).split(";")[1]);
		/*
		this.x = calcX(x,posX);
		this.y = calcY(y,posY);
		*/
		for (int i = 0; i < pokemonInMap.length; i++) {
			pokemonInMap[i][0] = 0;
		}
		
		if (info.get(1).compareTo("")!=0){
			String[] list = info.get(1).split(";");
			for (int i = 0; i < list.length; i++) {
				int num = Integer.parseInt(list[i].split(":")[1]);
				num = num - 1;
				String[] clusterFuck = list[i].split(":")[0].split(",");

				pokemonInMap[num][0] = 1;

				pokemonInMap[num][1] = Integer.parseInt(clusterFuck[0].split("-")[0]);
				pokemonInMap[num][2] = Integer.parseInt(clusterFuck[0].split("-")[1]);
				pokemonInMap[num][3] = Integer.parseInt(clusterFuck[1].split("-")[0]);
				pokemonInMap[num][4] = Integer.parseInt(clusterFuck[1].split("-")[1]);
				pokemonInMap[num][5] = Integer.parseInt(clusterFuck[2].split("-")[0]);
				pokemonInMap[num][6] = Integer.parseInt(clusterFuck[2].split("-")[1]);
			}
		}
		
		info.remove(1);
		info.remove(0);
		
		this.image = createMapRegion(info);
		
		this.bounds = createMapBounds(info);
		
		this.tileInformation = info;
	}
		
	private BufferedImage createMapRegion(List<String> info){
		BufferedImage mapRegion;
		
		BufferedImage tempStitched = new BufferedImage( MAP_TOTAL_SIZE_X, MAP_TOTAL_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempStitched.getGraphics();

		for (int i = 0; i < MAP_ROW_TILES; i++) {
			String[] thisrow = info.get(i).split(",");
			for (int j = 0; j < MAP_ROW_TILES; j++) {
				int xTile, yTile;

				int thistileset = Integer.parseInt(thisrow[j].split("-")[0], 16);
				int thistiletype = Integer.parseInt(thisrow[j].split("-")[1], 16);

				xTile = getTileXinImage(thistileset,thistiletype);
				yTile = getTileYinImage(thistileset,thistiletype);
				
				g.drawImage( tileset.getSubimage(xTile, yTile, TILE_WIDTH, TILE_HEIGHT), (int)(i*TILE_WIDTH*MAP_MULT), (int)(j*TILE_HEIGHT*MAP_MULT), (int)(TILE_WIDTH*MAP_MULT), (int)(TILE_HEIGHT*MAP_MULT), null);

				int thisobjset = Integer.parseInt(thisrow[j].split("-")[2], 16);
				int thisobjtype = Integer.parseInt(thisrow[j].split("-")[3], 16);

				xTile = getObjXinImage(thisobjset,thisobjtype);
				yTile = getObjYinImage(thisobjset,thisobjtype);	

				g.drawImage( objSets[thisobjset].getSubimage(xTile, yTile, TILE_WIDTH, TILE_HEIGHT), (int)(i*TILE_WIDTH*MAP_MULT), (int)(j*TILE_HEIGHT*MAP_MULT),(int)(TILE_WIDTH*MAP_MULT), (int)(TILE_HEIGHT*MAP_MULT), null);
			}
		}

		mapRegion = tempStitched;
		
		return mapRegion;
	}
			
	private String[][] createMapBounds(List<String> info){
		String[][] bounds;
		
		bounds = new String[MAP_ROW_TILES][MAP_ROW_TILES];

		for (int i = 0; i < MAP_ROW_TILES; i++) {
			String[] thisrow = info.get(i).split(",");
			for (int j = 0; j < MAP_ROW_TILES; j++) {

				int thistileset = Integer.parseInt(thisrow[j].split("-")[0], 16);
			//	int thistiletype = Integer.parseInt(thisrow[j].split("-")[1], 16);

				int thisobjset = Integer.parseInt(thisrow[j].split("-")[2], 16);
				int thisobjtype = Integer.parseInt(thisrow[j].split("-")[3], 16);

				if (thistileset == 2 || thistileset == 7){
					bounds[i][j] = "2";
				}else{
					bounds[i][j] = "0";
				}
				// objects, house, house2, house3, center, shop, gym, tree, tree2, wstone, wstone2
				if (thisobjset == 0){
					if (thisobjtype==0){
					}else if (thisobjtype == 1){
						bounds[i][j] = bounds[i][j] + "1";
				//	}else if (thisobjtype == 2){
				//		bounds[i][j] = bounds[i][j] + "2";
				//	}else if (thisobjtype == 5){
				//		bounds[i][j] = bounds[i][j] + "3";
				//	}else if (thisobjtype == 6){
				//		bounds[i][j] = bounds[i][j] + "3";
					}else{
						bounds[i][j] = "1";
					}
				}else{
					bounds[i][j] = "1";
				}

			}
		}
		
		return bounds;
	}
	
	public static int getTileXinImage(int setinfo, int tileinfo){
		int regX = (int)(Math.floor((double)tileinfo%MAX_TILE_IN_SET_ROW)*TILE_WIDTH);
		int specialX = (int)(Math.floor((double)setinfo%MAX_SET_IN_TILE_SET_ROW)*(TILE_WIDTH*MAX_TILE_IN_SET_ROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX+specialX;
	}
	
	public static int getTileYinImage(int setinfo, int tileinfo){
		int regY = (int)(Math.floor((double)tileinfo/MAX_TILE_IN_SET_ROW)*TILE_HEIGHT);
		int specialY = (int)(Math.floor((double)setinfo/MAX_SET_IN_TILE_SET_ROW)*(TILE_HEIGHT*MAX_TILE_IN_SET_COL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY+specialY;
	}
	
	public static int getObjXinImage(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSets[setinfo].getWidth()/TILE_WIDTH);
		int regX = (int)(Math.floor((double)tileinfo%maxtileinsetrow)*TILE_WIDTH);
	//	int specialX = (int)(Math.floor((double)setinfo%MAXSETINTILESETROW)*(TILERESIZEDWIDTH*MAXTILEINSETROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX;
	}
	
	public static int getObjYinImage(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSets[setinfo].getWidth()/TILE_WIDTH);
		int regY = (int)(Math.floor((double)tileinfo/maxtileinsetrow)*TILE_HEIGHT);
	//	int specialY = (int)(Math.floor((double)setinfo/MAXSETINTILESETROW)*(TILERESIZEDHEIGHT*MAXTILEINSETCOL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY;
	}
	
	public String[] getTileInformation(int x, int y){
		
		return (this.tileInformation.get(y).split(",")[x].split("-"));
	}
	
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @return the bounds
	 */
	public String[][] getBounds() {
		return bounds;
	}

	/**
	 * @return the pokemonInMap
	 */
	public int[][] getPokemonInMap() {
		return pokemonInMap;
	}
	
	public int[] getWildPokemon(int x, int y){
		int[][] pokemonInTile = new int[100][2];
		int numPokemon=0;
		
		Random rnd = new Random();
		
		for (int i = 0; i < pokemonInMap.length; i++) {
			if (pokemonInMap[i][0]==1){
				if (pokemonInMap[i][3] <= x &&  x <= pokemonInMap[i][4] && pokemonInMap[i][5] <= y &&  y <= pokemonInMap[i][6]){
					pokemonInTile[numPokemon][0] = i+1;
					pokemonInTile[numPokemon][1] = rnd.nextInt(pokemonInMap[i][2]-pokemonInMap[i][1])+pokemonInMap[i][1];
					
					numPokemon = numPokemon + 1;
				}
			}
		}
		
		if (numPokemon == 0){
			pokemonInTile[0][0] = 1;
			pokemonInTile[0][1] = 5;
			numPokemon = numPokemon+1;
		}
		return pokemonInTile[rnd.nextInt(numPokemon)];
	}
	
	/**
	 * @return the xMap
	 */
	public int getxMap() {
		return xMap;
	}

	/**
	 * @return the yMap
	 */
	public int getyMap() {
		return yMap;
	}
	
	
}
