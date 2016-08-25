/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {

	BufferedImage playerSprite;
	
        int x = 0;
        int y = 0;
           boolean xUp = false;
           boolean yUp = false;
	/*
	public GameDisplay() {
            
	}
	*/
	public void paint(Graphics g){
		//g.drawImage(playerSprite, 100, 100, this);
                    //x = x+5;
                    if (yUp){
                        y = y-1;
                    }else{
                        y=y+1;
                    }
                    if (xUp){
                        x = x-1;
                    }else{
                        x=x+1;
                    }
                    if (y>getHeight()-50){
                        yUp = true;
                    }else if (y<0){
                        yUp = false;
                    }
                    if (x>getWidth()-50){
                        xUp = true;
                    }else if (x<0){
                        xUp = false;
                    }
                    g.setColor(Color.YELLOW);
                    g.fillOval(x,y,50,50);
                
        }

    @Override
    public void run() {
        while (true){
           repaint();
           
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            //    Logger.getLogger(GameDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
}
