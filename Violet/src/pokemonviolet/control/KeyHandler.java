/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 *
 * @author Andres
 */
public class KeyHandler extends KeyAdapter{
	private String lastPressed = "";
    public void keyReleased(KeyEvent key) {
		boolean changedSomething=false;
		String sendAction = "";
		
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			if (lastPressed.compareTo("UP")==0){
				changedSomething=true;
				sendAction=lastPressed;
			}
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			if (lastPressed.compareTo("UP")==0){
				changedSomething=true;
				sendAction=lastPressed;
			}
		}else if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
			if (lastPressed.compareTo("UP")==0){
				changedSomething=true;
				sendAction=lastPressed;
			}
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
			if (lastPressed.compareTo("UP")==0){
				changedSomething=true;
				sendAction=lastPressed;
			}
		}else if(key.getKeyCode() == KeyEvent.VK_K){
			changedSomething=true;
			sendAction="B";
		}else if(key.getKeyCode() == KeyEvent.VK_J){
			changedSomething=true;
			sendAction="A";
		}

		if (changedSomething){
			pokemonviolet.model.Game.receiveKeyAction(sendAction,"RELEASE");
		}
    }
  
	
			
	
    public void keyPressed(KeyEvent key) {
		boolean changedSomething=false;
		String sendAction = "";
		
		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			lastPressed="UP";
			changedSomething=true;
			sendAction=lastPressed;
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			lastPressed="DOWN";
			changedSomething=true;
			sendAction=lastPressed;
		}else if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
			lastPressed="LEFT";
			changedSomething=true;
			sendAction=lastPressed;
		}else if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
			lastPressed="RIGHT";
			changedSomething=true;
			sendAction=lastPressed;
		}else if(key.getKeyCode() == KeyEvent.VK_K){
			changedSomething=true;
			sendAction="B";
		}else if(key.getKeyCode() == KeyEvent.VK_J){
			changedSomething=true;
			sendAction="A";
		}

		if (changedSomething){
			pokemonviolet.model.Game.receiveKeyAction(sendAction,"PRESS");
		}
    }
}
