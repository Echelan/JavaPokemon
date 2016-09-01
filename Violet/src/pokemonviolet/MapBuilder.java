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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author Andres
 */
public class MapBuilder extends JFrame implements WindowListener, ActionListener, MouseListener, KeyListener {

	private JLabel[][] tileGridLabel;
	private ImageIcon[][] tileGridImage;
	private int[][] tile;
	private int[][] setT;
	private JLabel[][] objGridLabel;
	private ImageIcon[][] objGridImage;
	private int[][] obj;
	private int[][] setO;
	
	//<editor-fold defaultstate="collapsed" desc="statics">
	private static int TILEWIDTH = 16;
	private static int TILEHEIGHT = 16;
	private static int TILERESIZEDWIDTH = 32;
	private static int TILERESIZEDHEIGHT = 32;
	
	private static int MAXTILEINTILESET = 90;
	private static int MAXTILEINTILESETROW = 15;
	private static int MAXSETINTILESET = 6;
	private static int MAXTILEINSET = 15;
	private static int MAXTILEINSETROW = 5;
	private static int MAXTILEINSETCOL = 3;
	private static int MAXSETINTILESETROW = 3;
	
	private static int MAPROWTILES = 20;
	private static int STARTX = 30;
	private static int STARTY = 10;
	//</editor-fold>
	
	private static BufferedImage tileset;
	private static BufferedImage gym;
	private static BufferedImage center;
	private static BufferedImage house;
	private static BufferedImage objects;
	private static BufferedImage shop;
	private static BufferedImage[] objSets;
	
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
	
	private JLabel tileID;
	private JLabel setID;
	private JLabel xCoordsDisplay;
	private JLabel yCoordsDisplay;
	private JButton[][] tileOutSetBtn;
	private JButton[][] tileInSetBtn;
	private int xStart, yStart, xEnd, yEnd;
	private boolean isSelecting = false;
	private boolean isTiling = true;
	private JPanel tilePanel;
	private JPanel objPanel;
	private JPanel genPanel;
	private final JButton swapInterfaceBtn;
	private JLabel dimDisplay;
	
	public MapBuilder() {
		setLayout(null);
		setSize(900,700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField sad = new JTextField();
		sad.setBounds(800,0,1,1);
		sad.addKeyListener(this);
		add(sad);
		
        try {
			BufferedImage oldTileset = ImageIO.read(new File("tileset.png"));
			tileset = scale(oldTileset,240,96,2.0,2.0);
			
			BufferedImage oldShop = ImageIO.read(new File("shop.png"));
			shop = scale(oldShop,64,64,2.0,2.0);
			
			BufferedImage oldGym = ImageIO.read(new File("gym.png"));
			gym = scale(oldGym,96,80,2.0,2.0);
			
			BufferedImage oldCenter = ImageIO.read(new File("center.png"));
			center = scale(oldCenter,80,80,2.0,2.0);
			
			BufferedImage oldObjects = ImageIO.read(new File("objects.png"));
			objects = scale(oldObjects,48,48,2.0,2.0);
			
			BufferedImage oldHouse = ImageIO.read(new File("house.png"));
			house = scale(oldHouse,80,80,2.0,2.0);
			
			objSets = new BufferedImage[] {objects,house,center,shop,gym};
			
			xStart=0;
			yStart=0;
			xEnd=MAPROWTILES-1;
			yEnd=MAPROWTILES-1;
			
			tileGridLabel = new JLabel[MAPROWTILES][MAPROWTILES];
			tileGridImage = new ImageIcon[MAPROWTILES][MAPROWTILES];
			objGridLabel = new JLabel[MAPROWTILES][MAPROWTILES];
			objGridImage = new ImageIcon[MAPROWTILES][MAPROWTILES];
			tile = new int[MAPROWTILES][MAPROWTILES];
			setT = new int[MAPROWTILES][MAPROWTILES];
			obj = new int[MAPROWTILES][MAPROWTILES];
			setO = new int[MAPROWTILES][MAPROWTILES];
			for (int i = 0; i < MAPROWTILES; i++) {
				for (int j = 0; j < MAPROWTILES; j++) {
					int xTile,yTile,xPos,yPos;
					
					obj[i][j] = 0;
					setO[i][j] = 0;
					
					xTile = getObjX(setO[i][j],obj[i][j]);
					yTile = getObjY(setO[i][j],obj[i][j]);					
					
					objGridImage[i][j] = new ImageIcon(objSets[obj[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
					
					xPos = STARTX+(i*TILERESIZEDWIDTH);
					yPos = STARTY+(j*TILERESIZEDHEIGHT);
					objGridLabel[i][j] = new JLabel(objGridImage[i][j]);
					objGridLabel[i][j].addMouseListener(this);
					objGridLabel[i][j].setBounds(xPos,yPos,TILERESIZEDWIDTH,TILERESIZEDHEIGHT);
					add(objGridLabel[i][j]);
					
					tile[i][j] = 0;
					setT[i][j] = 0;
					
					xTile = getTileX(setT[i][j],tile[i][j]);
					yTile = getTileY(setT[i][j],tile[i][j]);					
					
					tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
					
					xPos = STARTX+(i*TILERESIZEDWIDTH);
					yPos = STARTY+(j*TILERESIZEDHEIGHT);
					tileGridLabel[i][j] = new JLabel(tileGridImage[i][j]);
					tileGridLabel[i][j].addMouseListener(this);
					tileGridLabel[i][j].setBounds(xPos,yPos,TILERESIZEDWIDTH,TILERESIZEDHEIGHT);
					add(tileGridLabel[i][j]);
					
				}
			}
        } catch (IOException ex) {

        }
		
		swapInterfaceBtn = new JButton("Swap");
		swapInterfaceBtn.setBounds(740,620,90,40);
		swapInterfaceBtn.addActionListener(this);
		swapInterfaceBtn.setFocusable(false);
		add(swapInterfaceBtn);
		
		createGenInterface();
		createTileInterface();
		createObjInterface();
		objPanel.setVisible(false);
		
		setVisible(true);
	}

	public void createGenInterface(){
		genPanel = new JPanel();
		genPanel.setLayout(null);
		genPanel.setBounds(680,5,200,250);
		genPanel.setBackground(Color.blue);
		add(genPanel);
		
		xCoordsDisplay = new JLabel("START: ("+xStart+","+yStart+")");
		xCoordsDisplay.setBounds(10,10,140,20);
		xCoordsDisplay.setForeground(Color.white);
		xCoordsDisplay.setFocusable(false);
		genPanel.add(xCoordsDisplay);
		
		yCoordsDisplay = new JLabel("END: ("+xEnd+","+yEnd+")");
		yCoordsDisplay.setBounds(10,30,140,20);
		yCoordsDisplay.setForeground(Color.white);
		yCoordsDisplay.setFocusable(false);
		genPanel.add(yCoordsDisplay);
		
		tileID = new JLabel("Tile ID: 0");
		tileID.setBounds(110,10,140,20);
		tileID.setForeground(Color.white);
		tileID.setFocusable(false);
		genPanel.add(tileID);
		
		setID = new JLabel("Set ID: 0");
		setID.setBounds(110,30,140,20);
		setID.setForeground(Color.white);
		setID.setFocusable(false);
		genPanel.add(setID);
			
		nextSetBtn = new JButton("Next Set");
		nextSetBtn.setBounds(100,50,90,30);
		nextSetBtn.addActionListener(this);
		nextSetBtn.setFocusable(false);
		genPanel.add(nextSetBtn);
			
		prevSetBtn = new JButton("Prev Set");
		prevSetBtn.setBounds(5,50,90,30);
		prevSetBtn.addActionListener(this);
		prevSetBtn.setFocusable(false);
		genPanel.add(prevSetBtn);
			
		nextTileBtn = new JButton("Next Tile");
		nextTileBtn.setBounds(100,90,90,30);
		nextTileBtn.addActionListener(this);
		nextTileBtn.setFocusable(false);
		genPanel.add(nextTileBtn);
			
		prevTileBtn = new JButton("Prev Tile");
		prevTileBtn.setBounds(5,90,90,30);
		prevTileBtn.addActionListener(this);
		prevTileBtn.setFocusable(false);
		genPanel.add(prevTileBtn);
		
		saveMapBtn = new JButton("Save");
		saveMapBtn.setBounds(5,150,90,40);
		saveMapBtn.addActionListener(this);
		saveMapBtn.setFocusable(false);
		genPanel.add(saveMapBtn);
		
		loadMapBtn = new JButton("Load");
		loadMapBtn.setBounds(100,150,90,40);
		loadMapBtn.addActionListener(this);
		loadMapBtn.setFocusable(false);
		genPanel.add(loadMapBtn);
		
		dimDisplay = new JLabel("("+(xEnd+1-xStart)+"x"+(yEnd+1-yStart)+")");
		dimDisplay.setBounds(75,200,140,20);
		dimDisplay.setForeground(Color.white);
		dimDisplay.setFocusable(false);
		genPanel.add(dimDisplay);
	}
	
	public void createTileInterface(){
		tilePanel = new JPanel();
		tilePanel.setLayout(null);
		tilePanel.setBounds(680,260,200,350);
		tilePanel.setBackground(Color.orange);
		add(tilePanel);
			
		outBoxBtn = new JButton("Set Outer Box");
		outBoxBtn.setBounds(40,10,120,30);
		outBoxBtn.addActionListener(this);
		outBoxBtn.setFocusable(false);
		tilePanel.add(outBoxBtn);
			
		inBoxBtn = new JButton("Set Inner Box");
		inBoxBtn.setBounds(40,50,120,30);
		inBoxBtn.addActionListener(this);
		inBoxBtn.setFocusable(false);
		tilePanel.add(inBoxBtn);
		
		tileOutSetBtn = new JButton[3][3];
		for (int i = 0; i < tileOutSetBtn.length; i++) {
			for (int j = 0; j < tileOutSetBtn[i].length; j++) {
				tileOutSetBtn[i][j] = new JButton();
				tileOutSetBtn[i][j].setBounds(35+(i*45),90+(j*45),40,40);
				tileOutSetBtn[i][j].addActionListener(this);
				tileOutSetBtn[i][j].setFocusable(false);
				tilePanel.add(tileOutSetBtn[i][j]);
			}
		}
		
		tileInSetBtn = new JButton[2][2];
		for (int i = 0; i < tileInSetBtn.length; i++) {
			for (int j = 0; j < tileInSetBtn[i].length; j++) {
				tileInSetBtn[i][j] = new JButton();
				tileInSetBtn[i][j].setBounds(60+(i*45),230+(j*45),40,40);
				tileInSetBtn[i][j].addActionListener(this);
				tileInSetBtn[i][j].setFocusable(false);
				tilePanel.add(tileInSetBtn[i][j]);
			}
		}
	}
	
	public void createObjInterface(){
		objPanel = new JPanel();
		objPanel.setLayout(null);
		objPanel.setBounds(680,260,200,350);
		objPanel.setBackground(Color.green);
		add(objPanel);
		
		createObjBtn = new JButton("Create Object");
		createObjBtn.setBounds(40,10,120,30);
		createObjBtn.addActionListener(this);
		createObjBtn.setFocusable(false);
		objPanel.add(createObjBtn);
			
		clearObjBtn = new JButton("Clear Object");
		clearObjBtn.setBounds(40,50,120,30);
		clearObjBtn.addActionListener(this);
		clearObjBtn.setFocusable(false);
		objPanel.add(clearObjBtn);
		
		centerDims = new JButton("PC(5x5)");
		centerDims.setBounds(5,90,90,30);
		centerDims.addActionListener(this);
		centerDims.setFocusable(false);
		objPanel.add(centerDims);
		
		shopDims = new JButton("MRT(4x4)");
		shopDims.setBounds(105,90,90,30);
		shopDims.addActionListener(this);
		shopDims.setFocusable(false);
		objPanel.add(shopDims);
		
		gymDims = new JButton("GYM(6x5)");
		gymDims.setBounds(5,130,90,30);
		gymDims.addActionListener(this);
		gymDims.setFocusable(false);
		objPanel.add(gymDims);
		
		houseDims = new JButton("HSE(5x5)");
		houseDims.setBounds(105,130,90,30);
		houseDims.addActionListener(this);
		houseDims.setFocusable(false);	
		objPanel.add(houseDims);
	}
	
	public void swapInterfaces(){
		if (isTiling){
			isTiling = false;
			tilePanel.setVisible(false);
			objPanel.setVisible(true);
		}else{
			isTiling = true;
			tilePanel.setVisible(true);
			objPanel.setVisible(false);
		}
	}
	
	public void setObj(int type)
	{
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				setO[i][j] = type;
			}
		}
		createObj();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == outBoxBtn){
			setOutBox();
		}else if (e.getSource() == inBoxBtn){
			setInBox();
		}else if (e.getSource() == nextSetBtn){
			nextSet();
		}else if (e.getSource() == prevSetBtn){
			prevSet();
		}else if (e.getSource() == prevTileBtn){
			prevTile();
		}else if (e.getSource() == nextTileBtn){
			nextTile();
		}else if (e.getSource() == swapInterfaceBtn){
			swapInterfaces();
		}else if (e.getSource() == clearObjBtn){
			cleanObj();
		}else if (e.getSource() == createObjBtn){
			createObj();
		}else if (e.getSource() == houseDims){
			setObj(1);
		}else if (e.getSource() == gymDims){
			setObj(4);
		}else if (e.getSource() == centerDims){
			setObj(2);
		}else if (e.getSource() == shopDims){
			setObj(3);
		}else if (e.getSource() == saveMapBtn){
			writeMapFile();
		}else if (e.getSource() == loadMapBtn){
			loadMapFile();
		}else{
			setTile(e);
		}
	}
	
	public void setOutBox(){
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tile[i][j]=0;
				if (i>xStart){
					tile[i][j]=tile[i][j]+1;
					if (i==xEnd){
						tile[i][j]=tile[i][j]+1;
					}
				}
				if (j>yStart){
					tile[i][j]=tile[i][j]+MAXTILEINSETROW;
					if (j==yEnd){
						tile[i][j]=tile[i][j]+MAXTILEINSETROW;
					}
				}
				int xTile = getTileX(setT[i][j],tile[i][j]);
				int yTile = getTileY(setT[i][j],tile[i][j]);
				tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
			}
		}
	}
	
	public void setInBox(){
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tile[i][j]=12;
				if (i>xStart){
					tile[i][j]=tile[i][j]-1;
					if (i==xEnd){
						tile[i][j]=tile[i][j]-1;
					}
				}
				if (j>yStart){
					tile[i][j]=tile[i][j]-MAXTILEINSETROW;
					if (j==yEnd){
						tile[i][j]=tile[i][j]-MAXTILEINSETROW;
					}
				}
				if (i == xStart && j == yStart){
					tile[i][j]=8;
				}else if (i == xStart && j == yEnd){
					tile[i][j]=13;
				}else if (i == xEnd && j == yStart){
					tile[i][j]=9;
				}else if (i == xEnd && j == yEnd){
					tile[i][j]=14;
				}
				int xTile = getTileX(setT[i][j],tile[i][j]);
				int yTile = getTileY(setT[i][j],tile[i][j]);
				tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
			}
		}
	}
	
	public void prevSet(){
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		if (isTiling){
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		}else{
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		}
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				setInfo[i][j]=setInfo[i][j]-1;
				if (isTiling){
					if (tileInfo[i][j] >= MAXTILEINSET){
						tileInfo[i][j] = tileInfo[i][j] - MAXTILEINSET ;
					}else if (tile[i][j] < 0){
						tileInfo[i][j] = MAXTILEINSET + tileInfo[i][j];
					}
					if (setInfo[i][j] >= MAXSETINTILESET){
						setInfo[i][j] = setInfo[i][j] - MAXSETINTILESET ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = MAXSETINTILESET + setInfo[i][j];
					}
				}else{
					int maxset = objSets.length;
					if (setInfo[i][j] >= maxset){
						setInfo[i][j] = setInfo[i][j] - maxset ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = maxset + setInfo[i][j];
					}
					int maxtile = (objSets[setInfo[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setInfo[i][j]].getHeight()/TILERESIZEDHEIGHT);
					if (tileInfo[i][j] >= maxtile){
						tileInfo[i][j] = tileInfo[i][j] - maxtile ;
					}else if (tile[i][j] < 0){
						tileInfo[i][j] = maxtile + tileInfo[i][j];
					}
				}

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
					yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				}else{
					xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
					yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				}
				if (isTiling){
					imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}else{
					imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public void nextSet(){
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		if (isTiling){
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		}else{
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		}
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				setInfo[i][j]=setInfo[i][j]+1;
				if (isTiling){
					if (tileInfo[i][j] >= MAXTILEINSET){
						tileInfo[i][j] = tileInfo[i][j] - MAXTILEINSET ;
					}else if (tile[i][j] < 0){
						tileInfo[i][j] = MAXTILEINSET + tileInfo[i][j];
					}
					if (setInfo[i][j] >= MAXSETINTILESET){
						setInfo[i][j] = setInfo[i][j] - MAXSETINTILESET ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = MAXSETINTILESET + setInfo[i][j];
					}
				}else{
					int maxset = objSets.length;
					if (setInfo[i][j] >= maxset){
						setInfo[i][j] = setInfo[i][j] - maxset ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = maxset + setInfo[i][j];
					}
					int maxtile = (objSets[setInfo[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setInfo[i][j]].getHeight()/TILERESIZEDHEIGHT);
					if (tileInfo[i][j] >= maxtile){
						tileInfo[i][j] = tileInfo[i][j] - maxtile ;
					}else if (tile[i][j] < 0){
						tileInfo[i][j] = maxtile + tileInfo[i][j];
					}
				}

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
					yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				}else{
					xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
					yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				}
				if (isTiling){
					imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}else{
					imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public void prevTile(){
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		if (isTiling){
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		}else{
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		}
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tileInfo[i][j]=tileInfo[i][j]-1;
				if (isTiling){
					if (tileInfo[i][j] >= MAXTILEINSET){
						tileInfo[i][j] = tileInfo[i][j] - MAXTILEINSET ;
					}else if (tileInfo[i][j] < 0){
						tileInfo[i][j] = MAXTILEINSET + tileInfo[i][j];
					}
				}else{
					int maxtile = (objSets[setInfo[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setInfo[i][j]].getHeight()/TILERESIZEDHEIGHT);
					if (tileInfo[i][j] >= maxtile){
						tileInfo[i][j] = tileInfo[i][j] - maxtile ;
					}else if (tileInfo[i][j] < 0){
						tileInfo[i][j] = maxtile + tileInfo[i][j];
					}
				}

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
					yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				}else{
					xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
					yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				}
				
			//	imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
			//	labelInfo[i][j].setIcon(tileGridImage[i][j]);
			
				if (isTiling){
					imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}else{
					imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public void nextTile(){
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		if (isTiling){
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		}else{
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		}
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tileInfo[i][j]=tileInfo[i][j]+1;
				if (isTiling){
					if (tileInfo[i][j] >= MAXTILEINSET){
						tileInfo[i][j] = tileInfo[i][j] - MAXTILEINSET ;
					}else if (tileInfo[i][j] < 0){
						tileInfo[i][j] = MAXTILEINSET + tileInfo[i][j];
					}
				}else{
					int maxtile = (objSets[setInfo[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setInfo[i][j]].getHeight()/TILERESIZEDHEIGHT);
					if (tileInfo[i][j] >= maxtile){
						tileInfo[i][j] = tileInfo[i][j] - maxtile ;
					}else if (tileInfo[i][j] < 0){
						tileInfo[i][j] = maxtile + tileInfo[i][j];
					}
				}

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
					yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				}else{
					xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
					yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				}
				
			//	imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
			//	labelInfo[i][j].setIcon(imageInfo[i][j]);
				if (isTiling){
					imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}else{
					imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public void setTile(ActionEvent e){
		int tileX=0, tileY=0, base=0;
		for (int i = 0; i < tileOutSetBtn.length; i++) {
			for (int j = 0; j < tileOutSetBtn[i].length; j++) {
				if (tileOutSetBtn[i][j] == e.getSource()){
					tileX = i;
					tileY = j;
					base = 0;
				}
			}
		}
		for (int i = 0; i < tileInSetBtn.length; i++) {
			for (int j = 0; j < tileInSetBtn[i].length; j++) {
				if (tileInSetBtn[i][j] == e.getSource()){
					tileX = i;
					tileY = j;
					base = 8;
				}
			}
		}
		int tileID = base + tileX + (tileY*5);
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tile[i][j]=tileID;

				int xTile = getTileX(setT[i][j],tile[i][j]);
				int yTile = getTileY(setT[i][j],tile[i][j]);
				
				tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
			}
		}
	}
	
	public void modTile(MouseEvent e){
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		if (isTiling){
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		}else{
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		}
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {int mods = (int)((long)(e.getModifiers()-e.MOUSE_EVENT_MASK));

				if (mods == e.CTRL_MASK + e.ALT_MASK){
					setInfo[i][j]=setInfo[i][j] - 1;
				}else if (mods == e.CTRL_MASK){
					setInfo[i][j] = setInfo[i][j] + 1;
				}else if (mods == e.ALT_MASK){
					tileInfo[i][j]=tileInfo[i][j] - 1;
				}else{
					tileInfo[i][j]=tileInfo[i][j] + 1;
				}
					
				if (isTiling){
					if (tileInfo[i][j] >= MAXTILEINSET){
						tileInfo[i][j] = tileInfo[i][j] - MAXTILEINSET ;
					}else if (tileInfo[i][j] < 0){
						tileInfo[i][j] = MAXTILEINSET + tileInfo[i][j];
					}
					if (setInfo[i][j] >= MAXSETINTILESET){
						setInfo[i][j] = setInfo[i][j] - MAXSETINTILESET ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = MAXSETINTILESET + setInfo[i][j];
					}
				}else{
					int maxtile = (objSets[setInfo[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setInfo[i][j]].getHeight()/TILERESIZEDHEIGHT);
					if (tileInfo[i][j] >= maxtile){
						tileInfo[i][j] = tileInfo[i][j] - maxtile ;
					}else if (tile[i][j] < 0){
						tileInfo[i][j] = maxtile + tileInfo[i][j];
					}
					if (setInfo[i][j] >= objSets.length){
						setInfo[i][j] = setInfo[i][j] - objSets.length ;
					}else if (setInfo[i][j] < 0){
						setInfo[i][j] = objSets.length + setInfo[i][j];
					}
				}

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
					yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				}else{
					xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
					yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				}
				
				if (isTiling){
					imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}else{
					imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				}
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public void cleanObj(){
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				obj[i][j] = 0;
				setO[i][j] = 0;

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setO[i][j],obj[i][j]);
					yTile = getTileY(setO[i][j],obj[i][j]);
				}else{
					xTile = getObjX(setO[i][j],obj[i][j]);
					yTile = getObjY(setO[i][j],obj[i][j]);
				}
				
				objGridImage[i][j]= new ImageIcon(objSets[setO[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				objGridLabel[i][j].setIcon(objGridImage[i][j]);				
			}
		}
	}
	
	public void createObj(){
		int counter = 0;
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				
				obj[i][j] =  counter;
				int rowtile = (objSets[setO[i][j]].getWidth()/TILERESIZEDWIDTH);
				counter = counter+rowtile;
				
				int maxtile = (objSets[setO[i][j]].getWidth()/TILERESIZEDWIDTH)*(objSets[setO[i][j]].getHeight()/TILERESIZEDHEIGHT);
				if (counter >= maxtile){
					counter = counter - (maxtile-1) ;
				}
				

				int xTile;
				int yTile;
				if (isTiling){
					xTile = getTileX(setO[i][j],obj[i][j]);
					yTile = getTileY(setO[i][j],obj[i][j]);
				}else{
					xTile = getObjX(setO[i][j],obj[i][j]);
					yTile = getObjY(setO[i][j],obj[i][j]);
				}
				
				objGridImage[i][j]= new ImageIcon(objSets[setO[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				objGridLabel[i][j].setIcon(objGridImage[i][j]);
			}
		}
	}
	
	public static int getTileX(int setinfo, int tileinfo){
		int regX = (int)(Math.floor((double)tileinfo%MAXTILEINSETROW)*TILERESIZEDWIDTH);
		int specialX = (int)(Math.floor((double)setinfo%MAXSETINTILESETROW)*(TILERESIZEDWIDTH*MAXTILEINSETROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX+specialX;
	}
	
	public static int getTileY(int setinfo, int tileinfo){
		int regY = (int)(Math.floor((double)tileinfo/MAXTILEINSETROW)*TILERESIZEDHEIGHT);
		int specialY = (int)(Math.floor((double)setinfo/MAXSETINTILESETROW)*(TILERESIZEDHEIGHT*MAXTILEINSETCOL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY+specialY;
	}
	
	public static int getObjX(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSets[setinfo].getWidth()/TILERESIZEDWIDTH);
		int regX = (int)(Math.floor((double)tileinfo%maxtileinsetrow)*TILERESIZEDWIDTH);
	//	int specialX = (int)(Math.floor((double)setinfo%MAXSETINTILESETROW)*(TILERESIZEDWIDTH*MAXTILEINSETROW));
		
	//	System.out.println("X: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialX+", TILE: "+regX+") = "+(regX+specialX));
		
		return regX;
	}
	
	public static int getObjY(int setinfo, int tileinfo){
		int maxtileinsetrow = (objSets[setinfo].getWidth()/TILERESIZEDWIDTH);
		int regY = (int)(Math.floor((double)tileinfo/maxtileinsetrow)*TILERESIZEDHEIGHT);
	//	int specialY = (int)(Math.floor((double)setinfo/MAXSETINTILESETROW)*(TILERESIZEDHEIGHT*MAXTILEINSETCOL));
		
	//	System.out.println("Y: ( SET: "+setinfo+", TILE: "+tileinfo+") -> "+"( SET: "+specialY+", TILE: "+regY+") = "+(regY+specialY));
		
		return regY;
	}
	
	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight, double xScale, double yScale) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage((int)(dWidth*xScale), (int)(dHeight*yScale), imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
		//	graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		//	graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		//	graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(imageToScale, 0, 0, (int)(dWidth*xScale), (int)(dHeight*yScale), null);
            graphics2D.dispose();
        }
        return scaledImage;
    }

	public void writeMapFile(){
        FileWriter fw = null;
        try {
			File archivo = new File("map1.txt");
            fw = new FileWriter(archivo.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
			
            for(int i = 0; i < MAPROWTILES; i++){
				for(int j = 0; j < MAPROWTILES; j++){
					String info[] = new String[4];
					
					info[0]=Integer.toHexString(setT[i][j]);
					info[1]=Integer.toHexString(tile[i][j]);
					info[2]=Integer.toHexString(setO[i][j]);
					info[3]=Integer.toHexString(obj[i][j]);
					
					for (int k = 0; k < info.length; k++) {
						if (info[k].length()==1){
							info[k]="0"+info[k];
						}
						bw.write(info[k]);
						if (k!=(info.length-1)){
							bw.write("-");
						}
					}
					bw.write(",");
				}
				bw.newLine();
            }
            bw.close();
            
        } catch (IOException ex) {
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
            }
        }
	}
	
	public void loadMapFile(){
		
		List<String> readMapInfo = null;
		try {
			File archivo = new File("map1.txt");
			readMapInfo = Files.readAllLines(archivo.toPath());
			
			for (int i = 0; i < readMapInfo.size(); i++) {
				String[] thisline = readMapInfo.get(i).split(",");
				for (int j = 0; j < thisline.length; j++) {
					String[] tileInfo = thisline[j].split("-");
					for (int k = 0; k < tileInfo.length; k++) {
						int thisInfo = Integer.parseInt( tileInfo[k], 16);
						
						switch(k){
							case 0:
								setT[i][j] = thisInfo;
							break;
							case 1:
								tile[i][j] = thisInfo;
							break;
							case 2:
								setO[i][j] = thisInfo;
							break;
							case 3:
								obj[i][j] = thisInfo;
							break;
						}
					}
				}
			}
			refresh();
		} catch (IOException ex) {
		}
		
	}
	
	public void refresh(){
		int[][] setInfo=setT;
		int[][] tileInfo=tile;
		ImageIcon[][] imageInfo=tileGridImage;
		JLabel[][] labelInfo=tileGridLabel;
		for (int i = 0; i < MAPROWTILES; i++) {
			for (int j = 0; j < MAPROWTILES; j++) {
				
				int xTile;
				int yTile;
				xTile = getTileX(setInfo[i][j],tileInfo[i][j]);
				yTile = getTileY(setInfo[i][j],tileInfo[i][j]);
				
				imageInfo[i][j]= new ImageIcon(tileset.getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
		setInfo=setO;
		tileInfo=obj;
		imageInfo=objGridImage;
		labelInfo=objGridLabel;
		for (int i = 0; i < MAPROWTILES; i++) {
			for (int j = 0; j < MAPROWTILES; j++) {
				
				int xTile;
				int yTile;
				xTile = getObjX(setInfo[i][j],tileInfo[i][j]);
				yTile = getObjY(setInfo[i][j],tileInfo[i][j]);
				
				imageInfo[i][j]= new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILERESIZEDWIDTH, TILERESIZEDHEIGHT));
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}
	
	public int[] getPos(int x, int y){
		int[] pos = new int[2];
		
		for (int i = 0; i < MAPROWTILES; i++) {
			for (int j = 0; j < MAPROWTILES; j++) {
				
			//	System.out.println("X: "+ gridLabel[i][j].getX()+"-"+x+"-"+(gridLabel[i][j].getX()+gridLabel[i][j].getWidth()) );
				if (tileGridLabel[i][j].getX()<=x && x<=(tileGridLabel[i][j].getX()+tileGridLabel[i][j].getWidth())){
					pos[0] = i;
				}
				
			//	System.out.println("Y: "+ gridLabel[i][j].getY()+"-"+y+"-"+(gridLabel[i][j].getY()+gridLabel[i][j].getHeight()) );
				if (tileGridLabel[i][j].getY()<=y && y<=(tileGridLabel[i][j].getY()+tileGridLabel[i][j].getHeight())){
					pos[1] = j;
					
				}
				
			}
		}
		
		return pos;
	}
	
	public void clearHighlight(){
		for (int i = 0; i < MAPROWTILES; i++) {
			for (int j = 0; j < MAPROWTILES; j++) {
				int xPos = STARTX+(i*TILERESIZEDWIDTH);
				int yPos = STARTY+(j*TILERESIZEDHEIGHT);
				tileGridLabel[i][j].setBounds(xPos, yPos, TILERESIZEDHEIGHT, TILERESIZEDWIDTH);
			}
		}
	}
	
	public void highlight(int xParam, int yParam){
		clearHighlight();
		int thisStartX, thisStartY, thisEndX, thisEndY;
		if (xStart<xParam){
			thisStartX = xStart;
			thisEndX = xParam;
		}else{
			thisEndX = xStart;
			thisStartX = xParam;
		}
		if (yStart<yParam){
			thisStartY = yStart;
			thisEndY = yParam;
		}else{
			thisEndY = yStart;
			thisStartY = yParam;
		}
		for (int i = thisStartX; i <= thisEndX; i++) {
			for (int j = thisStartY; j <= thisEndY; j++) {
				int xPos = STARTX+(i*TILERESIZEDWIDTH);
				int yPos = STARTY+(j*TILERESIZEDHEIGHT);
				tileGridLabel[i][j].setBounds(xPos+2, yPos+2, TILERESIZEDHEIGHT-4, TILERESIZEDWIDTH-4);
			}
		}
		dimDisplay.setText("("+(thisEndX+1-thisStartX)+"x"+(thisEndY+1-thisStartY)+")");
	}
	
	//<editor-fold defaultstate="collapsed" desc="Mouse Overrides">
	@Override
	public void mouseClicked(MouseEvent e) {
	//	modTile(e);
		
		int[] receive = caller(e);
		xStart = receive[0];
		yStart = receive[1];
		xEnd = receive[0];
		yEnd = receive[1];
		highlight(xEnd,yEnd);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int[] receive = caller(e);
		xStart = receive[0];
		yStart = receive[1];
		isSelecting = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int[] receive = caller(e);
		
		xEnd = receive[0];
		yEnd = receive[1];
		
		if (xEnd<xStart){
			int temp=xEnd;
			xEnd=xStart;
			xStart=temp;
		}
		if (yEnd<yStart){
			int temp=yEnd;
			yEnd=yStart;
			yStart=temp;
		}
		
		xCoordsDisplay.setText("START: ("+xStart+","+yStart+")");
		yCoordsDisplay.setText("END: ("+xEnd+","+yEnd+")");
		
		isSelecting = false;
		//highlight();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if (isSelecting){
		int[] receive = caller(e);
		/*
		xEnd = receive[0];
		yEnd = receive[1];
		
		if (xEnd<xStart){
			int temp=xEnd;
			xEnd=xStart;
			xStart=temp;
		}
		if (yEnd<yStart){
			int temp=yEnd;
			yEnd=yStart;
			yStart=temp;
		}
		
		xCoordsDisplay.setText("START: ("+xStart+","+yStart+")");
		yCoordsDisplay.setText("END: ("+xEnd+","+yEnd+")");
		*/
		highlight(receive[0],receive[1]);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (isSelecting){
		int[] receive = caller(e);
		/*
		xEnd = receive[0];
		yEnd = receive[1];
		
		if (xEnd<xStart){
			int temp=xEnd;
			xEnd=xStart;
			xStart=temp;
		}
		if (yEnd<yStart){
			int temp=yEnd;
			yEnd=yStart;
			yStart=temp;
		}
		
		xCoordsDisplay.setText("START: ("+xStart+","+yStart+")");
		yCoordsDisplay.setText("END: ("+xEnd+","+yEnd+")");
		*/
		highlight(receive[0],receive[1]);
		}
	}
	
	public int[] caller(MouseEvent e){
		return getPos(e.getComponent().getX()+e.getX(),e.getComponent().getY()+e.getY());
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Window Overrides">
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	//</editor-fold>
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
			clearHighlight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
