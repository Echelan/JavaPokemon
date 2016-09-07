/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 * @author Andres
 */
public class Map {

	//<editor-fold defaultstate="collapsed" desc="Statics">
		private static int TILE_WIDTH = 16;
		private static int TILE_HEIGHT = 16;
		private static int TILE_RESIZED_WIDTH = 32;
		private static int TILE_RESIZED_HEIGHT = 32;

		private static int MAX_TILE_IN_TILESET = 90;
		private static int MAX_TILE_IN_TILESET_ROW = 15;
		private static int MAX_SET_IN_TILESET = 6;
		private static int MAX_TILE_IN_SET = 15;
		private static int MAX_TILE_IN_SET_ROW = 5;
		private static int MAX_TILE_IN_SET_COL = 3;
		private static int MAX_SET_IN_TILE_SET_ROW = 3;

		private static int MAP_ROW_TILES = 20;
		private static int START_X = 30;
		private static int START_Y = 10;
		
		private static double MAP_MULT = 2.0;
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Static Images">
		private static BufferedImage tileset;
		private static BufferedImage gym;
		private static BufferedImage center;
		private static BufferedImage house;
		private static BufferedImage objects;
		private static BufferedImage shop;
		
		private static BufferedImage tilesetSMALL;
		private static BufferedImage gymSMALL;
		private static BufferedImage centerSMALL;
		private static BufferedImage houseSMALL;
		private static BufferedImage objectsSMALL;
		private static BufferedImage shopSMALL;
		
		private static BufferedImage[] objSets;
		private static BufferedImage[] objSetsSMALL;
	//</editor-fold>	
		
	//<editor-fold defaultstate="collapsed" desc="Vars">
		private JLabel[][] tileGridLabel;
		private ImageIcon[][] tileGridImage;
		private int[][] tile;
		private int[][] setT;
		private JLabel[][] objGridLabel;
		private ImageIcon[][] objGridImage;
		private int[][] obj;
		private int[][] setO;
		private int[][] objDims;

		private JButton[][] tileOutSetBtn;
		private JButton[][] tileInSetBtn;

		private JButton outBoxBtn;
		private JButton inBoxBtn;
		private JButton nextSetBtn;
		private JButton prevSetBtn;
		private JButton nextTileBtn;
		private JButton prevTileBtn;
		private JButton createObjBtn;
		private JButton clearObjBtn;
		private JButton centerDims;
		private JButton gymDims;
		private JButton houseDims;
		private JButton shopDims;
		private JButton saveMapBtn;
		private JButton loadMapBtn;
		private JButton lessID;
		private JButton moreID;
		private JButton swapInterfaceBtn;

		private JLabel tileID;
		private JLabel setID;
		private JLabel xCoordsDisplay;
		private JLabel yCoordsDisplay;
		private JLabel dimDisplay;
		private JLabel mapLabelID;
		private JPanel tilePanel;
		private JPanel objPanel;
		private JPanel genPanel;
		private boolean isTiling = true;
		private boolean isSelecting = false;
		private int xStart, yStart, xEnd, yEnd;
	//</editor-fold>
	
	public Map(int xMap, int yMap) {
		
        try {
			tilesetSMALL = ImageIO.read(new File("tileset.png"));
			
			shopSMALL = ImageIO.read(new File("shop.png"));
			
			gymSMALL = ImageIO.read(new File("gym.png"));
			
			centerSMALL = ImageIO.read(new File("center.png"));
			
			objectsSMALL = ImageIO.read(new File("objects.png"));
			
			houseSMALL = ImageIO.read(new File("house.png"));
			
			objSets = new BufferedImage[] {objects,house,center,shop,gym};
			objSetsSMALL = new BufferedImage[] {objectsSMALL,houseSMALL,centerSMALL,shopSMALL,gymSMALL};
			objDims = new int[][] { {1,1},{5,5},{5,5},{4,4},{6,5} };
			
			xStart=0;
			yStart=0;
			xEnd=MAP_ROW_TILES-1;
			yEnd=MAP_ROW_TILES-1;
			
			tileGridLabel = new JLabel[MAP_ROW_TILES][MAP_ROW_TILES];
			tileGridImage = new ImageIcon[MAP_ROW_TILES][MAP_ROW_TILES];
			objGridLabel = new JLabel[MAP_ROW_TILES][MAP_ROW_TILES];
			objGridImage = new ImageIcon[MAP_ROW_TILES][MAP_ROW_TILES];
			
			tile = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			setT = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			obj = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			setO = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			
        } catch (IOException ex) {

        }
	}
	
	public static BufferedImage getMapRegion(int id){
		BufferedImage mapRegion = null;
		
		if (Game.INFO_MAPS.get(id) != null){
			
			BufferedImage tempStitched = new BufferedImage( MAP_ROW_TILES*TILE_WIDTH*2 , MAP_ROW_TILES*TILE_HEIGHT*2, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) tempStitched.getGraphics();
			
			for (int i = 0; i < MAP_ROW_TILES; i++) {
				String[] thisrow = Game.INFO_MAPS.get(id).get(i).split(",");
				for (int j = 0; j < MAP_ROW_TILES; j++) {
					int xTile, yTile;
					
					int thistileset = Integer.parseInt(thisrow[j].split("-")[0], 16);
					int thistiletype = Integer.parseInt(thisrow[j].split("-")[1], 16);
					
					xTile = getTileX(thistileset,thistiletype);
					yTile = getTileY(thistileset,thistiletype);	

					g.drawImage( tilesetSMALL.getSubimage(xTile, yTile, TILE_WIDTH, TILE_HEIGHT), (int)(i*TILE_WIDTH*MAP_MULT), (int)(j*TILE_HEIGHT*MAP_MULT), (int)(TILE_WIDTH*MAP_MULT), (int)(TILE_HEIGHT*MAP_MULT), null);
					
					
					int thisobjset = Integer.parseInt(thisrow[j].split("-")[2], 16);
					int thisobjtype = Integer.parseInt(thisrow[j].split("-")[3], 16);
					
					xTile = getObjX(thisobjset,thisobjtype);
					yTile = getObjY(thisobjset,thisobjtype);	

					g.drawImage( objSetsSMALL[thisobjset].getSubimage(xTile, yTile, TILE_WIDTH, TILE_HEIGHT), (int)(i*MAP_MULT*TILE_WIDTH), (int)(j*MAP_MULT*TILE_HEIGHT),(int)(TILE_WIDTH*2), (int)(TILE_HEIGHT*MAP_MULT), null);
				}
			}
			
			mapRegion = tempStitched;
		}
		
		return mapRegion;
	}
			
	public static String[][] getMapBounds(int id){
		String[][] bounds = null;
		
		if (Game.INFO_MAPS.get(id) != null){
			bounds = new String[MAP_ROW_TILES][MAP_ROW_TILES];
			
			for (int i = 0; i < MAP_ROW_TILES; i++) {
				String[] thisrow = Game.INFO_MAPS.get(id).get(i).split(",");
				for (int j = 0; j < MAP_ROW_TILES; j++) {
					
					int thistileset = Integer.parseInt(thisrow[j].split("-")[0], 16);
				//	int thistiletype = Integer.parseInt(thisrow[j].split("-")[1], 16);
					
					int thisobjset = Integer.parseInt(thisrow[j].split("-")[2], 16);
					int thisobjtype = Integer.parseInt(thisrow[j].split("-")[3], 16);

					if (thistileset == 2){
						bounds[i][j] = "2";
					}else{
						bounds[i][j] = "0";
					}
					// objects, house, center, shop, gym
					if (thisobjset == 0){
						if (thisobjtype==0 || thisobjtype==3 || thisobjtype==8){
							
						}else if (thisobjtype == 7){
							bounds[i][j] = bounds[i][j] + "1";
						}else{
							bounds[i][j] = "1";
						}
					}else{
						bounds[i][j] = "1";
					}
					
				}
			}
		}
		
		return bounds;
	}
	
	public static int getTileX(int setinfo, int tileinfo){
		int regX = (int)(Math.floor((double)tileinfo%MAX_TILE_IN_SET_ROW)*TILE_WIDTH);
		int specialX = (int)(Math.floor((double)setinfo%MAX_SET_IN_TILE_SET_ROW)*(TILE_WIDTH*MAX_TILE_IN_SET_ROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX+specialX;
	}
	
	public static int getTileY(int setinfo, int tileinfo){
		int regY = (int)(Math.floor((double)tileinfo/MAX_TILE_IN_SET_ROW)*TILE_HEIGHT);
		int specialY = (int)(Math.floor((double)setinfo/MAX_SET_IN_TILE_SET_ROW)*(TILE_HEIGHT*MAX_TILE_IN_SET_COL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY+specialY;
	}
	
	public static int getObjX(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSetsSMALL[setinfo].getWidth()/TILE_WIDTH);
		int regX = (int)(Math.floor((double)tileinfo%maxtileinsetrow)*TILE_WIDTH);
	//	int specialX = (int)(Math.floor((double)setinfo%MAXSETINTILESETROW)*(TILERESIZEDWIDTH*MAXTILEINSETROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX;
	}
	
	public static int getObjY(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSetsSMALL[setinfo].getWidth()/TILE_WIDTH);
		int regY = (int)(Math.floor((double)tileinfo/maxtileinsetrow)*TILE_HEIGHT);
	//	int specialY = (int)(Math.floor((double)setinfo/MAXSETINTILESETROW)*(TILERESIZEDHEIGHT*MAXTILEINSETCOL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY;
	}
	
	
}
