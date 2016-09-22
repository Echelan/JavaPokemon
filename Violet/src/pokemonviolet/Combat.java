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
	private static int SPRITE_WIDTH;
	private static int SPRITE_HEIGHT;
	
	public Combat(Player player, Trainer enemy) {
		this.player = player;
		this.enemy = enemy;
		this.display = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_RGB);
		this.waitingAction = false;
		this.optionsMoves = 0;
		this.optionsMain = 0;
		
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentPlayerPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" appeared!");
		
		refreshDisplayImage();
		
		this.player.setInCombat(true);
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
			
			g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/Male Front Sprites/"+enemyId+".png")), 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			
			g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/Male Back Sprites/"+currentPlayerPokemon.getId()+".png")), 100, 100, SPRITE_WIDTH, SPRITE_HEIGHT, null);
			
			g.drawImage(ImageIO.read(new File("assets/Pokemon Sprites/Male Back Sprites/15.png")), 100, 100, SPRITE_WIDTH, SPRITE_HEIGHT, null);
		}catch(IOException ex){
			System.err.println(ex);
		}
		try{
			if (waitingAction){
				if (optionsMoves == 0){
					int uiW = (120*2), uiH = (48*2);
					g.drawImage(ImageIO.read(new File("assets/combat/uioptionsdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);
					
					g.setColor(Color.red);
					g.fillRect(maxX-60+(int)(Math.floor(optionsMain/2)*30), maxY-60+(int)(Math.floor(optionsMain%2)*30),20,20);
				}else{
					int uiW = (240*2), uiH = (48*2);
					g.drawImage(ImageIO.read(new File("assets/combat/uimovesdisplay.png")), maxX-uiW, maxY-uiH, uiW, uiH, null);
					
					g.setColor(Color.red);
					g.fillRect(30+(int)(Math.floor(optionsMoves/2)*150), maxY-60+(int)(Math.floor(optionsMoves%2)*30),20,20);
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
						g.drawString(moveName, 30+(int)(Math.floor(i/2)*140), maxY-60+(int)(Math.floor(i%2)*30));
					}
				}
			}
		}catch(IOException ex){
			
		}
	}

	public void accept(){
		if (waitingAction){
			if (optionsMoves == 0){
				optionsMoves = 1;
			}else{
				System.out.println(currentPlayerPokemon.getNameNick()+ " used "+currentPlayerPokemon.getMoves()[optionsMoves].getNameDisplay());
			}
		}else{
			displayTextQueue.remove(0);
			if (displayTextQueue.size()==0){
				waitingAction = true;
				displayTextQueue.add("What will "+currentPlayerPokemon.getNameNick()+ " do?");
				optionsMain = 1;
			}
		}
		refreshDisplayImage();
	}
	
	public void move(int dir){
		if (waitingAction){
			if (optionsMoves == 0){
				optionsMain = optionsMain+dir;
				if (optionsMain<1){
					optionsMain = optionsMain+4;
				}else if (optionsMain>4){
					optionsMain=optionsMain-4;
				}
			}else{
				optionsMoves = optionsMoves+dir;
				if (optionsMoves<1){
					optionsMoves = optionsMoves+4;
				}else if (optionsMoves>4){
					optionsMoves=optionsMoves-4;
				}
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
	
	
}
