/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 *
 * @author Andres
 */
public class Movement extends KeyAdapter{
		
    public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			if (Game.player.getDirection().compareTo("UP")==0){
				Game.player.setDirection("");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			if (Game.player.getDirection().compareTo("DOWN")==0){
				Game.player.setDirection("");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
			if (Game.player.getDirection().compareTo("LEFT")==0){
				Game.player.setDirection("");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
			if (Game.player.getDirection().compareTo("RIGHT")==0){
				Game.player.setDirection("");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_J){
			Game.player.setRunning(false);
		}
    }
  
    public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			if (Game.player.getvDirection().compareTo("")==0){
				Game.player.setvDirection("UP");
				Game.player.setDirection("UP");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			if (Game.player.getvDirection().compareTo("")==0){
				Game.player.setvDirection("DOWN");
				Game.player.setDirection("DOWN");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
			if (Game.player.getvDirection().compareTo("")==0){
				Game.player.setvDirection("LEFT");
				Game.player.setDirection("LEFT");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
			if (Game.player.getvDirection().compareTo("")==0){
				Game.player.setvDirection("RIGHT");
				Game.player.setDirection("RIGHT");
			}
		}else if(key.getKeyCode() == KeyEvent.VK_J){
			Game.player.setRunning(true);
		}
    }
}
