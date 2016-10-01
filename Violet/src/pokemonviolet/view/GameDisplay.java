/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.view;

import java.awt.Canvas;
import java.awt.Graphics;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	String lastState;
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
		lastState="";
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
			if (!Handler.gameState.isEmpty()){
				Graphics g = getBufferStrategy().getDrawGraphics();
			
				if (lastState.compareTo(Handler.gameState.get(Handler.gameState.size()-1).getName())!=0){
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					lastState = Handler.gameState.get(Handler.gameState.size()-1).getName();
				}

				g.drawImage(DisplayParser.displayImage(), 0, 0, this.getWidth(), this.getHeight(), this);

				repaint();
			}
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
