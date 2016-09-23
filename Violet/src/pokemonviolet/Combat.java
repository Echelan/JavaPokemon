/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Combat {

	private final Player player;
	private final Trainer enemy;
	private Pokemon currentPlayerPokemon;
	private Pokemon currentEnemyPokemon;
	private BufferedImage display;
	private ArrayList<String> displayTextQueue;
	private int maxX = 480, maxY = 320;
	private boolean waitingAction;
	private int optionsMain;
	private int optionsMoves;
	private static int SPRITE_WIDTH = 80*2;
	private static int SPRITE_HEIGHT = 80*2;
	
	public Combat(Player player, Trainer enemy) {
		this.player = player;
		this.enemy = enemy;
		this.display = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_RGB);
		this.waitingAction = false;
		this.optionsMoves = -1;
		this.optionsMain = -1;
		
		
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" appeared!");
		
		refreshDisplayImage();
		
		this.player.setInCombat(true);
		this.player.setSpawnSteps(this.player.roll(1,2,3));
	}
	
	private void refreshDisplayImage(){
		Graphics g = getDisplay().getGraphics();
		g.clearRect(getDisplay().getMinX(), getDisplay().getMinY(), getDisplay().getWidth(), getDisplay().getHeight());
		
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/uibkgdisplay.png")), 0, 0, maxX, maxY, null);
		}catch(IOException ex){
		}
		
		g.setFont(new Font("Arial",0,18));
		g.drawString(displayTextQueue.get(0), 30, maxY-60);
		
		try{
			String enemyId = ""+currentEnemyPokemon.getId();
			if (Integer.parseInt(enemyId)<10){
				enemyId = "0"+enemyId;
				if (Integer.parseInt(enemyId)<100){
					enemyId = "0"+enemyId;
				}
			}
			
			g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/Male Front Sprites/"+enemyId+".png")), maxX-50-SPRITE_WIDTH, 18, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			
			g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/Male Back Sprites/"+currentPlayerPokemon.getId()+".png")), 65, 90, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			
		}catch(IOException ex){
			System.err.println(ex);
		}
		try{
			if (waitingAction){
				if (optionsMoves == -1){
					int uiW = (120*2), uiH = (48*2);
					g.drawImage(ImageIO.read(new File("assets/combat/uioptionsdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);
					
					g.setColor(Color.red);
					g.fillRect(maxX-230+(int)(Math.floor(optionsMain/2)*110), maxY-70+(int)(Math.floor(optionsMain%2)*30),20,20);
				}else{
					int uiW = (240*2), uiH = (48*2);
					g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);
					
					g.setColor(Color.red);
					g.fillRect(10+(int)(Math.floor(optionsMoves/2)*140), maxY-75+(int)(Math.floor(optionsMoves%2)*40),20,20);
					for (int i = 0; i < 4; i++) {
						String moveName;
						if (i < currentPlayerPokemon.getNumMoves()){
							moveName = currentPlayerPokemon.getMoves()[i].getNameDisplay();
							if (moveName == null || moveName == ""){
								moveName = "--";
							}
						}else{
							moveName = "--";
						}
						g.setColor(Color.black);
						g.drawString(moveName, 30+(int)(Math.floor(i/2)*140), maxY-60+(int)(Math.floor(i%2)*40));
					}
				}
			}
		}catch(IOException ex){
			
		}
	}

	public void accept(){
		if (waitingAction){
			if (optionsMoves == -1){
				if (optionsMain == 0){
					optionsMoves = 0;
				}else if (optionsMain == 3){
					this.dispose();
				}
			}else{
				waitingAction = false;
			}
		}else{
			displayTextQueue.remove(0);
			if (displayTextQueue.size()==0){
				waitingAction = true;
				displayTextQueue.add("What will "+currentPlayerPokemon.getNameNick()+ " do?");
				optionsMain = 0;
			}
		}
		refreshDisplayImage();
	}
	
	public void cancel(){
		if (waitingAction){
			if (optionsMoves == -1){
				optionsMain = 3;
			}else{
				optionsMoves = -1;
			}
		}
		
		refreshDisplayImage();
	}
	
	public void move(String dir){
		if (waitingAction){
			int x,y;
			if (optionsMoves == -1){
				y = (optionsMain%2);
				x = (int)Math.floor(optionsMain/2);
				
			}else{
				y = (optionsMoves%2);
				x = (int)Math.floor(optionsMoves/2);
			}
			switch(dir){
				case "LEFT":
					x = x-1;
				break;
				case "UP":
					y = y-1;
				break;
				case "RIGHT":
					x = x+1;
				break;
				case "DOWN":
					y = y+1;
				break;
			}

			if (x<0){
				x = 1;
			}else if (x>1){
				x = 0;
			}
			
			if (y<0){
				y = 1;
			}else if (y>1){
				y = 0;
			}

			if (optionsMoves == -1){
				optionsMain = (x*2)+y;
			}else{
				optionsMoves = (x*2)+y;
			}
			refreshDisplayImage();
		}
	}
	
	/**
	 * @return the display
	 */
	public BufferedImage getDisplay() {
		return display;
	}
	
	private void dispose(){
		Game.currentBattle = null;
		this.player.setInCombat(false);
	}
	
}
