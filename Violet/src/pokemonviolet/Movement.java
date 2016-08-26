/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 *
 * @author Andres
 */
public class Movement extends KeyAdapter{
    
    public void keyReleased(KeyEvent key) {switch (key.getKeyCode()) {
		case KeyEvent.VK_UP:
			Game.player.setUp(true);
		break;
		case KeyEvent.VK_DOWN:
			Game.player.setDown(true);
		break;
		case KeyEvent.VK_LEFT:
			Game.player.setLeft(true);
		break;
		case KeyEvent.VK_RIGHT:
			Game.player.setRight(true);
		break;
		}
    }
  
    public void keyPressed(KeyEvent key) {switch (key.getKeyCode()) {
		case KeyEvent.VK_UP:
		   Game.player.setUp(true);
		break;
		case KeyEvent.VK_DOWN:
		   Game.player.setDown(true);
		break;
		case KeyEvent.VK_LEFT:
		   Game.player.setLeft(true);
		break;
		case KeyEvent.VK_RIGHT:
		   Game.player.setRight(true);
		break;
	  }
    }
}
