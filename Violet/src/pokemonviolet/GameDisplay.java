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
		
	int x[],y[],xs,ys;
	boolean xRising[], yRising[];
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
		xs=100;
		ys=100;
		
		x = new int[2];
		y = new int[2];
		xRising = new boolean[2];
		yRising = new boolean[2];
		
		x[0] = 0;
		y[0] = 0;
		xRising[0] = false;
		yRising[0] = false;
		
		x[1] = 800;
		y[1] = 800;
		xRising[1] = true;
		yRising[1] = true;
	}
	
	public void paint(Graphics g){
		getBufferStrategy().show();
	}
	
    @Override
    public void run() {
		createBufferStrategy(2);
        while (true){
			Graphics g = getBufferStrategy().getDrawGraphics();
			
			if (!Game.player.isInCombat()){
				g.drawImage(Game.ALL_MAPS, Game.ALL_MAPS_X, Game.ALL_MAPS_Y, Game.ALL_MAPS_WIDTH, Game.ALL_MAPS_HEIGHT, this);

				g.drawImage(Game.player.getCurFrameImage(), this.getWidth()/2, this.getHeight()/2,(int)(Game.player.SPRITE_X*Game.player.SPRITE_RESIZE),(int)(Game.player.SPRITE_Y*Game.player.SPRITE_RESIZE), this);
			}else{
				g.fillRect(x[0], y[0], xs, ys);
				g.fillRect(x[1], y[1], xs, ys);
				
				if (x[0]>this.getWidth()-xs){
					xRising[0] = false;
				}else if (x[0]<0){
					xRising[0] = true;
				}
				
				if (y[0]>this.getHeight()-ys){
					yRising[0] = false;
				}else if (y[0]<0){
					yRising[0] = true;
				}
				
				if (xRising[0]){
					x[0] = x[0]+30;
				}else{
					x[0] = x[0]-30;
				}
				
				if (yRising[0]){
					y[0] = y[0]+30;
				}else{
					y[0] = y[0]-30;
				}
				
				if (x[1]>this.getWidth()-xs){
					xRising[1] = false;
				}else if (x[1]<0){
					xRising[1] = true;
				}
				
				if (y[1]>this.getHeight()-ys){
					yRising[1] = false;
				}else if (y[1]<0){
					yRising[1] = true;
				}
				
				if (xRising[1]){
					x[1] = x[1]+30;
				}else{
					x[1] = x[1]-30;
				}
				
				if (yRising[1]){
					y[1] = y[1]+30;
				}else{
					y[1] = y[1]-30;
				}
			}
			repaint();
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
