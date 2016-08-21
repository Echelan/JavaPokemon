/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas {

	BufferedImage playerSprite;
	
	
	public GameDisplay() {
		try {
			playerSprite = ImageIO.read(new File("player.png"));
		} catch (IOException ex) {
			//Logger.getLogger(GameDisplay.class.getName()).log(Level.SEVERE, null, ex);
		}
		//super(gc);
	}
	
	public void paint(Graphics g){
		g.drawImage(playerSprite, 100, 100, this);
		
	}
	
}
