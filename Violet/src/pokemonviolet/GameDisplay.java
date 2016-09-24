/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	boolean combatBasics;
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
		combatBasics = false;
	}
	
	public void paint(Graphics g){
		try{
			getBufferStrategy().show();
		}catch(Exception ex){
			
		}
	}
	
    @Override
    public void run() {
		createBufferStrategy(2);
        while (true){
			Graphics g = getBufferStrategy().getDrawGraphics();
			
			if (!Game.player.isInCombat()){
				if (combatBasics){
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					combatBasics = false;
				}
				
				g.drawImage(Game.ALL_MAPS, Game.ALL_MAPS_X, Game.ALL_MAPS_Y, Game.ALL_MAPS_WIDTH, Game.ALL_MAPS_HEIGHT, this);

				g.drawImage(Game.player.getCurFrameImage(), this.getWidth()/2, this.getHeight()/2,(int)(Game.player.SPRITE_X*Game.player.SPRITE_RESIZE),(int)(Game.player.SPRITE_Y*Game.player.SPRITE_RESIZE), this);
			}else{
				if (!combatBasics){
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					combatBasics = true;
				}
				g.drawImage(Game.currentBattle.getDisplay(),0,0,this.getWidth(),this.getHeight(),this);
			}
			repaint();
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
