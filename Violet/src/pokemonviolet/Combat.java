/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static pokemonviolet.Game.ALL_MAPS_HEIGHT;
import static pokemonviolet.Game.ALL_MAPS_WIDTH;

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
	
	public Combat(Player player, Trainer enemy) {
		this.player = player;
		this.enemy = enemy;
		this.display = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_RGB);
		
		this.currentEnemyPokemon = enemy.getTeam()[enemy.getCurrentPokemon()];
		this.currentEnemyPokemon = player.getTeam()[player.getCurrentPokemon()];
		
		displayTextQueue = new ArrayList<String>();
		displayTextQueue.add("A wild "+currentEnemyPokemon.getNameNick()+" appeared!");
		
		refreshDisplayImage();
		
		this.player.setInCombat(true);
	}
	
	private void refreshDisplayImage(){
		Graphics g = getDisplay().getGraphics();
		try{
			g.drawImage(ImageIO.read(new File("assets/combat/uibkgdisplay.png")), 0, 0, maxX, maxY, null);
		}catch(IOException ex){
		}
		g.setFont(new Font("Arial",0,18));
		g.drawString(displayTextQueue.get(0), 30, maxY-70);
	}

	/**
	 * @return the display
	 */
	public BufferedImage getDisplay() {
		return display;
	}
	
	
}
